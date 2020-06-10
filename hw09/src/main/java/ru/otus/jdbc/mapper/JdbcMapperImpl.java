package ru.otus.jdbc.mapper;

import lombok.extern.slf4j.Slf4j;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.interfaces.EntityClassMetaData;
import ru.otus.jdbc.mapper.interfaces.EntitySQLMetaData;
import ru.otus.jdbc.mapper.interfaces.JdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class JdbcMapperImpl<T> implements JdbcMapper<T> {

    private final DbExecutorImpl<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;

    public JdbcMapperImpl(SessionManagerJdbc sessionManager) {
        dbExecutor = new DbExecutorImpl<>();
        this.sessionManager = sessionManager;
    }

    @Override
    public void insert(T objectData) {
        EntityClassMetaDataImpl<T> entityClassMetaData = new EntityClassMetaDataImpl<>(objectData.getClass());
        EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl<>(entityClassMetaData);
        String sqlInsert = entitySQLMetaData.getInsertSql();
        sessionManager.beginSession();
        try {
            dbExecutor.executeInsert(getConnection(), sqlInsert, getSqlParamsFromObject(objectData));
            sessionManager.commitSession();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            sessionManager.rollbackSession();
        }

    }

    @Override
    public void update(T objectData) {
        EntityClassMetaDataImpl<T> entityClassMetaData = new EntityClassMetaDataImpl<>(objectData.getClass());
        EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl<>(entityClassMetaData);
        Field idField = entityClassMetaData.getIdField();
        idField.setAccessible(true);
        Object id = null;
        try {
            id = idField.get(objectData);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        sessionManager.beginSession();
            List<Object> params = getSqlParamsFromObject(objectData);
            params.add(id);
            try {
                dbExecutor.executeInsert(getConnection(), entitySQLMetaData.getUpdateSql(), params);
                sessionManager.commitSession();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
    }

    @Override
    public void insertOrUpdate(T objectData) {
        EntityClassMetaDataImpl<T> entityClassMetaData = new EntityClassMetaDataImpl<>(objectData.getClass());
        Field idField = entityClassMetaData.getIdField();
        idField.setAccessible(true);
        long id = -1;

        try {
            id = (long) idField.get(objectData);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (id == -1) {
            return;
        }

        T findObject = findById(id, (Class<T>) objectData.getClass());
        if (findObject == null) {
            insert(objectData);
        }

        update(objectData);
    }

    @Override
    public T findById(long id, Class<T> clazz){
        EntityClassMetaDataImpl<T> entityClassMetaData = new EntityClassMetaDataImpl<>(clazz);
        EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl<>(entityClassMetaData);
        sessionManager.beginSession();
        try {
            Optional<T> findObject = dbExecutor.executeSelect(getConnection(), entitySQLMetaData.getSelectByIdSql(), id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return newObject(resultSet, clazz);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                return null;
            });
            return findObject.orElse(null);
        } catch (SQLException throwables) {
            sessionManager.rollbackSession();
            throwables.printStackTrace();
        }

        return null;
    }

    private T newObject(ResultSet resultSet, Class<T> clazz) {
        EntityClassMetaData<T> entityClassMetaData = new EntityClassMetaDataImpl<>(clazz);
        Constructor<T> constructor = entityClassMetaData.getConstructor();
        try {
            T newObject = constructor.newInstance();
            entityClassMetaData.getAllFields().forEach(field -> {
                try {
                    field.setAccessible(true);
                    field.set(newObject, resultSet.getObject(field.getName()));
                } catch (IllegalAccessException | SQLException e) {
                    e.printStackTrace();
                }
            });
            return newObject;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    private List<Object> getSqlParamsFromObject(T objectData) {
        EntityClassMetaData<T> entityClassMetaData = new EntityClassMetaDataImpl<>(objectData.getClass());
        return entityClassMetaData.getFieldsWithoutId().stream()
                .peek(field -> field.setAccessible(true))
                .map(field -> {
                    try {
                        return field.get(objectData);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    return null;
                }).collect(Collectors.toList());
    }

}

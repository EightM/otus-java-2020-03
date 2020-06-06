package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.interfaces.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaDataImpl<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(Class<T> clazz) {
        entityClassMetaData = new EntityClassMetaDataImpl<>(clazz);
    }

    @Override
    public String getSelectAllSql() {
        String tableName = entityClassMetaData.getName();
        return String.format("SELECT * FROM %s", tableName);
    }

    @Override
    public String getSelectByIdSql() {
        String tableName = entityClassMetaData.getName();
        Field id = entityClassMetaData.getIdField();
        return String.format("SELECT * FROM %s WHERE %s = ?", tableName, id.getName());
    }

    @Override
    public String getInsertSql() {
        String tableName = entityClassMetaData.getName();
        String fieldsWithoutId = entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));
        return String.format("INSERT INTO %s(%s) values (?)", tableName, fieldsWithoutId);

    }

    @Override
    public String getUpdateSql() {
//        String tableName = entityClassMetaData.getName();
//        String fieldsWithoutId = entityClassMetaData.getFieldsWithoutId().stream()
//                .map(Field::getName)
//                .collect(Collectors.joining(","));
//        return String.format("UPDATE %s ")
        return "";
    }
}

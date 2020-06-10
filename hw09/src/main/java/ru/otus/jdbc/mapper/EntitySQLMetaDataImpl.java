package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.interfaces.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {

    private final EntityClassMetaDataImpl<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaDataImpl<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
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
        String paramList = IntStream.range(0, entityClassMetaData.getFieldsWithoutId().size())
                .mapToObj(i -> "?")
                .collect(Collectors.joining(","));
        return String.format("insert into %s(%s) values (%s)", tableName, fieldsWithoutId, paramList);

    }

    @Override
    public String getUpdateSql() {
        String tableName = entityClassMetaData.getName();
        String updateFields = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName() + " = ?")
                .collect(Collectors.joining(","));
        String idName = entityClassMetaData.getIdField().getName();
        return String.format("UPDATE %s SET %s WHERE %s = ?", tableName, updateFields, idName);
    }
}

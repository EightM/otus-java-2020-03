package ru.otus.jdbc.mapper;

import ru.otus.jdbc.mapper.annotations.Id;
import ru.otus.jdbc.mapper.interfaces.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;
    private final Map<String, List<Field>> cache = new HashMap<>();
    private static final String ALL_FIELDS = "allFields";

    public EntityClassMetaDataImpl(Class<?> clazz) {
        this.clazz = (Class<T>) clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }

    @Override
    public Field getIdField() {
        cache.putIfAbsent(ALL_FIELDS, Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
        return cache.get(ALL_FIELDS).stream()
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst().orElseThrow();
    }

    @Override
    public List<Field> getAllFields() {
        cache.putIfAbsent(ALL_FIELDS, Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
        return cache.get(ALL_FIELDS);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        cache.putIfAbsent("fieldsWithoutId", Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .collect(Collectors.toList()));
        return cache.get("fieldsWithoutId");
    }
}

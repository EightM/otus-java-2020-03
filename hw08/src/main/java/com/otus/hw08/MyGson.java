package com.otus.hw08;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;

public class MyGson {
    public <T> String toJson(T t) {

        if (Objects.isNull(t)) {
            return null;
        }

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        var objectClass = t.getClass();
        var fields = objectClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                var fieldValue = field.get(t);
                JsonValue jsonValue = createJsonValue(fieldValue, field.getType());
                jsonObjectBuilder.add(field.getName(), jsonValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return jsonObjectBuilder.build().toString();
    }

    private JsonValue createJsonValue(Object fieldValue, Class<?> fieldType) {
        if (fieldType.isPrimitive()) {
            return createPrimitiveValue(fieldValue, fieldType);
        } else if (fieldType.isArray()) {
            return createArrayValue(fieldValue);
        } else if (fieldType.equals(String.class)) {
            return Json.createValue(fieldValue.toString());
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            Collection<?> collection = (Collection<?>) fieldValue;
            return createArrayValue(collection.toArray());
        }

        return JsonValue.NULL;
    }

    private JsonValue createArrayValue(Object fieldValue) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        int length = Array.getLength(fieldValue);
        for (int i = 0; i < length; i++) {
            Object value = Array.get(fieldValue, i);
            arrayBuilder.add(createJsonValue(value, value.getClass()));
        }
        return arrayBuilder.build();
    }

    private JsonValue createPrimitiveValue(Object fieldValue, Class<?> fieldType) {
        if (fieldType.equals(int.class)) {
            return Json.createValue((int) fieldValue);
        } else if (fieldType.equals(long.class)) {
            return Json.createValue((long) fieldValue);
        } else if (fieldType.equals(short.class)) {
            return Json.createValue((short) fieldValue);
        } else if (fieldType.equals(byte.class)) {
            return Json.createValue((byte) fieldValue);
        } else if (fieldType.equals(char.class)) {
            return Json.createValue((char) fieldValue);
        } else if (fieldType.equals(float.class)) {
            return Json.createValue((float) fieldValue);
        } else if (fieldType.equals(double.class)) {
            return Json.createValue((double) fieldValue);
        } else {
            boolean value = (boolean) fieldValue;
            return value ? JsonValue.TRUE : JsonValue.FALSE;
        }
    }

}

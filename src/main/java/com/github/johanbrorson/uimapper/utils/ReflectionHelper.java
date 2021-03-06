package com.github.johanbrorson.uimapper.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionHelper {

  public static Iterable<Field> getDeclaredFields(Class<?> clazz) {
    final List<Field> allFields = new ArrayList<>();
    final Field[] fields = clazz.getDeclaredFields();
    allFields.addAll(Arrays.asList(fields));
    return allFields;
  }

  public static void setField(Object obj,Field field, Object value) throws IllegalArgumentException, IllegalAccessException {
    final boolean wasAccessible = field.isAccessible();
    field.setAccessible(true);
    field.set(obj, value);
    field.setAccessible(wasAccessible);
  }
}

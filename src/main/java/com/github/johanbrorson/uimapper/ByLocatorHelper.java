package com.github.johanbrorson.uimapper;

import com.github.johanbrorson.uimapper.annotation.AnnotationHelper;
import com.github.johanbrorson.uimapper.utils.ReflectionHelper;

import org.openqa.selenium.By;

import java.io.IOException;
import java.lang.reflect.Field;

public class ByLocatorHelper {

  public static <T> void initClassVariables(Class<?> clazz) throws Exception {
    final Iterable<Field> fields = ReflectionHelper.getDeclaredFields(clazz);
    for (final Field field : fields) {
      if (!AnnotationHelper.isByLocatorAnnotationPresent(field)) {
        continue;
      }
      final By by = getByForField(clazz, field);
      ReflectionHelper.setField(null,field, by);
    }
  }

  public static <T> void initInstanceVariables(Object object) throws Exception {
    final Iterable<Field> fields = ReflectionHelper.getDeclaredFields(object.getClass());
    for (final Field field : fields) {
      if (!AnnotationHelper.isByLocatorAnnotationPresent(field)) {
        continue;
      }
      final By by = getByForField(object.getClass(), field);
      ReflectionHelper.setField(object,field, by);
    }
  }

  private static By getByForField(Class<?> clazz, Field field) throws IOException {
    final UIMapper map = new UIMapper(clazz);
    final String name = AnnotationHelper.getByLocatorNameAnnotation(field);
    return map.getLocator(name).getBy();
  }
}

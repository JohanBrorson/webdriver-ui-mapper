package com.github.johanbrorson.uimapper.annotation;

import java.lang.reflect.Field;

import org.openqa.selenium.By;

public class AnnotationHelper {

  public static String getFilePathAnnotation(Class<?> clazz) {
    if (isLocatorFileAnnotationPresent(clazz)) {
      LocatorFile locatorFile = getLocatorFileAnnotation(clazz);
      return locatorFile.filePath();
    } else {
      return "";
    }
  }

  public static String getByLocatorNameAnnotation(Field field) {
    final ByLocator annotation = getByLocatorAnnotation(field);
    return annotation.name().isEmpty() ? field.getName() : annotation.name();
  }

  public static boolean isByLocatorAnnotationPresent(Field field) {
    if (!field.getType().equals(By.class)) {
      return false;
    }

    return field.isAnnotationPresent(ByLocator.class);
  }

  private static boolean isLocatorFileAnnotationPresent(Class<?> clazz) {
    return clazz.isAnnotationPresent(LocatorFile.class);
  }

  private static LocatorFile getLocatorFileAnnotation(Class<?> clazz) {
    return clazz.getAnnotation(LocatorFile.class);
  }

  private static ByLocator getByLocatorAnnotation(Field field) {
    return field.getAnnotation(ByLocator.class);
  }
}

package com.github.johanbrorson.uimapper.annotation;

public class AnnotationHelper {

  public static String getFilePathAnnotation(Class<?> clazz) {
    if (isLocatorFileAnnotationPresent(clazz)) {
      LocatorFile locatorFile = getLocatorFileAnnotation(clazz);
      return locatorFile.filePath();
    } else {
      return "";
    }
  }

  private static boolean isLocatorFileAnnotationPresent(Class<?> clazz) {
    return clazz.isAnnotationPresent(LocatorFile.class);
  }

  private static LocatorFile getLocatorFileAnnotation(Class<?> clazz) {
    return clazz.getAnnotation(LocatorFile.class);
  }

}

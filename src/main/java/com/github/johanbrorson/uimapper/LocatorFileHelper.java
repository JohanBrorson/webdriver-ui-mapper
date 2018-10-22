package com.github.johanbrorson.uimapper;

import com.github.johanbrorson.uimapper.exceptions.LocatorFileIOException;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LocatorFileHelper {

  public static String getContent(String filePath) {
    InputStream is = getInputStream(filePath);
    return getContent(is);
  }

  public static String getContent(File file) {
    InputStream is = getFileAsStream(file);
    return getContent(is);
  }

  private static String getContent(InputStream is) {
    try {
      return IOUtils.toString(is, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new LocatorFileIOException(e);
    }
  }

  private static InputStream getInputStream(String filePath) {
    InputStream is;
    File file = new File(filePath);
    if (file.exists()) {
      is = getFileAsStream(file);
    } else {
      is = getResourceAsStream(filePath);
    }
    return is;
  }

  private static InputStream getFileAsStream(File file) {
    try {
      return new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new LocatorFileIOException(e);
    }
  }

  private static InputStream getResourceAsStream(String resourceName) {
    InputStream is = UIMapper.class.getClassLoader().getResourceAsStream(resourceName);
    if (is == null) {
      throw new LocatorFileIOException("Could not find file: " + resourceName);
    }
    return is;
  }

}

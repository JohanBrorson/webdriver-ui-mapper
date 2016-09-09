package com.github.johanbrorson.uimapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.johanbrorson.uimapper.annotation.AnnotationHelper;
import com.github.johanbrorson.uimapper.exceptions.LocatorNotFoundException;

public class UIMapper {
  private final ObjectMapper mapper = new ObjectMapper();
  private String content;

  public UIMapper(Class<?> clazz) {
    String filePath = AnnotationHelper.getFilePathAnnotation(clazz);
    content = LocatorFileHelper.getContent(filePath);
  }

  public UIMapper(Object object) {
    String filePath = AnnotationHelper.getFilePathAnnotation(object.getClass());
    content = LocatorFileHelper.getContent(filePath);
  }

  public UIMapper(File json) {
    content = LocatorFileHelper.getContent(json);
  }

  public Locator getLocator(String name) throws IOException {
    JsonParser jsonParser = getJsonParser();
    jsonParser.nextToken();
    while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
      Locator locator = mapper.readValue(jsonParser, Locator.class);
      if (name.equals(locator.getName())) {
        return locator;
      }
    }

    throw new LocatorNotFoundException("The locator " + name + " was not found");
  }

  public List<Locator> getLocators() throws IOException {
    List<Locator> locators = new ArrayList<Locator>();
    JsonParser jsonParser = getJsonParser();
    jsonParser.nextToken();
    while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
      Locator locator = mapper.readValue(jsonParser, Locator.class);
      locators.add(locator);
    }

    return locators;
  }

  private JsonParser getJsonParser() throws IOException {
    JsonFactory jsonFactory = new JsonFactory();
    return jsonFactory.createParser(content);
  }

}

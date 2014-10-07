package com.github.johanbrorson.uimapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.johanbrorson.uimapper.annotation.AnnotationHelper;
import com.github.johanbrorson.uimapper.exceptions.LocatorNotFoundException;

public class UIMapper {
  private final ObjectMapper mapper = new ObjectMapper();
  private final File json;

  public UIMapper(Class<?> clazz) {
    this.json = new File(AnnotationHelper.getFilePathAnnotation(clazz));
  }

  public UIMapper(File json) {
    this.json = json;
  }

  public Locator getLocator(String name) throws JsonParseException, IOException, LocatorNotFoundException {
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

  public List<Locator> getLocators() throws JsonParseException, IOException {
    List<Locator> locators = new ArrayList<Locator>();
    JsonParser jsonParser = getJsonParser();
    jsonParser.nextToken();
    while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
      Locator locator = mapper.readValue(jsonParser, Locator.class);
      locators.add(locator);
    }

    return locators;
  }

  private JsonParser getJsonParser() throws JsonParseException, IOException {
    JsonFactory jsonFactory = new JsonFactory();
    return jsonFactory.createParser(json);
  }

}

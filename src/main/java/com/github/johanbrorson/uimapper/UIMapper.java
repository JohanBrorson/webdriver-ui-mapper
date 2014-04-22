package com.github.johanbrorson.uimapper;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.johanbrorson.uimapper.exceptions.LocatorNotFoundException;

public class UIMapper {
  private final ObjectMapper mapper = new ObjectMapper();
  private final File json;

  public UIMapper(File json) {
    this.json = json;
  }

  public Locator getLocator(String name) throws JsonParseException, JsonMappingException, IOException, LocatorNotFoundException {
    JsonFactory jsonFactory = new JsonFactory();
    JsonParser jsonParser = jsonFactory.createParser(json);
    jsonParser.nextToken(); // Advance stream to START_ARRAY
    while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
      Locator locator = mapper.readValue(jsonParser, Locator.class);
      if (name.equals(locator.getName())) {
        return locator;
      }
    }

    throw new LocatorNotFoundException("The locator " + name + " was not found");
  }

}

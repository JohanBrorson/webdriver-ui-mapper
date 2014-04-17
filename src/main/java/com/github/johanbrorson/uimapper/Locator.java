package com.github.johanbrorson.uimapper;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.johanbrorson.uimapper.exceptions.IllegalMethodException;

@JsonPropertyOrder({"name", "selector", "method"})
public class Locator {

  @JsonProperty("name")
  private String name;
  @JsonProperty("selector")
  private String selector;
  @JsonProperty("method")
  private Method method;
  private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public enum Method {
    CSS, ID, XPATH
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("selector")
  public String getSelector() {
    return selector;
  }

  @JsonProperty("selector")
  public void setSelector(String selector) {
    this.selector = selector;
  }

  @JsonProperty("method")
  public Method getMethod() {
    return method;
  }

  @JsonProperty("method")
  public void setMethod(Method method) {
    this.method = method;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

  public By getBy() throws IllegalMethodException {
    switch (method) {
      case CSS:
        return By.cssSelector(selector);
      case ID:
        return By.id(selector);
      case XPATH:
        return By.xpath(selector);
      default:
        throw new IllegalMethodException();
    }
  }
}

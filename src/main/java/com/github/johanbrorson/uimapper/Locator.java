package com.github.johanbrorson.uimapper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

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
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public Locator() {

  }

  public Locator(Locator locator) {
    this.name = locator.getName();
    this.selector = locator.getSelector();
    this.method = locator.getMethod();
    this.additionalProperties = locator.getAdditionalProperties();
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
      case CLASS_NAME:
        return By.className(selector);
      case CSS_SELECTOR:
        return By.cssSelector(selector);
      case ID:
        return By.id(selector);
      case LINK_TEXT:
        return By.linkText(selector);
      case NAME:
        return By.name(selector);
      case PARTIAL_LINK_TEXT:
        return By.partialLinkText(selector);
      case TAG_NAME:
        return By.tagName(selector);
      case XPATH:
        return By.xpath(selector);
      default:
        throw new IllegalMethodException();
    }
  }

  /**
   * Checks if the selector is valid
   *
   * @return <code>true</code> if the selector is valid, otherwise <code>false</code>
   */
  public boolean hasValidSelector() {
    switch (method) {
      case ID:
        return hasValidIdSelector();
      case XPATH:
        return hasValidXpathSelector();
      default:
        return !isSelectorNullOrEmpty();
    }
  }

  private boolean hasValidIdSelector() {
    if (!isSelectorNullOrEmpty()) {
      Pattern pattern = Pattern.compile("\\s");
      Matcher matcher = pattern.matcher(selector);
      return !matcher.find();
    } else {
      return false;
    }
  }

  private boolean hasValidXpathSelector() {
    if (!isSelectorNullOrEmpty()) {
      XPathFactory factory = XPathFactory.newInstance();
      XPath xpath = factory.newXPath();
      try {
        xpath.compile(selector);
      } catch (XPathExpressionException e) {
        return false;
      }
      return true;
    } else {
      return false;
    }
  }

  private boolean isSelectorNullOrEmpty() {
    return selector == null || selector.isEmpty();
  }

}

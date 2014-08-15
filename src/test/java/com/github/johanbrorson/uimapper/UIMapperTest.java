package com.github.johanbrorson.uimapper;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.johanbrorson.uimapper.Locator;
import com.github.johanbrorson.uimapper.UIMapper;
import com.github.johanbrorson.uimapper.exceptions.IllegalMethodException;
import com.github.johanbrorson.uimapper.exceptions.LocatorNotFoundException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UIMapperTest {
  private UIMapper map;

  @BeforeClass
  public void setUp() {
    File json = new File("src/test/resources/testPage.json");
    map = new UIMapper(json);
  }

  @Test
  public void testGetMethod() throws JsonParseException, JsonMappingException, IOException, LocatorNotFoundException {
    Assert.assertEquals(map.getLocator("validId").getMethod(), Locator.Method.ID);
    Assert.assertEquals(map.getLocator("validXpath").getMethod(), Locator.Method.XPATH);
    Assert.assertEquals(map.getLocator("validClassName").getMethod(), Locator.Method.CLASS_NAME);
    Assert.assertEquals(map.getLocator("validCssSelector").getMethod(), Locator.Method.CSS_SELECTOR);
  }

  @Test
  public void testGetName() throws JsonParseException, JsonMappingException, IOException, LocatorNotFoundException {
    Assert.assertEquals(map.getLocator("validId").getName(), "validId");
    Assert.assertEquals(map.getLocator("validXpath").getName(), "validXpath");
    Assert.assertEquals(map.getLocator("validClassName").getName(), "validClassName");
    Assert.assertEquals(map.getLocator("validCssSelector").getName(), "validCssSelector");
  }

  @Test
  public void testGetSelector() throws JsonParseException, JsonMappingException, IOException, LocatorNotFoundException {
    Assert.assertEquals(map.getLocator("validId").getSelector(), "valid-id");
    Assert.assertEquals(map.getLocator("validXpath").getSelector(), "//button[@type='submit']");
    Assert.assertEquals(map.getLocator("validClassName").getSelector(), "valid-class");
    Assert.assertEquals(map.getLocator("validCssSelector").getSelector(), "#valid-id");
  }

  @Test
  public void testGetBy() throws JsonParseException, JsonMappingException, IOException, LocatorNotFoundException, IllegalMethodException {
    Assert.assertEquals(map.getLocator("validId").getBy(), By.id("valid-id"));
    Assert.assertEquals(map.getLocator("validXpath").getBy(), By.xpath("//button[@type='submit']"));
    Assert.assertEquals(map.getLocator("validClassName").getBy(), By.className("valid-class"));
    Assert.assertEquals(map.getLocator("validCssSelector").getBy(), By.cssSelector("#valid-id"));
  }

  @Test
  public void testHasValidSelector() throws JsonParseException, JsonMappingException, IOException, LocatorNotFoundException {
    Assert.assertTrue(map.getLocator("validId").hasValidSelector());
    Assert.assertTrue(map.getLocator("validXpath").hasValidSelector());
    Assert.assertTrue(map.getLocator("validClassName").hasValidSelector());
    Assert.assertTrue(map.getLocator("validCssSelector").hasValidSelector());
    Assert.assertFalse(map.getLocator("invalidId").hasValidSelector());
    Assert.assertFalse(map.getLocator("invalidXpath").hasValidSelector());
  }

  @Test(expectedExceptions = LocatorNotFoundException.class)
  public void testLocatorNotFound() throws JsonParseException, JsonMappingException, IOException, LocatorNotFoundException {
    map.getLocator("locatorThatDoesntExist");
  }

}

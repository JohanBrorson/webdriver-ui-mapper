package com.github.johanbrorson.uimapper;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.johanbrorson.uimapper.Locator;
import com.github.johanbrorson.uimapper.UIMapper;
import com.github.johanbrorson.uimapper.exceptions.IllegalMethodException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UIMapperTest {
  private File json;
  private UIMapper map;

  @BeforeClass
  public void setUp() {
    json = new File("src/test/resources/testPage.json");
    map = new UIMapper(json);
  }

  @Test
  public void testGetLocator() throws JsonParseException, IOException {
    Locator locator = map.getLocator("username");
    Assert.assertEquals("username", locator.getName());
    Assert.assertEquals(Locator.Method.ID, locator.getMethod());
    Assert.assertEquals("login-usr", locator.getSelector());
  }

  @Test
  public void testGetBy() throws JsonParseException, IOException, IllegalMethodException {
    Locator locator = map.getLocator("loginButton");
    Assert.assertEquals(By.xpath("//button[@type='submit']"), locator.getBy());
  }

  @Test(expectedExceptions = InvalidFormatException.class)
  public void testIllegalMethod() throws JsonParseException, IOException, IllegalMethodException {
    map.getLocator("illegalMethod");
  }
}

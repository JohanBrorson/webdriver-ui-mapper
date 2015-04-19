package com.github.johanbrorson.uimapper;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.johanbrorson.uimapper.annotation.LocatorFile;
import com.github.johanbrorson.uimapper.exceptions.LocatorNotFoundException;

@LocatorFile(filePath = "src/test/resources/testPage.json")
public class UIMapperTest {
  private final UIMapper map = new UIMapper(UIMapperTest.class);

  @Test
  public void testGetMethod() throws IOException {
    Assert.assertEquals(map.getLocator("validId").getMethod(), Method.ID);
    Assert.assertEquals(map.getLocator("validXpath").getMethod(), Method.XPATH);
    Assert.assertEquals(map.getLocator("validClassName").getMethod(), Method.CLASS_NAME);
    Assert.assertEquals(map.getLocator("validCssSelector").getMethod(), Method.CSS_SELECTOR);
  }

  @Test
  public void testGetName() throws IOException {
    Assert.assertEquals(map.getLocator("validId").getName(), "validId");
    Assert.assertEquals(map.getLocator("validXpath").getName(), "validXpath");
    Assert.assertEquals(map.getLocator("validClassName").getName(), "validClassName");
    Assert.assertEquals(map.getLocator("validCssSelector").getName(), "validCssSelector");
  }

  @Test
  public void testGetSelector() throws IOException {
    Assert.assertEquals(map.getLocator("validId").getSelector(), "valid-id");
    Assert.assertEquals(map.getLocator("validXpath").getSelector(), "//button[@type='submit']");
    Assert.assertEquals(map.getLocator("validClassName").getSelector(), "valid-class");
    Assert.assertEquals(map.getLocator("validCssSelector").getSelector(), "#valid-id");
  }

  @Test
  public void testGetBy() throws IOException {
    Assert.assertEquals(map.getLocator("validId").getBy(), By.id("valid-id"));
    Assert.assertEquals(map.getLocator("validXpath").getBy(), By.xpath("//button[@type='submit']"));
    Assert.assertEquals(map.getLocator("validClassName").getBy(), By.className("valid-class"));
    Assert.assertEquals(map.getLocator("validCssSelector").getBy(), By.cssSelector("#valid-id"));
  }

  @Test
  public void testHasValidSelector() throws IOException {
    Assert.assertTrue(map.getLocator("validId").hasValidSelector());
    Assert.assertTrue(map.getLocator("validXpath").hasValidSelector());
    Assert.assertTrue(map.getLocator("validClassName").hasValidSelector());
    Assert.assertTrue(map.getLocator("validCssSelector").hasValidSelector());
  }

  @Test
  public void testHasValidSelectorWithInvalidSelectors() throws IOException {
    Assert.assertFalse(map.getLocator("invalidId").hasValidSelector());
    Assert.assertFalse(map.getLocator("invalidXpath").hasValidSelector());
  }

  @Test(expectedExceptions = LocatorNotFoundException.class)
  public void testLocatorNotFound() throws IOException {
    map.getLocator("locatorThatDoesntExist");
  }

  @Test
  public void testGetLocators() throws IOException {
    Assert.assertEquals(map.getLocators().size(), 6);
  }

}

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
  public void testCopyLocator() throws IOException {
    Locator original = map.getLocator("validId");
    Locator locator = new Locator(original);
    Assert.assertEquals(locator.getName(), original.getName());
    Assert.assertEquals(locator.getSelector(), original.getSelector());
    Assert.assertEquals(locator.getMethod(), original.getMethod());
    Assert.assertEquals(locator.getAdditionalProperties(), original.getAdditionalProperties());
  }

  @Test
  public void testEditLocatorCopy() throws IOException {
    Locator original = map.getLocator("validId");
    Locator locator = new Locator(original);

    locator.setName("newName");
    locator.setSelector("//div");
    locator.setMethod(Method.XPATH);

    Assert.assertEquals(locator.getName(), "newName");
    Assert.assertEquals(locator.getSelector(), "//div");
    Assert.assertEquals(locator.getMethod(), Method.XPATH);

    Assert.assertNotEquals(locator.getName(), original.getName());
    Assert.assertNotEquals(locator.getSelector(), original.getSelector());
    Assert.assertNotEquals(locator.getMethod(), original.getMethod());
  }

  @Test
  public void testGetMethod() throws IOException {
    Assert.assertEquals(map.getLocator("validId").getMethod(), Method.ID);
    Assert.assertEquals(map.getLocator("validXpath").getMethod(), Method.XPATH);
    Assert.assertEquals(map.getLocator("validTagName").getMethod(), Method.TAG_NAME);
    Assert.assertEquals(map.getLocator("validClassName").getMethod(), Method.CLASS_NAME);
    Assert.assertEquals(map.getLocator("validCssSelector").getMethod(), Method.CSS_SELECTOR);
    Assert.assertEquals(map.getLocator("validLinkTextSelector").getMethod(), Method.LINK_TEXT);
    Assert.assertEquals(map.getLocator("validPartialLinkTextSelector").getMethod(), Method.PARTIAL_LINK_TEXT);
  }

  @Test
  public void testGetName() throws IOException {
    Assert.assertEquals(map.getLocator("validId").getName(), "validId");
    Assert.assertEquals(map.getLocator("validXpath").getName(), "validXpath");
    Assert.assertEquals(map.getLocator("validTagName").getName(), "validTagName");
    Assert.assertEquals(map.getLocator("validClassName").getName(), "validClassName");
    Assert.assertEquals(map.getLocator("validCssSelector").getName(), "validCssSelector");
    Assert.assertEquals(map.getLocator("validLinkTextSelector").getName(), "validLinkTextSelector");
    Assert.assertEquals(map.getLocator("validPartialLinkTextSelector").getName(), "validPartialLinkTextSelector");
  }

  @Test
  public void testGetSelector() throws IOException {
    Assert.assertEquals(map.getLocator("validId").getSelector(), "valid-id");
    Assert.assertEquals(map.getLocator("validXpath").getSelector(), "//button[@type='submit']");
    Assert.assertEquals(map.getLocator("validTagName").getSelector(), "a");
    Assert.assertEquals(map.getLocator("validClassName").getSelector(), "valid-class");
    Assert.assertEquals(map.getLocator("validCssSelector").getSelector(), "#valid-id");
    Assert.assertEquals(map.getLocator("validLinkTextSelector").getSelector(), "valid link text");
    Assert.assertEquals(map.getLocator("validPartialLinkTextSelector").getSelector(), "valid partial");
  }

  @Test
  public void testGetBy() throws IOException {
    Assert.assertEquals(map.getLocator("validId").getBy(), By.id("valid-id"));
    Assert.assertEquals(map.getLocator("validXpath").getBy(), By.xpath("//button[@type='submit']"));
    Assert.assertEquals(map.getLocator("validTagName").getBy(), By.tagName("a"));
    Assert.assertEquals(map.getLocator("validClassName").getBy(), By.className("valid-class"));
    Assert.assertEquals(map.getLocator("validCssSelector").getBy(), By.cssSelector("#valid-id"));
    Assert.assertEquals(map.getLocator("validLinkTextSelector").getBy(), By.linkText("valid link text"));
    Assert.assertEquals(map.getLocator("validPartialLinkTextSelector").getBy(), By.partialLinkText("valid partial"));
  }

  @Test
  public void testHasValidSelector() throws IOException {
    Assert.assertTrue(map.getLocator("validId").hasValidSelector());
    Assert.assertTrue(map.getLocator("validXpath").hasValidSelector());
    Assert.assertTrue(map.getLocator("validTagName").hasValidSelector());
    Assert.assertTrue(map.getLocator("validClassName").hasValidSelector());
    Assert.assertTrue(map.getLocator("validCssSelector").hasValidSelector());
    Assert.assertTrue(map.getLocator("validLinkTextSelector").hasValidSelector());
    Assert.assertTrue(map.getLocator("validPartialLinkTextSelector").hasValidSelector());
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
    Assert.assertEquals(map.getLocators().size(), 9);
  }

}

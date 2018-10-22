package com.github.johanbrorson.uimapper;

import com.github.johanbrorson.uimapper.annotation.ByLocator;
import com.github.johanbrorson.uimapper.annotation.LocatorFile;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ByLocatorHelperTest {

  @Test
  public void testInitClassVariables() throws Exception {
    Assert.assertEquals(TestClassA.validId, By.id("valid-id"));
    Assert.assertEquals(TestClassA.anotherName, By.xpath("//button[@type='submit']"));
    Assert.assertEquals(TestClassA.locatorThatDoesntExist, null);
    Assert.assertEquals(TestClassA.notAnnotatedField, null);
  }

  @Test
  public void testInitInstanceVariables() throws Exception {
    TestClassB b = new TestClassB();
    Assert.assertEquals(b.validId, By.id("valid-id"));
    Assert.assertEquals(b.anotherName, By.xpath("//button[@type='submit']"));
    Assert.assertEquals(b.locatorThatDoesntExist, null);
    Assert.assertEquals(b.notAnnotatedField, null);
  }

  @LocatorFile(filePath = "src/test/resources/testPage.json")
  static class TestClassA {
    @ByLocator protected static By validId;
    @ByLocator(name = "validXpath") protected static By anotherName;
    @ByLocator protected static String locatorThatDoesntExist;
    protected static By notAnnotatedField;

    static {
      try {
        ByLocatorHelper.initClassVariables(TestClassA.class);
      } catch (Exception e) {
        Assert.fail("Failed to initialize fields", e);
      }
    }
  }

  @LocatorFile(filePath = "src/test/resources/testPage.json")
  class TestClassB {
    @ByLocator protected By validId;
    @ByLocator(name = "validXpath") protected By anotherName;
    @ByLocator protected String locatorThatDoesntExist;
    protected By notAnnotatedField;

    protected TestClassB() throws Exception {
      ByLocatorHelper.initInstanceVariables(this);
    }
  }
}

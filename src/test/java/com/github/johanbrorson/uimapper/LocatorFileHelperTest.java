package com.github.johanbrorson.uimapper;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.github.johanbrorson.uimapper.exceptions.LocatorFileIOException;

public class LocatorFileHelperTest {
  private final static String TEST_FILE = "src/test/resources/content-test.txt";
  private final static String EXPECTED_CONTENT = "Line 1\nLine 2\n";

  @Test
  public void testGetContentFromFile() {
    File file = new File(TEST_FILE);
    Assert.assertEquals(LocatorFileHelper.getContent(file), EXPECTED_CONTENT);
  }

  @Test
  public void testGetContentFromFilePath() {
    Assert.assertEquals(LocatorFileHelper.getContent(TEST_FILE), EXPECTED_CONTENT);
  }

  @Test(expectedExceptions = LocatorFileIOException.class)
  public void testLocatorFileIOException() {
    LocatorFileHelper.getContent("src/test/resources/file-that-doesnt-exist");
  }

}

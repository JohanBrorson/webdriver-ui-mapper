package com.github.johanbrorson.uimapper;

import com.github.johanbrorson.uimapper.exceptions.LocatorFileIOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class LocatorFileHelperTest {

  @Test
  public void testGetContentFromFile() {
    final File file = new File("src/test/resources/content-from-file.txt");
    final String expectedContent = "1\n2\n";
    Assert.assertEquals(LocatorFileHelper.getContent(file), expectedContent);
  }

  @Test
  public void testGetContentFromFilePath() {
    final String testFile = "src/test/resources/content-from-file-path.txt";
    final String expectedContent = "A\nB\n";
    Assert.assertEquals(LocatorFileHelper.getContent(testFile), expectedContent);
  }

  @Test(expectedExceptions = LocatorFileIOException.class)
  public void testGetContentFromNonExistingFile() {
    LocatorFileHelper.getContent(new File("src/test/resources/file-that-doesnt-exist"));
  }

  @Test(expectedExceptions = LocatorFileIOException.class)
  public void testGetContentFromNonExistingFilePath() {
    LocatorFileHelper.getContent("src/test/resources/file-that-doesnt-exist");
  }

}

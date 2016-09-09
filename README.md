# WebDriver UI Mapper [![Build Status](https://travis-ci.org/JohanBrorson/webdriver-ui-mapper.svg?branch=master)](https://travis-ci.org/JohanBrorson/webdriver-ui-mapper) [![codecov](https://codecov.io/gh/JohanBrorson/webdriver-ui-mapper/branch/master/graph/badge.svg)](https://codecov.io/gh/JohanBrorson/webdriver-ui-mapper)

WebDriver UI Mapper is a tool that maps JSON files with WebDriver locators to Locator java objects

### Advantages of UI Maps
* Easier maintenance by having a central location for UI objects instead of having them scattered throughout the tests.
* Readability can be improved by giving the selectors human-readable names.
* Possible to reuse the locators.

### Maven Dependency
In your Maven project, add the following to your pom.xml file

```xml
    <dependency>
      <groupId>com.github.johanbrorson</groupId>
      <artifactId>webdriver-ui-mapper</artifactId>
      <version>[1.4.0, 2.0)</version>
    </dependency>
```

### Example UI Map
```json
[
  {
    "name": "searchInput",
    "selector": "//input[@name='q']",
    "method": "XPATH"
  },
  {
    "name": "searchButton",
    "selector": "btnG",
    "method": "NAME"
  }
]
```

### Example Page Object Class
```java
@LocatorFile(filePath = "locators/SearchPage.json")
public class SearchPage {
  private final UIMapper map = new UIMapper(SearchPage.class);
  private final WebDriver driver;
  @ByLocator private By searchInput;
  @ByLocator private By searchButton;

  public SearchPage(WebDriver driver) throws IOException {
    this.driver = driver;
    ByLocatorHelper.initInstanceVariables(this);
  }

  public void enterSearchText(String searchText) {
    driver.findElement(searchInput).clear();
    driver.findElement(searchInput).sendKeys(searchText);
  }

  public void clickSearchButton() {
    driver.findElement(searchButton).click();
  }
}

```

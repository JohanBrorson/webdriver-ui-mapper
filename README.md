# WebDriver UI Mapper [![Build Status](https://travis-ci.org/JohanBrorson/webdriver-ui-mapper.svg?branch=master)](https://travis-ci.org/JohanBrorson/webdriver-ui-mapper)

WebDriver UI Mapper is a tool that maps JSON files with WebDriver locators to Locator java objects

### Advantages of UI Maps
* Easier maintenance by having a centralal location for UI objects instead of having them scattered throughout the tests.
* Readability can be improved by giving the selectors human-readable names.
* Possible to reuse the locators.

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
  private By searchInput;
  private By searchButton;

  public SearchPage(WebDriver driver) throws IOException {
    this.driver = driver;
    searchInput = map.getLocator("searchInput").getBy();
    searchButton = map.getLocator("searchButton").getBy();
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
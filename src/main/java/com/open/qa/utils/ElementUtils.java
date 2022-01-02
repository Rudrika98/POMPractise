package com.open.qa.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.Factory.DriverFactory;

public class ElementUtils {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtils(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	public By getBy(String locatorType, String locatorValue) {

		By locator = null;

		switch (locatorType.toLowerCase()) {
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "xpath":
			locator = By.xpath(locatorValue);
			break;
		case "cssSelector":
			locator = By.cssSelector(locatorValue);
			break;
		case "linkText":
			locator = By.linkText(locatorValue);
			break;
		case "partialLinkText":
			locator = By.partialLinkText(locatorValue);
			break;
		case "tagName":
			locator = By.tagName(locatorValue);
			break;
		case "className":
			locator = By.className(locatorValue);
			break;
		default:
			System.out.println("Please pass the right locator type and value..  ");
		}
		return locator;
	}

	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highLight)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public WebElement getElement(By locator, int timeOut) {
		return performPresenceOfElementLocated(locator, timeOut);
	}

	public WebElement getElement(String locatorType, String locatorValue) {
		return driver.findElement(getBy(locatorType, locatorValue));
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public void performClear(By locator) {
		getElement(locator).clear();
	}
	
	public void performSendKeys(By locator, String value) {
		performClear(locator);
		getElement(locator).sendKeys(value);
	}

	public void performSendKeys(String locatorType, String locatorValue, String value) {
		getElement(locatorType, locatorValue).sendKeys(value);
	}

	public void performSendKeys(By locator, int timeOut, String value) {
		performPresenceOfElementLocated(locator, timeOut).sendKeys(value);
	}

	public void performSendKeys(By locator, int timeOut, long intervalTime, String value) {
		performPresenceOfElementLocated(locator, timeOut, intervalTime).sendKeys(value);
	}

	public void performClick(By locator) {
		getElement(locator).click();
	}

	public void performClick(String locator, String locatorType) {
		getElement(locator, locatorType).click();
	}

	public void performClick(By locator, int timeOut) {
		performPresenceOfElementLocated(locator, timeOut).click();
	}

	public void performClick(By locator, int timeOut, long intervalTime) {
		performPresenceOfElementLocated(locator, timeOut, intervalTime).click();
	}

	public String performGetText(By locator) {
		return getElement(locator).getText();
	}

	public String performGetText(String locator, String locatorType) {
		return getElement(locator, locatorType).getText();
	}

	public String getAttributeValue(By locator, String attributeName) {
		return getElement(locator).getAttribute(attributeName);
	}

	public boolean performIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();
	}

	public boolean isElementExist(By locator) {
		int elementCount = getElementsCount(locator);
		if (elementCount >= 1) {
			System.out.println("Element is found... " + locator);
			return true;
		} else {
			System.out.println("Element is not found... " + locator);
			return false;
		}
	}

	public List<String> getElementsTextList(By locator) {
		List<String> eletextList = new ArrayList<String>();
		if (getElementsCount(locator) != -1) {
			List<WebElement> eleList = getElements(locator);
			for (WebElement e : eleList) {
				String eleText = e.getText();
				if (!eleText.isEmpty()) {
					eletextList.add(eleText);
				}
			}
		} else {
			System.out.println("List is empty ... Checked count ");
		}
		return eletextList;
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public void printListValues(List<String> eleList) {
		for (String e : eleList) {
			System.out.println(e);
		}
	}

	public List<String> getAttributesList(By locator, String attributeName) {
		List<WebElement> eleList = getElements(locator);
		List<String> attrList = new ArrayList<String>();

		for (WebElement e : eleList) {
			String attrValue = e.getAttribute(attributeName);
			attrList.add(attrValue);
		}
		return attrList;
	}

	/*******************************
	 * Drop Down Utils
	 ********************************************/
	public void doDropdownSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doDropdownSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doDropdownSelectBy(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public void doSelectDropDownValue(By locator, String value) {

		Select select = new Select(getElement(locator));
		List<WebElement> countryList = select.getOptions();

		for (WebElement e : countryList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equalsIgnoreCase(value)) {
				e.click();
				break;
			}
		}
	}

	public void selectDropDownValueWithoutSelect(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);
		System.out.println(optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	/*******************************************
	 * Links Util
	 *****************************************************/
	public List<String> getLinksText(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> linksTextList = new ArrayList<String>();

		for (WebElement e : eleList) {
			String text = e.getText().trim();
			linksTextList.add(text);
		}
		return linksTextList;
	}

	public void clickOnElementFromSection(By locator, String linkText) {
		List<WebElement> eleList = getElements(locator);

		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.equalsIgnoreCase(linkText)) {
				e.click();
				break;
			}
		}
	}

	/******************************************
	 * Table Utils
	 **********************************************************/

	public void printTable(By rowLocator, By colLocator, String beforeXpath, String afterXpath) {
		int rowCount = getElementsCount(rowLocator);
		int colCount = getElementsCount(colLocator);
		for (int row = 2; row <= rowCount; row++) {
			for (int col = 1; col <= colCount; col++) {
				String xpath = beforeXpath + row + afterXpath + col + "]";
				String text = performGetText(By.xpath(xpath));
				System.out.println(text + "      |          ");
			}
			System.out.println();
		}

	}

	/**************************************************
	 * Wait Utils
	 ********************************************************/

	public WebElement performPresenceOfElementLocated(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	// By customizing polling time
	public WebElement performPresenceOfElementLocated(By locator, int timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(intervalTime));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public WebElement isElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForElementToBeVisible(By locator, int timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(intervalTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	// wait for list of webelements
	public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(intervalTime));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	public WebElement waitForElementToBeVisibleWithWebElement(By locator, int timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(intervalTime));
		return wait.until(ExpectedConditions.visibilityOf(getElement(locator)));
	}

	public List<String> getElementsTextListWithWait(By locator, int timeOut) {
		List<WebElement> eleList = waitForElementsToBeVisible(locator, timeOut);
		List<String> eleTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			eleTextList.add(e.getText());
		}
		return eleTextList;
	}

	public List<String> getElementsTextListWithWait(By locator, int timeOut, long intervalTime) {
		List<WebElement> eleList = waitForElementsToBeVisible(locator, timeOut, intervalTime);
		List<String> eleTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			eleTextList.add(e.getText());
		}
		return eleTextList;
	}

	/**********************************
	 * Wait Utils For Non Web Elements
	 ********************************/
	public boolean waitForUrlToContains(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.urlContains(urlFraction));
	}

	public boolean waitForUrlToBe(String url, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.urlToBe(url));
	}

	public String doGetTitleWithFraction(String titleFraction, int timeOut) {
		if (waitForTitle(titleFraction, timeOut)) {
			return driver.getTitle();
		}
		return "";
	}

	public String doGetTitle(String title, int timeOut) {
		if (waitForExactTitle(title, timeOut)) {
			return driver.getTitle();
		}
		return "";
	}

	public boolean waitForTitle(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.titleContains(titleFraction));
	}

	public boolean waitForExactTitle(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.titleIs(title));
	}

	public void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	public void clickElementWhenReady(By locator, int timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(intervalTime));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	public void waitForElement(By locator, int timeOut, int pollingTime) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
				.withMessage(Error.ELEMENT_NOT_FOUND_ERROR_MESSAGE);

	}

	/******************************
	 * Alert Utils
	 ***********************************************/
	public Alert waitForAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public void doAlertAccept(int timeOut) {
		waitForAlert(timeOut).accept();
	}

	public void doAlertDismiss(int timeOut) {
		waitForAlert(timeOut).dismiss();
	}

	public String getAlertText(int timeOut) {
		return waitForAlert(timeOut).getText();
	}

	public void enterAlertText(String text, int timeOut) {
		waitForAlert(timeOut).sendKeys(text);
	}
	
	public List<String> getListString(List<WebElement> ele) {
		List<String> eleString = new ArrayList<String>(); 
		for(WebElement e : ele) {
			eleString.add(e.getText());
		}
		return eleString;
	}

	
}

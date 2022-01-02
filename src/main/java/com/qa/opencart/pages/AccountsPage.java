package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.open.qa.utils.Constants;
import com.open.qa.utils.ElementUtils;

public class AccountsPage {
	
	private WebDriver driver; 
	private ElementUtils eleUtil; 
	
	private By header = By.cssSelector("div#logo a"); 
	private By accountsSection = By.cssSelector("div#content h2"); 
	private By searchField = By.name("search");
	private By searchButton = By.cssSelector("div#search button"); 
	private By logOutLink = By.linkText("Logout");

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver); 
	}
	
	public String getAccountPageTitle() {
		return eleUtil.doGetTitle(Constants.ACCOUNTS_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}
	
	public String getAccountsPageHeader() {
		return eleUtil.performGetText(header);
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.performIsDisplayed(logOutLink);
	}
	
	public void logOut() {
		if(isLogoutLinkExist())
			eleUtil.performClick(logOutLink);
	}
	
	public List<String> getAccountSecList() {
		List<WebElement> secList = eleUtil.waitForElementsToBeVisible(accountsSection, 10);
		List<String> accSecValList = eleUtil.getListString(secList);
		return accSecValList;
	}
	
	public boolean isSearchExist() {
		return eleUtil.performIsDisplayed(searchField);
	}
	
	public SearchResultsPage doSearch(String productName) {
		System.out.println("Searching the product : " +productName);
		eleUtil.performSendKeys(searchField, productName);
		eleUtil.performClick(searchButton);
		return new SearchResultsPage(driver);
	}
}

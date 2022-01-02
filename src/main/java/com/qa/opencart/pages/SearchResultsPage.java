package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.open.qa.utils.ElementUtils;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtils eleUtil;
	
	private By productResults = By.cssSelector("div.caption a"); 
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}
	
	public int getProductsListCount() {
		int resultCount = eleUtil.waitForElementsToBeVisible(productResults, 10,2000).size();
		System.out.println("The search product count: " +resultCount);
		return resultCount;
	}
	
	public ProductInfoPage selectProduct(String mainProductName) {
		System.out.println("Main product name is : " + mainProductName);
		List<WebElement> searchList = eleUtil.waitForElementsToBeVisible(productResults, 10,2000);
		for(WebElement e: searchList) {
			String text = e.getText();
			if(text.equals(mainProductName)) {
				e.click();
				break; 
			}
		}
		return new ProductInfoPage(driver);
	}

}

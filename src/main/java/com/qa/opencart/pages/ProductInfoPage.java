package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.open.qa.utils.ElementUtils;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtils eleUtil;

	private By productHeaderName = By.xpath("//div[@id='content']//h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(1)  li");
	private By productPriceData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(2)  li");

	private Map<String, String> productInfoMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}

	public String getProductHeader() {
		String productHeaderText = eleUtil.performGetText(productHeaderName);
		System.out.println("Product Header Text is: " + productHeaderText);
		return productHeaderText;
	}

	public int getProductImagesCount() {
		return eleUtil.waitForElementsToBeVisible(productImages, 10).size();
	}

	public Map<String, String> getProductInfo() {
		productInfoMap = new HashMap<String, String>();
		productInfoMap.put("name", getProductHeader());
		getProductMetaData();
		getProductPriceData();
		return productInfoMap;
	}

	private void getProductMetaData() {
		List<WebElement> metaDataList = eleUtil.getElements(productMetaData);
		for(WebElement ele: metaDataList) {
			String text = ele.getText(); 
			String meta[] = text.split(":"); 
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productInfoMap.put(metaKey, metaValue);
		}
	}

	private void getProductPriceData() {
		List<WebElement> metaPriceList = eleUtil.getElements(productPriceData);
		String price = metaPriceList.get(0).getText().trim();
		String exPrice = metaPriceList.get(1).getText().trim();
		productInfoMap.put("price", price);
		productInfoMap.put("exPrice", exPrice);
	}
}

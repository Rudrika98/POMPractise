package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.open.qa.utils.Constants;

public class ProductInfoPageTest extends BaseTest {
	
	@BeforeClass
	public void productInfoSetUp() {
		accountsPage = loginPage.doLogin(prop.getProperty("userName"), prop.getProperty("password"));
	}

	@Test(priority = 1)
	public void productHeaderTest() {
		searchResultPage = accountsPage.doSearch("MacBook");
		productInfoPage = searchResultPage.selectProduct("MacBook Pro");
		Assert.assertEquals(productInfoPage.getProductHeader(), "MacBook Pro");
	}

	
	@Test(priority = 2)
	public void productImagesCount() {
		searchResultPage = accountsPage.doSearch("iMac");
		productInfoPage = searchResultPage.selectProduct("iMac");
		Assert.assertEquals(productInfoPage.getProductImagesCount(),Constants.IMAC_IMAGE_COUNT);
	}
	
	@Test(priority = 3)
	public void productInfoTest() {
		searchResultPage = accountsPage.doSearch("iMac");
		productInfoPage = searchResultPage.selectProduct("iMac");
//		Map<String,String> actProductInfoMap = productInfoPage.getProductInfo();
//		actProductInfoMap.forEach((k,v) -> System.out.println(k + ":" + v));
////		Assert.assertEquals(actProductInfoMap.get("name"), "iMac");
////		Assert.assertEquals(actProductInfoMap.get("Product Code"), "Product 14");
////		Assert.assertEquals(actProductInfoMap.get("price"), "$100.00");
//		softAssert.assertEquals(actProductInfoMap.get("name"), "iMac");
//		softAssert.assertEquals(actProductInfoMap.get("Product Code"), "Product 14");
//		softAssert.assertEquals(actProductInfoMap.get("price"), "$100.00");
//		softAssert.assertAll(); 
	}
}

package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.open.qa.utils.Constants;
import com.open.qa.utils.Error;
import com.qa.opencart.listeners.TestAllureListener;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;

@Epic("Epic 100 : Design Open Cart App -Account Page")
@Story("US 102: Open Cart AccountDesign with multiple features")
@Listeners(TestAllureListener.class)
public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accPageSetUp() {
		accountsPage = loginPage.doLogin(prop.getProperty("userName"), prop.getProperty("password"));
	}

	@Test
	public void accPageTitleTest() {
		String actTitle = accountsPage.getAccountPageTitle();
		System.out.println("Accounts Page Title : " + actTitle);
		Assert.assertEquals(actTitle, Constants.ACCOUNTS_PAGE_TITLE);
	}

	@Test
	public void accPageHeaderTest() {
		String header = accountsPage.getAccountsPageHeader();
		System.out.println("Acc page header is: " + header);
		Assert.assertEquals(header, Constants.ACCOUNTS_PAGE_HEADER, Error.ACCOUNT_PAGE_HEADERNOT_FOUND_ERROR_MESSAGE);
	}

	@Test
	public void isLogOutExistTest() {
		Assert.assertTrue(accountsPage.isLogoutLinkExist());
	}

	@Test
	public void accSecsTest() {
		List<String> actAccSecList = accountsPage.getAccountSecList();
		Assert.assertEquals(actAccSecList, Constants.getExpectedAccSecList());
	}

	@DataProvider
	public Object[][] productData() {
		return new Object[][] { { "MacBook" }, { "Apple" }, { "Samsung" } };

	}

	@Test(dataProvider = "productData")
	public void searchTest(String productName) {
		searchResultPage = accountsPage.doSearch(productName);
		Assert.assertTrue(searchResultPage.getProductsListCount() > 0);
	}

	@DataProvider
	public Object[][] productSelectData() {
		return new Object[][] { 
			{ "MacBook","MacBook Pro" },
			{ "Apple" , "Apple Cinema" }, 
			{ "Samsung" , "Samsung SyncMaster 941BW"} , 
			{"iMac","iMac"}
			};

	}

	@Test(dataProvider = "productSelectData")
	public void selectProductTest(String productName, String mainProductName) {
		searchResultPage = accountsPage.doSearch(productName);
		productInfoPage = searchResultPage.selectProduct(mainProductName);
		Assert.assertEquals(productInfoPage.getProductHeader(), mainProductName);
	}
}

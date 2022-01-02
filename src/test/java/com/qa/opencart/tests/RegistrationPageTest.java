package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.open.qa.utils.Constants;
import com.open.qa.utils.ExcelUtil;

public class RegistrationPageTest extends BaseTest {

	@BeforeClass
	public void setUpRegistration() {
		regPage = loginPage.goToRegisterPage();
	}
	
	public String getRandomEmail() {
		Random randomGenerator = new Random(); 
		String email = "rudrikaS" + randomGenerator.nextInt(1000) +"@gmail.com" ; 
		return email;
	}

	@DataProvider
	public Object[][] getRegisterData() {
		return ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
	}

	@Test(dataProvider = "getRegisterData")
	public void userRegistrationTest(String firstName, String lastName, String telephone, String password,
			String subscribe) {
		Assert.assertTrue(
				regPage.accountRegistration(firstName, lastName, getRandomEmail(), telephone, password, subscribe));
	}

}

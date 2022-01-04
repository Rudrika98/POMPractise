package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.open.qa.utils.Constants;
import com.open.qa.utils.ElementUtils;
import io.qameta.allure.Step;


public class LoginPage {

	// 1. Declare private driver
	private WebDriver driver;
	private ElementUtils eleUtil;

	// 2. Create constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtils(driver);
	}

	// 3. By locators
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By logInBtn = By.xpath("//input[@value='Login']");
	private By registerLink = By.linkText("Register");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By loginErrorMessage = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	
	
	// 4. pageActions
	@Step("Getting Login page title value....")
	public String getLoginPageTitle() {
		return eleUtil.doGetTitle(Constants.LOGIN_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}

	@Step("Getting Login page url....")
	public boolean getLoginPageUrl() {
		return eleUtil.waitForUrlToContains(Constants.LOGIN_PAGE_URL_FRACTION, Constants.DEFAULT_TIME_OUT);
	}

	@Step("Checking foregot pwd link exist or not....")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.performIsDisplayed(forgotPwdLink);
	}

	@Step("Checking register pwd link exist or not....")
	public boolean isRegisterLinkExist() {
		return eleUtil.performIsDisplayed(registerLink);
	}

	@Step("do login with userName : {0} and password : {1} ")
	public AccountsPage doLogin(String un, String pwd) {
		System.out.println("login with : " + un + " : " + pwd);
		eleUtil.performSendKeys(emailId, un);
		eleUtil.performSendKeys(password,pwd);
		eleUtil.performClick(logInBtn);
		return new AccountsPage(driver);
	}
	
	@Step("do login with wrong userName : {0} and wrong password : {1} ")
	public boolean doLoginWithWrongCredentials(String un, String pwd) {
		System.out.println("Try to login with wrong credentials: " + un + " : " + pwd);
		eleUtil.performSendKeys(emailId, un);
		eleUtil.performSendKeys(password,pwd);
		eleUtil.performClick(logInBtn);
		String errorMessage = eleUtil.performGetText(loginErrorMessage);
		System.out.println(errorMessage);
		if(errorMessage.equals(Constants.LOGIN_ERROR_MESSAGE)) {
			System.out.println("Login Unsucessful...");
			return false;
		}
		return true;
	}
	
	@Step("Navigating to registeration page....")
	public RegistrationPage goToRegisterPage() {
		eleUtil.performClick(registerLink);
		return new RegistrationPage(driver);
	}
}

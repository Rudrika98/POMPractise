package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.open.qa.utils.Constants;
import com.open.qa.utils.ElementUtils;

public class RegistrationPage {
	
	private WebDriver driver; 
	private ElementUtils eleUtil; 
	
	private By firstName = By.id("input-firstname"); 
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone"); 
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm"); 
	
	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");
	
	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	private By sucessMessg = By.cssSelector("div#content h1");

	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	public RegistrationPage(WebDriver driver) {
		this.driver = driver; 
		eleUtil = new ElementUtils(driver);
	}
	
	
	public boolean accountRegistration(String firstName, String lastName, String email, String telephone,
			String password,String subscribe) {
		eleUtil.performSendKeys(this.firstName, firstName);
		eleUtil.performSendKeys(this.lastName, lastName);
		eleUtil.performSendKeys(this.email, email);
		eleUtil.performSendKeys(this.telephone, telephone);
		eleUtil.performSendKeys(this.password, password);
		eleUtil.performSendKeys(this.confirmPassword, password);
		
		if(subscribe.equals("yes")) {
			eleUtil.performClick(subscribeYes);
		} else {
			eleUtil.performClick(subscribeNo);
		}
		
		eleUtil.performClick(agreeCheckBox);
		eleUtil.performClick(continueButton);
		String message = eleUtil.waitForElementToBeVisible(sucessMessg, 5,1000).getText();
		
		System.out.println("Registration Message is : "+message);
		
		if(message.contains(Constants.REGISTER_SUCESS_MESSG)) {
			System.out.println("Rudrika");
			eleUtil.performClick(logoutLink);
			eleUtil.performClick(registerLink);
			return true; 
		}
		return false; 
	}
}

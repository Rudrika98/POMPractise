package com.open.qa.utils;

import java.util.ArrayList;
import java.util.List;

public class Constants {

	public static final String LOGIN_PAGE_TITLE = "Account Login"; 
	public static final String ACCOUNTS_PAGE_TITLE = "My Account"; 
	public static final String ACCOUNTS_PAGE_HEADER = "Your Store";
	
	public static final String LOGIN_PAGE_URL_FRACTION = "route=account/login";
	public static final String ACCOUNTS_PAGE_FRACTION = "Account Login"; 
	
	public static final int DEFAULT_TIME_OUT = 5;  //DEFAULT_TIME_OUT
	public static final int IMAC_IMAGE_COUNT = 3; 
	public static final int MACBOOKPRO_IMAGE_COUNT = 3;
	public static final int MACBOOKAIR_IMAGE_COUNT = 3;
	
	public static final String LOGIN_ERROR_MESSAGE = " Warning: No match for E-Mail Address and/or Password." ;
	public static final String REGISTER_SUCESS_MESSG = "Your Account Has Been Created!";
	public static final String REGISTER_SHEET_NAME = "registration";
	
	
	
	
	public static List<String> getExpectedAccSecList() {
		List<String> expectedSectionList = new ArrayList<String>(); 
		expectedSectionList.add("My Account");
		expectedSectionList.add("My Orders");
		expectedSectionList.add("My Affiliate Account");
		expectedSectionList.add("Newsletter");
		return expectedSectionList;
	}
	
}

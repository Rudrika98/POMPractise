package com.qa.opencart.Factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public static String highLight;
	public OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This method is used to initialize the web driver
	 * 
	 * @param browserName
	 * @return this will return the driver
	 */
	public WebDriver init_driver(Properties prop) {

		String browserName = prop.getProperty("browser").trim();
		System.out.println("browser name is : " + browserName);
		highLight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);

		if (browserName.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
		} else if (browserName.equals("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
		} else if (browserName.equals("Edge")) {
			driver = new EdgeDriver();
		} else {
			System.out.println("Please pass the right browser");
		}

		getDriver().manage().window().fullscreen();
		getDriver().manage().deleteAllCookies();
		//openUrl(prop.getProperty("url"));
		URL url= null;
		try {
			url = new URL(prop.getProperty("url"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		openUrl(url);
		return getDriver();
	}

	/**
	 * getDriver() : it will return a thread local copy of the web driver
	 * 
	 * @return
	 */
	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	/***
	 * This method is used to initialize the properties
	 * 
	 * @return this will return properties prop reference
	 */
	public Properties init_prop() {
		prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");

		if (envName == null) {
			System.out.println("Running on PROD env: ");
			try {
				ip = new FileInputStream("./src/test/resources/config/config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Running on environment: " + envName);
			try {
				switch (envName.toLowerCase()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa-config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev-config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage-config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat-config.properties");
					break;
				default:
					System.out.println("Please pass the right environment : ");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * take screenshot method
	 */

	public String getScreenshot() {
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
		File destn = new File(path);
		try {
			FileUtils.copyFile(src, destn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

	public void openUrl(String url) {
		try {
			if (url == null) {
				throw new Exception("url is null");
			}
		} catch (Exception e) {

		}
		getDriver().get(url);
	}
	
	public void openUrl(URL url) {
		try {
			if(url == null) {
				throw new Exception("url is null");
			}
		}catch(Exception e) {
			
		}
		getDriver().navigate().to(url);
	}
	
	public void openUrl(String baseUrl, String path) {
		try {
			if(baseUrl == null) {
				throw new Exception("baseUrl is null");
			}
		}catch(Exception e) {
			
		}
		// http:amazon.com/accpage/users.html
		getDriver().navigate().to(baseUrl + "/" +path);
	}
	
	public void openUrl(String baseUrl, String path, String queryParam) {
		try {
			if(baseUrl == null) {
				throw new Exception("baseUrl is null");
			}
		}catch(Exception e) {
			
		}
		getDriver().navigate().to(baseUrl + "/" +path + "?" + queryParam);
	}
}


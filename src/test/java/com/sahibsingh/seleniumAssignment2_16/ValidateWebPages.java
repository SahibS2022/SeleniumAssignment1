package com.sahibsingh.seleniumAssignment2_16;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ValidateWebPages {
	WebDriver wd;
	SoftAssert sf;
	WebDriverWait wdWait;
	Actions action;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver",
				"G:\\Pivot Coaching\\Driver\\Chrome Driver 108\\chromedriver.exe");
		wd = new ChromeDriver();
		wd.get("http://seleniumpractise.blogspot.com/2016/08/how-to-perform-mouse-hover-in-selenium.html");
		wd.manage().window().maximize();
		wdWait = new WebDriverWait(wd, Duration.ofSeconds(30));
		sf = new SoftAssert();
		action = new Actions(wd);
	}

	@Test
	public void validateMultipleTabs() {

		/* Asserting the title of the Main Web Page */
		softAssertEquals(getWebElementByCssSelector("h3.entry-title").getText(),
				"How to perform mouse hover in Selenium Webdriver", "Heading Text is Incorrect!");
		/* Getting the parent window handle */
		String parentWebHandle = wd.getWindowHandle();

		/* Performing the mouse hover action on button */
		actionMoveToElement(getWebElementByCssSelector("div.dropdown button"));

		/* Performing the Click action on first dropdown option */
		actionClick(getWebElementByCssSelector("div.dropdown-content a:first-of-type"));

		/* Asserting the title of the First Child Web Page */
		softAssertEquals(wd.getTitle(), "Selenium Webdriver Tutorial - Selenium Tutorial for Beginners",
				"Title is Incorrect!");

		/* Switching Back to the Main Web Page */
		wd.switchTo().window(parentWebHandle);

		/* Performing the mouse hover action on button */
		actionMoveToElement(getWebElementByCssSelector("div.dropdown button"));

		/* Performing the Click action on second dropdown option */
		actionClick(getWebElementByCssSelector("div.dropdown-content a:nth-of-type(2)"));

		/* Asserting the title of Web Page */
		softAssertEquals(wd.getTitle(), "TestNG Tutorials for Selenium Webdriver with Real Time Examples",
				"Title is Incorrect!");

		/* Navigate back to the Main Web Page */
		wd.navigate().back();

		/* setting explicit wait on a web element */
		setExplicitWait("div.dropdown button");

		/* Performing the mouse hover action on button */
		actionMoveToElement(getWebElementByCssSelector("div.dropdown button"));

		/* setting explicit wait on a web element */
		setExplicitWait("div.dropdown-content a:last-of-type");

		/* Performing the Click action on third dropdown option */
		actionClick(getWebElementByCssSelector("div.dropdown-content a:last-of-type"));

		/* Asserting the title of Web Page */
		softAssertEquals(wd.getTitle(), "Complete Ultimate Appium tutorial for beginners using JAVA for Selenium",
				"Title is Incorrect!");
	}

	/* Method to get the Web element by using CSS Locator */
	public WebElement getWebElementByCssSelector(String CssLocator) {
		WebElement element = wd.findElement(By.cssSelector(CssLocator));
		return element;
	}

	/*
	 * Method to set the Explicit Wait on Web Element on Condition of a WebElement
	 */
	public void setExplicitWait(String CssLocator) {
		wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CssLocator)));
	}

	/* Method to provide soft assertion by using Equals */
	public void softAssertEquals(String actualText, String expectedText, String msg) {
		sf.assertEquals(actualText, expectedText, msg);
	}

	/* Method to perform the Click Action on a Web Element */
	public void actionClick(WebElement webElement) {
		action.click(webElement).perform();
	}

	/* Method to perform the Move To Element Action on a Web Element */
	public void actionMoveToElement(WebElement element) {
		action.moveToElement(element).perform();
	}

	@AfterMethod
	public void tearDown() {
		wd.quit();
	}
}

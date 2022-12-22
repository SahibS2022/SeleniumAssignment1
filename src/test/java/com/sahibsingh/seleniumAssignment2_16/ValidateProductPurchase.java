package com.sahibsingh.seleniumAssignment2_16;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.checkerframework.checker.units.qual.s;
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

public class ValidateProductPurchase {

	WebDriver wd;
	SoftAssert sf;
	WebDriverWait wdWait;
	Actions action;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver",
				"G:\\Pivot Coaching\\Driver\\Chrome Driver 108\\chromedriver.exe");
		wd = new ChromeDriver();
		wd.get("https://www.demoblaze.com/index.html");
		wd.manage().window().maximize();
		wdWait = new WebDriverWait(wd, Duration.ofSeconds(10));
		sf = new SoftAssert();
		action = new Actions(wd);
	}

	@Test
	public void validatePurchaseItem() {
		softAssertEquals(wd.getTitle(), "STORE", "Title is Incorrect!");
		setExplicitWait("div#tbodyid>div:nth-of-type(7) div.card-block a");
		getWebElementByCssSelector("div#tbodyid>div:nth-of-type(7) div.card-block a").click();
		setExplicitWait("#tbodyid h2");
		softAssertEquals(getWebElementByCssSelector("#tbodyid h2").getText(), "HTC One M9",
				"Product name is Incorrect!");
		setExplicitWait("#tbodyid h3");
		softAssertEquals(getWebElementByCssSelector("#tbodyid h3").getText(), "$700", "Product Price is Incorrect!");
		getWebElementByCssSelector("div#tbodyid>div:last-of-type a").click();
		wdWait.until(ExpectedConditions.alertIsPresent());
		wd.switchTo().alert().accept();
		setExplicitWait("#navbarExample>ul>li");

		/* Perform click action on List element By using Supplier Interface */
		Supplier<List<WebElement>> supplier = () -> {
			List<WebElement> listOfElements = wd.findElements(By.cssSelector("#navbarExample>ul>li"));
			return listOfElements;
		};
		for (WebElement element : supplier.get()) {
			if (element.getText().contains("Cart")) {
				element.click();
				break;
			}
		}
		setExplicitWait("tbody#tbodyid tr>td:nth-of-type(2)");
		softAssertEquals(getWebElementByCssSelector("tbody#tbodyid tr>td:nth-of-type(2)").getText(), "HTC One M9",
				"Product Name is Incorrect");
		softAssertEquals(getWebElementByCssSelector("tbody#tbodyid tr>td:nth-of-type(3)").getText(), "700",
				"Product Price is Incorrect");
		softAssertEquals(getWebElementByCssSelector("#totalp").getText(), "700", "Total Price is Incorrect");
		setExplicitWait("div.col-lg-1 button");
		getWebElementByCssSelector("div.col-lg-1 button").click();
		getWebElementByCssSelector("input#name").sendKeys("Sahib");
		getWebElementByCssSelector("input#country").sendKeys("Canada");
		getWebElementByCssSelector("input#city").sendKeys("Toronto");
		getWebElementByCssSelector("input#card").sendKeys("12345678901234");
		getWebElementByCssSelector("input#month").sendKeys("December");
		getWebElementByCssSelector("input#year").sendKeys("2022");
		getWebElementByCssSelector("div#orderModal div.modal-footer button:last-of-type").click();
		softAssertEquals(getWebElementByCssSelector("div.sweet-alert h2").getText(), "Thank you for your purchase!",
				"Purchase Greeting message is Incorrect!");
		softAssertEquals(
				getWebElementByCssSelector("div.sweet-alert p").getText(), "Id: 3559451" + "		Amount: 700 USD"
						+ "		Card Number: 12345678901234" + "		Name: Sahib" + "		Date: 19/11/2022",
				"Purchase details are Incorrect!");
		getWebElementByCssSelector("button.confirm").click();
		softAssertEquals(wd.getTitle(), "STORE!", "Title is Incorrect!");
	}

	public WebElement getWebElementByCssSelector(String CssLocator) {
		WebElement element = wd.findElement(By.cssSelector(CssLocator));
		return element;
	}

	/* Method to perform the Click Action on a Web Element */
	public void actionClick(WebElement webElement) {
		action.click(webElement).perform();
	}

	public void setExplicitWait(String CssLocator) {
		wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(CssLocator)));
	}

	public void softAssertEquals(String actualText, String expectedText, String msg) {
		sf.assertEquals(actualText, expectedText, msg);
	}

	@AfterMethod
	public void tearDown() {
		wd.quit();
	}

}

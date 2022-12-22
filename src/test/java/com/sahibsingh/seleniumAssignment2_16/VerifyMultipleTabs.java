package com.sahibsingh.seleniumAssignment2_16;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;
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

public class VerifyMultipleTabs {
	WebDriver wd;
	SoftAssert sf;
	WebDriverWait wdWait;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver",
				"G:\\Pivot Coaching\\Driver\\Chrome Driver 108\\chromedriver.exe");
		wd = new ChromeDriver();
		wd.get("https://seleniumpractise.blogspot.com/2017/07/multiple-window-examples.html");
		wd.manage().window().maximize();
		wdWait = new WebDriverWait(wd, Duration.ofSeconds(20));
		sf = new SoftAssert();
	}

	@Test
	public void validateMultipleTabs() {
		softAssertEquals(getWebElementByCssSelector("h3[itemprop='name']").getText(), "Multiple window examples",
				"Text is Incorrect!");
		String parentWindowHandle = wd.getWindowHandle();
		System.out.println(wd.getTitle() + "\n" + parentWindowHandle);
		getWebElementByCssSelector("a[name='link1']:first-of-type").click();
		softAssertEquals(wd.getTitle(), "Google", "Title is Incorrect!");
		wd.switchTo().window(parentWindowHandle);
		getWebElementByCssSelector("a[name='link1']:nth-of-type(2)").click();
		softAssertEquals(wd.getTitle(), "Facebook - log in or sign up", "Title is Incorrect!");
		wd.switchTo().window(parentWindowHandle);
		getWebElementByCssSelector("a[name='link1']:nth-of-type(3)").click();
		softAssertEquals(wd.getTitle(),
				"Yahoo | Mail, Weather, Search, News, Finance, Sports, Shopping, Entertainment, Video",
				"Title is Incorrect!");
		wd.switchTo().window(parentWindowHandle);
		getWebElementByCssSelector("div.entry-content a:last-of-type").click();
		softAssertEquals(wd.getTitle(), "Facebook - log in or sign up", "Title is Incorrect!");
		Set<String> allWindowHandles = wd.getWindowHandles();
		Iterator<String> iterate = allWindowHandles.iterator();
		while (iterate.hasNext()) {
			String childWindow = iterate.next();
			if (!parentWindowHandle.equalsIgnoreCase(childWindow)) {
				wd.switchTo().window(childWindow);
				System.out.println(wd.switchTo().window(childWindow).getTitle());
				System.out.println(wd.getWindowHandle());
			}
		}
		wd.switchTo().window(parentWindowHandle);
	}

	public WebElement getWebElementByCssSelector(String CssLocator) {
		WebElement element = wd.findElement(By.cssSelector(CssLocator));
		return element;
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

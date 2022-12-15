package com.sahibsingh.seleniumAssignment1;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ECommerceTests {
	WebDriver wd;
	String selectedPhoneName;

	/* Set Up method to setUp webDriver and webSite URL */
	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver",
				"G:\\Pivot Coaching\\Driver\\Chrome Driver 108\\chromedriver.exe");
		wd = new ChromeDriver();
		wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wd.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		wd.manage().window().maximize();
	}

	/* Login method to verify the login functionality */
	@Test
	public void verifyOrderConfirmation() {
		wd.findElement(By.cssSelector("div.well>a")).click();
		WebElement firstNameInputField = wd.findElement(By.cssSelector("input[name='firstname']"));
		WebElement lastNameInputField = wd.findElement(By.cssSelector("input[name='lastname']"));
		WebElement emailInpField = wd.findElement(By.cssSelector("input[name='email']"));
		WebElement telephoneInputField = wd.findElement(By.cssSelector("input[name='telephone']"));
		WebElement passwordInpField = wd.findElement(By.cssSelector("input[name='password']"));
		WebElement confirmPassInputField = wd.findElement(By.cssSelector("input[name='confirm']"));
		WebElement privacyPolicyCheckboxField = wd.findElement(By.cssSelector("input[name='agree']"));
		WebElement continueBtn = wd.findElement(By.cssSelector("div.buttons div.pull-right>input:last-of-type"));

		// send text to an input fields
		firstNameInputField.sendKeys("sahib");
		lastNameInputField.sendKeys("gill");
		String randomEmail = generateRandomEmail();
		emailInpField.sendKeys(randomEmail);
		telephoneInputField.sendKeys("5146217769");
		passwordInpField.sendKeys("admin@1234");
		confirmPassInputField.sendKeys("admin@1234");
		privacyPolicyCheckboxField.click();
		continueBtn.submit();
		String title = wd.getTitle();
		Assert.assertEquals(title, "Your Account Has Been Created!", "Registration Failed");

		// Login with registered account
		wd.findElement(By.cssSelector("div#content div.pull-right>a")).click();
		wd.findElement(By.cssSelector("ul.list-inline li:nth-of-type(2)>a")).click();
		wd.findElement(By.cssSelector("ul.dropdown-menu li:nth-of-type(5)>a")).click();
		wd.findElement(By.cssSelector("div.pull-right>a")).click();
		wd.navigate().to("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		WebElement emailInputField = wd.findElement(By.cssSelector("form div.form-group:first-of-type input"));
		WebElement passwordInputField = wd.findElement(By.cssSelector("form div.form-group:last-of-type input"));
		WebElement loginBtn = wd.findElement(By.cssSelector("input[type='submit']"));
		emailInputField.sendKeys(randomEmail);
		passwordInputField.sendKeys("admin@1234");
		loginBtn.submit();
		String titleOfPage = wd.getTitle();
		Assert.assertEquals(titleOfPage, "My Account", "User is not signed in");
		verifyListElement();// method to verify list item
	}

	private void verifyListElement() {
		List<WebElement> listOfElelements = wd.findElements(By.cssSelector("nav#menu div:last-of-type ul>li"));
		for (int i = 0; i < listOfElelements.size(); i++) {
			if (listOfElelements.get(i).getText().equals("Phones & PDAs")) {
				listOfElelements.get(i).click();
				break;
			}
		}
		selectedPhoneName = wd
				.findElement(By.cssSelector("div.product-layout:last-of-type div.product-thumb>div:nth-of-type(2) a"))
				.getText();
		wd.findElement(By.cssSelector("div.product-layout:last-of-type div.product-thumb>div:nth-of-type(2) a"))
				.click();
		wd.findElement(By.cssSelector("button#button-cart")).click();
		sleep();
		WebElement btnCart = wd.findElement(By.cssSelector("div#cart>button"));
		Assert.assertEquals(btnCart.getText(), "1 item(s) - $337.99", "Invalid Product Details");
		btnCart.click();
		wd.findElement(By.cssSelector("p.text-right a:last-of-type")).click();
		verifyBillingDetails();// method to verify billing details for user
	}

	private void verifyBillingDetails() {
		WebElement firstNameInputField = wd.findElement(By.cssSelector("input[name='firstname']"));
		WebElement lastNameInputField = wd.findElement(By.cssSelector("input[name='lastname']"));
		WebElement companyInputField = wd.findElement(By.cssSelector("input[name='company']"));
		WebElement address1InputField = wd.findElement(By.cssSelector("input[name='address_1']"));
		WebElement address2InputField = wd.findElement(By.cssSelector("input[name='address_2']"));
		WebElement cityInputField = wd.findElement(By.cssSelector("input[name='city']"));
		WebElement postCodeInputField = wd.findElement(By.cssSelector("input[name='postcode']"));

		// send text to an input fields
		firstNameInputField.sendKeys("sahib");
		lastNameInputField.sendKeys("gill");
		companyInputField.sendKeys("learning");
		address1InputField.sendKeys("11 somewhere avenue");
		address2InputField.sendKeys("local");
		cityInputField.sendKeys("toronto");
		postCodeInputField.sendKeys("x1t9vj");
		wd.findElement(By.cssSelector("select[name='country_id']")).click();
		selectElementByVisibleText(wd.findElement(By.cssSelector("select[name='country_id']")), "Canada");
		wd.findElement(By.cssSelector("select[name='zone_id']")).click();
		selectElementByVisibleText(wd.findElement(By.cssSelector("select[name='zone_id']")), "Nunavut");
		wd.findElement(By.cssSelector("input#button-payment-address")).click();
		verifyDeliveryDetails();// method to verify delivery details for user
	}

	private void verifyDeliveryDetails() {
		wd.findElement(By.cssSelector("form.form-horizontal>div.radio input")).click();
		wd.findElement(By.cssSelector("input#button-shipping-address")).click();
		wd.findElement(By.cssSelector("input[name='shipping_method']")).click();
		WebElement inputFieldComment = wd.findElement(By.cssSelector("textarea[name='comment']"));
		inputFieldComment.sendKeys("Please call me before delivery !");
		wd.findElement(By.cssSelector("input#button-shipping-method")).click();
		wd.findElement(By.cssSelector("div#collapse-payment-method div div.radio input")).click();
		wd.findElement(By.cssSelector("input[name='agree']")).click();
		wd.findElement(By.cssSelector("input#button-payment-method")).click();
		WebElement selectedProductName = wd.findElement(By.cssSelector("div.table-responsive>table tbody>tr>td a"));
		Assert.assertEquals(selectedProductName.getText(), selectedPhoneName, "Invalid Product name");
		WebElement productQuantity = wd
				.findElement(By.cssSelector("div.table-responsive>table tbody>tr>td:nth-of-type(3)"));
		Assert.assertEquals(productQuantity.getText(), "1", "Invalid Product Quantity");
		wd.findElement(By.cssSelector("input#button-confirm")).click();
		sleep();
		WebElement confirmationText = wd.findElement(By.cssSelector("#content h1"));
		Assert.assertEquals(confirmationText.getText(), "Your order has been placed!", "Order Not Confirmed");
	}

	@AfterMethod
	public void tearDown() {
		wd.close();
	}

	// method to select element by visible text
	private void selectElementByVisibleText(WebElement element, String text) {
		Select sc = new Select(element);
		sc.selectByVisibleText(text);
	}

	private void sleep() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// method to generate random email
	private String generateRandomEmail() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(1000);
		String randomEmail = "tester" + randomInt + "@pivot.com";
		return randomEmail;
	}
}

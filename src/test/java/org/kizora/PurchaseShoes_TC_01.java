package org.kizora;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.kizora.TestUtils.AndroidBaseTest;
import org.kizora.TestUtils.Log;
import org.kizora.pageObjects.android.CartPage;
import org.kizora.pageObjects.android.ProductCatalogue;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PurchaseShoes_TC_01 extends AndroidBaseTest {
	
	
	@Test(priority = 2)
	public void verifyLoginFunctinality() {
		Log.startTestCase("Login test case started");	
		formPage.setNameField("Rahul");
		Log.info("Name Entered");
		formPage.setGender("male");
		Log.info("Gender Selected");
		formPage.setCountrySelection("Canada");
		Log.info("country selected");
		formPage.submitForm();
		Log.info("form submitted");
		String nextPageTitle=formPage.getTitleProduct();
		Assert.assertEquals(nextPageTitle, "Products");
		Log.endTestCase("login test case end");
				
	}
	
	

	@Test(dataProvider = "getData", groups = { "Smoke" },priority = 1)
	public void verifyPurchaseFunctionality(HashMap<String, String> input) throws InterruptedException {
		Log.startTestCase("verify purchase functionality");
		formPage.setNameField(input.get("name"));
		Log.info("Name entered");
		formPage.setGender(input.get("gender"));
		Log.info("gender selected");
		formPage.setCountrySelection(input.get("country"));
		Log.info("country selected");
		ProductCatalogue productCatalogue = formPage.submitForm();
		Log.info("form submitted");
		productCatalogue.addItemToCartByIndex(0);
		Log.info("first item added");
		productCatalogue.addItemToCartByIndex(0);
		Log.info("second item added");
		CartPage cartPage = productCatalogue.goToCartPage();
		Log.info("clicked on cart page");
		double totalSum = cartPage.getProductsSum();
		double displayFormattedSum = cartPage.getTotalAmountDisplayed();
		AssertJUnit.assertEquals(totalSum, displayFormattedSum);
		cartPage.acceptTermsConditions();
		cartPage.submitOrder();
		Log.endTestCase("verify purchase functionality");

	}

	// @BeforeMethod(alwaysRun=true)
	public void preSetup() {

		formPage.setActivity();

	}

	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data = getJsonData(
				System.getProperty("user.dir") + "\\src\\test\\java\\org\\kizora\\testData\\eCommerce.json");

		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

}

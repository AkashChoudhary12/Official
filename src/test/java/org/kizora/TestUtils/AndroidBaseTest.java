package org.kizora.TestUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.kizora.pageObjects.android.FormPage;
import org.kizora.utils.AppiumUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.model.Log;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class AndroidBaseTest extends AppiumUtils {

	public AndroidDriver driver;
	public AppiumDriverLocalService service;
	public FormPage formPage;
	

	@BeforeMethod(alwaysRun = true)
	public void ConfigureAppium() throws IOException {
		Properties prop = new Properties();
	    DOMConfigurator.configure("log4j.xml");
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\main\\java\\org\\kizora\\resources\\data.properties");
		String ipAddress = System.getProperty("ipAddress") != null ? System.getProperty("ipAddress")
				: prop.getProperty("ipAddress");
		System.out.println(ipAddress);
		prop.load(fis);
		// String ipAddress = prop.getProperty("ipAddress");
		String port = prop.getProperty("port");

		service = startAppiumServer(ipAddress, Integer.parseInt(port)); 

		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName(prop.getProperty("AndroidDeviceNames")); // emulator
		// options.setDeviceName("Android Device");// real device
		options.setChromedriverExecutable("C:\\Chrome_driver\\chromedriver_win32");
		options.setApp(System.getProperty("user.dir")
				+ "\\src\\test\\java\\org\\kizoraTest\\resources\\General-Store.apk");

		driver = new AndroidDriver(service.getUrl(), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		formPage = new FormPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
		service.stop();
	}

}

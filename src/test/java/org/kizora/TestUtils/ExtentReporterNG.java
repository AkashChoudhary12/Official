package org.kizora.TestUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	static ExtentReports extent;
	
	public static ExtentReports getReporterObject()
	{
		
	//	ExtentReports , ExtentSparkReporter this are the classes which are very imp
		String path =System.getProperty("user.dir")+"//reports//index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Android Automation Results");
		reporter.config().setDocumentTitle("Test Results");              // tile of webpage of report
		
		extent =new ExtentReports();      // main to drive our reporting execution
		extent.attachReporter(reporter);   // reporter object need to pass here 
		extent.setSystemInfo("Tester", "Akash Choudhary");
		return extent;
		
	}

	
}

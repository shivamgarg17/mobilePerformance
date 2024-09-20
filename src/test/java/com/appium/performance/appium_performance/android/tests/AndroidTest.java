package com.appium.performance.appium_performance.android.tests;

import com.appium.performance.appium_performance.android.pages.HomePage;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertTrue;
import static utilites.PerformanceManager.*;


public class AndroidTest extends BaseTest {
    public HomePage homePage;
    String appPackage = "com.showtimeapp";

    public AndroidTest() throws IOException {
        homePage = new HomePage();
    }

    @Test(description = "Click on PhoneNo Login",priority = 0)
    @Description("Launching an app")
    public void testGoogleButton() throws IOException {
//        homePage.googleSignup();
        homePage.launchActivity();
        getCPUUsage(appPackage);
        getMemoryInfo(appPackage);
        getBatteryInfo();
        getNetworkUsage();
        getFrameRenderingInfo(appPackage);
    }

    @Test(priority = 2)
    @Description(" Open a streaming channels")
    public void getPhoneNumberActivity() throws IOException {
        homePage.cancelLayout();
        homePage.enterPhoneNumber("9953825948");
        getCPUUsage(appPackage);
        getMemoryInfo(appPackage);
        getBatteryInfo();
        getNetworkUsage();
        getFrameRenderingInfo(appPackage);
    }

//    String cpuUsage = getCPUUsage(appPackage);
//    // Get Memory Info
//    String memoryInfo = getMemoryInfo(appPackage);
//    // Get Battery Info
//    String batteryInfo = getBatteryInfo();
//    // Get Network Usage
//    String networkUsage = getNetworkUsage();
//    // Get Frame Rendering Info (FPS)
//    String frameRenderingInfo = getFrameRenderingInfo(appPackage);


}

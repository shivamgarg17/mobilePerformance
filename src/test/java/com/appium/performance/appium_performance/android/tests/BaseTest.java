package com.appium.performance.appium_performance.android.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static driver.AndroidManager.createAndroidDriver;
import static driver.AndroidManager.quitSession;

public class BaseTest {
    @BeforeClass(alwaysRun = true)
    public void testSetup() {
        createAndroidDriver();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        quitSession();
    }
}

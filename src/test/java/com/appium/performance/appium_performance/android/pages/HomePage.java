package com.appium.performance.appium_performance.android.pages;

import driver.AndroidManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static driver.AndroidManager.getDriver;

public class HomePage extends AndroidManager {

    private WebElement layoutCancelBtn(){
        return getDriver().findElement(AppiumBy.accessibilityId("Cancel"));
    }

    private WebElement selectPhoneNo(){
        return getDriver().findElement(AppiumBy.id("com.showtimeapp:id/signUpWithPhoneNo"));
    }

    public WebElement phoneNumberText(){
        return getDriver().findElement(AppiumBy.id("com.showtimeapp:id/phoneNumberET"));
    }
    private WebElement googleLoginBtn(){
        return getDriver().findElement(AppiumBy.id("com.showtimeapp:id/googleLoginButton"));
    }


    public void googleSignup() {
        googleLoginBtn().isEnabled();
        googleLoginBtn().click();
    }
    public void launchActivity(){
//        getDriver().activateApp(activityName);

        selectPhoneNo().click();
    }
    public void cancelLayout(){
        layoutCancelBtn().click();
//        getDriver().switchTo().frame("com.google.android.gms:id/design_bottom_sheet").close();
    }

    public void enterPhoneNumber(String number) {
        phoneNumberText().isEnabled();
        phoneNumberText().sendKeys(number);
    }
}

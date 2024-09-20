package com.appium.performance.appium_performance.android.pages;

import driver.AndroidManager;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;


import java.util.ArrayList;
import java.util.List;

public class MobileBasePage {
    private static List<Object> mobilePages = new ArrayList<>();

    public MobileBasePage(){
        PageFactory.initElements(new AppiumFieldDecorator(AndroidManager.getDriver()), this);
    }

    /**
     * Maintain List of Page Objects Classes
     *
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getPage(Class<T> clazz) {
        for (Object page : mobilePages) {
            if (page.getClass() == clazz)
                return (T) page;
        }
        T page = null;
        try {
            page = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        mobilePages.add(page);
        return page;
    }

    /**
     * Reset Pages List
     *
     */
    public static void resetPages(){
        mobilePages.removeAll(mobilePages);
    }

}

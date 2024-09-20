package driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import server.AppiumManager;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.net.URL;
import java.time.Duration;

import static server.AppiumManager.*;

public class AndroidManager {
    private static final String APP_PATH = String.valueOf(
            Path.of(System.getProperty("user.dir"), "/src/test/resources/app", "webdriverio-app.apk"));
    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();
    private static final Logger LOG = (Logger) LogManager.getLogger("DriverManager.class");

    public static void createAndroidDriver() {

        try {
            startServer("android");
            //        setDriver (new AndroidDriver (getService ().getUrl (), geckoOptionsFirefox ()));
            setDriver(new AndroidDriver(new URI("http://127.0.0.1:4723/wd/hub").toURL(), uiAutomator2OptionsProverbial()));
            setupDriverTimeouts();
        }
        catch (MalformedURLException | URISyntaxException e) {
            LOG.error(e.getMessage());
        }
    }

    public static AndroidDriver getDriver() {
        return AndroidManager.DRIVER.get();
    }

    public static void quitSession() {
        if (null != DRIVER.get()) {
            LOG.info("Closing the driver...");
            getDriver().quit();
            DRIVER.remove();
            stopServer();
        }
    }

    private static void setDriver(final AndroidDriver driver) {
        AndroidManager.DRIVER.set(driver);
    }

    private static void setupDriverTimeouts() {
        getDriver().manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(5));
    }

    //    private static DesiredCapabilities setCapabilities() {
    //        DesiredCapabilities capabilities = new DesiredCapabilities();
    //        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
    //        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_5_API_30");
    //        capabilities.setCapability(MobileCapabilityType.APP, APP_PATH);
    //        capabilities.setCapability("appPackage", "com.wdiodemoapp");
    //        capabilities.setCapability("appActivity", "com.wdiodemoapp.MainActivity");
    //        capabilities.setCapability("noReset", false);
    //        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
    //        return capabilities;
    //    }

//    private static UiAutomator2Options uiAutomator2OptionsChrome() {
//
//        final UiAutomator2Options uiAutomator2Options;
//        uiAutomator2Options = new UiAutomator2Options().setAvd("Pixel_6_API_34")
//                .setAvdLaunchTimeout(Duration.ofSeconds(300))
//                .setAvdReadyTimeout(Duration.ofSeconds(100))
//                .setDeviceName("Pixel_6_API_34")
//                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
//                .withBrowserName("chrome")
//                .setAutoGrantPermissions(true)
//                .setNoReset(false);
//
//        return uiAutomator2Options;
//    }

    private static UiAutomator2Options uiAutomator2OptionsProverbial() {

        final UiAutomator2Options uiAutomator2Options;
        uiAutomator2Options = new UiAutomator2Options().setAvd("Pixel_8a_API_35")
                .setAvdLaunchTimeout(Duration.ofSeconds(500))
                .setAvdReadyTimeout(Duration.ofSeconds(500))
                .setDeviceName("Pixel_8a_API_35")
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
//                .setApp(APP_PATH)
                .setAutoGrantPermissions(true)
                .setAppPackage("com.showtimeapp")
                .setAppActivity("com.pocketaces.ivory.view.activities.SignUpActivity")
                .setNoReset(false);
        return uiAutomator2Options;
    }

}

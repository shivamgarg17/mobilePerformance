package utilites;

import io.qameta.allure.Attachment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PerformanceManager {

    // Add ADB command methods here (e.g. getCPUUsage, getMemoryInfo, etc.)
    @Attachment(value = "{0}", type = "text/plain")
    public static String addToAllureReport(String attachmentName, String data) {
        return data;
    }

    public static void getCPUUsage(String packageName) throws IOException {
        String command = "adb shell top -n 1 | grep " +packageName;
        addToAllureReport("CPU Usage For: "+ packageName,executeCommand(command));
    }

    public static void getMemoryInfo(String packageName) throws IOException {
        String command = "adb shell dumpsys meminfo " +packageName;
        addToAllureReport("Memory Usage For: "+packageName,executeCommand(command));
    }

    public static void getBatteryInfo() throws IOException {
        String command = "adb shell dumpsys batterystats";
        addToAllureReport("Battery Usage",executeCommand(command));
    }

    public static void getNetworkUsage() throws IOException {
        String command = "adb shell cat /proc/net/xt_qtaguid/stats";
        addToAllureReport("Network usage",executeCommand(command));
    }

    public static void getFrameRenderingInfo(String packageName) throws IOException {
        String command = "adb shell dumpsys gfxinfo "+ packageName;
        addToAllureReport("Memory Usage For: "+packageName,executeCommand(command));
    }

    public static String executeCommand(String command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        return output.toString();
    }
}

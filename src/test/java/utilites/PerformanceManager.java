package utilites;

import com.google.gson.Gson;
import io.qameta.allure.Attachment;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PerformanceManager {

    // Add ADB command methods here (e.g. getCPUUsage, getMemoryInfo, etc.)
    @Attachment(value = "{0}", type = "text/plain")
    public static String addToAllureReport(String attachmentName, String data) {
        return data;
    }
    @Attachment(value = "{0}", type = "application/json")
    public static String saveJsonData(String attachmentName, Map<String, String> data) throws IOException {
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);
        try (FileWriter file = new FileWriter("target/allure-results/" + attachmentName + ".json")) {
            file.write(jsonData);
        }
        return jsonData;
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
        String frameUsage = executeCommand(command);
        Map<String,String> data = new HashMap<>();
        data.put("FrameUsage",frameUsage);
        saveJsonData("Memory Usage For: "+packageName,data);
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

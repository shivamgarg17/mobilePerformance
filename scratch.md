To display performance metrics (such as CPU, memory, battery, and network usage) as a **Dashboard in Allure** reports, you'll need to use the **Allure Dashboard Plugin** and modify your Allure report to include custom widgets or charts that present the data in a dashboard format.

### Steps to Add Performance Metrics to the Allure Dashboard

1. **Install the Allure Dashboard Plugin**:
    - The **Allure Dashboard Plugin** provides a visual dashboard to your reports with customizable widgets. To use it, you need to add the plugin dependency in your project.
    - Add the following in your `pom.xml` for Maven:

   ```xml
   <dependency>
       <groupId>io.qameta.allure</groupId>
       <artifactId>allure-plugin</artifactId>
       <version>2.13.8</version> <!-- Ensure it's compatible with your Allure version -->
   </dependency>
   ```

2. **Add Allure Dashboard Plugin to Your Allure Configuration**:
   Download and install the Allure Dashboard Plugin by running:

   ```bash
   allure plugin install allure-dashboard
   ```

   This will automatically integrate the dashboard functionality in your Allure reports.

3. **Create Custom Data Attachments**:
   To create a custom dashboard widget, you need to save your performance metrics data in a format that Allure can parse and visualize. One way to do this is to save the performance metrics as JSON files.

4. **Generate and Attach JSON Metrics Files**:
   Modify your existing methods to save the performance metrics in JSON format and add them as attachments in the Allure report. Here’s how to modify the Java code:

#### Modified Java Code to Save Performance Data in JSON

```java
import com.google.gson.Gson;
import io.qameta.allure.Attachment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PerformanceMetricsAppium {

    // Add Allure Dashboard JSON Data
    @Attachment(value = "{0}", type = "application/json")
    public static String saveJsonData(String attachmentName, Map<String, String> data) throws IOException {
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);
        try (FileWriter file = new FileWriter("target/allure-results/" + attachmentName + ".json")) {
            file.write(jsonData);
        }
        return jsonData;
    }

    // CPU usage with Allure attachment and saving to JSON
    public static String getCPUUsage(String packageName) throws IOException {
        String command = "adb shell top -n 1 | grep " + packageName;
        String cpuUsage = executeCommand(command);
        Map<String, String> data = new HashMap<>();
        data.put("CPU Usage", cpuUsage);
        saveJsonData("cpu_usage", data); // Save data as JSON
        return cpuUsage;
    }

    // Memory info with Allure attachment and saving to JSON
    public static String getMemoryInfo(String packageName) throws IOException {
        String command = "adb shell dumpsys meminfo " + packageName;
        String memoryInfo = executeCommand(command);
        Map<String, String> data = new HashMap<>();
        data.put("Memory Info", memoryInfo);
        saveJsonData("memory_info", data); // Save data as JSON
        return memoryInfo;
    }

    // Battery stats with Allure attachment and saving to JSON
    public static String getBatteryInfo() throws IOException {
        String command = "adb shell dumpsys batterystats";
        String batteryInfo = executeCommand(command);
        Map<String, String> data = new HashMap<>();
        data.put("Battery Info", batteryInfo);
        saveJsonData("battery_info", data); // Save data as JSON
        return batteryInfo;
    }

    // Network usage with Allure attachment and saving to JSON
    public static String getNetworkUsage() throws IOException {
        String command = "adb shell cat /proc/net/xt_qtaguid/stats";
        String networkUsage = executeCommand(command);
        Map<String, String> data = new HashMap<>();
        data.put("Network Usage", networkUsage);
        saveJsonData("network_usage", data); // Save data as JSON
        return networkUsage;
    }

    // FPS rendering info with Allure attachment and saving to JSON
    public static String getFrameRenderingInfo(String packageName) throws IOException {
        String command = "adb shell dumpsys gfxinfo " + packageName;
        String frameRenderingInfo = executeCommand(command);
        Map<String, String> data = new HashMap<>();
        data.put("Frame Rendering Info (FPS)", frameRenderingInfo);
        saveJsonData("frame_rendering_info", data); // Save data as JSON
        return frameRenderingInfo;
    }

    // Method to execute ADB commands
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
```

### 5. **Customizing Dashboard Widgets for Allure**:
Allure allows you to create custom widgets using JSON data. Once the performance metrics are stored in JSON format, they will be visible in the "Attachments" section. However, to show them as charts or widgets on the dashboard, you need to configure Allure widgets.

To do this:

- You will need to write a custom Allure plugin that processes the attached JSON files and visualizes them.
- You can refer to the [Allure Plugin documentation](https://docs.qameta.io/allure/#_plugins) to learn how to create custom plugins for Allure.

### Example Widget Configuration:

In your plugin's configuration (if you create one), you could define a custom widget for performance metrics. For example:

```json
{
  "type": "widget",
  "name": "Performance Metrics",
  "description": "Shows CPU, Memory, and Network Usage",
  "data": [
    {
      "name": "CPU Usage",
      "source": "cpu_usage.json"
    },
    {
      "name": "Memory Info",
      "source": "memory_info.json"
    },
    {
      "name": "Network Usage",
      "source": "network_usage.json"
    }
  ],
  "layout": {
    "rows": 2,
    "columns": 2
  }
}
```

### 6. **Generate the Allure Report**:
Once the test has been executed and the performance metrics have been collected:

1. Run the tests using:

   ```bash
   mvn clean test
   ```

2. Generate the Allure report using:

   ```bash
   mvn allure:report
   ```

3. Serve the Allure report to view it in the browser:

   ```bash
   mvn allure:serve
   ```

The report will now contain your performance metrics in the dashboard, presented as charts or tables based on the attached JSON data.

### Conclusion

By integrating the **Allure Dashboard Plugin** and saving your performance metrics as JSON files, you can display custom dashboards in the Allure reports. For further customization, you can create custom widgets or plugins that process the JSON data and show it in a more visual format like graphs or charts.

Let me know if you need additional guidance for setting up custom Allure plugins or widgets for advanced reporting!
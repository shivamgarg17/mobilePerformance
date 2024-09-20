Framework Details

Run the Test and Generate Allure Reports

Once you have written the test cases with Allure annotations and attachments, follow these steps to generate the Allure report:

	1.	Run the Tests:Execute the tests using your preferred method (IDE, Maven, etc.).
    2.	Generate Allure Report: Use the following Maven commands to generate and serve the Allure report:

1. mvn clean test
2. mvn allure:report
3. mvn allure:serve
     
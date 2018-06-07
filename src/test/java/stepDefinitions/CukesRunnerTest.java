package stepDefinitions;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import utils.UtilProperty;
import java.io.IOException;

/******************************************************************************
 Class Name: CukesRunnerTest
 Cucumber - JUnit Runner Class
 ******************************************************************************/

//To execute through command line, use: mvn test -Dcucumber.options="--tags @Test"
//OR
//To execute through command line, use: mvn -D"cucumber.options=--tags @Test" test

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true,
				features={"src/test/resources/feature/"},
//				plugin = {"pretty", "html:Reports/cucumber-html-report" },
				plugin = {"html:Reports/cucumber-html-report",
				        "json:Reports/cucumber.json", "pretty:Reports/cucumber-pretty"},
				tags={"@con"})//, tags={"@Test2"})
public class CukesRunnerTest {

    @BeforeClass
    public static void setUp() {

        //Disable popup blocker for IE via registry edit
        if (UtilProperty.browserType.contains("ie")) {
            try {
                Runtime.getRuntime().exec("REG ADD \"HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\New Windows\" /F /V \"PopupMgr\" /T REG_DWORD /D \"0\"");
            } catch (Exception e) {
                System.out.println("Error occurred when attempting to disable popup blocker for Internet Explorer");
            }
        }
    }

    @AfterClass
    public static void cleanUp() throws IOException {
        System.out.println("After Class!!");
        Hooks.closeBrowser();
    }
}

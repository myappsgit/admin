package utils;

import automationExceptions.ConnectionInterruptedException;
import automationExceptions.ElementsFileNotFound;
import wrapper.SeleniumFunctions;
import constants.GlobalConstants;
import frames.Global_Frames;
import stepDefinitions.Hooks;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.management.ManagementFactory;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/******************************************************************************
 Class Name: SetUpFile
 Contains functions related to set-up
 ******************************************************************************/

public class SetUpFile {

    public String className = getClass().getSimpleName();
    private static SetUpFile setUpFile = null;

    /**************************************************************************
     * name: SetUpFile()
     * functionality: Constructor to initialize json object and frames
     *************************************************************************/
    public SetUpFile(){
        UtilFunctions.log("Class: " + className + "; Method: constructor");
        initializeFramesMap();
        initializeElementJSONFiles();
    }

    /**************************************************************************
     * name: initializeFramesMap()
     * functionality: Function to initialize hash maps for frames and values
     * for each page
     * return: void
     *************************************************************************/
    public void initializeFramesMap(){
        Global_Frames.setGlobalFramesMap();
    }

    /**************************************************************************
     * name: getSetUpFileObj()
     * functionality: Function to get setup file obj
     * return: setup file object
     *************************************************************************/
    public static SetUpFile getSetUpFileObj(){
        if (setUpFile == null)
            setUpFile = new SetUpFile();
        return setUpFile;
    }

    /**************************************************************************
     * name: initializeElementJSONFiles
     * functionality: Function to initialize json file for each page
     * return: void
     *************************************************************************/
    public void initializeElementJSONFiles(){
        JSONParser parser = new JSONParser();

        try {
            UtilFunctions.log("Initializing JSON files");
            //Initializing Element repository
            GlobalConstants.jsonObjLoginElements = (JSONObject)parser.parse(new FileReader(UtilProperty.sMainDir + "\\src\\test\\java\\elementRepository\\Login_Elements.json"));
            GlobalConstants.jsonObjGlobalElements = (JSONObject)parser.parse(new FileReader(UtilProperty.sMainDir + "\\src\\test\\java\\elementRepository\\Global_Elements.json"));
            GlobalConstants.jsonObjServiceProviderElements = (JSONObject)parser.parse(new FileReader(UtilProperty.sMainDir + "\\src\\test\\java\\elementRepository\\ServiceProvider_Elements.json"));
            GlobalConstants.jsonObjConsumerElements = (JSONObject)parser.parse(new FileReader(UtilProperty.sMainDir + "\\src\\test\\java\\elementRepository\\Consumer_Elements.json"));
            GlobalConstants.jsonObjAdvisorElements = (JSONObject)parser.parse(new FileReader(UtilProperty.sMainDir + "\\src\\test\\java\\elementRepository\\Advisor_Elements.json"));
            GlobalConstants.jsonObjAdminElements = (JSONObject)parser.parse(new FileReader(UtilProperty.sMainDir + "\\src\\test\\java\\elementRepository\\Admin_Elements.json"));
        } catch (IOException e) {
            try{
                throw new ElementsFileNotFound("The Json file containing the elements information is not found");
            }
            catch (ElementsFileNotFound p){
                UtilFunctions.log("ElementsFileNotFound " + p.getMessage());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            UtilFunctions.log("ParseException" + e.getMessage());
        }
    }


    /**
     * ***********************************************************************
     * name: setUpBrowser(WebDriver driver, String userName)
     * functionality: Function to open and login to browser
     * param: WebDriver driver - WebDriver object
     * param: String userName - Name of user
     * return: returns driver object
     * ***********************************************************************
     */
    public WebDriver setUpBrowser(WebDriver driver, String userName, String password) throws ConnectionInterruptedException {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        String driverProcessName = "";
        String browserProcessName = "";
        if (UtilProperty.browserType.contains("ie")) {
            //kill existing IE browsers
            try {
                Runtime.getRuntime().exec("Taskkill /F /IM iexplore.exe /T");
            }
            catch(Exception e){
                //add some code
            }
            driver = new InternetExplorerDriver();
            driverProcessName = "IEDriverServer.exe";
            browserProcessName = "iexplore.exe";
            UtilFunctions.log("InternetExplorerDriver initialized");
        } else if (UtilProperty.browserType.equals("firefox")) {
//        	FirefoxProfile profile = new FirefoxProfile();
//        	profile.setPreference("dom.popup_maximum", 0);
//        	profile.setPreference("privacy.popups.showBrowserMessage", false);
//        	driver = new FirefoxDriver(profile);

//            ProfilesIni profile1 = new ProfilesIni();
//            System.setProperty("webdriver.firefox.marionette", "false");
//            FirefoxProfile myprofile = profile1.getProfile("automationTest");
//            driver = new FirefoxDriver(myprofile);
            
            //try this if profile doesn't work
        	FirefoxOptions ffOptions = new FirefoxOptions();
        	ffOptions.addPreference("dom.popup_maximum", 0);
        	ffOptions.addPreference("privacy.popups.showBrowserMessage", false);
        	driver = new FirefoxDriver(ffOptions);
            driverProcessName = "geckodriver.exe";
            browserProcessName = "firefox.exe";
            UtilFunctions.log("Firefox driver initialized");
        } else if (UtilProperty.browserType.equals("edge")) {
//        	 EdgeOptions eOptions = new EdgeOptions();
        	 DesiredCapabilities capabilities = DesiredCapabilities.edge();
        	 driver = new EdgeDriver(capabilities);

  
            driverProcessName = "MicrosoftWebDriver.exe";
            browserProcessName = "MicrosoftEdgeCP.exe";
            UtilFunctions.log("Edge Browser initialized");
        } else {
            UtilProperty.browserType = "chrome";
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-popup-blocking");
            options.addArguments("test-type");
            options.addArguments("start-maximized");
            options.addArguments("--js-flags=--expose-gc");
            options.addArguments("--enable-precise-memory-info");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-default-apps");
            options.addArguments("--disable-extensions");
            options.addArguments("test-type=browser");    
            options.addArguments("disable-infobars");
            driver = new ChromeDriver(options);
            driverProcessName = "chromedriver.exe";
            browserProcessName = "chrome.exe";
            UtilFunctions.log("ChromeDriver initialized");
        }

        driver.manage().window().maximize();

        String jvmProcessId = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        if (jvmProcessId != null) {
            String webDriverProcessId = getChildProcessID(jvmProcessId, driverProcessName);
            GlobalConstants.browserProcessId = getChildProcessID(webDriverProcessId, browserProcessName);
        }

        Hooks.setDriver(driver);

        openURL(driver);
        try {
        	//update this to be application independent
        	driver.findElement(By.xpath("//a[@class='topMenuRound' and text()='Login']")).click();
        	//---------------
            logInApplication(driver, userName, password);
        } catch (InterruptedException e) {
            throw new ConnectionInterruptedException("Error while logging in the application");
        }
        SeleniumFunctions.switchToNewWindow(driver);
        return driver;
    }

    /**
     * ***********************************************************************
     * name: launchApplication(WebDriver driver)
     * functionality: Function to open application
     * param: WebDriver driver - WebDriver object
     * return: returns driver object
     * ***********************************************************************
     */
    public WebDriver launchApplication() throws ConnectionInterruptedException {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        WebDriver driver;
        String driverProcessName = "";
        String browserProcessName = "";
        if (UtilProperty.browserType.contains("ie")) {
            //kill existing IE browsers
            try {
                Runtime.getRuntime().exec("Taskkill /F /IM iexplore.exe /T");
            }
            catch(Exception e){
                //add some code
            }
            driver = new InternetExplorerDriver();
            driverProcessName = "IEDriverServer.exe";
            browserProcessName = "iexplore.exe";
            UtilFunctions.log("InternetExplorerDriver initialized");
        } else if (UtilProperty.browserType.equals("firefox")) {
//        	FirefoxProfile profile = new FirefoxProfile();
//        	profile.setPreference("dom.popup_maximum", 0);
//        	profile.setPreference("privacy.popups.showBrowserMessage", false);
//        	driver = new FirefoxDriver(profile);

            ProfilesIni profile1 = new ProfilesIni();

            FirefoxProfile myprofile = profile1.getProfile("automationTest");
            driver = new FirefoxDriver(myprofile);
            
            //try this if profile doesn't work
//        	FirefoxOptions ffOptions = new FirefoxOptions();
//        	ffOptions.addPreference("dom.popup_maximum", 0);
//        	ffOptions.addPreference("privacy.popups.showBrowserMessage", false);
//        	driver = new FirefoxDriver(ffOptions);
            driverProcessName = "geckodriver.exe";
            browserProcessName = "firefox.exe";
            UtilFunctions.log("Firefox driver initialized");
        } else if (UtilProperty.browserType.equals("edge")) {
//        	 EdgeOptions eOptions = new EdgeOptions();
        	 DesiredCapabilities capabilities = DesiredCapabilities.edge();
        	 driver = new EdgeDriver(capabilities);

  
            driverProcessName = "MicrosoftWebDriver.exe";
            browserProcessName = "MicrosoftEdgeCP.exe";
            UtilFunctions.log("Edge Browser initialized");
        } else {
            UtilProperty.browserType = "chrome";
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-popup-blocking");
//            options.addArguments("test-type");
//            options.addArguments("start-maximized");
//            options.addArguments("--js-flags=--expose-gc");
//            options.addArguments("--enable-precise-memory-info");
//            options.addArguments("--disable-default-apps");
            options.addArguments("--disable-extensions");
//            options.addArguments("test-type=browser");    
//            options.addArguments("disable-infobars");
            driver = new ChromeDriver(options);
            driverProcessName = "chromedriver.exe";
            browserProcessName = "chrome.exe";
            UtilFunctions.log("ChromeDriver initialized");
        }

        driver.manage().window().maximize();

        String jvmProcessId = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        if (jvmProcessId != null) {
            String webDriverProcessId = getChildProcessID(jvmProcessId, driverProcessName);
            GlobalConstants.browserProcessId = getChildProcessID(webDriverProcessId, browserProcessName);
        }

        Hooks.setDriver(driver);

        openURL(driver);
        SeleniumFunctions.switchToNewWindow(driver);
        return driver;
    }
    
    /**************************************************************************
     * name: openURL(WebDriver driver)
     * functionality: Function to open given url
     * param: WebDriver driver - WebDriver object
     * return: void
     *************************************************************************/
    public void openURL(WebDriver driver){
        UtilFunctions.log("Class: " + className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
        try{
            driver.get(UtilProperty.url);
        }
        catch (Exception e){
            UtilFunctions.log("URL: "+ UtilProperty.url + " not present. ABORTING!!");
            Assert.assertTrue("URL not present", false);
        }
//        SeleniumFunctions.explicitWait(driver, GlobalConstants.TEN, Global_Frames.globalFramesMap.get("PARENT_FRAME") + ";id");
//
//        if (SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(Global_Frames.globalFramesMap.get("PARENT_FRAME") + ";id")) == null){
//            Assert.assertTrue("URL: " + UtilProperty.url + " does not work.", false);
//        }
//
//        SeleniumFunctions.switchToFrame(driver, SeleniumFunctions.setByValues(Global_Frames.globalFramesMap.get("PARENT_FRAME") + ";name"));
    }


    /**************************************************************************
     * Function to login to application
     *
     * @param driver WebDriver object
     * @param userName name of user to login as
     * @param password password for user
     *************************************************************************/
    public void logInApplication(WebDriver driver, String userName, String password) throws ConnectionInterruptedException {
        UtilFunctions.log("Class: " + className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
        //Use default username or password if null/empty values passed
        if (StringUtils.isEmpty(userName))
            userName = UtilProperty.userName;
        if (StringUtils.isEmpty(password))
            password = UtilProperty.userPwd;
        
        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName("Login");
        String unTextField = UtilFunctions.getElementFromJSONFile(fileObj, "TEXT_FIELDS.Username", "id");
        String pwTextField = UtilFunctions.getElementFromJSONFile(fileObj, "TEXT_FIELDS.Password", "id");
        String login = UtilFunctions.getElementFromJSONFile(fileObj, "BUTTONS.Login_Submit", "id");
        
        WebElement unElt = SeleniumFunctions.findElement(driver, By.id(unTextField));
        unElt.clear();
        unElt.sendKeys(userName);
        UtilFunctions.log("UserName: " + userName + " entered");
        WebElement pwElt = SeleniumFunctions.findElement(driver, By.id(pwTextField));
        pwElt.click();
        pwElt.clear();
        pwElt.sendKeys(password);
        UtilFunctions.log("Password: " + password + " entered");
                
        WebElement loginButton = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(UtilFunctions.getElementFromJSONFile(GlobalConstants.jsonObjGlobalElements, "BUTTONS.ConLoginSubmit", "path")));
//        WebElement loginButton = SeleniumFunctions.findElement(driver, By.id(login));
//        Actions action = new Actions(driver);
//        action.moveToElement(loginButton).build().perform();

        loginButton.click();
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            throw new ConnectionInterruptedException("Error while logging in the application");
        }
        UtilFunctions.log("Login button clicked");
    }


    /**
     * ***********************************************************************
     * Helper function to get the process ID of a child process
     *
     * @param parentID process ID of parent process
     * @param childProcessName name of child process to find
     * @return the process ID of the child process with name matching childProcessName
     * ***********************************************************************
     */
    public String getChildProcessID(String parentID, String childProcessName) {
        try {
            Scanner scan = new Scanner(Runtime.getRuntime().exec("wmic process where (ParentProcessId=" + parentID + ") get Caption,ProcessId").getInputStream());
            scan.useDelimiter("\\A");
            String childProcessIds = scan.hasNext() ? scan.next() : "";
            String[] stringTokens = childProcessIds.split("\\s+");
            for (int i = 0; i < stringTokens.length; i = i + 2) {
                if (childProcessName.equalsIgnoreCase(stringTokens[i])) {
                    scan.close();
                    return stringTokens[i + 1];
                }
            }
            scan.close();
            UtilFunctions.log("No child process " + childProcessName + " found for parent process " + parentID + ".");
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            UtilFunctions.log("Error getting child PID for " + childProcessName + " from parent process " + parentID + ". " + e.getMessage());
            return "";
        }
    }
}
package wrapper;

import constants.GlobalConstants;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.UtilFunctions;

import java.util.List;
import java.util.Set;

import static stepDefinitions.Hooks.driver;


/******************************************************************************
Class Name: SeleniumFunctions
Contains functions related to Selenium APIs
******************************************************************************/

public class SeleniumFunctions {

    public String className = getClass().getSimpleName();
    private static SeleniumFunctions seleniumFunctions = new SeleniumFunctions();

    public static String parentWindow;
    public static String portalWindow;

    /**************************************************************************
     * name: findElement(WebDriver driver, By value)
     * functionality: Returns the object of element with driver object
     * param: WebDriver driver - WebDriver object
     * param: By value - By value of object based on xpath, id or name, etc
     * return: returns the element object
     *************************************************************************/
    public static WebElement findElement(WebDriver driver, By value){
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        try {
        	explicitWait(driver, GlobalConstants.ONE, "", value);
            return driver.findElement(value);
        }
        catch (Exception e){
            e.printStackTrace();
            UtilFunctions.log("Element not found. Returning null. Exception: " + e.getMessage());
            return null;
        }
    }


    /**************************************************************************
     * name: findElements(WebDriver driver, By value)
     * functionality: Get list of elements obtained from driver object
     * param: WebDriver driver - WebDriver object
     * param: By value - By value of object based on xpath, id or name, etc
     * return: returns list of elements obtained from driver object
     *************************************************************************/
    public static List<WebElement> findElements(WebDriver driver, By value){
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        try {
        	explicitWait(driver, GlobalConstants.ONE, "", value);
            return driver.findElements(value);
        }
        catch (Exception e){
            e.printStackTrace();
            UtilFunctions.log("Element not found. Returning null. Exception: " + e.getMessage());
            return null;
        }
    }


    /**************************************************************************
     * name: findElementByWebElement(WebElement element, By value)
     * functionality: Function tries twice to fetch not null element object.
     * Returns the object of element with element object
     * param: WebElement element - WebElement object
     * param: By value - By value of object based on xpath, id or name, etc
     * return: returns the element object
     *************************************************************************/
    public static WebElement findElementByWebElement(WebElement element, By value){
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        int findCnt = 0;
        WebElement elementObj;
        while (true){
            findCnt++;
            try {
            	explicitWait(driver, GlobalConstants.ONE, "", value);
                elementObj = element.findElement(value);
                UtilFunctions.log("Element found. Returning element: " + elementObj.getText());
                break;
            }
            catch (Exception e){
                e.printStackTrace();
                elementObj = null;
                if (findCnt > 1) {
                    UtilFunctions.log("Element not found. Returning null. Exception: " + e.getMessage());
                    break;
                }
            }
        }
        return elementObj;
    }


    /**************************************************************************
     * name: findElementsByWebElement(WebElement element, By value)
     * functionality: Returns the object of elements with element object
     * param: WebElement element - WebElement object
     * param: By value - By value of object based on xpath, id or name, etc
     * return: returns the list of element object
     *************************************************************************/
    public static List<WebElement> findElementsByWebElement(WebElement element, By value){
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        try {
        	explicitWait(driver, GlobalConstants.ONE, "", value);
            return element.findElements(value);
        }
        catch (Exception e){
            e.printStackTrace();
            UtilFunctions.log("Element not found. Returning null. Exception: " + e.getMessage());
            return null;
        }
    }


    /**************************************************************************
     * name: explicitWait(WebDriver driver, int timeOut, String element,
     * By... eltByValue)
     * functionality: Explicit wait until object gets visible (Max timeout -
     * 10 seconds)
     * param: WebDriver driver - WebDriver object
     * param: int timeOut - time to wait
     * param: String element - Locator + Locator type (xpath, id, etc)
     * param: By... eltByValue - By value of object based on xpath, id or
     * name, etc. Optional variable
     * return: void
     *************************************************************************/
    public static void explicitWait(WebDriver driver, int timeOut, String element, By... eltByValue){
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        UtilFunctions.log("frame: " + element);

        String[] propArr = element.split(";");
        String value = "";
        String searchCriteria = "";
        if (propArr.length > 1) {
            value = propArr[0];
            searchCriteria = propArr[1];
        }
        By locator;
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        if (eltByValue.length == 0) {
            switch (searchCriteria) {
                case "xpath":
                    locator = By.xpath(value);
                    break;
                case "path":
                    locator = By.xpath(value);
                    break;
                case "name":
                    locator = By.name(value);
                    break;
                case "id":
                    locator = By.id(value);
                    break;
                case "className":
                    locator = By.className(value);
                    break;
                default:
                    locator = null; //Need to edit default criteria
                    break;
            }
        }
        else
            locator  = eltByValue[0];
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }
        catch (Exception e){
            e.printStackTrace();
            UtilFunctions.log("Element not found after timeout of " + timeOut + " seconds. Exception: " + e.getMessage());
        }
    }

    /**************************************************************************
     * name: explicitWaitForInvisibility(WebDriver driver, int timeOut,
     * String element, By... eltByValue)
     * functionality: Explicit wait until object gets invisible (Max timeout -
     * 10 seconds)
     * param: WebDriver driver - WebDriver object
     * param: int timeOut - time to wait
     * param: String element - Locator + Locator type (xpath, id, etc)
     * param: By... eltByValue - By value of object based on xpath, id or
     * name, etc. Optional variable
     * return: void
     *************************************************************************/
    public static void explicitWaitForInvisibility(WebDriver driver, int timeOut, String element, By... eltByValue){
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        UtilFunctions.log("frame: " + element);

        String[] propArr = element.split(";");
        String value = "";
        String searchCriteria = "";
        if (propArr.length > 1) {
            value = propArr[0];
            searchCriteria = propArr[1];
        }
        By locator;
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        if (eltByValue.length == 0) {
            switch (searchCriteria) {
                case "xpath":
                    locator = By.xpath(value);
                    break;
                case "path":
                    locator = By.xpath(value);
                    break;
                case "name":
                    locator = By.name(value);
                    break;
                case "id":
                    locator = By.id(value);
                    break;
                case "className":
                    locator = By.className(value);
                    break;
                default:
                    locator = null; //Need to edit default criteria
                    break;
            }
        }
        else
            locator  = eltByValue[0];
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**************************************************************************
     * name: switchToFrame(WebDriver driver, By value)
     * functionality: Function used to switch frames. Explicit wait before
     * switching to each element
     * param: WebDriver driver - WebDriver object
     * param: By value - By value of object based on xpath, id or name, etc
     * return: driver object of new frame
     *************************************************************************/
    public static WebDriver switchToFrame(WebDriver driver, By value){
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        WebElement element;
        try {
            explicitWait(driver, GlobalConstants.ONE, "", value);
            element = driver.findElement(value);
            return driver.switchTo().frame(element);
        }
        catch (Exception exception){
            exception.printStackTrace();
            UtilFunctions.log("Element not found for switching frames. Exception: " + exception.getMessage());
            return null;
        }
    }

    /**************************************************************************
     * name: selectFrame(WebDriver driver, String frameName,
     * String identifierType)
     * functionality: Function used to separate list of frames and invoke
     * switchToFrame function one at a time
     * param: WebDriver driver - WebDriver object
     * param: String frameName - String containing the frame hierarchy
     * separated by "."
     * param: String identifierType - xpath, id, name, etc
     * return: void
     *************************************************************************/
    public static void selectFrame(WebDriver driver, String frameName, String identifierType) {
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        UtilFunctions.log("frameName: " + frameName);
        UtilFunctions.log("identifierType: " + identifierType);
        GlobalConstants.setGlobalFrameValue(frameName);
        String[] frameArray = frameName.split("\\.");
        driver.switchTo().defaultContent();
        if(frameName.equals("NO_FRAME"))
            return;
        for (String frame : frameArray) {
            System.out.println("Frame Name :: " + frame);
            UtilFunctions.log("FrameName: " + frame);
            if (frame.startsWith("**")) {
                frame = frame.replace("**", "");
                UtilFunctions.log("Frame name is dynamic. Switching using xpath/element.");
                if (switchToFrame(driver, setByValues("//iframe[starts-with(@id, '" + frame + "')]" + ";xpath")) == null) {
                    UtilFunctions.log("Dynamic value is null when searched using xpath. Using identifierType 'element name' now.");
                    switchToFrame(driver, setByValues("//frame[@name='" + frame + "']" + ";xpath"));
                }
            } else if (switchToFrame(driver, setByValues(frame + ";" + identifierType)) == null) {
                UtilFunctions.log("identifierType: " + identifierType + " value is null. Using identifierType 'name' now.");
                switchToFrame(driver, setByValues(frame + ";name"));
            }
        }
//        }
    }

    /**************************************************************************
     * name: setByValues(String propField)
     * functionality: Create By values based on locator and locator type
     * param: String propField - Locator + Locator type (xpath, id, etc)
     * return: returns By object
     *************************************************************************/
    public static By setByValues(String propField){
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        UtilFunctions.log("propField: " + propField);
        String switchValue;
        String switchType;
        String[] byType;
        if (propField.contains(";")){
            byType = propField.split(";");
            switchType = byType[1];
            switchValue = byType[0];
        }
        else{
            switchType = "xpath";
            switchValue = propField;
        }
        UtilFunctions.log("switchType: " + switchType);
        UtilFunctions.log("switchValue: " + switchValue);
        switch(switchType){
            case "xpath":
                return By.xpath(switchValue);
            case "name":
                return By.name(switchValue);
            case "id":
                return By.id(switchValue);
            case "tagName":
                return By.tagName(switchValue);
            case "className":
                return By.className(switchValue);
            default:
                return null;
        }
    }


    /**************************************************************************
     * name: switchToNewWindow(WebDriver driver)
     * functionality: Function used to switch to a new window
     * param: WebDriver driver - WebDriver object
     * return: void
     *************************************************************************/
    public static void switchToNewWindow(WebDriver driver){
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        parentWindow = driver.getWindowHandle();
        UtilFunctions.log("parentWindow: " + parentWindow);
        Set<String> handles =  driver.getWindowHandles();
        for(String windowHandle  : handles)
        {
            UtilFunctions.log("windowHandle: " + windowHandle);
            if(!windowHandle.equals(parentWindow))
            {
                portalWindow = windowHandle;
                UtilFunctions.log("Window handle not equal to parent window. Switching to new window now: " + windowHandle);
                driver.switchTo().window(windowHandle);
                driver.manage().window().maximize();
                UtilFunctions.log("Switched to new window: " + windowHandle);
                break;
            }
        }
    }
    
    /**************************************************************************
     * name: handleAlert(WebDriver driver, String buttonName)
     * functionality: Function used to handle alerts on pages
     * param: WebDriver driver - WebDriver object
     * param: String buttonName - Name of button on alert
     * return: boolean
     *************************************************************************/
    public static boolean handleAlert(WebDriver driver, String buttonName){
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        UtilFunctions.log("buttonName: " + buttonName);

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        switch (buttonName){
            case "OK":
                alert.accept();
                UtilFunctions.log("Alert accepted. Returning true.");
                return true;
            case "Cancel":
                alert.dismiss();
                UtilFunctions.log("Alert dismissed. Returning false.");
                return false;
            default:
                return false;
        }
    }
    
    /**************************************************************************
     * name: checkAlertPresent(WebDriver driver)
     * functionality: Function used to check whether alert is present or not
     * param: WebDriver driver - WebDriver object
     * return: boolean
     *************************************************************************/
    public static boolean checkAlertPresent(WebDriver driver){
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        boolean alertPresent;
        WebDriverWait wait = new WebDriverWait(driver, 0);
        try{
            wait.until(ExpectedConditions.alertIsPresent());
            alertPresent = true;
            UtilFunctions.log("Alert present. Returning true.");
        }
        catch (org.openqa.selenium.TimeoutException eTO){
            eTO.printStackTrace();
            alertPresent = false;
            UtilFunctions.log("Alert not present. Returning false. Exception: " + eTO.getMessage());
        }
        return alertPresent;
    }
    
    /**************************************************************************
     * name: getAlertText(WebDriver driver)
     * functionality: Function used to get alert text
     * param: WebDriver driver - WebDriver object
     * return: String
     *************************************************************************/
    public static String getAlertText(WebDriver driver){
        UtilFunctions.log("Class: " + seleniumFunctions.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        String alertText;
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try{
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alertText = alert.getText();
            UtilFunctions.log("Got the alert text, returning the text");
        }catch (org.openqa.selenium.TimeoutException eTO){
            eTO.printStackTrace();
            UtilFunctions.log("Unable to get the text " + eTO.getMessage());
            alertText = null;
        }
        return alertText;
    }

    /**************************************************************************
     * name: closeNewWindow(WebDriver driver) functionality: Function used to
     * close new window param: WebDriver driver - WebDriver object return: void
     *************************************************************************/
    public static void closeNewWindow(WebDriver driver) {

        Set<String> handles = driver.getWindowHandles();
        for (String windowHandle : handles) {
            if (!windowHandle.equals(GlobalConstants.parentWindow)) {
                driver.switchTo().window(windowHandle);
                driver.close();
            }
            driver.switchTo().window(GlobalConstants.parentWindow);
        }
    }
}

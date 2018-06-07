package support;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import constants.GlobalConstants;
import utils.UtilFunctions;
import wrapper.SeleniumFunctions;

/******************************************************************************
Class Name: Navigation
Contains common functions related to navigation and selection of pages
******************************************************************************/

public class Navigation {
	
	public String className = getClass().getSimpleName();
	private static Navigation nav = new Navigation();
	private static HashMap<String, String> pageSet = new HashMap<>();
	static String pageName = "";
	
	
    /**************************************************************************
     * name: setPage(String page)
     * functionality: Set the current page value
     * param: String page - Current page name
     * return: void
     *************************************************************************/
    public static void setPage(String page) {
        pageName = page;
    }

    /**************************************************************************
     * name: selectNavigationPage(WebDriver driver, String pageName)
     * functionality: Function used to select and click page
     * param: WebDriver driver - WebDriver object
     * param: String pageName - Name of page
     * param: String subPageName - Name of the sub page
     * return: boolean
     *************************************************************************/
    public static boolean selectNavigationPage(WebDriver driver, String pageName, String subPageName){
        UtilFunctions.log("Class: " + nav.className + "; Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        UtilFunctions.log("Current page name: " + pageName);
        UtilFunctions.log("Current sub page name: " + subPageName);
        pageName = pageName.replace(" ", "");
        
        if (StringUtils.isEmpty(subPageName)) {
        	setNavPageParameters();
            //Click on Tab
            SeleniumFunctions.explicitWait(driver, GlobalConstants.TWENTY, pageSet.get(pageName) + ";" + "id");
            WebElement pageElement = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(pageSet.get(pageName) + ";id"));
            if (pageElement != null) {
                pageElement.click();
                UtilFunctions.log("Page: " + pageName + " is present and clicked. Returning true.");
                return true;
            }
            else {
                UtilFunctions.log("Page: " + pageName + " is not present. Returning false.");
                return false;
            }
        }
        else {
        	//This is a temp XPath. Need to change this as per the application
            String subPageXPath = "//li[@id=" + "'li" + pageSet.get(pageName) + "'" + "]";
            UtilFunctions.log("subPageXPath: " + subPageXPath);
            WebElement subPageElement = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(subPageXPath + ";xpath"));
            if (subPageElement != null){
            	subPageElement.click();
                UtilFunctions.log("subPage: " + subPageName + " is present and clicked.");
                return true;
            }
            else {
                UtilFunctions.log("subPage: " + subPageName + " is not present. Returning false.");
                return false;
            }
        }
    }
    
    /**************************************************************************
     * name: navigationPageExists(WebDriver driver, String pageName)
     * functionality: Function used to check whether page exists or not
     * param: WebDriver driver - WebDriver object
     * param: String pageName - Name of page
     * return: boolean
     * @throws InterruptedException 
     *************************************************************************/
    public static boolean navigationPageExists(WebDriver driver, String pageName) throws InterruptedException{
        UtilFunctions.log("Class: " + nav.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
        UtilFunctions.log("Current page name: " + pageName);

        pageName = pageName.replace(" ", "");
        String navPageXPath = "//li[@id='" + pageSet.get(pageName) + "']";
        UtilFunctions.log("navSubTabXPath: " + navPageXPath);
        WebElement element = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(navPageXPath + ";xpath"));
        if (element == null) {
            UtilFunctions.log("navPageXPath: " + navPageXPath + " does not exist. Returning false.");
            return false;
        }
        else {
        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        	Thread.sleep(500);
            UtilFunctions.log("navPageXPath: " + navPageXPath + " exist. Returning true.");
            return true;
        }
    }
    
    /**************************************************************************
     * name: navigationSubPageExists(WebDriver driver, String subPageName)
     * functionality: Function used to check whether sub-tab exists or not
     * param: WebDriver driver - WebDriver object
     * param: String pageName - Name of page
     * return: boolean
     *************************************************************************/
    public static boolean navigationSubPageExists(WebDriver driver, String subPageName){
        UtilFunctions.log("Class: " + nav.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
        UtilFunctions.log("Current page name: " + subPageName);

        String navSubPageXPath = "//li[@id='li" + subPageName + "']";
        UtilFunctions.log("navSubTabXPath: " + navSubPageXPath);
        WebElement element = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(navSubPageXPath + ";xpath"));
        if (element == null) {
            UtilFunctions.log("navSubPageXPath: " + navSubPageXPath + " does not exist. Returning false.");
            return false;
        }
        else {
            UtilFunctions.log("navSubPageXPath: " + navSubPageXPath + " exist. Returning true.");
            return true;
        }
    }

    /**************************************************************************
     * name: setNavPageParameters()
     * functionality: Function used to set maps related to navigation pages
     * return: void
     *************************************************************************/
    public static void setNavPageParameters(){
        //Set pageSet
        pageSet.put("Consumer", "Consumer"); 	
    }
}

package support;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.support.ui.WebDriverWait;
import stepDefinitions.Hooks;
import wrapper.SeleniumFunctions;
import constants.DataTableVals;
import constants.GlobalConstants;
import cucumber.api.DataTable;
import utils.UtilFunctions;

/******************************************************************************
Class Name: Page
Contains common functions related to all pages
******************************************************************************/

public class Page {
	
		public String className = getClass().getSimpleName();
		private static Page page = new Page();
		static String pageName = "";
		private static int count = 0;
	    
	    
	    /**************************************************************************
	     * name: clickButton(WebDriver driver, String pageName, String buttonName,
	     * String... paneName)
	     * functionality: Function to click on a button
	     * param: WebDriver driver - WebDriver object
	     * param: String pageName - Name of page
	     * param: String buttonName - Name of button to be clicked
	     * return: boolean
	     *************************************************************************/
	    public static boolean clickButton(WebDriver driver, String pageName, String buttonName) throws InterruptedException {
	        UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
	        UtilFunctions.log("Current page name: " + pageName);
	        buttonName = buttonName.replace(" ", "");
	        UtilFunctions.log("Button to be clicked: " + buttonName);
	        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(pageName);
	        String[] elementType = UtilFunctions.getElementStringAndType(fileObj, "BUTTONS." + buttonName);
	        String buttonPath = elementType[0];
	        String buttonMethod = elementType[1];
	        
	        SeleniumFunctions.explicitWait(driver, GlobalConstants.TEN, buttonPath + ";" + buttonMethod);
	        WebElement btnObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(buttonPath + ";" + buttonMethod));
	        if (btnObj == null){
	            UtilFunctions.log("Button '"+ buttonName + "' object null. Returning false.");
	            return false;
	        }
	        else{
	            if (!btnObj.isDisplayed()){
	                System.out.println(btnObj.isDisplayed());
	                List<WebElement> btnObjList = SeleniumFunctions.findElements(driver, SeleniumFunctions.setByValues(buttonPath + ";" + buttonMethod));
	                for (WebElement btnObjChild : btnObjList){
	                    if (btnObjChild.isDisplayed()) {
	                        btnObj = btnObjChild;
	                        break;
	                    }
	                }
	                System.out.println(btnObj.isDisplayed());
	            }
	            try{
	            btnObj.click();
				}
				catch (Exception e){
					((JavascriptExecutor) Hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", btnObj);
					((JavascriptExecutor) Hooks.getDriver()).executeScript("arguments[0].click();", btnObj);
					e.printStackTrace();
				}
	            Thread.sleep(1000);
	            UtilFunctions.log("Button '"+ buttonName + "' object not null and is clicked. Returning true.");
	            return true;
	        }

	    }

	/**************************************************************************
	 * name: clickMiscElement(WebDriver driver, String pageName,
	 * String imageName, String sectionName, String... paneName)
	 * functionality: Function to click on miscellaneous elements
	 * param: WebDriver driver - WebDriver object
	 * param: String pageName - Name of current page
	 * param: String imageName - Name of image to be clicked
	 * param: String... paneName - Optional parameter for current pane
	 * name
	 * return: boolean
	 *************************************************************************/
	public static boolean clickMiscElement(WebDriver driver, String pageName, String elementName, String paneName, String exists){
		UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
		UtilFunctions.log("Current tab name: " + pageName);
		UtilFunctions.log("ImageName: " + elementName);

		elementName = elementName.replace(" ", "");
		JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(pageName);
		String[] elementType = UtilFunctions.getElementStringAndType(fileObj, "MISC_ELEMENTS." + elementName);
		String path = elementType[0];
		String method = elementType[1];
		SeleniumFunctions.explicitWait(driver, GlobalConstants.TEN, path + ";" + method);
		WebElement eleObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(path + ";" + method));
		if (eleObj == null) {
			UtilFunctions.log("Element: '" + elementName + "' does not exist. Returning false.");
			return false;
		}
		else{
			try {
				eleObj.click();
				Thread.sleep(1000);
			} catch (Exception e) {
				try{
					((JavascriptExecutor) Hooks.getDriver()).executeScript("arguments[0].scrollIntoView(true);", eleObj);
					((JavascriptExecutor) Hooks.getDriver()).executeScript("arguments[0].click();", eleObj);
				} catch (Exception e1){
					e1.printStackTrace();
					return false;
				}
			}
			UtilFunctions.log("Element: '" + elementName + "' exist and clicked. Returning true.");
			return true;
		}
	}

	/**************************************************************************
	 * name: checkDataTableInPane(WebDriver driver, String tabName,
	 * DataTable dataTable)
	 * functionality: Function to check availability of all the radio, text
	 * boxes etc. present in the data table
	 * param: WebDriver driver - WebDriver object
	 * param: String pageName - Name of current page
	 * param: DataTable dataTable - Stores list of items
	 * return: returns "" string if successful
	 *************************************************************************/
	public static String checkDataTableInPane(WebDriver driver, String pageName, String state, DataTable dataTable) {
		UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
		UtilFunctions.log("Current page name: " + pageName);

	        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(pageName);
	        boolean retValue = true;
	        String retString = "";
	        String[] elementType;
	        String fieldPath;
	        String fieldMethod;
	        List<Map<String, String>> dataList = dataTable.asMaps(String.class, String.class);
	        for (Map data : dataList){
	            String fieldType = (String) data.get("Type");
	            String fieldName = ((String) data.get("Name"));
	            if (!fieldType.equals("link")) {
	                fieldName = ((String) data.get("Name")).replace(" ", "");
	            }
	            UtilFunctions.log("fieldType: " + fieldType + "\nfieldName: " + fieldName);
	            switch (fieldType){
	                case "element":
	                    elementType = UtilFunctions.getElementStringAndType(fileObj, "MISC_ELEMENTS." + fieldName);
	                    fieldPath = elementType[0];
	                    fieldMethod = elementType[1];
	                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
	                    SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
	                    WebElement eleObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
	                    if (eleObj == null)
	                        retValue = false;
	                    else {
	                        if (state != null && state.equals("enabled")) {
	                            retValue = eleObj.getAttribute("disabled") == null;
	                        } else if (state != null && state.equals("disabled")) {
	                            retValue = eleObj.getAttribute("disabled") != null;
	                        }
	                        else if (state != null && state.equals("not display")) {
	                            retValue = eleObj.getAttribute("display") != null;
	                        }else {
	                            retValue = true;
	                        }
	                    }
	                    break;
	                case "text":
	                    elementType = UtilFunctions.getElementStringAndType(fileObj, "TEXT_FIELDS." + fieldName);
	                    fieldPath = elementType[0];
	                    fieldMethod = elementType[1];
	                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
	                    retValue = checkTextBoxExists(driver, fieldPath, fieldMethod);
	                    break;
	                case "radio":
	                    elementType = UtilFunctions.getElementStringAndType(fileObj, "RADIOS." + fieldName);
	                    fieldPath = elementType[0];
	                    fieldMethod = elementType[1];
	                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
	                    retValue = checkRadioExists(driver, fieldPath, fieldMethod);
	                    break;
	                case "button":
	                    elementType = UtilFunctions.getElementStringAndType(fileObj, "BUTTONS." + fieldName);
	                    fieldPath = elementType[0];
	                    fieldMethod = elementType[1];
	                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
	                    SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
	                    WebElement btnObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
	                    if (btnObj == null)
	                        retValue = false;
	                    else {
	                        if (state != null && state.equals("enabled")) {
	                            retValue = btnObj.getAttribute("disabled") == null;
	                        }
	                        else if (state != null && state.equals("disabled")) {
	                            retValue = btnObj.getAttribute("disabled") != null;
	                        }
	                        else
	                            retValue = true;
	                    }
	                    break;
	                case "dropdown":
	                    elementType = UtilFunctions.getElementStringAndType(fileObj, "DROPDOWNS." + fieldName);
	                    fieldPath = elementType[0];
	                    fieldMethod = elementType[1];
	                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
	                    SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
	                    WebElement dropdownObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
	                    if (dropdownObj == null)
	                        retValue = false;
	                    else {
	                        if (state != null && state.equals("enabled")) {
	                            retValue = dropdownObj.getAttribute("disabled") == null;
	                        }
	                        else if (state != null && state.equals("disabled")) {
	                            retValue = dropdownObj.getAttribute("disabled") != null;
	                        }
	                        else
	                            retValue = true;
	                    }
	                    break;
	                case "link":
	                    fieldName = fieldName.trim();
	                    WebElement linkObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues("//*[starts-with(normalize-space(./text()), '" + fieldName + "')]"));
	                    if (linkObj == null)
	                        retValue = false;
	                    else {
	                        if (state != null && state.equals("enabled")) {
	                            retValue = linkObj.getAttribute("disabled") == null;
	                        } else if (state != null && state.equals("disabled")) {
	                            retValue = linkObj.getAttribute("disabled") != null;
	                        }
	                        else if (state != null && state.equals("not display")) {
	                            retValue = linkObj.getAttribute("display") != null;
	                        }else {
	                            retValue = true;
	                        }
	                    }
	                    break;
	                case "check":
	                    elementType = UtilFunctions.getElementStringAndType(fileObj, "CHECKBOXES." + fieldName);
	                    fieldPath = elementType[0];
	                    fieldMethod = elementType[1];
	                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
	                    SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
	                    WebElement checkboxObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
	                    if (checkboxObj == null)
	                        retValue = false;
	                    else {
	                        if (state != null && state.equals("enabled")) {
	                            retValue = checkboxObj.getAttribute("disabled") == null;
	                        }
	                        else if (state != null && state.equals("disabled")) {
	                            retValue = checkboxObj.getAttribute("disabled") != null;
	                        }
	                        else
	                            retValue = true;
	                    }
	                    break;
	                default:
	                	//add a default case if required
	                    break;
	            }
	            if (retValue) {
	                continue;
	            }
	            retString = retString + "Field: " + fieldName + " of Type: " + fieldType + " is not present.";
	            UtilFunctions.log(retString);
	            retValue = true;
	        }
	        return retString;
	    }
	    
	    /**************************************************************************
	     * name: checkTextBoxExists(WebDriver driver, String fieldName,
	     * String fieldMethod)
	     * functionality: Function to check whether text box exists or not
	     * param: WebDriver driver - WebDriver object
	     * param: String fieldName - Name of text field
	     * param: String fieldMethod - Type of radio field name such as xpath, id,
	     * name, etc.
	     * return: boolean
	     *************************************************************************/
	    public static boolean checkTextBoxExists(WebDriver driver, String fieldName, String fieldMethod){
	        UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
	        String xPathValue;
	        switch (fieldMethod){
	            case "name":
	                xPathValue = "//*[(local-name()='input' or local-name()='textarea') and (@name='" + fieldName + "' or @id='" + fieldName + "' or @testid='" + fieldName + "')]";
	                break;
	            case "id":
	                xPathValue = "//*[(local-name()='input' or local-name()='textarea') and (@name='" + fieldName + "' or @id='" + fieldName + "' or @testid='" + fieldName + "')]";
	                break;
	            default:
	                xPathValue = fieldMethod;
	                break;
	        }
	        UtilFunctions.log("Text box: '" + fieldName + "'; xpath: " + xPathValue);
	        SeleniumFunctions.explicitWait(driver, GlobalConstants.TEN, xPathValue + ";" + "xpath");
	        WebElement element = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(xPathValue + ";" + "xpath"));
	        if (element == null) {
	            UtilFunctions.log("Text box: " + fieldName + " does not exist. Returning false.");
	            return false;
	        }
	        else {
	            UtilFunctions.log("Text box: " + fieldName + " exist. Returning true.");
	            return true;
	        }
	    }
	    
	    /**************************************************************************
	     * name: checkRadioExists(WebDriver driver, String fieldPath,
	     * String fieldMethod)
	     * functionality: Function to check whether radio button exists or not
	     * param: WebDriver driver - WebDriver object
	     * param: String fieldPath - Name of radio field
	     * param: String fieldMethod - Type of radio field name such as xpath, id,
	     * name, etc.
	     * return: boolean
	     *************************************************************************/
	    private static boolean checkRadioExists(WebDriver driver, String fieldPath, String fieldMethod) {
	        UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
	        String xPathValue;
	        switch (fieldMethod){
	            case "name":
	                xPathValue = "//input[@type='radio' and @name='" + fieldPath + "']";
	                break;
	            case "id":
	                xPathValue = "//input[@type='radio' and (@name='" + fieldPath + "' or @id='" + fieldPath + "')]";
	                break;
	            default:
	                xPathValue = fieldPath;
	                break;
	        }
	        SeleniumFunctions.explicitWait(driver, GlobalConstants.TEN, xPathValue + ";" + "xpath");
	        WebElement element = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(xPathValue + ";" + "xpath"));
	        if (element == null) {
	            UtilFunctions.log("Radio: " + fieldPath + " does not exist. Returning false.");
	            return false;
	        }
	        else {
	            UtilFunctions.log("Radio: " + fieldPath + " exist. Returning true.");
	            return true;
	        }
	    }
	    
	    /**************************************************************************
	     * name: findTextBox(WebDriver driver, String textBoxName,
	     *  String textMethod)
	     * functionality: Function to find text box object
	     * param: WebDriver driver - WebDriver object
	     * param: String textBoxName - Text box field name
	     * param: String textMethod - Text method type in json, such as xpath, id,
	     * name, etc.
	     * return: returns text box object
	     *************************************************************************/
	    public static WebElement findTextBox(WebDriver driver, String textBoxName, String textMethod){
	        UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
	        UtilFunctions.log("Text box name: " + textBoxName);
	        String xpathValue = "";
	        if (textMethod.equals("id") || textMethod.equals("name"))
	            xpathValue = "//*[(local-name()='input' or local-name()='textarea') and (@name='" + textBoxName + "' or @id='" + textBoxName + "' or @testid='" + textBoxName + "')]";
	        else if (textMethod.equals("path") || textMethod.equals("xpath"))
	            xpathValue = "//*[(local-name()='input' or local-name()='textarea') and " + textBoxName + "]";

	        SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, xpathValue + ";xpath");
	        return SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(xpathValue + ";xpath"));
	    }

	    /**************************************************************************
	     * name: setTextBox(WebDriver driver, String pageName, String value,
	     * String fieldName)
	     * functionality: Function to enter value in text box
	     * param: WebDriver driver - WebDriver object
	     * param: String pageName - Name of current page
	     * param: String value - Value to be entered in text box
	     * param: String fieldName - Text box field name
	     * return: boolean
	     *************************************************************************/
	    public static boolean setTextBox(WebDriver driver, String pageName, String value, String fieldName){
	        try {
	            UtilFunctions.log("Class: " + page.className + "; Method: " + new Object() {
	            }.getClass().getEnclosingMethod().getName());
	            UtilFunctions.log("Current page name: " + pageName);
	            fieldName = fieldName.replace(" ", "");
	            UtilFunctions.log("Text box field name: " + fieldName);

	            JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(pageName);
	            String[] elementType = UtilFunctions.getElementStringAndType(fileObj, "TEXT_FIELDS." + fieldName);
	            String textPath = elementType[0];
	            String textMethod = elementType[1];

	            UtilFunctions.log("Text box textPath: " + textPath);
	            UtilFunctions.log("Text box textMethod: " + textMethod);

			Thread.sleep(1000);
//			WebElement txtObj = findTextBox(driver, textPath, textMethod);
			WebElement txtObj = SeleniumFunctions.findElement(Hooks.getDriver(),SeleniumFunctions.setByValues(textPath + ";" + textMethod));
			if (txtObj == null) {
				UtilFunctions.log("Text box '" + fieldName + "' object is null. Returning false.");
				return false;
			} else {
				txtObj.click();
				txtObj.clear();
				txtObj.sendKeys(value);
				Thread.sleep(1000);
				UtilFunctions.log("Text box '" + fieldName + "' object not null. Entered Value: '" + value + "'. Returning true.");
				return true;
			}
		}
		catch (Exception e){
			UtilFunctions.log("Trying again. Exception: " + e.getMessage());
			count++;
			if (count <= GlobalConstants.ONE) {
				UtilFunctions.log("Recursive calling of the function.");
				GlobalConstants.setGlobalFrameValue("");
				return setTextBox(driver, pageName, value, fieldName);
			}
			else
				count = 0;
			return false;
		}
	}

	/**************************************************************************
	 * name: selectDropDownOption(WebDriver driver, String searchString,
	 * String value, String searchMethod
	 * functionality: Function to select drop down item based on value
	 * param: WebDriver driver - WebDriver object
	 * param: String searchString - Name of drop down element
	 * param: String value - Item to be selected
	 * param: String searchMethod - Method such as xpath, id, etc.
	 * return: void
	 *************************************************************************/
	public static void selectDropDownOption(WebDriver driver, String searchString, String value, String searchMethod) {
		UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
		SeleniumFunctions.explicitWait(driver, GlobalConstants.TEN, searchString + ";" + searchMethod);
		WebElement dropDownItem = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(searchString + ";" + searchMethod));
		if (dropDownItem != null){
			UtilFunctions.log("Drop down element not null");
			try {
				Select select = new Select(dropDownItem);
				select.selectByVisibleText(value);
			}catch (Exception e){
				try{
					dropDownItem.click();
					WebElement dropDownValue = SeleniumFunctions.findElement(Hooks.getDriver(),By.xpath("//ul/descendant::*[text()='"+value+"']"));
					dropDownValue.click();
				}catch (Exception e1){
					UtilFunctions.log("Item " + value + " not selected from drop down due to Exception: " + e.getMessage());
					throw(e1);
				}
			}
		}else{
			UtilFunctions.log("Drop down object is not found");
			throw new NoSuchElementException("Cannot locate dropdown with locator: " + searchMethod + "= " +searchString);
		}
		UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
	}
	/**************************************************************************
	 * name: setCheckBox(WebDriver driver, String pageName,
	 * String checkBoxName, String checkType)
	 * functionality: Function to check/un-check checkboxes
	 * param: WebDriver driver - WebDriver object
	 * param: String pageName - Name of current page
	 * param: String checkBoxName - Name of checkbox
	 * param: String checkType - Type of check, i.e. check or uncheck
	 *************************************************************************/
	public static boolean setCheckBox(WebDriver driver, String pageName, String checkBoxName, String checkType) throws InterruptedException {
		UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
		UtilFunctions.log("Current page name: " + pageName);
		UtilFunctions.log("Checkbox name: " + checkBoxName);
		UtilFunctions.log("CheckType: " + checkType);

//	        checkBoxName = checkBoxName.replace(" ", "");
	        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(pageName);
//	        if (!pageName.equals("")){
	            String[] elementType = UtilFunctions.getElementStringAndType(fileObj, "CHECKBOXES." + checkBoxName.replace(" ", ""));
	            String chBoxValue = elementType[0];
	            String chBoxMethod = elementType[1];
	            if (chBoxValue == null && chBoxMethod ==null) {
	                chBoxValue = checkBoxName.split(";")[0];
	                chBoxMethod = checkBoxName.split(";")[1];
	            }
	            UtilFunctions.log("CheckBox value: " + chBoxValue);
	            UtilFunctions.log("CheckBox method: " + chBoxMethod);

	            WebElement checkBoxObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(chBoxValue + ";" + chBoxMethod));
	            if (checkBoxObj != null){
	                boolean setCheckboxSuccess = true;
	                if (checkBoxObj.isSelected() && checkType.equals("uncheck")) {
	                    int retryCount = 0;
	                    while (checkBoxObj.isSelected()) {
	                        if (retryCount >= 5) {
	                            UtilFunctions.log("Unable to un-check checkbox after 5 attempts.  Breaking out of retry loop.");
	                            setCheckboxSuccess = false;
	                            break;
	                        }
	                        UtilFunctions.log("Trying to un-check checkbox");
	                        System.out.println(checkBoxObj.isDisplayed());
	                        System.out.println(checkBoxObj.isEnabled());
	                        checkBoxObj.click();
	                        Thread.sleep(500);
	                        retryCount += 1;
	                    }
	                }
	                else if (!checkBoxObj.isSelected() && checkType.equals("check")) {
	                    int retryCount = 0;
	                    while (!checkBoxObj.isSelected()) {
	                        if (retryCount >= 5) {
	                            UtilFunctions.log("Unable to check checkbox after 5 attempts.  Breaking out of retry loop.");
	                            setCheckboxSuccess = false;
	                            break;
	                        }
	                        UtilFunctions.log("Trying to check checkbox");
	                        System.out.println(checkBoxObj.isDisplayed());
	                        System.out.println(checkBoxObj.isEnabled());
	                        checkBoxObj.click();
	                        Thread.sleep(500);
	                        retryCount += 1;
	                    }
	                }
	                if (setCheckboxSuccess) {
	                    UtilFunctions.log("Action performed on checkbox: '" + checkType + "'. Returning true.");
	                    return true;
	                } else {
	                    UtilFunctions.log("Action failed on checkbox: '" + checkType + "'. Returning false.");
	                    return false;
	                }
	            }
	            else {
	                UtilFunctions.log("Checkbox: '" + checkBoxName + "' object is null. Returning false.");
	                return false;
	            }
//	        }
//	        else {
//	            UtilFunctions.log("Current tab : '" + pageName + "' name is null. Returning false.");
//	            return false;
//	        }
	    }
	    
	    /**************************************************************************
	     * name: checkElementsExists(WebDriver driver, DataTable dataTable,
	     * String paneName)
	     * functionality: Function used to check if elements exists
	     * param: WebDriver driver - WebDriver object
	     * param: DataTable dataTable - Stores the charge headers and their values
	     * param: String paneName - Optional parameter to store pane name
	     * return: "" if elements exist
	     *************************************************************************/
	    //Need to implement logic to use paneName
	    public static String checkElementsExists(WebDriver driver, DataTable dataTable, String paneName) throws InterruptedException {
	        UtilFunctions.log("Class: " + page.className + "; Method: " + new Object() {
	        }.getClass().getEnclosingMethod().getName());
	        UtilFunctions.log("Current tab name: " + pageName);

	        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(pageName);
	        boolean retValue = true;
	        String retString = "";
	        String[] elementType;
	        String fieldPath;
	        String fieldMethod;
	        
//	        List<DataTableVals> dataList = dataTable.asList(DataTableVals.class);
	        List<Map<String, String>> dataList = dataTable.asMaps(String.class, String.class);
	        for(Map data : dataList){
	        	String fieldName = (String) data.get("Name");
	            String fieldType = ((String) data.get("Type"));
	        	
	            if (!fieldType.equals("link")) {
	                fieldName = fieldName.replace(" ", "");
	            }
	            
	        	switch (fieldType){
                case "element":
                    elementType = UtilFunctions.getElementStringAndType(fileObj, "MISC_ELEMENTS." + fieldName);
                    fieldPath = elementType[0];
                    fieldMethod = elementType[1];
                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
                    SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
                    WebElement eleObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
                    if (eleObj != null){
                    	eleObj.isDisplayed();	
                    }
                    break;
                case "textfield":
                    elementType = UtilFunctions.getElementStringAndType(fileObj, "TEXT_FIELDS." + fieldName);
                    fieldPath = elementType[0];
                    fieldMethod = elementType[1];
                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
                    WebElement textObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
                    if(textObj != null){
                    	textObj.isDisplayed();
                    }
                    break;
                case "radio":
                    elementType = UtilFunctions.getElementStringAndType(fileObj, "RADIOS." + fieldName);
                    fieldPath = elementType[0];
                    fieldMethod = elementType[1];
                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
                    WebElement radioObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
                    if(radioObj != null){
                    	radioObj.isDisplayed();
                    }
                    break;
                case "button":
                    elementType = UtilFunctions.getElementStringAndType(fileObj, "BUTTONS." + fieldName);
                    fieldPath = elementType[0];
                    fieldMethod = elementType[1];
                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
                    SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
                    WebElement btnObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
                    if (btnObj != null){
                    	btnObj.isDisplayed();
                    }
                    break;
                case "dropdown":
                    elementType = UtilFunctions.getElementStringAndType(fileObj, "DROPDOWNS." + fieldName);
                    fieldPath = elementType[0];
                    fieldMethod = elementType[1];
                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
                    SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
                    WebElement dropdownObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
                    if (dropdownObj != null){
                    	dropdownObj.isDisplayed();
                    }
                    break;
                case "link":
                    fieldName = fieldName.trim();
                    WebElement linkObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues("//*[starts-with(normalize-space(./text()), '" + fieldName + "')]"));
                    if (linkObj != null){
                    	linkObj.isDisplayed();
                    }
                    break;
                case "checkbox":
                    elementType = UtilFunctions.getElementStringAndType(fileObj, "CHECKBOXES." + fieldName);
                    fieldPath = elementType[0];
                    fieldMethod = elementType[1];
                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
                    SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
                    WebElement checkboxObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
                    if (checkboxObj != null){
                    	checkboxObj.isDisplayed();
                    }
                    break;
                default:
                	retString="Element Not Present";
                    break;    	
	        }
	            if (retValue) {
	                continue;
	            }
	            retString = retString + "Field: " + fieldName + " of Type: " + fieldType + " is not present.";
	            UtilFunctions.log(retString);
	            retValue = true;
	        }
	        return retString;
	    }
	    
	    /**************************************************************************
	     * name: setMultipleFieldValues(WebDriver driver, DataTable dataTable,
	     * String paneName)
	     * functionality: Function used to set value for multiple elements
	     * param: WebDriver driver - WebDriver object
	     * param: DataTable dataTable - Stores the charge headers and their values
	     * param: String paneName - Optional parameter to store pane name
	     * return: "" if values set successfully
	     *************************************************************************/
	    //Need to implement logic to use paneName
	    public static String setMultipleFieldValues(WebDriver driver, DataTable dataTable, String paneName, String pageName) throws InterruptedException {
	        UtilFunctions.log("Class: " + page.className + "; Method: " + new Object() {
	        }.getClass().getEnclosingMethod().getName());
	        UtilFunctions.log("Current tab name: " + pageName);

	        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(pageName);
	        boolean retValue = true;
	        String retString = "";
	        String[] elementType;
	        String fieldPath;
	        String fieldMethod;
	        
	        List<DataTableVals> dataList = dataTable.asList(DataTableVals.class);
	        for(DataTableVals data : dataList){
	        	String fieldName = data.returnName();
	        	String fieldType = data.returnType();
	        	String fieldValue = data.returnValue();
	        	
	            if (!fieldType.equals("link")) {
	                fieldName = fieldName.replace(" ", "");
	            }
	            
	        	switch (fieldType){
                case "element":
                    elementType = UtilFunctions.getElementStringAndType(fileObj, "MISC_ELEMENTS." + fieldName);
                    fieldPath = elementType[0];
                    fieldMethod = elementType[1];
                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
                    SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
                    WebElement eleObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
                    if (eleObj != null){
                    	eleObj.click();	
                    }
                    break;
                case "textfield":
                    elementType = UtilFunctions.getElementStringAndType(fileObj, "TEXT_FIELDS." + fieldName);
                    fieldPath = elementType[0];
                    fieldMethod = elementType[1];
                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
                    WebElement textObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
                    if(textObj != null){
                    	if(StringUtils.isEmpty(fieldValue)){
                    		textObj.click();
                        	textObj.clear();
                    	}
                    	else{
                        	textObj.click();
                        	textObj.clear();
                        	textObj.sendKeys(fieldValue);	
                    	}
                    }
                    break;
                case "radio":
                    elementType = UtilFunctions.getElementStringAndType(fileObj, "RADIOS." + fieldName);
                    fieldPath = elementType[0];
                    fieldMethod = elementType[1];
                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
                    WebElement radioObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
                    if(radioObj != null){
                    	radioObj.click();
                    }
                    break;
                case "button":
                    elementType = UtilFunctions.getElementStringAndType(fileObj, "BUTTONS." + fieldName);
                    fieldPath = elementType[0];
                    fieldMethod = elementType[1];
                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
                    SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
                    WebElement btnObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
                    if (btnObj != null){
                    	btnObj.click();
                    }
                    break;
                case "dropdown":
                    elementType = UtilFunctions.getElementStringAndType(fileObj, "DROPDOWNS." + fieldName);
                    fieldPath = elementType[0];
                    fieldMethod = elementType[1];
                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
                    SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
                    WebElement dropdownObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(fieldPath + ";" + fieldMethod));
                    selectDropDownOption(driver, fieldPath, fieldValue, fieldMethod);
                    break;
                case "link":
                    fieldName = fieldName.trim();
                    WebElement linkObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues("//*[starts-with(normalize-space(./text()), '" + fieldName + "')]"));
                    if (linkObj != null){
                    	linkObj.click();
                    }
                    break;
                case "checkbox":
                    elementType = UtilFunctions.getElementStringAndType(fileObj, "CHECKBOXES." + fieldName);
                    fieldPath = elementType[0];
                    fieldMethod = elementType[1];
                    UtilFunctions.log("fieldPath: " + fieldPath + "\nfieldMethod: " + fieldMethod);
                    SeleniumFunctions.explicitWait(driver, GlobalConstants.FIFTEEN, fieldPath + ";" + fieldMethod);
                    setCheckBox(driver, pageName, fieldName, fieldValue);
                    break;
                default:
                	//add a default case if required
                    break;    	
	        }
	            if (retValue) {
	                continue;
	            }
	            retString = retString + "Field: " + fieldName + " of Type: " + fieldType + " is not present.";
	            UtilFunctions.log(retString);
	            retValue = true;
	        }
	        return retString;
	    }
	    
	    /**************************************************************************
	     * name: textExists(WebDriver driver, String text, String sectionPath,
	     * boolean exact)
	     * functionality: Function to check whether text exists or not on the
	     * required page
	     * param: WebDriver driver - WebDriver object
	     * param: String text - Text to be checked
	     * param: boolean exact - Boolean value to generate xpath as per the value
	     * return: boolean
	     *************************************************************************/
	    public static boolean textExists(WebDriver driver, String text, boolean exact) throws InterruptedException {
	        UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());

	            String xPath = "//*[";
	            if (exact)
	                xPath += "text()=\"" + text + "\"";
	            else
//	                xPath += "contains(normalize-space(.), \"" + text + "\")";
	            xPath += "contains(normalize-space(.), '" + text + "')";
	            xPath += "]";
	            UtilFunctions.log("xPath: " + xPath);
	            SeleniumFunctions.explicitWait(driver, GlobalConstants.TEN, xPath + ";xpath");
	            WebElement textObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(xPath + ";xpath"));
	            String appText;
	            if (textObj == null) {
	                UtilFunctions.log("Text: '" + text + "' does not exist. Returning false.");
	                return false;
	            }
	            else{
	            	appText = textObj.getText().trim();
	            	appText = appText.toLowerCase();
		            text = text.toLowerCase();
		            UtilFunctions.log("This is my text: " + text);
		            UtilFunctions.log("This is the application text: " + appText);
	                if(appText.contains(text) || (textObj.getText().indexOf(text) > 0)){
	                    UtilFunctions.log("Text: '" + text + "' exist. Returning true.");
	                    return true;
	                }
//	                else{
//	                    WebElement newTextObj = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues("tr" + ";tagName"));
//	                        if (newTextObj.getAttribute("outerHTML").replace("&nbsp;", " ").trim().indexOf(text.replace("&nbsp;", " ").trim()) > 0){
//	                            UtilFunctions.log("Text: '" + text + "' exist. Returning true.");
//	                            return true;
//	                        }
//	                        else{
//	                            UtilFunctions.log("Text: '" + text + "' does not exist. Returning false.");
//	                            return false;
//	                        }
//	                    }
	            }
				return false;
	    }
	    
	    /**************************************************************************
	     * name: handleAlert(WebDriver driver, String buttonName)
	     * functionality: Function used to handle alerts on pages
	     * param: WebDriver driver - WebDriver object
	     * param: String buttonName - Name of button on alert
	     * return: boolean
	     *************************************************************************/
	    public static boolean handleAlert(WebDriver driver, String buttonName){
	        UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
	        UtilFunctions.log("Alert button name: " + buttonName);

		return SeleniumFunctions.handleAlert(driver, buttonName);
	}

	/**************************************************************************
	 * name: clickLinkText(WebDriver driver, String tabName, String link,
	 * String pane)
	 * functionality: Function to click on link
	 * param: WebDriver driver - WebDriver object
	 * param: String link - Link display text
	 * return: boolean
	 *************************************************************************/
	public static boolean clickLinkText(WebDriver driver, String link) throws InterruptedException {
		UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
		UtilFunctions.log("Current tab name: " + pageName);

		WebElement linkElement;
		Thread.sleep(1000);
		linkElement = findLinkText(driver, link, "", "");
		if (linkElement == null){
			UtilFunctions.log("Link: '" + link + "' object is null. Returning false.");
			return false;
		}
		else{
//	            linkElement.click();
	            try {
	                int linkClickCnt = 0;
	                while (linkElement.isDisplayed()) {
	                    linkClickCnt++;
	                    linkElement.click();
	                    if (linkClickCnt >= GlobalConstants.ONE){
	                        UtilFunctions.log("Link: '" + link + "' clicked. Returning true.");
	                        return true;
	                    }
	                    else{
	                        UtilFunctions.log("Waiting for Link: '" + link + "' to be clicked.");
	                        Thread.sleep(1000);
	                    }
	                }
	            }
	            catch (StaleElementReferenceException e){
	                System.out.println(e.getMessage());
	                UtilFunctions.log("Link clicked and no more visible");
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	                UtilFunctions.log("Exception: " + e.getMessage());
	            } catch (WebDriverException e) {
	                e.printStackTrace();
	                UtilFunctions.log("Exception: " + e.getMessage());
	            }
	            catch (Exception e) {
	                e.printStackTrace();
	                count++;
	                UtilFunctions.log("Exception: " + e.getMessage());
	                if (count <= GlobalConstants.ONE){
	                    clickLinkText(driver, link);
	                }
	                else
	                    count = 0;
	            }
	            UtilFunctions.log("Link: '" + link + "' object not null and clicked. Returning true.");
	            return true;
	        }
	    }
	    
	    /**************************************************************************
	     * Find link via text and click it
	     *
	     * @param driver WebDriver object
	     * @param text name of link text
	     * @param tag tg of link
	     * @param section section of xpath value
	     * @return WebElement
	     *************************************************************************/
	    public static WebElement findLinkText(WebDriver driver, String text, String tag, String section){
	        UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());

	        if (tag.equals(""))
	            tag  = "*";
	        String xpath = "//" + tag + "[starts-with(normalize-space(./text()), '" + text + "')";
	        if (!section.equals("")) {
	            xpath += " and ancestor::" + section;
	        }
	        xpath += "]";
	        if (xpath.contains(":://"))
	            xpath = xpath.replace(":://", "::");
	        UtilFunctions.log("Link text: '" + text + "'; xPath: '" + xpath + "'. Returning object of the link text.");
	        SeleniumFunctions.explicitWait(driver, GlobalConstants.TWENTY, xpath + ";xpath");
	        return SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(xpath + ";xpath"));
	    }
	    
	    public static WebElement findPage(WebDriver driver, String tabName, String pageName) {
	        return findPage(driver, tabName, pageName, GlobalConstants.TEN);
	    }

	/**************************************************************************
	 * name: findPage(WebDriver driver, String tabName, String pageName)
	 * functionality: Function to find and return page object
	 * param: WebDriver driver - WebDriver object
	 * param: String tabName - Name of tab
	 * param: String pageName - Name of page
	 * return: returns the page's object
	 *************************************************************************/
	public static WebElement findPage(WebDriver driver, String tabName, String pageName, int waitTime){
		UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
		UtilFunctions.log("Current tab name: " + tabName);

		pageName = pageName.replace(" ", "");
		System.out.println(pageName);
		UtilFunctions.log("PaneName: " + pageName);
		JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(tabName);
		String pagePath = UtilFunctions.getElementFromJSONFile(fileObj, "PAGES." + pageName, "path");
		UtilFunctions.log("Pane xpath: " + pagePath);
		WebElement elt = null;

		if(pageName.equals("Consumer")){
			SeleniumFunctions.explicitWait(Hooks.getDriver(),10,"",By.tagName("div"));
			elt = SeleniumFunctions.findElement(driver, By.tagName("div"));
		}
		else if(pageName.equals("Admin")){
			SeleniumFunctions.explicitWait(Hooks.getDriver(),10,"",By.tagName("div"));
			elt = SeleniumFunctions.findElement(driver, By.tagName("div"));
		}
		else if(pageName.equals("Advisor")){
			SeleniumFunctions.explicitWait(Hooks.getDriver(),10,"",By.tagName("div"));
			elt = SeleniumFunctions.findElement(driver, By.tagName("div"));
		}
		else {
			elt = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(pagePath + ";" + "xpath"));
		}
		return elt;
	}

	/**************************************************************************
	 * name: selectDropDownOptionByIndex(WebDriver driver,
	 * String searchString, int index, String searchMethod)
	 * functionality: Function to select drop down item based on index
	 * param: WebDriver driver - WebDriver object
	 * param: String searchString - Name of drop down element
	 * param: int index - Index of item to be selected
	 * param: String searchMethod - Method such as xpath, id, etc.
	 * return: void
	 *************************************************************************/
	private static void selectDropDownOptionByIndex(WebDriver driver, String searchString, int index, String searchMethod) {
		UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
		switch (searchMethod){
			case "xpath":
				searchString = "//select["+ searchString + "]";
				break;
			case "id":
				searchString = "//select[@name='"+ searchString + "'or @id='" + searchString + "']";
				break;
			case "name":
				searchString = "//select[@name='"+ searchString + "'or @id='" + searchString + "']";
				break;
		}
		UtilFunctions.log("Search String: " + searchString);
		SeleniumFunctions.explicitWait(driver, GlobalConstants.FIVE, searchString + ";" + searchMethod);
		WebElement dropDownItem = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(searchString + ";" + searchMethod));
		if (dropDownItem != null){
			UtilFunctions.log("Drop down element not null");
			Select select = new Select(dropDownItem);
			if (index == -1){
				index = select.getOptions().size() - 1;
			}
			select.selectByIndex(index);
		}
		else
			UtilFunctions.log("Drop down element is null");
		UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
	}

	/**************************************************************************
	 * name: selectDropDownInPane(WebDriver driver, String pageName,
	 * String value, String dropDownName)
	 * functionality: Function to select value in a drop down
	 * param: WebDriver driver - WebDriver object
	 * param: String tabName - Name of tab
	 * param: String value - Name of item to be selected
	 * param: String dropDownName - Name of dropDown
	 * return: void
	 *************************************************************************/
	public static void selectDropDownInPane(WebDriver driver, String pageName, String value, String dropDownName){
		UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
		UtilFunctions.log("Current tab name: " + pageName);
		String searchString = null;
		String searchMethod = null;

	        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(pageName);
	        UtilFunctions.log("PaneName: " + pageName);
	        dropDownName = dropDownName.replace(" ", "");
	        UtilFunctions.log("Dropdown Name: " + dropDownName);

	        try{
	            String[] elementType = UtilFunctions.getElementStringAndType(fileObj, "DROPDOWNS." + dropDownName);
	            searchString = elementType[0];
	            searchMethod = elementType[1];
	                if (value.equals("the first item"))
	                    selectDropDownOptionByIndex(driver, searchString, 0, searchMethod);
	                else if (value.equals("the last item"))
	                    selectDropDownOptionByIndex(driver, searchString, -1, searchMethod);
	                else
	                    selectDropDownOption(driver, searchString, value, searchMethod);
	        }catch (Exception e){
	            UtilFunctions.log("Item " + value + " not selected from dropDown " + dropDownName + " due to Exception: " + e.getMessage());
	            throw (e);
	        }
	        UtilFunctions.log("Class: " + page.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
	    }

	/**************************************************************************
	 * name: setRadioValue(WebDriver driver, String tabName, String value,
	 * String radioName, String... paneName)
	 * functionality: Function to set values for clicking radio button and
	 * invoke the function to click on radio button
	 * param: WebDriver driver - WebDriver object
	 * param: String tabName - Name of tab
	 * param: String value - Radio button display text
	 * param: String radioName - Name of radio button in json file
	 * param: String... paneName - Optional parameter for pane name
	 * return: boolean
	 *************************************************************************/
	public static boolean setRadioValue(WebDriver driver, String tabName, String value, String radioName, String... paneName) {
		UtilFunctions.log("Class: " + page.className + "; Method: " + new Object() {
		}.getClass().getEnclosingMethod().getName());
		UtilFunctions.log("Current tab name: " + tabName);        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(tabName);
		String[] elementType;
		String fieldPath;
		String fieldMethod;
		String selectBy = "";
		radioName = radioName.replace(" ", "");

		UtilFunctions.log("Radio name: " + radioName);
		elementType = UtilFunctions.getElementStringAndType(fileObj, "RADIOS." + radioName);

		fieldPath = elementType[0];
		fieldMethod = elementType[1];

		UtilFunctions.log("Radio field path: " + fieldPath);
		UtilFunctions.log("Radio field method: " + fieldMethod);
		try {
			selectBy = UtilFunctions.getElementFromJSONFile(fileObj, "RADIOS." + radioName, "select_by");

			if (selectBy == null)
				selectBy = "";
			} catch (Exception e) {

			UtilFunctions.log("Select by set to null.");
			selectBy = "";
		}

		return setRadio(driver, fieldPath, selectBy, value);

	}

	/**************************************************************************
	 * name: setRadio(WebDriver driver, String fieldPath, String selectBy,
	 * String value)
	 * functionality: Function to generate radio button xpath, find object and
	 * click on the button
	 * param: WebDriver driver - WebDriver object
	 * param: String fieldPath - Name of radio field
	 * param: String selectBy - Radio button selecting criteria
	 * param: String value - Radio button display text
	 * return: boolean
	 *************************************************************************/

	public static boolean setRadio(WebDriver driver, String fieldPath, String selectBy, String value) {
		UtilFunctions.log("Class: " + page.className + "; Method: " + new Object() {
		}.getClass().getEnclosingMethod().getName());

		String xPath;
		String radioPos;

		switch (selectBy) {
			case "value":
				xPath = "//input[@type='radio' and @name='" + fieldPath + "' and (@value='" + value + "' or @displaylabel='" + value + "')]";
				break;
			case "label":
				xPath = "//input[@type='radio' and (@displaylabel='" + value + "' or following-sibling::label[text()='" + value + "'])]";
				break;
			case "span":
				xPath = "//input[@type='radio' and following-sibling::span[text()='" + value + "']]";
				break;
			case "xpath":
				xPath = fieldPath;
				break;
			case "position":
				if (value.toLowerCase().equals("yes")) {
					radioPos = "1";
				} else {
					radioPos = "2";
				}
				xPath = "//input[@type='radio' and @name='" + fieldPath + "' and position()='" + radioPos + "']";
				break;
			default:
				xPath = "//input[@type='radio' and @name='" + fieldPath + "' and (@value='" + value + "' or @displaylabel='" + value + "')]";
				break;
		}
		UtilFunctions.log("selectBy: " + selectBy);
		UtilFunctions.log("xpath: " + xPath);

		SeleniumFunctions.explicitWait(driver, GlobalConstants.FIVE, xPath + ";" + "xpath");

		WebElement element = SeleniumFunctions.findElement(driver, SeleniumFunctions.setByValues(xPath + ";" + "xpath"));
		if (element == null) {
			UtilFunctions.log("Radio with name: '" + fieldPath + "' and value: '" + value + "' object is null. Returning false.");
			return false;
		} else {
//            element.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
//            while (!element.isSelected())
//                element.click();
			if (!element.isSelected()){
				element.click();
			}
			UtilFunctions.log("Radio with name: '" + fieldPath + "' and value: '" + value + "' object is not null and clicked. Returning true.");
			return true;
		}
	}

	/**************************************************************************
	 * name: verifyPDFContent(String strURL, String reqTextInPDF)
	 * functionality: Function to verify content in PDF
	 * param: String strURL - URL of the PDF file
	 * param: String reqTextInPDF - Text to be verified in the PDF
	 * return: boolean
	 *************************************************************************/
	public boolean verifyPDFContent(String strURL, String reqTextInPDF) {

		boolean flag = false;

		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		String parsedText = null;

		try {
			URL url = new URL(strURL);
			BufferedInputStream file = new BufferedInputStream(url.openStream());
			PDFParser parser = new PDFParser((RandomAccessRead) file);

			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(2);

			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
		} catch (MalformedURLException e2) {
			System.err.println("URL string could not be parsed "+e2.getMessage());
		} catch (IOException e) {
			System.err.println("Unable to open PDF Parser. " + e.getMessage());
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}

		System.out.println("+++++++++++++++++");
		System.out.println(parsedText);
		System.out.println("+++++++++++++++++");

		if(StringUtils.isNotEmpty(parsedText)){
			if(parsedText.contains(reqTextInPDF)) {
				flag=true;
			}
		}

		return flag;
	}
}

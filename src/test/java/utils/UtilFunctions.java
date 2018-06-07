package utils;

import constants.GlobalConstants;
import frames.*;
import org.ini4j.Ini;
import org.json.simple.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


/******************************************************************************
 Class Name: UtilFunctions
 Contains utility functions
 ******************************************************************************/

public class UtilFunctions {

    public String className = getClass().getSimpleName();
    private static UtilFunctions utilFunctions = new UtilFunctions();


    /**************************************************************************
     * name: getElementFromJSONFile(JSONObject jsonObj, String searchID,
     * String reqField)
     * functionality: Function to fetch value from json file
     * param: JSONObject jsonObj - JSON file object
     * param: String searchID - Name of element
     * param: String reqField - Type of field such as path, name, id, etc.
     * return: value from json file, null if not found
     *************************************************************************/
    public static String getElementFromJSONFile(JSONObject jsonObj, String searchID, String reqField){
        UtilFunctions.log("Class: " + utilFunctions.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());

        String result = null;
        //Try to find element definition within json sub-section
        try {
            if (GlobalConstants.subSection != "") {
                JSONObject subSectionObj = (JSONObject) jsonObj.get(GlobalConstants.subSection);
                if (searchID.contains(".") && reqField != "") {
                    String[] searchIDArr = searchID.split("\\.");
                    JSONObject childObj = (JSONObject) subSectionObj.get(searchIDArr[0]);
                    JSONObject childObj1 = (JSONObject) childObj.get(searchIDArr[1]);
                    UtilFunctions.log("Return: " + childObj1.get(reqField));
                    result = (String) childObj1.get(reqField);
                } else if (searchID.contains(".") && reqField == "") {
                    String[] searchIDArr = searchID.split("\\.");
                    JSONObject childObj = (JSONObject) subSectionObj.get(searchIDArr[0]);
                    if (childObj.get(searchIDArr[1]) != null) {
                        UtilFunctions.log("Return: " + childObj.get(searchIDArr[1]));
                        result = (String) childObj.get(searchIDArr[1]);
                    }
                } else {
                    UtilFunctions.log("Return: " + jsonObj.get(searchID));
                    result = (String) subSectionObj.get(searchID);
                }
                if (result != null) {
                    return result;
                }
            }
        }
        catch (Exception e){
            //add some code or leave as it is
        }

        //If element not found in json sub-section, try to find element in main json
        try {
            if (searchID.contains(".") && reqField != "") {
                String[] searchIDArr = searchID.split("\\.");
                JSONObject childObj = (JSONObject) jsonObj.get(searchIDArr[0]);
                JSONObject childObj1 = (JSONObject) childObj.get(searchIDArr[1]);
                UtilFunctions.log("Return: " + childObj1.get(reqField));
                result = (String) childObj1.get(reqField);
            } else if (searchID.contains(".") && reqField == "") {
                String[] searchIDArr = searchID.split("\\.");
                JSONObject childObj = (JSONObject) jsonObj.get(searchIDArr[0]);
                if (childObj.get(searchIDArr[1]) == null) {
                    UtilFunctions.log("Return: null");
                    return null;
                }
                else {
                    UtilFunctions.log("Return: " + childObj.get(searchIDArr[1]));
                    result = (String) childObj.get(searchIDArr[1]);
                }
            } else {
                UtilFunctions.log("Return: " + jsonObj.get(searchID));
                result = (String) jsonObj.get(searchID);
            }
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            UtilFunctions.log("Exception: " + e.getMessage());
            return null;
        }
    }

    /**************************************************************************
     * name: getElementStringAndType(JSONObject jsonObj, String searchID)
     * functionality: Function to fetch value from json file if search method
     * is not known
     * param: JSONObject jsonObj - JSON file object
     * param: String searchID - Name of element
     * return: value array from json file
     *************************************************************************/
    public static String[] getElementStringAndType(JSONObject jsonObj, String searchID) {
        UtilFunctions.log("Class: " + utilFunctions.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
        String[] retArr = new String[2];
        if (getElementFromJSONFile(jsonObj, searchID, "path") != null) {
            retArr[0] = getElementFromJSONFile(jsonObj, searchID, "path");
            retArr[1] = "xpath";
            UtilFunctions.log("xpath present");
            return retArr;
        }
        else if (getElementFromJSONFile(jsonObj, searchID, "id") != null) {
            retArr[0] = getElementFromJSONFile(jsonObj, searchID, "id");
            retArr[1] = "id";
            UtilFunctions.log("id present");
            return retArr;
        }
        else if (getElementFromJSONFile(jsonObj, searchID, "name") != null){
            retArr[0] = getElementFromJSONFile(jsonObj, searchID, "name");
            retArr[1] = "name";
            UtilFunctions.log("name present");
            return retArr;
        }
        else {
            UtilFunctions.log("Unknown Element Type");
            return retArr;
        }
    }
 
    /**************************************************************************
     * name: getJSONFileObjBasedOnPageName(String pageName)
     * functionality: Function to get json file object based on the page name
     * param: String pageName - Name of page
     * return: json file object
     *************************************************************************/
    public static JSONObject getJSONFileObjBasedOnPageName(String pageName){
        UtilFunctions.log("Class: " + utilFunctions.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
        switch (pageName){
            case "Login":
                return GlobalConstants.jsonObjLoginElements;
            case "ServiceProvider":
                return GlobalConstants.jsonObjServiceProviderElements;
            case "Advisor":
                return GlobalConstants.jsonObjAdvisorElements;
            case "Consumer":
                return GlobalConstants.jsonObjConsumerElements;
            case "Admin":
                return GlobalConstants.jsonObjAdminElements;
            default:
                return GlobalConstants.jsonObjGlobalElements;
        }
    }


    /**************************************************************************
     * name: getFrameMapBasedOnPageName(String tabName)
     * functionality: Function to fetch frame map object
     * param: String tabName - Name of tab
     * return: frame hashmap
     *************************************************************************/
    public static HashMap getFrameMapBasedOnPageName(String tabName){
        UtilFunctions.log("Class: " + utilFunctions.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
        switch (tabName){
            case "Global":
                return Global_Frames.globalFramesMap;
            default:
                return Global_Frames.globalFramesMap;
        }
    }


    /**************************************************************************
     * name: getTableValues(String tabName, String tableName)
     * functionality: Function to fetch table values from json file
     * param: String tabName - Name of tab
     * param: String tableName - Name of table
     * return: returns array of table values
     *************************************************************************/
    public static String[] getTableValues(String tabName, String tableName){
        UtilFunctions.log("Class: " + utilFunctions.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
        String[] tableDetailArr = new String[10];
        tabName = tabName.replace(" ", "");
        tableName = tableName.replace(" ", "");
        JSONObject fileObj = UtilFunctions.getJSONFileObjBasedOnPageName(tabName);
        HashMap frameMap = UtilFunctions.getFrameMapBasedOnPageName(tabName);
        try {
            tableDetailArr[0] = UtilFunctions.getElementFromJSONFile(fileObj, "TABLES." + tableName, "path"); //tablePath
            tableDetailArr[1] = UtilFunctions.getElementFromJSONFile(fileObj, "TABLES." + tableName, "id"); //tableId
            tableDetailArr[2] = UtilFunctions.getElementFromJSONFile(fileObj, "TABLES." + tableName, "head"); //tableHead
            tableDetailArr[3] = UtilFunctions.getElementFromJSONFile(fileObj, "TABLES." + tableName, "body"); //tableBody
            tableDetailArr[4] = UtilFunctions.getFrameValue(frameMap, UtilFunctions.getElementFromJSONFile(fileObj, "TABLES." + tableName, "frame")); //paneFrames
        }
        catch (Exception e){
            e.printStackTrace();
            UtilFunctions.log("Exception: " + e.getMessage());
        }
        return tableDetailArr;
    }


    /**************************************************************************
     * name: getFrameValue(HashMap frameMap, String frameName)
     * functionality: Function to fetch frame value from frame map
     * param: HashMap frameMap - HashMap of frame
     * param: String frameName - Name of frame
     * return: returns frame value
     *************************************************************************/
    public static String getFrameValue(HashMap frameMap, String frameName){
        UtilFunctions.log("Class: " + utilFunctions.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
        String frameValue = "";
        frameValue = (String) frameMap.get(frameName);
        if (frameValue == null)
            frameValue = Global_Frames.globalFramesMap.get(frameName);
        UtilFunctions.log("FrameValue: " + frameValue);
        return frameValue;
    }


    /**************************************************************************
     * name: log(String logDetail)
     * functionality: Function for logging
     * param: String logDetail - String to be logged
     * return: void
     *************************************************************************/
    public static void log(String logDetail){
        try{
            GlobalConstants.getLogger().info(logDetail);
        }
        catch (Exception e){
            GlobalConstants.getLogger().info("Exception while logging. Exception message: " + e.getMessage());
        }
    }


    /**************************************************************************
     * name: getValueFromTextFile(String valueToRead)
     * functionality: Get value as stored in text file
     * param: String valueToRead - Value to read
     * return: returns value from text file in String format
     *************************************************************************/
    public static String getValueFromTextFile(String valueToRead){
        UtilFunctions.log("Class: " + utilFunctions.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
        try (BufferedReader br = new BufferedReader(new FileReader(UtilProperty.setUpFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(valueToRead)){
                    UtilFunctions.log("Value to read: " + valueToRead);
                    String value = br.readLine();
                    if (value.equals("") || value == null)
                        break;
                    else
                        return value;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            UtilFunctions.log("File or value not present.");
        }
        if (valueToRead.contains("SECTION"))
            return "DEFAULT";
        else if (valueToRead.contains("APP"))
            return "chrome";
        else
            return "";
    }


    /**************************************************************************
     * name: getValueFromIniFile(String section, String key)
     * functionality: Get value stored in ini file
     * param: String section - Name of ini section
     * param: String key - Key to get value of
     * return: returns value from ini file in String format
     *************************************************************************/
    public static String getValueFromIniFile(String section, String key) {
        UtilFunctions.log("Class: " + utilFunctions.className + "; Method: " + new Object(){}.getClass().getEnclosingMethod().getName());
        try {
            Ini ini = new Ini(new File(UtilProperty.setUpIni));
            System.out.println(ini.get(section, key));
            UtilFunctions.log("SectionName: " + section);
            UtilFunctions.log("KeyName: " + key);
            UtilFunctions.log("Value: " + ini.get(section, key));
            if (ini.get(section, key) == null) {
                UtilFunctions.log("Returning: " + ini.get("DEFAULT", key));
                return ini.get("DEFAULT", key);
            }
            else {
                UtilFunctions.log("Returning: " + ini.get(section, key));
                return ini.get(section, key);
            }
        }
        catch (IOException e){
            e.printStackTrace();
            UtilFunctions.log("File or value not present. Retuning Default values.");
            return "";
        }
    }
}

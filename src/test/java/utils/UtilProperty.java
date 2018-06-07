package utils;

import java.io.File;


/******************************************************************************
 Class Name: UtilProperty
 Contains utility properties
 ******************************************************************************/

public class UtilProperty {

    //public static String browserType = "ie11";
    //public static String browserType = "chrome";

    public static String sMainDir = System.getProperty("user.dir");
    public static String setUpFile = sMainDir + "\\setUp.txt";
    public static String setUpIni = sMainDir + File.separator+"src"+File.separator+"test\\java\\support\\setUp.ini";
    
    public static String browserType = UtilFunctions.getValueFromTextFile("APP");
    public static String sectionName = UtilFunctions.getValueFromTextFile("SECTION");
    public static String tagName = UtilFunctions.getValueFromTextFile("TAG_NAME");

    public static String url = UtilFunctions.getValueFromIniFile(sectionName, "url");
    public static String userName = UtilFunctions.getValueFromIniFile(sectionName, "username");
    public static String userPwd = UtilFunctions.getValueFromIniFile(sectionName, "password");
}
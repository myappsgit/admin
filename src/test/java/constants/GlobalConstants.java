package constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;

import utils.UtilProperty;

public class GlobalConstants {
	
	//Elements
	public static JSONObject jsonObjLoginElements;
	public static JSONObject jsonObjGlobalElements;
	public static JSONObject jsonObjConsumerElements;
	public static JSONObject jsonObjServiceProviderElements;
    public static JSONObject jsonObjAdvisorElements;
    public static JSONObject jsonObjAdminElements;

	private static Logger log;
	public static String frameValue = "";

    public static String parentWindow;
	
    public static String startTime;
    public static String endTime;
    public static String pageName;
    public static String subSection;

    public static int ONE = 1;
    public static int TWO = 2;
    public static int FIVE = 5;
    public static int TEN = 10;
    public static int FIFTEEN = 15;
    public static int TWENTY = 20;
    public static int THIRTY = 30;
    public static int SIXTY = 60;

    public static int LONG_TIMEOUT = TEN;

    public static String browserProcessId = "";
	
    /**************************************************************************
     * name: setGlobalFrameValue(String value)
     * functionality: Initialize global frameValue with current frame value
     * param: String value - Current frame value
     * return: void
     *************************************************************************/
    public static void setGlobalFrameValue(String value){
        frameValue = value;
    }
    
    /**************************************************************************
     * name: getGlobalFrameValue()
     * return: returns the global frameValue
     *************************************************************************/
    public static String getGlobalFrameValue(){
        return frameValue;
    }
	
    /**************************************************************************
     * name: getLogger()
     * functionality: Get Logger class object for logging
     * return: logger object
     *************************************************************************/
    public static Logger getLogger(){
        if (log == null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy-hh-mm-ss");
            System.setProperty("current.date.time", dateFormat.format(new Date()));
            PropertyConfigurator.configure(UtilProperty.sMainDir + "\\log4j.properties");
            log = Logger.getLogger("rootLogger");
        }
        return log;
    }
    
    /**************************************************************************
     *generate random numbers for temp user
     *************************************************************************/
    static int randomNum = (new Random().nextInt(89999)) + 10000;
    public static HashMap<String, String> tempUser = new HashMap<String, String>() {{
        put("Username", "Verve" + randomNum);
        put("Password", "123456");
    }};
}

package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.junit.Assert;

import api.api_DataRepository.CreateUser;
import cucumber.api.DataTable;
import gherkin.deps.net.iharder.Base64;

public class APICommon {
	public static String globalURLType = "";
	public static String jsonBody = "";
	public static String userSessionId = "";
	
	// Building request using requestSpecBuilder
	RequestSpecBuilder builder = new RequestSpecBuilder();
	RequestSpecification requestSpec = builder.build();
	
	
	public void CreateUser(String httpMethod, String contextPath, DataTable dataTable)
	{   
		List<CreateUser> dataList = dataTable.asList(CreateUser.class);
        for(CreateUser data : dataList){
        	String userName = data.returnuserName();
        	String email = data.returnemailId();
        	String phone = data.returnmobileNo();
        	String password = data.returnpassword();
        	String addName = data.returnaddressingName();
        	String gender = data.returngender();
        	String city = data.returncity();
        	String dob = data.returnedob();
        	
        	String encodedPassword = Base64.encodeBytes(password.getBytes());
        	
        	
        String jsonStr = "{\"userName\": \""+userName+"\","
    			+ " \"emailId\": \""+email+"\","
    			+ " \"mobileNo\": \"+91"+phone+"\","
    			+ " \"authorization\":{ \"password\": \""+encodedPassword+"\" },"
    			+ " \"termsConditionsHistories\":[ { "
    			+ "\"termsConditions\":{ \"id\":\"14\" } } ],"
    			+ " \"photo\":null,"
    			+ " \"product\":\"Huddil\","
    			+ " \"addressingName\": \""+addName+"\","
    			+ " \"gender\": \""+gender+"\","
    			+ " \"city\": \""+city+"\","
    			+ " \"dob\":\""+dob+"\" }";
		
      //Initializing Rest API's URL
    	String APIUrl = "http://192.168.0.105:9292/solutions-1.0.0-BUILD-SNAPSHOT/usrDts/?redirectUrl=http://khushwantyadav.com/activate-email";
    	
//    	String APIUrl = "http://192.168.0.107:9191/solutions-1.0.0-BUILD-SNAPSHOT/usrDts/?redirectUrl=http://khushwantyadav.com/activate-email";

    				
    	// Building request using requestSpecBuilder
//    	RequestSpecBuilder builder = new RequestSpecBuilder();
    		
    	//Setting API's body
    	builder.setBody(jsonStr);
    		
    	//Setting content type as application/json or application/xml
    	builder.setContentType("application/json; charset=UTF-8");
    		
//    	RequestSpecification requestSpec = builder.build();

    	//Making post request with authentication, leave blank in case there are no credentials- basic("","")
    	Response response = RestAssured.given()
    				.spec(requestSpec).when().post(APIUrl);
    	String resCode = response.getHeader("responsecode");
    		
    	//Asserting that result
        
    	Assert.assertEquals("1111", resCode);
    	
//		RestAssured.baseURI  = "http://192.168.0.105:9292/solutions-1.0.0-BUILD-SNAPSHOT/" + contextPath + "?redirectUrl=http://khushwantyadav.com/activate-email";	
//    	Response r = RestAssured.given()
//    	.contentType("application/json").    	
//    	body(jsonStr).
//        when().
//        post("");
//
//    	String body = r.getBody().asString();
//    	System.out.println(body);
//    	
//    	String resCode = r.getHeader("responsecode");
//    	Assert.assertEquals("1111", resCode);
        }
	}
	
	public void getSessionId(String userName, String passWord){
		String authUrl = "http://1.23.62.114:9292/solutions-1.0.0-BUILD-SNAPSHOT/auth/";
		String encodedPassword = Base64.encodeBytes(passWord.getBytes());
		String encodedUsername = Base64.encodeBytes(userName.getBytes());
		
		jsonBody = "{\n    \"salt\":\""+encodedPassword+"\","
				+ "\n    \"password\":\""+encodedUsername+"\","
				+ "\n    \"product\":\"Huddil\"\n}";
		
		builder.setBody(jsonBody);
		
		Response response = RestAssured.given()
				.spec(requestSpec).when().post(authUrl);
		userSessionId = response.getHeader("sessionId");
		
		
	}
	
	public void CreateVideoTask(String taskTitle, String filePath){
		String uploadUrl = "http://1.23.62.114:9292/Huddil-1.0.0-BUILD-SNAPSHOT/upload/";
		RequestSpecBuilder builder = new RequestSpecBuilder();
		
		File videoFile = new File("C:\\testVideos\\Naruto Twin Rasenshuriken.mp4");
		builder.addMultiPart(videoFile);
		
		RequestSpecification requestSpec = builder.build();
		
		//Making post request with authentication, leave blank in case there are no credentials- basic("","")
    	Response response = RestAssured.given()
    				.spec(requestSpec).when().post(uploadUrl);
    	String resCode = response.getHeader("ResponseCode");
    	
    	//Asserting that result
    	Assert.assertEquals("1041", resCode);
    	
    	String location = response.getHeader("location");
    	
    	System.out.println(location);
    	System.out.println(taskTitle);
    	
    	String thumbnail = null;
    	File imageFile = new File("C:\\testThumbs\\thumb.jpg");
    	
    	try {
			thumbnail = Base64.encodeBytes(FileUtils.readFileToByteArray(imageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	System.out.println(thumbnail);
    		
    	String jsonForApprove = "{\n  \"taskForApprovalCategories\": [\n        {\n  "
    			+ "          \"category\": {\n            "
    			+ "    \"id\": 1}\n        },\n  "
    			+ "      {\n            \"category\": {\n    "
    			+ "            \"id\": 2}\n   "
    			+ "     },\n        {\n     "
    			+ "       \"category\": {\n    "
    			+ "            \"id\": 3}\n   "
    			+ "     }\n    ],\n\n "
    			+ "\"title\":\""+taskTitle+"\","
    			+ "\n"
    			+ "  \"description\": \"sampletest\",\n "
    			+ " \"geoLocation\": null,\n "
    			+ " \"icon\":null,\n "
    			+ "  \"ageFrom\": 15,\n "
    			+ "  \"ageTo\":60,\n "
    			+ " \"gender\": \"m\",\n "
    			+ " \"taskType\": \"Video\",\n "
    			+ " \"rewardPoints\": 35,\n "
    			+ " \"thumbnail\": \""+thumbnail+"\","
    			+ "\n "
    			+ " \"priority\": 1,\n "
    			+ " \"taskId\": null,\n "
    			+ " \"advisorComment\": "
    			+ "\"test successfull\",\n "
    			+ " \"status\": 1,\n  "
    			+ "\"googleAppsForApproval\": null,\n"
    			+ "  \"refId\":0,\n "
    			+ " \"videosForApproval\":{\n   "
    			+ "   \"location\":\""+location+"\","
    			+ "\n "
    			+ "     \"minResolution\":360,\n  "
    			+ "    \"maxResolution\":720,\n   "
    			+ "   \"duration\":4\n  },\n "
    			+ " \"windowsAppsForApproval\": null,\n "
    			+ " \"pollsForApproval\":null,\n  "
    			+ "\"iphoneAppsForApproval\":null,\n"
    			+ "  \"endDate\":\"2018-09-02\"\n}";	
    	
    	String approveUrl = "http://1.23.62.114:9292/Huddil-1.0.0-BUILD-SNAPSHOT/taskForApproval/"+userSessionId+"";
    	
    	RequestSpecBuilder builder1 = new RequestSpecBuilder();
    	RequestSpecification requestSpec1 = builder1.build();
    	
    	//Setting API's body
    	builder1.setBody(jsonForApprove);
    		
    	//Setting content type as application/json or application/xml
    	builder1.setContentType("application/json; charset=UTF-8");
    		
    	requestSpec1 = builder1.build();

    	//Making post request with authentication, leave blank in case there are no credentials- basic("","")
    	Response response1 = RestAssured.given()
    				.spec(requestSpec1).when().post(approveUrl);
    	resCode = response1.getHeader("responsecode");
    		
    	//Asserting that result
        
    	Assert.assertEquals("1011", resCode);
    	
	}
}
 

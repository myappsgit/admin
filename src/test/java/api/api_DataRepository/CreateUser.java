package api.api_DataRepository;

//Custom class used in list
public class CreateUser {
	 	private String userName;
	    private String emailId;
	    private String mobileNo;
	    private String password;
	    private String addressingName;
	    private String gender;
	    private String city;
	    private String dob;


	    //Constructor
	    public CreateUser(String userName, String emailId, String mobileNo, String password, String addressingName, String gender, String city, String dob) {
	        this.userName = userName;
	        this.emailId = emailId;
	        this.mobileNo = mobileNo;
	        this.password = password;
	        this.addressingName = addressingName;
	        this.gender = gender;
	        this.city = city;
	        this.dob = dob;
	    }
	    
	    public String returnuserName(){
	    	return userName;
	    }
	    
	    public String returnemailId(){
	    	return emailId;
	    }
	    
	    public String returnmobileNo(){
	    	return mobileNo;
	    }
	    
	    public String returnpassword(){
	    	return password;
	    }
	    
	    public String returnaddressingName(){
	    	return addressingName;
	    }
	    
	    public String returngender(){
	    	return gender;
	    }
	    
	    public String returncity(){
	    	return city;
	    }
	    
	    public String returnedob(){
	    	return dob;
	    }
}
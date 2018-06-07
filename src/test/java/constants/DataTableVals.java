package constants;

//import java.util.List;
//Custom class used in list
public class DataTableVals {
	 	private String Name;
	    private String Type;
	    private String Value;
//	    private List<String> listVals;

	    //Constructor
	    public DataTableVals(String Name, String Type, String Value) {
	        this.Name = Name;
	        this.Type = Type;
	        this.Value = Value;
	    }
   
	    public String returnName(){
	    	return Name;
	    }
	    
	    public String returnType(){
	    	return Type;
	    }
	    
	    public String returnValue(){
	    	return Value;
	    }
}

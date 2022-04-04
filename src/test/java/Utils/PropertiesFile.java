package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFile {

/*public static void main(String[] args) {
	getBaseURI();
}	*/
	static Properties prop=new Properties();
	static InputStream input;
	public static String getBaseURI()
	{
		
		
		try {
			input = new
					FileInputStream("/Users/suresh/eclipse-workspace/JobsAPIAutomation/src/test/resources/properties/config.properties");
			prop.load(input);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (prop.getProperty("baseURI"));
		
	}
	
	public static String getEndPoint()
	{
		String endPoint=null;
		try {
			input = new
					FileInputStream("/Users/suresh/eclipse-workspace/JobsAPIAutomation/src/test/resources/properties/config.properties");
			prop.load(input);
			endPoint = prop.getProperty("getEndPoint");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return endPoint;
		
	}
}

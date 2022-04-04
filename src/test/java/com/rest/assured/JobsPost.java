package com.rest.assured;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utils.ExcelHandling;
import Utils.PropertiesFile;
import io.restassured.response.Response;
import junit.framework.Assert;

public class JobsPost {
	
	@Test(dataProvider="PostData")
	public void PostJob(String JobId,String JobTitle,String JobCompanyName, 
			String JobLocation,String JobType,String JobPostedTIme,String JobDesc) throws ParseException {
		
		//set the end point from properties file
		baseURI = PropertiesFile.getBaseURI();
		String endPoint = PropertiesFile.getEndPoint();
		
		//request body
		JSONObject request=new JSONObject();
		
		request.put("Job Id", JobId);
		request.put("Job Title",JobTitle );
		request.put("Job Company Name", JobCompanyName);
		request.put("Job Location", JobLocation);
		request.put("Job Type", JobType);
		request.put("Job Posted time", JobPostedTIme);
		request.put("Job Description", JobDesc);
		System.out.println(request.toJSONString());
		
			
		Response response=
		given().
			contentType("application/json").
			body(request.toJSONString()).
			//body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/jobsSchema.json")).
		when().
			post(baseURI+endPoint).
		then().
			extract().response();
		
		
		System.out.println("Respose as string:"+response.getBody().asPrettyString());
		System.out.println("Status line:" + response.getStatusLine()); //200 OK
		
		int statusCode=response.getStatusCode();
		String statusLine=response.getStatusLine();
		String responseString = response.asString().replace("NaN", "null");
		
		//Json parser converts JSON String to JSON object
		/*JSONParser parser = new JSONParser();  
		JSONObject json = (JSONObject) parser.parse(responseString);  
		System.out.println("JSON after NaN"+json);
		//System.out.println("JSON after NaN"+json.data.Job);
		*/
	
		//Json schema validation
				assertThat(responseString,
						matchesJsonSchemaInClasspath("schema/jobsSchema.json"));
				
		if (statusCode==200)
		{
		//Respose body validation
		/*JsonPath responseJson = response.jsonPath();
		responseJson.get("data[\"Job Location\"]");
		System.out.println("data"+responseJson.get("data[\"Job Location\"]")); */
		
		assertTrue(responseString.contains("Job Id"));
		Assert.assertNotNull("Job id");
		assertTrue(responseString.contains("Job Title"));
		assertTrue(responseString.contains("Job Company Name"));
		assertTrue(responseString.contains("Job Location"));
		assertTrue(responseString.contains("Job Type"));
		assertTrue(responseString.contains("Job Posted time"));
		assertTrue(responseString.contains("Job Description"));
		
		}
		else if(statusCode== 409)
		{
			System.out.println("Job id already exists");
			Assert.assertEquals(statusCode, 409);
			Assert.assertEquals(statusLine.contains("409 CONFLICT"),true);
			Assert.assertEquals(response.asString().contains("already exists"), true);
		}
		else
			System.out.println("Invalid request/data");
	} 
	
	@DataProvider(name="PostData")
	public Object[][] getData(){
		String path="/Users/suresh/eclipse-workspace/JobsAPIAutomation/src/test/resources/data/TestData.xlsx";
		String sheetName="Post";
		Object data[][]= ExcelHandling.getTestData(path,sheetName);
		return data;
	}
		
}

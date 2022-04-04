package com.rest.assured;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utils.ExcelHandling;
import Utils.PropertiesFile;
import io.restassured.response.Response;
import junit.framework.Assert;

public class JobsPut {
	
	@Test(dataProvider="PutData")
	public void putJob(String JobId,String JobTitle,String JobCompanyName, 
			String JobLocation,String JobType,String JobPostedTIme)
	{
		
		//set the end point from properties file
		baseURI = PropertiesFile.getBaseURI();
		String endPoint = PropertiesFile.getEndPoint();
				
		//create data for put request
		JSONObject putRequest = new JSONObject();
		
		putRequest.put("Job Id", JobId);
		putRequest.put("Job Title",JobTitle );
		putRequest.put("Job Company Name", JobCompanyName);
		putRequest.put("Job Location", JobLocation);
		putRequest.put("Job Type", JobType);
		putRequest.put("Job Posted time", JobPostedTIme);
		
		//performing put
		Response response = given().
			contentType("application/json").
			body(putRequest.toJSONString()).
		when().put(endPoint).
		then().extract().response();
		System.out.println("Respose as string:"+response.getBody().asPrettyString());
		System.out.println("Status line:" + response.getStatusLine()); //200 OK
		
		int statusCode=response.getStatusCode();
		String statusLine=response.getStatusLine();
		String responseString = response.asString().replace("NaN", "null");
		
		//schema validation
		assertThat(responseString,
				matchesJsonSchemaInClasspath("schema/jobsSchema.json"));
		
		
		if (statusCode==200)
		{
		
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
	
	
	@DataProvider(name="PutData")
	public Object[][] getData(){
		String path="/Users/suresh/eclipse-workspace/JobsAPIAutomation/src/test/resources/data/TestData.xlsx";
		String sheetName="Put";
		Object data[][]= ExcelHandling.getTestData(path,sheetName);
		return data;
	}
}

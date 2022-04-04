package com.rest.assured;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utils.ExcelHandling;
import Utils.PropertiesFile;
import io.restassured.response.Response;

public class JobsDelete {

	@Test (dataProvider="DeleteData")
	public void deleteJob(String jobId)
	{
		//setting the endpoint
		baseURI = PropertiesFile.getBaseURI();
		String endPoint = PropertiesFile.getEndPoint();
		System.out.println(baseURI+endPoint);
		
		//delete request
		Response response= given().//contentType("application/json")
			queryParam("Job Id", jobId).when().delete(endPoint).then().extract().response();
		System.out.println("Deleted response:" + response.asPrettyString());
		int statusCode= response.getStatusCode();
		System.out.println(statusCode);
		
		//assertions
		if(statusCode ==200)
		{
			Assert.assertEquals(statusCode, 200);
		}
		else if (statusCode == 404)
		{
			Assert.assertEquals(statusCode, 404);
			Assert.assertEquals(response.asString().contains("Job  not found"),true);
		}
		else
			System.out.println("Invalid request/data.");
	}
	
	@DataProvider(name="DeleteData")
	public Object[][] getData(){
		String path="/Users/suresh/eclipse-workspace/JobsAPIAutomation/src/test/resources/data/TestData.xlsx";
		String sheetName="Delete";
		Object data[][]= ExcelHandling.getTestData(path,sheetName);
		return data;
	}
}

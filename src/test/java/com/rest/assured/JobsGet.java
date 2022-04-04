package com.rest.assured;
import Utils.*;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.File;

import org.junit.Assert;
import org.testng.annotations.Test;

import  static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;
/*yet to be completed
 1. JSON Schema generation-- done
 2.schema validation -- done
 3.response body validation 
 4. TestNG execution -- done
*/

public class JobsGet {
	@Test
	public void getall()
	{
		
		//set endpoint from properties file
		baseURI = PropertiesFile.getBaseURI();
		String endPoint = PropertiesFile.getEndPoint();
		System.out.println(baseURI+endPoint);
		
		//get the response from API
		Response response = given().get(baseURI+endPoint).
				then().statusCode(200).extract().response();
		//Assert.assertEquals(response.getStatusCode(), 200);
		//assertThat().body(matchesJsonSchemaInClasspath("schema/jobsSchema.json"));
			
		System.out.println(response.getBody().asPrettyString());
		
		
		//response body validation
		String stringResponse= response.asString();
		Assert.assertEquals(response.getStatusCode(), 200);
		String responseString =stringResponse.replace("NaN","null");
		System.out.println(responseString);
		
		assertTrue(responseString.contains("Job Id"));
		Assert.assertNotNull("Job id");
		assertTrue(responseString.contains("Job Title"));
		assertTrue(responseString.contains("Job Company Name"));
		assertTrue(responseString.contains("Job Location"));
		assertTrue(responseString.contains("Job Type"));
		assertTrue(responseString.contains("Job Posted time"));
		assertTrue(responseString.contains("Job Description"));
		/*response body validation
		 *  Response getresponse = httprequest.request(Method.GET);

			JsonPath path = JsonPath.from(getresponse.getBody().asString().replaceAll("NaN", "null"));
			Map<String,String> jobids =	path.get("data.\"Job Id\"");

			//	System.out.println(" key value pair of all JobIds " + jobids);

			String jobIdkey=null;
			for (String key : jobids.keySet())
			{
				// getting key for the actual jobId value

				String IdValue = jobids.get(key);
				//System.out.println(" Job Id values " + IdValue);

				if(IdValue!=null) {
					if(jobids.get(key).equals(JobId)){
						System.out.println(" Key of the actual Job Id " + key);
						jobIdkey=key;
						break;
					}
				}
			}

			String actualJobTit = path.get("data.\"Job Title\"."+jobIdkey+"");
			String actualJobloc = path.get("data.\"Job Location\"."+jobIdkey+"");
			String actualJobcomnam = path.get("data.\"Job Company Name\"."+jobIdkey+"");
			String actualJobTyp = path.get("data.\"Job Type\"."+jobIdkey+"");
			String actualJobposted = path.get("data.\"Job Posted time\"."+jobIdkey+"");
			String actualJobdes = path.get("data.\"Job Description\"."+jobIdkey+"");
			Assert.assertEquals(actualJobTit,Jobtitle);
			Assert.assertEquals(actualJobloc,Joblocation);
			Assert.assertEquals(actualJobcomnam,JobcompanyName);
			Assert.assertEquals(actualJobTyp,Jobtype);
			Assert.assertEquals(actualJobposted,Jobposttime);
			Assert.assertEquals(actualJobdes,JobDescription);

		}
		 */
		//Assert.assertequals(JsonSchemaValidator.matchesJsonSchemaInClasspath("jobsSchema.json"), true);
		//assertThat(responseWithoutNan,JsonSchemaValidator.matchesJsonSchemaInClasspath("jobsSchema.json"));
		//assertThat(responseWithoutNan,JsonSchemaValidator.matchesJsonSchemaInClasspath("jobsSchema.json"));
//		assertThat(responseWithoutNan,
//				matchesJsonSchemaInClasspath(ff.getAbsolutePath()));
		
		System.out.println("Adding print statement");
		//Json schema validation
		assertThat(responseString,
				matchesJsonSchemaInClasspath("schema/jobsSchema.json"));
						
	}
	
}

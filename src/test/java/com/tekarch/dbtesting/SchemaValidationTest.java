package com.tekarch.dbtesting;

import java.util.Random;
//import java.io.File;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
//import io.restassured.module.jsv.JsonSchemaValidator;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


import static io.restassured.RestAssured.*;

//import io.restassured.response.Response;
////import io.restassured.specification.RequestSpecification;

public class SchemaValidationTest {

	@Test
	public void schemaTest()
	{
		Random ran = new Random();
		int randomInt = ran.nextInt(2000);
		ProjectPojo pObj = new ProjectPojo("renu","PojoSchemaVal"+randomInt,"Completed",0);
		
					   given()
						   .baseUri("http://49.249.28.218:8091")
						   .contentType(ContentType.JSON)
						   .body(pObj)
					   .when()	
					   		.post("/addProject")
					   		 		
					   .then()
					  		.log().all()
					  		.assertThat().statusCode(201)
					  		//.assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File("./addProjectSchema.json")));
					   		.assertThat().body(matchesJsonSchemaInClasspath("addProjectSchema.json"));
					  			
		
	}
	
	
	
}

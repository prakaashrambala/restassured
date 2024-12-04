package com.petstore.tests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.petstore.api.PetstoreAPI;
import com.petstore.models.Pet;
import com.petstore.utils.ApiUtils;

import groovy.json.JsonParser;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.io.IOException;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class PetstoreTestNG {

	String path = System.getProperty("user.dir");
    private static final String API_KEY = "your_api_key";
    private int petId;

    @BeforeClass
    public void setup(){
        // Set up any required configurations or pre-conditions.
    	 
    }
   
    @AfterClass
    public void teardown() {
        // Perform cleanup after tests, if necessary.
    }

    @Test(priority = 1)
    public void createPetTest() throws JsonProcessingException, IOException {
    	// Reading the test data through json
    	ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(path+"/src/test/resources/createTestData.json"));
        String testID = jsonNode.get("id").asText();
        System.out.println("Test :::::: "+testID);
        System.out.println("Test :::::: "+jsonNode.toString());
        Response response = PetstoreAPI.createPet(jsonNode.toString());
        ApiUtils.verifyResponseCode(response, 200);
        ApiUtils.validatePetResponse(response);
        petId = response.jsonPath().getInt("id");  // Save the pet ID for future tests.
    }

    @Test(priority = 2)
    public void getPetTest() {
        Response response = PetstoreAPI.getPetById(petId);
        ApiUtils.verifyResponseCode(response, 200);
        ApiUtils.validatePetResponse(response);
    }

    @Test(priority = 3)
    public void updatePetTest() throws JsonProcessingException, IOException {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	ObjectNode  jsonNode = (ObjectNode) objectMapper.readTree(new File(path+"/src/test/resources/createTestData.json"));
        jsonNode.put("status", "sold");
  
        System.out.println("After update :::: "+jsonNode.toString());

        Response response = PetstoreAPI.updatePet(jsonNode.toString());
        ApiUtils.verifyResponseCode(response, 200);
        ApiUtils.validatePetResponse(response);
    }

    @Test(priority = 4)
    public void deletePetTest() {
        Response response = PetstoreAPI.deletePet(petId, API_KEY);
        ApiUtils.verifyResponseCode(response, 200);
    }
    @Test(priority = 5)
    public void findPetsByStatusTest() {
        String status = "available"; // Status we want to filter pets by

        // Call the Petstore API to find pets by status
        Response response = PetstoreAPI.findPetsByStatus(status);

        // Validate that the response code is 200 (OK)
        Assert.assertEquals(response.statusCode(), 200, "Expected status code 200 for finding pets by status");

        // Validate that the pets returned have the expected status
        Assert.assertTrue(response.body().asString().contains(status), "Pets should have status 'available'");
    }
    
}

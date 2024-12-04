package com.petstore.tests;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.api.PetstoreAPI;
import com.petstore.models.Pet;
import com.petstore.utils.ApiUtils;

import groovy.json.JsonParser;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.io.IOException;

import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class PetstoreTestNG {

    private static final String API_KEY = "your_api_key";
    private int petId;

    @BeforeClass
    public void setup() throws JsonProcessingException, IOException {
        // Set up any required configurations or pre-conditions.
    	
    	 ObjectMapper objectMapper = new ObjectMapper();
         JsonNode jsonNode = objectMapper.readTree(new File("D:\\Users\rabalasubramaniam\\eclipse-workspace\\APIProject\\src\\test\\resources\\createTestData.json"));
         String testID = jsonNode.get("id").asText();
         System.out.println("Test :::::: "+testID);
    }

    
    
    
    @AfterClass
    public void teardown() {
        // Perform cleanup after tests, if necessary.
    }

    @Test(priority = 1)
    public void createPetTest() {
        Pet pet = new Pet();
        pet.setName("Doggie");
        pet.setStatus("available");

        Response response = PetstoreAPI.createPet(pet.toString());
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
    public void updatePetTest() {
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setName("Updated Doggie");
        pet.setStatus("sold");

        Response response = PetstoreAPI.updatePet(pet.toString());
        ApiUtils.verifyResponseCode(response, 200);
        ApiUtils.validatePetResponse(response);
    }

    @Test(priority = 4)
    public void deletePetTest() {
        Response response = PetstoreAPI.deletePet(petId, API_KEY);
        ApiUtils.verifyResponseCode(response, 200);
    }
    
}

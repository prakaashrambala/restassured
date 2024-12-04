package com.petstore.tests;


import com.petstore.api.PetstoreAPI;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PetstoreTest {

    // Setup method - will run before each test
    @BeforeClass
    public void setup() {
        // This method can be used for setup actions like setting base URIs, logging, etc.
        System.out.println("Setting up the Petstore test suite...");
    }

    // Test case to create a pet
    @Test(priority = 1)
    public void createPetTest() {
        // Sample JSON data representing a pet
        String petJson = "{ \"id\": 101, \"name\": \"Doggie\", \"status\": \"available\" }";

        // Call the Petstore API to create a pet
        Response response = PetstoreAPI.createPet(petJson);

        // Validate that the response code is 200 (OK)
        Assert.assertEquals(response.statusCode(), 200, "Expected status code 200 for creating a pet");

        // Validate the pet data in the response (ensure the pet is created correctly)
        Assert.assertTrue(response.body().asString().contains("Doggie"), "Pet name should be 'Doggie'");
        Assert.assertTrue(response.body().asString().contains("available"), "Pet status should be 'available'");
    }

    // Test case to retrieve a pet by ID
    @Test(priority = 2)
    public void getPetByIdTest() {
        int petId = 101; // ID of the pet we created in the previous test

        // Call the Petstore API to get a pet by ID
        Response response = PetstoreAPI.getPetById(petId);

        // Validate that the response code is 200 (OK)
        Assert.assertEquals(response.statusCode(), 200, "Expected status code 200 for getting a pet");

        // Validate the pet details in the response (ensure the pet is fetched correctly)
        Assert.assertTrue(response.body().asString().contains("Doggie"), "Pet name should be 'Doggie'");
        Assert.assertTrue(response.body().asString().contains("available"), "Pet status should be 'available'");
    }

    // Test case to update a pet
    @Test(priority = 3)
    public void updatePetTest() {
        // Sample JSON data representing updated pet details
        String updatedPetJson = "{ \"id\": 101, \"name\": \"DoggieUpdated\", \"status\": \"sold\" }";

        // Call the Petstore API to update the pet
        Response response = PetstoreAPI.updatePet(updatedPetJson);

        // Validate that the response code is 200 (OK)
        Assert.assertEquals(response.statusCode(), 200, "Expected status code 200 for updating a pet");

        // Validate the updated pet details in the response
        Assert.assertTrue(response.body().asString().contains("DoggieUpdated"), "Pet name should be updated to 'DoggieUpdated'");
        Assert.assertTrue(response.body().asString().contains("sold"), "Pet status should be 'sold'");
    }

    // Test case to delete a pet
    @Test(priority = 4)
    public void deletePetTest() {
        int petId = 101; // ID of the pet we created and updated in the previous tests

        // The API key can be a placeholder in this example. For a real test, you'd need to pass a valid key.
        String apiKey = "your-api-key-here"; 

        // Call the Petstore API to delete the pet
        Response response = PetstoreAPI.deletePet(petId, apiKey);

        // Validate that the response code is 200 (OK)
        Assert.assertEquals(response.statusCode(), 200, "Expected status code 200 for deleting a pet");

        // Validate that the pet is successfully deleted (status code 404 means it no longer exists)
        Response getResponse = PetstoreAPI.getPetById(petId);
        Assert.assertEquals(getResponse.statusCode(), 404, "Expected status code 404 for fetching a deleted pet");
    }

    // Test case to find pets by status
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


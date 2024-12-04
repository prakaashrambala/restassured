package com.petstore.utils;



import io.restassured.response.Response;
import org.testng.Assert;

public class ApiUtils {

    public static void verifyResponseCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.statusCode(), expectedStatusCode,
                "Expected status code " + expectedStatusCode + " but got " + response.statusCode());
    }

    public static void validatePetResponse(Response response) {
        Assert.assertNotNull(response.jsonPath().getString("id"), "Pet ID is null");
        Assert.assertNotNull(response.jsonPath().getString("name"), "Pet name is null");
        Assert.assertNotNull(response.jsonPath().getString("status"), "Pet status is null");
    }

    public static void handleError(Response response) {
        if (response.statusCode() != 200) {
            throw new RuntimeException("API request failed with status code: " + response.statusCode());
        }
    }
}

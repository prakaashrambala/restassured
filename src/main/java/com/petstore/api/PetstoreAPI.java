package com.petstore.api;


import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetstoreAPI {
    private static final Logger logger = LoggerFactory.getLogger(PetstoreAPI.class);
    
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    
    public static Response createPet(String petJson) {
        logger.info("Creating pet with data: " + petJson);
        return RestAssured.given().filter(new AllureRestAssured())
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(petJson)
                .when()
                .post("/pet");
    }
    
    public static Response getPetById(int petId) {
        logger.info("Getting pet with ID: " + petId);
        return RestAssured.given().filter(new AllureRestAssured())
                .baseUri(BASE_URL)
                .when()
                .get("/pet/" + petId);
    }

    public static Response updatePet(String petJson) {
        logger.info("Updating pet with data: " + petJson);
        return RestAssured.given().filter(new AllureRestAssured())
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(petJson)
                .when()
                .put("/pet");
    }

    public static Response deletePet(int petId, String apiKey) {
        logger.info("Deleting pet with ID: " + petId);
        return RestAssured.given().filter(new AllureRestAssured())
                .baseUri(BASE_URL)
                .header("api_key", apiKey)
                .when()
                .delete("/pet/" + petId);
    }

    public static Response findPetsByStatus(String status) {
        logger.info("Finding pets with status: " + status);
        return RestAssured.given().filter(new AllureRestAssured())
                .baseUri(BASE_URL)
                .queryParam("status", status)
                .when()
                .get("/pet/findByStatus");
    }
}

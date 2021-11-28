package ru.praktikumservices.qascooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class CourierCreationNegativeDataTest {

    ScooterCourierMakerAndOperationsHelper courier = new ScooterCourierMakerAndOperationsHelper();

    @Before
    public void setUp() {
        // RestAssured.baseURI = SCOOTER_API_SERVICES_URL;
        courier.generateCourierCredentials();
    }

    @Test
    @DisplayName("Тест на попытку зарегистрировать курьера без передачи поля пароля")
    public void courierCreationWithoutPasswordFails (){
        String registerRequestBody = "{\"login\":\"" + courier.getCourierLogin() + "\","
                + "\"firstName\":\"" + courier.getCourierFirstName() + "\"}";
        courier.setCourierResponse(given()
                .spec(courier.setupAssured())
                .body(registerRequestBody)
                .when()
                .post("/api/v1/courier"));
        assertEquals(400, courier.getCourierResponse().getStatusCode());
    }

    @Test
    @DisplayName("Тест на попытку зарегистрировать курьера без передачи поля логина")
    public void courierCreationWithoutLoginFails (){
        String registerRequestBody = "{\"password\":\"" + courier.getCourierPassword() + "\","
                + "\"firstName\":\"" + courier.getCourierFirstName() + "\"}";
        courier.setCourierResponse(given()
                .spec(courier.setupAssured())
                .body(registerRequestBody)
                .when()
                .post("/api/v1/courier"));
        assertEquals(400, courier.getCourierResponse().getStatusCode());
    }

}

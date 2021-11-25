package ru.praktikumservices.qascooter;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class CourierCreationApiMethodsTest {

    private final String SCOOTER_API_SERVICES_URL = "http://qa-scooter.praktikum-services.ru/";
    ScooterCourierMakerAndOperationsHelper courier = new ScooterCourierMakerAndOperationsHelper();

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_API_SERVICES_URL;
        courier.generateCourierCredentials();
    }

    @After
    public void deleteCourierToClearTestData(){
        courier.deleteCourier();
    }

    @Test //проверяем возможность создать курьера и что возвращается ожидаемый ответ
    public void courierCreationIsPossible(){
        courier.registerCourier();
        assertEquals("{\"ok\":true}", courier.getCourierResponse().getBody().asString());
    }

    @Test //проверка что нельзя создать дубль курьера (и с совпадающим логином)
    public void sameCourierCredentialsIsNotAllowed (){
        courier.registerCourier();
        courier.registerCourier();
        assertEquals(409,courier.getCourierResponse().getStatusCode());
    }

    @Test
    public void courierCreationWithoutPasswordFails (){
        String registerRequestBody = "{\"login\":\"" + courier.getCourierLogin() + "\","
                                + "\"firstName\":\"" + courier.getCourierFirstName() + "\"}";
        courier.setCourierResponse(given()
                .header("Content-type", "application/json")
                .and()
                .body(registerRequestBody)
                .when()
                .post("/api/v1/courier"));
        assertEquals(400, courier.getCourierResponse().getStatusCode());
    }

    @Test
    public void courierCreationWithoutLoginFails (){
        String registerRequestBody = "{\"password\":\"" + courier.getCourierPassword() + "\","
                + "\"firstName\":\"" + courier.getCourierFirstName() + "\"}";
        courier.setCourierResponse(given()
                .header("Content-type", "application/json")
                .and()
                .body(registerRequestBody)
                .when()
                .post("/api/v1/courier"));
        assertEquals(400, courier.getCourierResponse().getStatusCode());
    }

}

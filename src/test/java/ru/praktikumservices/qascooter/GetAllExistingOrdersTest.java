package ru.praktikumservices.qascooter;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;


public class GetAllExistingOrdersTest {

    private final String SCOOTER_API_SERVICES_URL = "http://qa-scooter.praktikum-services.ru/";

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_API_SERVICES_URL;
    }

    @Test //если в ответе есть хоть одно поле ID, значит заказы есть
    public void isReturnedOrderListNotEmpty (){

        String ordersListFromResponse = given()
                .when()
                .get("/api/v1/orders")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().body().asString();

        assertTrue(ordersListFromResponse.contains("id"));
    }
}

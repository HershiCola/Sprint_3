package ru.praktikumservices.qascooter;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;

public class CourierLoginApiMethodsTest {

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

    @Test //курьер может авторизваться + успешный запрос возвращает id
    public void courierLoginSuccess (){
        courier.registerCourier();
        courier.courierLoggingIn();
        courier.setCourierId();
        assertNotEquals(0, courier.getCourierId());
    }

    @Test //незарегистрированный курьер не может залогиниться = система возвращает ошибку если указать неверный логин и пароль
    public void unregisteredCourierLoginFails(){
        courier.courierLoggingIn();
        assertEquals(404, courier.getCourierResponse().getStatusCode());
    }

    //два теста ниже: для авторизации нужны все обязательные поля, если какого-то из полей нет - вернет ошибку
    @Test(timeout = 10000) //баг системы qa-scooter, вечный запрос при отсутствии пароля
    public void courierLoginWithoutPasswordFails () {

        courier.registerCourier();
        String loginRequestBody = "{\"login\":\"" + courier.getCourierLogin() + "\"}";
        courier.setCourierResponse(given()
                .header("Content-type", "application/json")
                .and()
                .body(loginRequestBody)
                .when()
                .post("/api/v1/courier/login"));
        assertEquals(400, courier.getCourierResponse().getStatusCode());
    }

    @Test
    public void courierLoginWithoutLoginFails () {

        courier.registerCourier();
        String loginRequestBody = "{\"password\":\"" + courier.getCourierPassword() + "\"}";
        courier.setCourierResponse(given()
                .header("Content-type", "application/json")
                .and()
                .body(loginRequestBody)
                .when()
                .post("/api/v1/courier/login"));
        assertEquals(400, courier.getCourierResponse().getStatusCode());
    }
}

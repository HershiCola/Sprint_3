package ru.praktikumservices.qascooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;

public class CourierLoginApiMethodsTest {

        ScooterCourierMakerAndOperationsHelper courier = new ScooterCourierMakerAndOperationsHelper();

    @Before
    public void setUp() {
        courier.generateCourierCredentials();
    }

    @After
    public void deleteCourierToClearTestData(){
        courier.deleteCourier();
    }

    @Test //курьер может авторизваться + успешный запрос возвращает id
    @DisplayName("Тест на успешный логин курьера")
    public void courierLoginSuccess (){
        courier.registerCourier();
        courier.courierLoggingIn();
        courier.setCourierId();
        assertNotEquals(0, courier.getCourierId());
    }

    //два теста ниже: для авторизации нужны все обязательные поля, если какого-то из полей нет - вернет ошибку
    @Test(timeout = 5000) //баг системы qa-scooter, вечный запрос при отсутствии пароля
    @DisplayName("Тест на попытку логина курьера без передачи поля пароля")
    public void courierLoginWithoutPasswordFails () {

        courier.registerCourier();

        String loginRequestBody = "{\"login\":\"" + courier.getCourierLogin() + "\"}";
        courier.setCourierResponse(given()
                .spec(courier.setupAssured())
                .body(loginRequestBody)
                .when()
                .post("/api/v1/courier/login"));
        assertEquals(400, courier.getCourierResponse().getStatusCode());
    }

    @Test
    @DisplayName("Тест на попытку логина курьера без передачи поля логина")
    public void courierLoginWithoutLoginFails () {

        courier.registerCourier();

        String loginRequestBody = "{\"password\":\"" + courier.getCourierPassword() + "\"}";
        courier.setCourierResponse(given()
                .spec(courier.setupAssured())
                .body(loginRequestBody)
                .when()
                .post("/api/v1/courier/login"));
        assertEquals(400, courier.getCourierResponse().getStatusCode());
    }
}

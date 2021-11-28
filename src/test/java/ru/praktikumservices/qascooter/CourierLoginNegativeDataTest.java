package ru.praktikumservices.qascooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CourierLoginNegativeDataTest {

    ScooterCourierMakerAndOperationsHelper courier = new ScooterCourierMakerAndOperationsHelper();

    @Before
    public void setUp() {
        courier.generateCourierCredentials();
    }


    @Test //незарегистрированный курьер не может залогиниться = система возвращает ошибку если указать неверный логин и пароль
    @DisplayName("Тест на попытку логина незарегистрированного курьера")
    public void unregisteredCourierLoginFails(){
        courier.courierLoggingIn();
        assertEquals(404, courier.getCourierResponse().getStatusCode());
    }
}

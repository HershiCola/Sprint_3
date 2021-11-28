package ru.praktikumservices.qascooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourierCreationApiMethodsTest {

    ScooterCourierMakerAndOperationsHelper courier = new ScooterCourierMakerAndOperationsHelper();

    @Before
    public void setUp() {
        courier.generateCourierCredentials();
    }

    @After
    public void deleteCourierToClearTestData(){
         courier.deleteCourier();
    }

    @Test //проверяем возможность создать курьера и что возвращается ожидаемый ответ
    @DisplayName("Тест на возможность зарегистрировать курьера")
    public void courierCreationIsPossible(){
        courier.registerCourier();
        assertTrue(courier.getCourierResponse().jsonPath().getBoolean("ok"));
    }

    @Test //проверка что нельзя создать дубль курьера (и с совпадающим логином)
    @DisplayName("Тест на невозможность зарегистрировать одинаковых курьеров")
    public void sameCourierCredentialsIsNotAllowed (){
        courier.registerCourier();
        courier.registerCourier();
        assertEquals(409,courier.getCourierResponse().getStatusCode());
    }
}

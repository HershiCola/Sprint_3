package ru.praktikumservices.qascooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;

public class GetAllExistingOrdersTest extends RestAssuredSpecForTests {


    @Test //проверку переделал по примеру с вебинара, собираем список и проверяем что он не пуст
    @DisplayName("Тест на проверку того, что запрос возвращает список заказов")
    public void isReturnedOrderListNotEmpty (){

                List<Object> orders = given()
                        .spec(setupAssured())
                        .when()
                        .get("/api/v1/orders")
                        .then()
                        .extract()
                        .jsonPath()
                        .getList("orders");

                assertFalse(orders.isEmpty());
    }
}



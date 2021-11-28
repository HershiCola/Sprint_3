package ru.praktikumservices.qascooter;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class OrderCreationParameterizedApiTest extends RestAssuredSpecForTests{

    private final String color;
    private final int unexpectedNumber;

    public OrderCreationParameterizedApiTest(String color, int unexpectedNumber){
            this.color = color;
            this.unexpectedNumber = unexpectedNumber;
    };

    @Parameterized.Parameters
    //использую два параметра на случай,
    //если в дальнейшем ожидаемый результат будет варьироваться
    //а так вполне достаточно одного параметра color
    public static Object[][] checkColorCombinations() {
        return new Object[][] {
                {",\"color\":[\"BLACK\"]", 0},
                {",\"color\":[\"GREY\"]", 0},
                {",\"color\":[\"BLACK\", \"GRAY\"]", 0},
                {"", 0},
        };
    }

    @Test
    @DisplayName("Параметризованный тест на создание заказа")
    public void createOrderParameterizedTest(){

        //Генерацию JSON не давали ни в курсе, ни на вебинарах.
        String createOrderRequestBody = "{\"firstName\":\"TestName\","
                + "\"lastName\":\"TestLastName\","
                + "\"address\":\"Dmitrov City, 666\","
                + "\"metroStation\":\"7\","
                + "\"phone\":\"+7 222 333 44 55\","
                + "\"rentTime\":\"11\","
                + "\"deliveryDate\":\"2022-01-01\","
                + "\"comment\":\"Great job, fellas!\""
                + color +"}";

        int returnedTrackNumber = given()
                .spec(setupAssured())
                .body(createOrderRequestBody)
                .when()
                .post("/api/v1/orders")
                .then()
                .extract()
                .path("track");

        assertNotEquals(unexpectedNumber, returnedTrackNumber);
        }
}




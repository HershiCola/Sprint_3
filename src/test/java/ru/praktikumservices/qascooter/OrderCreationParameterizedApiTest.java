package ru.praktikumservices.qascooter;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class OrderCreationParameterizedApiTest {

    private final String SCOOTER_API_SERVICES_URL = "http://qa-scooter.praktikum-services.ru/";
    private final String color;
    private final int unexpectedNumber;

    public OrderCreationParameterizedApiTest(String color, int unexpectedNumber){
            this.color = color;
            this.unexpectedNumber = unexpectedNumber;
    };

    @Before
    public void setUp() {
        RestAssured.baseURI = SCOOTER_API_SERVICES_URL;
    }

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
    public void createOrderParameterizedTest(){

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
        .header("Content-type", "application/json")
                .and()
                .body(createOrderRequestBody)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");

        assertNotEquals(unexpectedNumber, returnedTrackNumber);
        }
}




package ru.praktikumservices.qascooter;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import static io.restassured.RestAssured.given;

//класс, описывающий работу сервиса (в отношении курьера)
public class ScooterCourierMakerAndOperationsHelper extends RestAssuredSpecForTests{

    private String courierLogin;
    private String courierPassword;
    private String courierFirstName;
    private int courierId = 0;

    private Response courierResponse;


    public Response getCourierResponse() {
        return courierResponse;
    }

    public String getCourierLogin() {
        return courierLogin;
    }

    public String getCourierPassword() {
        return courierPassword;
    }

    public String getCourierFirstName() {
        return courierFirstName;
    }

    @Step("Кладем в поле класса ответ для дальнейшей работы с ним")
    public void setCourierResponse(Response courierResponse) {
        this.courierResponse = courierResponse;
    }

    public int getCourierId() {
        return courierId;
    }

    @Step("Генерация полей курьера для регистрации")
    public void generateCourierCredentials() {

        courierLogin = RandomStringUtils.randomAlphabetic(10);
        courierPassword = RandomStringUtils.randomAlphabetic(10);
        courierFirstName = RandomStringUtils.randomAlphabetic(10);
    }

    @Step("Запрос на регистрацию курьера")
    public void registerCourier() {

        String registerRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";

        courierResponse = given()
                .spec(setupAssured())
                .body(registerRequestBody)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Запрос на логин пользователя")
    public void courierLoggingIn () {

            String requestByLoginAndPass = "{\"login\": \"" + courierLogin + "\", \"password\": \"" + courierPassword + "\"}";
            courierResponse = given().spec(setupAssured())
                    .body(requestByLoginAndPass).when().post("/api/v1/courier/login");
    }


    @Step("Сохранение айди из ответа после логина")
    public void setCourierId () {
            courierId = courierResponse
                    .then()
                    .extract()
                    .path("id");
    }

    @Step("Метод удаления курьера")
    public void deleteCourier () {

            courierLoggingIn();
            setCourierId();

            if (courierId != 0) {
                courierResponse = given()
                        .spec(setupAssured())
                        .when()
                        .delete("/api/v1/courier/" + courierId);
            }
        }
    }





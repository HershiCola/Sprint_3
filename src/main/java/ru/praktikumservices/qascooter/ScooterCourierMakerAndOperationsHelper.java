package ru.praktikumservices.qascooter;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import static io.restassured.RestAssured.given;

//класс, описывающий работу сервиса (в отношении курьера)
public class ScooterCourierMakerAndOperationsHelper {

    private String courierLogin;
    private String courierPassword;
    private String courierFirstName;
    private int courierId;

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

    public void setCourierResponse(Response courierResponse) {
        this.courierResponse = courierResponse;
    }

    public int getCourierId() {
        return courierId;
    }


    public void generateCourierCredentials() {

        courierLogin = RandomStringUtils.randomAlphabetic(10);
        courierPassword = RandomStringUtils.randomAlphabetic(10);
        courierFirstName = RandomStringUtils.randomAlphabetic(10);
    }

    public void registerCourier() {

        String registerRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";

        courierResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(registerRequestBody)
                .when()
                .post("/api/v1/courier");
    }

        public void courierLoggingIn () {

            String requestByLoginAndPass = "{\"login\": \"" + courierLogin + "\", \"password\": \"" + courierPassword + "\"}";
            courierResponse = given().header("Content-type", "application/json").and()
                    .body(requestByLoginAndPass).when().post("/api/v1/courier/login");
        }

        public void setCourierId () {
            courierId = courierResponse.then().extract().path("id");
        }

        public void deleteCourier () {
            if (courierId != 0) {
                courierResponse = given().when().delete("/api/v1/courier/" + courierId);
            }
        }
    }





package tests;

import io.restassured.RestAssured;
import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import models.pojo.LoginBodyPojoModel;
import models.pojo.LoginResponsePojoModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresInExtendedTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void loginTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\" }"; // todo bad practice

        given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void loginWithPojoModelTest() {
        LoginBodyPojoModel body = new LoginBodyPojoModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("cityslicka");

        LoginResponsePojoModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void loginWithLombokModelTest() {
        LoginBodyLombokModel body = new LoginBodyLombokModel();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("cityslicka");

        LoginResponseLombokModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }



}

package tests;

import api.application.UserApi;

import base.BaseTest;
import constants.StatusCode;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import payloads.UserPayload;

import java.io.File;

import static org.hamcrest.Matchers.equalTo;

public class UserTests extends BaseTest {

    @Test
    public void getUsersTest() {
        UserApi.getUsers()
                .then()
                .statusCode(StatusCode.CODE_200.code);
    }

    @Test
    public void createUserWithHashMap() {
        UserApi.createUser(UserPayload.createUserMap())
                .then()
                .statusCode(StatusCode.CODE_201.code);
    }

    @Test
    public void createUserWithJson() {
        UserApi.createUser(UserPayload.createUserJson())
                .then()
                .statusCode(StatusCode.CODE_201.code);
    }

    @Test
    public void createUserWithPOJO() {
        UserApi.createUser(UserPayload.createUserPOJO())
                .then()
                .statusCode(StatusCode.CODE_201.code);
    }

    @Test
    public void schemaValidationTest() {
        UserApi.getUser(1)
                .then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/resources/schemas/userSchema.json")));    }

    @Test
    public void updateUserTest() {
        UserApi.updateUser(1, UserPayload.createUserMap())
                .then()
                .statusCode(StatusCode.CODE_200.code);
    }

    @Test
    public void deleteUserTest() {
        UserApi.deleteUser(1)
                .then()
                .statusCode(StatusCode.CODE_200.code);
    }



    @Test
    public void fileUploadTest() {
        File file = new File("src/test/resources/test.txt");

        io.restassured.RestAssured.given()
                .multiPart("file", file)
                .post("https://postman-echo.com/post")
                .then()
                .statusCode(200);
    }

    @Test
    public void formUrlEncodedTest() {

        io.restassured.RestAssured.given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("name", "Joshua")
                .log().all()
                .when()
                .post("https://postman-echo.com/post")
                .then()
                .log().all()
                .statusCode(200)
                .body("form.name", org.hamcrest.Matchers.equalTo("Joshua"));
    }
}
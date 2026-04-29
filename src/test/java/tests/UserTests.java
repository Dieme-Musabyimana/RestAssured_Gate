package tests;

import api.application.UserApi;
import base.BaseTest;
import constants.StatusCode;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import payloads.UserPayload;
import utils.ConfigLoader;
import static utils.FakerUtils.*;
import static api.spec.SpecBuilder.getResponseSpec;

import java.io.File;

import static org.hamcrest.Matchers.equalTo;

public class UserTests extends BaseTest {

    @Test
    public void getUsersTest() {
        UserApi.getUsers()
                .then().spec(getResponseSpec())
                .statusCode(StatusCode.CODE_200.code);
    }

    @Test
    public void createUserWithHashMap() {
        UserApi.createUser(UserPayload.createUserMap())
                .then().spec(getResponseSpec())
                .statusCode(StatusCode.CODE_201.code);
    }

    @Test
    public void createUserWithJson() {
        UserApi.createUser(UserPayload.createUserJson())
                .then().spec(getResponseSpec())
                .statusCode(StatusCode.CODE_201.code);
    }

    @Test
    public void createUserWithPOJO() {
        UserApi.createUser(UserPayload.createUserPOJO())
                .then().spec(getResponseSpec())
                .statusCode(StatusCode.CODE_201.code);
    }

    @Test
    public void schemaValidationTest() {
        UserApi.getUser(1)
                .then().spec(getResponseSpec())
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
                .baseUri(ConfigLoader.getEchoUrl())
                .multiPart("file", file)
                .post("/post")
                .then()
                .statusCode(StatusCode.CODE_200.code);
    }

    @Test
    public void formUrlEncodedTest() {
        UserApi.echoPost(getFirstName());
    }


}
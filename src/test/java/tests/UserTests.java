package tests;
import api.application.UserApi;
import api.complexPojo.*;
import base.BaseTest;
import constants.StatusCode;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import payloads.ComplextPayload;
import payloads.UserPayload;
import utils.ConfigLoader;
import static api.application.UserApi.*;
import static org.testng.AssertJUnit.assertEquals;
import static payloads.UserPayload.createUserMap;
import static utils.FakerUtils.*;
import java.io.File;

public class UserTests extends BaseTest {

    @Test
    public void getUsersTest() {
        UserApi.getUsers()
                .then().statusCode(StatusCode.CODE_200.code);
    }

    @Test
    public void createUserWithHashMap() {
        createUser(createUserMap())
                .then().statusCode(StatusCode.CODE_201.code);
    }

    @Test
    public void createUserWithJson() {
        createUser(UserPayload.createUserJson())
                .then().statusCode(StatusCode.CODE_201.code);
    }

    @Test
    public void createUserWithPOJO() {
        createUser(UserPayload.createUserPOJO())
                .then().statusCode(StatusCode.CODE_201.code);
    }

    @Test
    public void schemaValidationTest() {
        getUser(ConfigLoader.getUserId())
                .then().body(JsonSchemaValidator.matchesJsonSchema(new File(ConfigLoader.getSchema())));    }

    @Test
    public void updateUserTest() {
        updateUser(ConfigLoader.getUserId(),createUserMap())
                .then().statusCode(StatusCode.CODE_200.code);
    }

    @Test
    public void deleteUserTest() {
        deleteUser(ConfigLoader.getUserId()).
                then().statusCode(StatusCode.CODE_200.code);
    }

    @Test
    public void fileUploadTest() {

        upload(new File(ConfigLoader.getFilePath()))
                .then().statusCode(StatusCode.CODE_200.code);
    }

    @Test
    public void formUrlEncodedTest() {
        echoPost(getFirstName())
                .then().statusCode(StatusCode.CODE_200.code);


    }



    @Test
    public void complexPojoTest(){
        RootUser requestUser = ComplextPayload.createComplexUserPayload();
        RootUser responseUser = UserApi.createUser(requestUser).as(RootUser.class);
        assertEquals(responseUser.getFirstName(), requestUser.getFirstName());
        assertEquals(responseUser.getCompany().getName(), "Dooley, Kozey and Cronin");
        assertEquals(responseUser.getAddress().getCity(), "Kigali");

    }}


package api.rest;

import io.restassured.response.Response;
import utils.ConfigLoader;

import java.io.File;

import static api.spec.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {


    public static Response get(String path) {
        return given().spec(getRequestSpec())
                .when().get(path).then().spec(getResponseSpec()).extract().response();
    }


    public static Response get(String path, String UserId) {
        return given().spec(getRequestSpec())
                .pathParam("id",UserId)
                .when().get(path).then().spec(getResponseSpec()).extract().response();
    }

    public static Response post(String path, Object payload) {
        return given().spec(getRequestSpec())
                .body(payload)
                .when().post(path).
                then().spec(getResponseSpec())
                .extract().response();
    }


    public static Response put(String path, String paramName, Object paramValue, Object payload) {
        return given().spec(getRequestSpec())
                .pathParam(paramName, paramValue)
                .body(payload)
                .when().put(path);
    }

    public static Response delete(String path, String paramName, Object paramValue) {
        return given().spec(getRequestSpec())
                .pathParam(paramName, paramValue)
                .when().delete(path);
    }
    public static Response UploadFile (String path  , File file ){

        return given()
                .baseUri(ConfigLoader.getEchoUrl())
                .multiPart("file", file)
                .post(path).
                then().spec(getPostManResponseSpec())
                .extract().response();


    }
    public static Response encodedTest (String path, String paramName, Object value  ){
        return given().spec(getPostManRequestSpec())
                .formParam(paramName,value)
                .when()
                .post( path)
                .then().
                extract().response();


    }
}
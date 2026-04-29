package api.rest;

import io.restassured.response.Response;
import utils.ConfigLoader;

import java.io.File;
import static utils.FakerUtils.*;
import static api.spec.SpecBuilder.getPostManRequestSpec;
import static api.spec.SpecBuilder.getRequestSpec;
import static io.restassured.RestAssured.given;

public class RestResource {


    public static Response get(String path) {
        return given().spec(getRequestSpec())
                .when().get(path);
    }


    public static Response get(String path, String paramName, Object paramValue) {
        return given().spec(getRequestSpec())
                .pathParam(paramName, paramValue)
                .when().get(path);
    }

    public static Response post(String path, Object payload) {
        return given().spec(getRequestSpec())
                .body(payload)
                .when().post(path);
    }


    public static Response put(String path, String paramName, Object paramValue, Object payload) {
        return given().spec(getRequestSpec())
                .pathParam(paramName, paramValue)
                .body(payload)
                .when().put(path);
    }

    // Existing method for simple DELETE
    public static Response delete(String path) {
        return given().spec(getRequestSpec())
                .when().delete(path);
    }


    public static Response delete(String path, String paramName, Object paramValue) {
        return given().spec(getRequestSpec())
                .pathParam(paramName, paramValue)
                .when().delete(path);
    }
    public static Response UploadFile (String path  , File file ){
        // File file = new File("src/test/resources/test.txt");

        return given()
                .baseUri(ConfigLoader.getEchoUrl())
                .multiPart("file", file)
                .post(path);


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
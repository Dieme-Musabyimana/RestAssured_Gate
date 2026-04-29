package api.rest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestResource {

    public static Response get(String path) {
        return given().spec(api.spec.SpecBuilder.getRequestSpec())
                .when().get(path);
    }

    public static Response post(String path, Object payload) {
        return given().spec(api.spec.SpecBuilder.getRequestSpec())
                .body(payload)
                .when().post(path);
    }

    public static Response put(String path, Object payload) {
        return given().spec(api.spec.SpecBuilder.getRequestSpec())
                .body(payload)
                .when().put(path);
    }

    public static Response delete(String path) {
        return given().spec(api.spec.SpecBuilder.getRequestSpec())
                .when().delete(path);
    }
}
package api.spec;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.ConfigLoader;

import static io.restassured.http.ContentType.JSON;

public class SpecBuilder {

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getBaseUrl())
                .setContentType(JSON)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(JSON).
                log(LogDetail.ALL)
                .expectResponseTime(org.hamcrest.Matchers.lessThan(3000L))
                .build();
    }
}
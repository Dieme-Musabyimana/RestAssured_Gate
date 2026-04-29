package api.spec;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utils.ConfigLoader;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.URLENC;
import static org.hamcrest.Matchers.lessThan;

public class SpecBuilder {

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getBaseUrl())
                .setContentType(JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(JSON).
                log(LogDetail.ALL)
                .expectResponseTime(lessThan(300000L))
                .build();
    }
    public static  RequestSpecification getPostManRequestSpec(){
        return  new RequestSpecBuilder().
                setBaseUri(ConfigLoader.getEchoUrl()).
                setContentType(JSON).
                log(LogDetail.ALL).
                build();
    }
    public static ResponseSpecification getPostManResponseSpec() {
        return new ResponseSpecBuilder()
                .expectContentType(JSON).
                log(LogDetail.ALL)
                .expectResponseTime(lessThan(3000L))
                .build();
    }

}
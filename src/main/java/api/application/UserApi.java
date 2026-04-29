package api.application;

import api.routes.Routes;
import api.rest.RestResource;
import io.restassured.response.Response;

import java.io.File;

import static api.rest.RestResource.UploadFile;
import static api.routes.Routes.*;

public class UserApi {

    public static Response getUsers() {
        return RestResource.get(GET_USERS);
    }

    public static Response getUser(String id) {
        return RestResource.get(GET_SINGLE_USER, id);
    }

    public static Response createUser(Object payload) {
        return RestResource.post(CREATE_USER, payload);
    }

    public static Response updateUser(String id, Object payload) {
        return RestResource.put(UPDATE_USER, "id", id, payload);
    }

    public static Response deleteUser( String id) {
        return RestResource.delete(DELETE_USER, "id", id);
    }
    public static Response echoPost(Object name ){
        return RestResource.encodedTest(ECHO_POST,"name",  name);
    }
    public static Response upload(File file  ){
        return UploadFile(ECHO_POST,file);
    }

}
package api.application;

import api.routes.Routes;
import api.rest.RestResource;
import io.restassured.response.Response;

public class UserApi {

    public static Response getUsers() {
        return RestResource.get(Routes.GET_USERS);
    }

    public static Response getUser(int id) {
        return RestResource.get(Routes.GET_SINGLE_USER.replace("{id}", String.valueOf(id)));
    }

    public static Response createUser(Object payload) {
        return RestResource.post(Routes.CREATE_USER, payload);
    }

    public static Response updateUser(int id, Object payload) {
        return RestResource.put(Routes.UPDATE_USER.replace("{id}", String.valueOf(id)), payload);
    }

    public static Response deleteUser(int id) {
        return RestResource.delete(Routes.DELETE_USER.replace("{id}", String.valueOf(id)));
    }
}
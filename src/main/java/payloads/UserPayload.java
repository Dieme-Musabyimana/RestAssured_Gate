package payloads;

import java.util.HashMap;

public class UserPayload {

    public static HashMap<String, Object> createUserMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("firstName", "John");
        map.put("lastName", "Doe");
        map.put("age", 25);
        return map;
    }

    public static String createUserJson() {
        return "{ \"firstName\": \"Jane\", \"lastName\": \"Doe\", \"age\": 30 }";
    }

    public static payloads.UserPOJO createUserPOJO() {
        payloads.UserPOJO user = new payloads.UserPOJO();
        user.setFirstName("Mike");
        user.setLastName("Ross");
        user.setAge(35);
        return user;
    }
}
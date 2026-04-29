package payloads;

import java.util.HashMap;

import static utils.FakerUtils.*;

public class UserPayload {

    public static HashMap<String, Object> createUserMap() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("firstName", getFirstName());
        map.put("lastName", getLastName());
        map.put("age", getAge());

        return map;
    }

    public static String createUserJson() {
        return "{ \"firstName\": \"" + getFirstName() + "\", " +
                "\"lastName\": \"" + getLastName() + "\", " +
                "\"age\": " + getAge() + " }";
    }

    public static UserPOJO createUserPOJO() {
        UserPOJO user = new UserPOJO();

        user.setFirstName(getFirstName());
        user.setLastName(getLastName());
        user.setAge(getAge());

        return user;
    }
}
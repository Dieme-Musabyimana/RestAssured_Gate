package payloads;

import utils.FakerUtils;
import java.util.HashMap;

public class UserPayload {

    public static HashMap<String, Object> createUserMap() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("firstName", FakerUtils.getFirstName());
        map.put("lastName", FakerUtils.getLastName());
        map.put("age", FakerUtils.getAge());

        return map;
    }

    public static String createUserJson() {
        return "{ \"firstName\": \"" + FakerUtils.getFirstName() + "\", " +
                "\"lastName\": \"" + FakerUtils.getLastName() + "\", " +
                "\"age\": " + FakerUtils.getAge() + " }";
    }

    public static UserPOJO createUserPOJO() {
        UserPOJO user = new UserPOJO();

        user.setFirstName(FakerUtils.getFirstName());
        user.setLastName(FakerUtils.getLastName());
        user.setAge(FakerUtils.getAge());

        return user;
    }
}
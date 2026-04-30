package utils;

import com.github.javafaker.Faker;

public class FakerUtils {

    public static String getFirstName() {
        Faker faker = new Faker();
        return faker.regexify("[A-Z][a-z]{6,10}");
    }

    public static String getLastName() {
        Faker faker = new Faker();
        return faker.regexify("[A-Z][a-z]{6,12}");
    }

    public static int getAge() {
        Faker faker = new Faker();
        return Integer.parseInt(faker.regexify("[0-9]"));
    }


}
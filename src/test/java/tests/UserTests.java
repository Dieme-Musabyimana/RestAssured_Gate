package tests;

import api.application.UserApi;

import api.complexPojo.*;
import base.BaseTest;
import constants.StatusCode;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import payloads.UserPayload;
import utils.ConfigLoader;

import java.io.File;

import static api.application.UserApi.getUser;

import static api.routes.Routes.GET_USERS;
import static org.testng.Assert.assertEquals;
import static payloads.UserPayload.createUserPOJO;


public class UserTests extends BaseTest {


    @Test
    public void complexPojoTest(){
        Coordinates coordinates=new Coordinates(-77.16213,-92.084824);
        Hair hair=new Hair("Red","Curly");
        Address address=new Address("626 Main Street","Kigali","Rwanda","KG","1111",coordinates,"Rwanda");
        Bank bank=new Bank("05/028","693233511855044","Diners Club Internationa","GBR","GB74MH2UZLR9TRPHYNU8F8");
        Company company=new Company("Engineering","Dooley, Kozey and Cronin","Sales Manager",address);
        Crypto cryptos=new Crypto("Bitcoin","0xb9fc2fe63b2a6c003f1c324c3bfa53259162181a","Ethereum (ERC20)");
        RootUser rootUser=new RootUser("Joshua","Musabyimana","Damaria",54,"Male",
                "example@gmail.com","07888796067","Joshua","ieieieu","1132-4-30",
                "https://dummyjson.com/icon/emilys/128","O+",198.0,54.9,"Green",hair,
                "42.48.100.32",address,"47:fa:41:18:ec:eb","University of Wisconsin--Madison",bank,company,
                "977-175","900-590-289","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36",
                cryptos,"admin");
        Response response=UserApi.createUser(rootUser);
        RootUser user = response.as(RootUser.class);
        assertEquals(user.getFirstName(), rootUser.getFirstName());
        assertEquals(user.getEmail(), rootUser.getEmail());
        assertEquals(user.getAddress().getCity(), "Kigali");
        assertEquals(user.getCompany().getName(), "Dooley, Kozey and Cronin");
        System.out.println(user.getCompany().department);


    }
}
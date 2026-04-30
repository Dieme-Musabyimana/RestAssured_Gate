package payloads;
import api.complexPojo.*;

public class ComplexPayload {

    public static RootUser createComplexUserPayload() {
        Coordinates coordinates = new Coordinates();
        coordinates.setLat(-77.16213);
        coordinates.setLng(-92.084824);

        Address address = new Address();
        address.setAddress("626 Main Street");
        address.setCity("Kigali");
        address.setState("Rwanda");
        address.setStateCode("0000");
        address.setCountry("Rwanda");
        address.setPostalCode("1111");
        address.setCoordinates(coordinates);

        Bank bank = new Bank();
        bank.setCardExpire("05/28");
        bank.setCardNumber("693233511855044");
        bank.setCardType("Diners Club International");
        bank.setCurrency("GBR");
        bank.setIban("GB74MH2UZLR9TRPHYNU8F8");

        Company company = new Company();
        company.setName("Dooley, Kozey and Cronin");
        company.setDepartment("Engineering");
        company.setTitle("Sales Manager");
        company.setAddress(address);

        Crypto cryptos = new Crypto();
        cryptos.setCoin("Bitcoin");
        cryptos.setWallet("0xb9fc2fe63b2a6c003f1c324c3bfa53259162181a");
        cryptos.setNetwork("Ethereum (ERC20)");

        Hair hair = new Hair();
        hair.setColor("Black");
        hair.setType("Curly");

        RootUser user = new RootUser();

        user.setFirstName("Joshua");
        user.setLastName("JOJO");
        user.setMaidenName("Damaria");
        user.setAge(54);
        user.setGender("Male");
        user.setEmail("example@gmail.com");
        user.setPhone("07888796067");
        user.setUsername("Joshua");
        user.setPassword("ieieieu");
        user.setBirthDate("1132-04-30");
        user.setHeight(198.0);
        user.setWeight(54.9);
        user.setEyeColor("Green");
        user.setImage("https://dummyjson.com/icon/emilys/128");
        user.setBloodGroup("O+");
        user.setHair(hair);
        user.setIp("42.48.100.32");
        user.setMacAddress("47:fa:41:18:ec:eb");
        user.setUniversity("University of Wisconsin--Madison");
        user.setUserAgent("UserAgentString...");
        user.setRole("admin");
        user.setEin("977-175");
        user.setSsn("900-590-289");
        user.setAddress(address);
        user.setBank(bank);
        user.setCompany(company);
        user.setCrypto(cryptos);

        return user;
    }
}

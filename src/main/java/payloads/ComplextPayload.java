package payloads;

import api.complexPojo.*;

public class ComplextPayload {
    public static RootUser createComplexUserPayload() {
        Coordinates coordinates = new Coordinates(-77.16213, -92.084824);
        Hair hair = new Hair("Red", "Curly");
        Address address = new Address("626 Main Street", "Kigali", "Rwanda", "KG", "1111", coordinates, "Rwanda");
        Bank bank = new Bank("05/028", "693233511855044", "Diners Club Internationa", "GBR", "GB74MH2UZLR9TRPHYNU8F8");
        Company company = new Company("Engineering", "Dooley, Kozey and Cronin", "Sales Manager", address);
        Crypto cryptos = new Crypto("Bitcoin", "0xb9fc2fe63b2a6c003f1c324c3bfa53259162181a", "Ethereum (ERC20)");

        return new RootUser("Joshua", "JOJO", "Damaria", 54, "Male",
                "example@gmail.com", "07888796067", "Joshua", "ieieieu", "1132-4-30",
                "https://dummyjson.com/icon/emilys/128", "O+", 198.0, 54.9, "Green", hair,
                "42.48.100.32", address, "47:fa:41:18:ec:eb", "University of Wisconsin--Madison", bank, company,
                "977-175", "900-590-289", "UserAgentString...",
                cryptos, "admin");
    }
}

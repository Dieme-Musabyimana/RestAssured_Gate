package api.complexPojo;

public class Address{

    private String address;
    private String city;
    private String state;
    private String stateCode;
    private String postalCode;
    private Coordinates coordinates;
    private String country;


    public Address(){}
    public Address(String address, String city, String state, String stateCode, String postalCode, Coordinates coordinates, String country) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.stateCode = stateCode;
        this.postalCode = postalCode;
        this.coordinates = coordinates;
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}

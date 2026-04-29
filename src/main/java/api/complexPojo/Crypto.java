package api.complexPojo;
public class Crypto{
    public Crypto(){}
    public Crypto(String coin, String wallet, String network) {
        this.coin = coin;
        this.wallet = wallet;
        this.network = network;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String coin;
    public String wallet;
    public String network;
}
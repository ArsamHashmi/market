package com.example.market.models;

public class model_coin_list {
    String coin_name, coin_symbol;
    double coin_price, change_24h;
    int icon;

    public model_coin_list(String coin_name, String coin_symbol, double coin_price, double change_24h, int icon) {
        this.coin_name = coin_name;
        this.coin_symbol = coin_symbol;
        this.coin_price = coin_price;
        this.change_24h = change_24h;
        this.icon = icon;
    }

    public String getCoin_name() {
        return coin_name;
    }

    public void setCoin_name(String coin_name) {
        this.coin_name = coin_name;
    }

    public String getCoin_symbol() {
        return coin_symbol;
    }

    public void setCoin_symbol(String coin_symbol) {
        this.coin_symbol = coin_symbol;
    }

    public double getCoin_price() {
        return coin_price;
    }

    public void setCoin_price(double coin_price) {
        this.coin_price = coin_price;
    }

    public double getChange_24h() {
        return change_24h;
    }

    public void setChange_24h(double change_24h) {
        this.change_24h = change_24h;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

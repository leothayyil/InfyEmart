package com.example.user.infyemart.Pojo;

/**
 * Created by USER on 06-01-2018.
 */

public class Pojo_Variant {

    private String offer;
    private String optionName;
    private String original_price;
    private String margin_price;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    private String itemId;

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getMargin_price() {
        return margin_price;
    }

    public void setMargin_price(String margin_price) {
        this.margin_price = margin_price;
    }
}

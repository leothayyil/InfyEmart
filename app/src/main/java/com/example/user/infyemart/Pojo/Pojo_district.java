package com.example.user.infyemart.Pojo;

/**
 * Created by USER on 04-01-2018.
 */

public class Pojo_district {
    private int id;
    private String district;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return id +" "+ district;
    }
}

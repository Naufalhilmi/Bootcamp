package com.example.bootcamp.model;

public class UserAddress {

    private String user_id;
    private String address;
    private int province_id;
    private int regencie_id;
    private int district_id;
    private int village_id;
    private String pos_code;

    public UserAddress() {
    }

    public UserAddress(String user_id, String address, int province_id, int regencie_id, int district_id, int village_id, String pos_code) {
        this.user_id = user_id;
        this.address = address;
        this.province_id = province_id;
        this.regencie_id = regencie_id;
        this.district_id = district_id;
        this.village_id = village_id;
        this.pos_code = pos_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getRegencie_id() {
        return regencie_id;
    }

    public void setRegencie_id(int regencie_id) {
        this.regencie_id = regencie_id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public int getVillage_id() {
        return village_id;
    }

    public void setVillage_id(int village_id) {
        this.village_id = village_id;
    }

    public String getPos_code() {
        return pos_code;
    }

    public void setPos_code(String pos_code) {
        this.pos_code = pos_code;
    }
}

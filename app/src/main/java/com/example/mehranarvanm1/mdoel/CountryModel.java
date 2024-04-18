package com.example.mehranarvanm1.mdoel;

public class CountryModel {
    private String name;
    private String flag;
    private String code;
    private String dial_code;

    public CountryModel(String name, String flag, String code, String dial_code) {
        this.name = name;
        this.flag = flag;
        this.code = code;
        this.dial_code = dial_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDial_code() {
        return dial_code;
    }

    public void setDial_code(String dial_code) {
        this.dial_code = dial_code;
    }
}

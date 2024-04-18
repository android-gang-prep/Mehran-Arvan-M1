package com.example.mehranarvanm1.mdoel;

public class SignUpPostModel {
    private String email;
    private String nickName;
    private String password;
    private String phone;

    public SignUpPostModel(String email, String nickName, String password, String phone) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

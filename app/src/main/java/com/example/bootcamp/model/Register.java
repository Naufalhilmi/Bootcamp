package com.example.bootcamp.model;

public class Register {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String retypepassword;

    public Register() {
    }

    public Register(String username, String firstname, String lastname, String email, String password, String retypepassword) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.retypepassword = retypepassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetypepassword() {
        return retypepassword;
    }

    public void setRetypepassword(String retypepassword) {
        this.retypepassword = retypepassword;
    }

    @Override
    public String toString() {
        return "Register{" +
                "username='" + username + '\'' +
                ", firstname=" + firstname +
                ", lastname='" + lastname + '\'' +
                ", email='" + email+ '\'' +
                ", password=" + password+
                '}';
    }
}

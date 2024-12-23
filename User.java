package com.example.hellofx;

import java.util.ArrayList;

abstract public class User {
    private String username;
    private String password;
    private String DateOfBirth;
    private Gender gender;
    private String address;
    public User(){}
    public User(String username, String password, String dateOfBirth, Gender gender, String address) {
        this.username = username;
        this.password = password;
        DateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract User Login(String username, String password);
    public abstract void Registration(String username,String password,String dateOfBirth,Gender gender,String address);

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", DateOfBirth='" + DateOfBirth + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                '}';
    }
}

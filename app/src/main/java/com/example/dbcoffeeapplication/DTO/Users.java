package com.example.dbcoffeeapplication.DTO;

public class Users {
    String id,numberphone, password,email,username,fullname,avatar,dob,gender,typeUser,status;

    public Users() {
        this.id = "";
        this.numberphone = "";
        this.username = "";
        this.password = "";
        this.dob = "";
        this.gender = "";
        this.typeUser = "1";
        this.email = "";
        this.avatar = "";
        this.fullname = "";
        this.status = "1";
    }

    public Users(String id, String numberphone, String password, String email, String username, String fullname, String avatar, String dob, String gender, String typeUser, String status) {
        this.id = id;
        this.numberphone = numberphone;
        this.password = password;
        this.email = email;
        this.username = username;
        this.fullname = fullname;
        this.avatar = avatar;
        this.dob = dob;
        this.gender = gender;
        this.typeUser = typeUser;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

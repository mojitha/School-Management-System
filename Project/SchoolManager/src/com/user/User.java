package com.user;

public class User {
    private String userID,name,userName,password,birthDate,registeredDate,email,phone,address,bio,img,status;
    
    // default constructor
    public User() {
        super();
    }
    
    // parameterized constructor
    public User(String userID, String name, String userName, String password, String birthDate, String registeredDate, String email, String phone, String address, String bio, String img, String status) {
        this.userID = userID;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
        this.registeredDate = registeredDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bio = bio;
        this.img = img;
        this.status = status;
    }
    
    // parameterized constructor
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    // setters and getters for user
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}

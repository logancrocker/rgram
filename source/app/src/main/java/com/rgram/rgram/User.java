package com.rgram.rgram;

public class User {

    public String userName;
    public String userDescription;
    public String userEmail;
    //public String uid;

    public User()
    {
        this.userName = "hello world";
        this.userEmail = "email@email.com";
        this.userDescription = "";
    }

    public User(String username, String email)
    {
        //this.uid = uid;
        this.userName = username;
        this.userEmail = email;
        this.userDescription = "";
    }

    //uid
    //public String getUid() { return uid; }
    //public void setUid(String uid) { this.uid = uid; }

    //user name
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    //description
    public String getUserDescription() { return userDescription; }
    public void setUserDescription(String userDescription) { this.userDescription = userDescription; }

    //email
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}

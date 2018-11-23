package com.rgram.rgram;

public class User {

    public String userName;
    public String userDescription;
    public String userEmail;
    public String uid;
    public Integer post_count;

    public User()
    {
        this.userName = "hello world";
        this.userEmail = "email@email.com";
        this.userDescription = "";
        this.post_count = 0;
    }

    public User(String username, String email, String uid)
    {
        this.uid = uid;
        this.userName = username;
        this.userEmail = email;
        this.userDescription = "";
        this.post_count = 0;
    }

    //uid
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    //user name
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    //description
    public String getUserDescription() { return userDescription; }
    public void setUserDescription(String userDescription) { this.userDescription = userDescription; }

    //email
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    //postCount
    public Integer getPost_count() { return post_count; }
    public void setPost_count(Integer post_count) { this.post_count = post_count; }
    public void incrementPost_count() { this.post_count += 1; }
}

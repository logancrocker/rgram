package com.rgram.rgram;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {

    public String userName;
    public String userDescription;
    public String userEmail;
    public String uid;
    public ArrayList<String> following;
    public ArrayList<String> followers;
    public ArrayList<String> posts;


    public User()
    {
        this.uid = "";
        this.userName = "hello world";
        this.userEmail = "email@email.com";
        this.userDescription = "";
        this.following = null;
        this.followers = null;
        this.posts = null;
    }

    public User(String username, String email, String uid)
    {
        this.uid = uid;
        this.userName = username;
        this.userEmail = email;
        this.userDescription = "";
        this.following = null;
        this.followers = null;
        this.posts = null;
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

    //following
    public ArrayList<String> getFollowing() { return following; }
    public void setFollowing(ArrayList<String> following) { this.following = following; }

    //followers
    public ArrayList<String> getFollowers() { return followers; }
    public void setFollowers(ArrayList<String> followers) { this.followers = followers; }

    //posts
    public ArrayList<String> getPosts() { return posts; }
    public void setPosts(ArrayList<String> posts) { this.posts = posts; }
}

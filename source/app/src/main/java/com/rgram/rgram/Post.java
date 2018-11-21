package com.rgram.rgram;

import java.util.ArrayList;

public class Post
{

    public Integer likes;
    public String path;
    public String imgDescription;
    public String uid;
    public ArrayList<String> tags;




    public Post()
    {
        this.likes = 0;
        this.path = "";
        this.imgDescription = "";
        uid = "";
        this.tags=null;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public Post(Integer likes, String path, String imgDescription, String uid, ArrayList<String> tags)
    {
        this.likes = likes;
        this.path = path;
        this.imgDescription = imgDescription;
        this.uid = uid;
        this.tags=tags;
    }

    public Integer getLikes() { return likes; }
    public void setLikes(Integer likes) { this.likes = likes; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getImgDescription() { return imgDescription; }
    public void setImgDescription(String imgDescription) { this.imgDescription = imgDescription; }

    public ArrayList getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

}


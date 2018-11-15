package com.rgram.rgram;

public class Post
{

    public Integer likes;
    public String path;
    public String imgDescription;
    public String uid;

    public Post()
    {
        this.likes = 0;
        this.path = "";
        this.imgDescription = "";
        uid = "";
    }

    public Post(Integer likes, String path, String imgDescription, String uid)
    {
        this.likes = likes;
        this.path = path;
        this.imgDescription = imgDescription;
        this.uid = uid;
    }

    public Integer getLikes() { return likes; }
    public void setLikes(Integer likes) { this.likes = likes; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getImgDescription() { return imgDescription; }
    public void setImgDescription(String imgDescription) { this.imgDescription = imgDescription; }

}


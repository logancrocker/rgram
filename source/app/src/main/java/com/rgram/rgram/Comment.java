package com.rgram.rgram;

public class Comment {

    private String content;
    private String uid;

    public Comment()
    {
        this.content = "";
        this.uid = "";
    }

    public Comment(String content, String uid)
    {
        this.content = content;
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

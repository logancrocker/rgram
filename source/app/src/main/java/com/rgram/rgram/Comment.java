package com.rgram.rgram;

public class Comment {
    private String title,content,userName;
    public Comment() {
    }

    public Comment(String title, String content, String userName) {
        this.title = title;
        this.content = content;
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
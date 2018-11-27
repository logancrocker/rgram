package com.rgram.rgram;

public class Messages {
    private String name;
    private String avatars;
    private String post;
    private String like;
    private int likenum;
    private String chat;
    private int chatnum;

    public Messages(String name, String avatars, String post, String like, int likenum, String chat, int chatnum) {
        this.name = name;
        this.avatars = avatars;
        this.post = post;
        this.like = like;
        this.likenum = likenum;
        this.chat = chat;
        this.chatnum = chatnum;
    }

    public String getName() {
        return name;
    }

    public String getAvatars() {
        return avatars;
    }

    public void setAvatars(String avatars) {
        this.avatars = avatars;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public int getLikenum() {
        return likenum;
    }

    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public int getChatnum() {
        return chatnum;
    }

    public void setChatnum(int chatnum) {
        this.chatnum = chatnum;
    }

}

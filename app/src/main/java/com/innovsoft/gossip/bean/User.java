package com.innovsoft.gossip.bean;

import com.google.gson.annotations.Expose;

/**
 * Created by SriMaddy on 3/27/2017.
 */
public class User {

    private String key;

//    @Expose
//    private long id;

//    @Expose
    private String username;

//    @Expose
    private String email;

//    @Expose
    private String password;

//    @Expose
    private String imagePath;

//    @Expose
    private long time;

    public User() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

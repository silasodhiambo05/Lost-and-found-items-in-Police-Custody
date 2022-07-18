package com.example.lost;

import java.util.Date;

public class Chat {

    private long text;
    private String user;
    private long time;

    public Chat(long text, String user) {
        this.text = text;
        this.user = user;

        // Initialize to current time
        text = new Date().getTime();
    }

    public Chat(){
    }

    public long getText() {
        return text;
    }

    public void setText(long text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
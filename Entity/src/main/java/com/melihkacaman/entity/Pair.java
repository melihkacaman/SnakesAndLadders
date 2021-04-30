package com.melihkacaman.entity;

import java.io.Serializable;

public class Pair implements Serializable {
    private int id;
    private String userName;
    private int selfId;

    public Pair(int id, String userName, int selfId) {
        this.id = id;
        this.userName = userName;
        this.selfId = selfId;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getSelfId() {
        return selfId;
    }
}

package com.melihkacaman.entity;

import java.io.Serializable;

public class CustomVector implements Serializable {
    public float x;
    public float y;

    public CustomVector(float x, float y) {
        this.x = x;
        this.y = y;
    }
}

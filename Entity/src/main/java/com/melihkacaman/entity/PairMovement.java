package com.melihkacaman.entity;

import java.io.Serializable;

public class PairMovement implements Serializable {
    private int id;   // the id of the player that starts to move
    private int dice;
    private CustomVector target;

    public PairMovement(int id, int dice, CustomVector target) {
        this.id = id;
        this.dice = dice;
        this.target = target;
    }

    public int getId() {
        return id;
    }

    public int getDice() {
        return dice;
    }

    public CustomVector getTarget() {
        return target;
    }
}

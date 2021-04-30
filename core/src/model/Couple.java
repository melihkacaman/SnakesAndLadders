package model;

import com.melihkacaman.entity.Pair;

import player.PlayerCharacter;

public class Couple {

    private Pair pair;
    private String selfUserName;
    private int selfId;

    private PlayerCharacter selfCharacter;
    private PlayerCharacter pairCharacter;

    public Couple(Pair pair, String selfUserName) {
        this.pair = pair;
        this.selfUserName = selfUserName;
        this.selfId = pair.getSelfId();

        if (pair.getUserName().length() >= selfUserName.length()) {
            pairCharacter = PlayerCharacter.REDBIRD;
            selfCharacter = PlayerCharacter.BLUEBIRD;
        }else {
            pairCharacter = PlayerCharacter.BLUEBIRD;
            selfCharacter = PlayerCharacter.REDBIRD;
        }
    }

    public String getPairUserName(){
        return this.pair.getUserName();
    }

    public String getSelfUserName() {
        return selfUserName;
    }

    public PlayerCharacter getSelfCharacter(){
        return selfCharacter;
    }

    public PlayerCharacter getPairCharacter(){
        return pairCharacter;
    }

    public Pair getPair(){
        return pair;
    }

    public int getSelfId() {
        return selfId;
    }
}

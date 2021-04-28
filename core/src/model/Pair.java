package model;

import player.PlayerCharacter;

public class Pair {

    private String pairUserName;
    private String selfUserName;

    private PlayerCharacter selfCharacter;
    private PlayerCharacter pairCharacter;

    public Pair(String pairUserName, String selfUserName) {
        this.pairUserName = pairUserName;
        this.selfUserName = selfUserName;

        if (pairUserName.length() >= selfUserName.length()) {
            pairCharacter = PlayerCharacter.REDBIRD;
            selfCharacter = PlayerCharacter.BLUEBIRD;
        }else {
            pairCharacter = PlayerCharacter.BLUEBIRD;
            selfCharacter = PlayerCharacter.REDBIRD;
        }
    }

    public String getPairUserName(){
        return this.pairUserName;
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
}

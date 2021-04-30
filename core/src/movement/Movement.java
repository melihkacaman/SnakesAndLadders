package movement;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

import player.Player;

public class Movement implements Serializable {
    private Player player;
    private int dice;
    private Vector2 target;

    public Movement(Player player, int dice, Vector2 target) {
        this.player = player;
        this.dice = dice;
        this.target = target;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getDice() {
        return dice;
    }

    public void setDice(int dice) {
        this.dice = dice;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }
}

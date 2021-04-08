package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import helpers.GameInfo;
import sceenes.PlayBoard;

public class PlayerController {

    public void stepForward(Player player, int dice){
        //player.movePlayer(dice);
        player.updatePlayer(dice);
    }


}

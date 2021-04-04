package player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.melihkacaman.snakesandladders.GameMain;

public class Player extends Sprite {
    private String name;
    private GameMain game;
    private World world;     // The world is the actual physics world that we are gonna put our player.
    private Body body;       // it's actual player body, manipulating player

    public Player(String name, World world,GameMain game , float x, float y, PlayerController character) {
        super();
        if (character.equals(PlayerCharacter.REDBIRD)){
            super.setTexture(new Texture("Players/Red Bird.png"));
        }else {
            super.setTexture(new Texture("Players/Blue Bird.png"));
        }
        setPosition(x - getWidth() / 2f, y - getHeight() / 2f);
        this.name = name;
        this.world = world;
        this.game = game;

        createBody();
    }

    private void createBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;   // it may be kinematic body, not affected by gravity
        bodyDef.position.set(getX(), getY());  // same as player's position

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();   // it may not be box.
        shape.setAsBox(getWidth() / 2f, getHeight() / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        // need density = mass

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void updatePlayer(){
        this.setPosition(body.getPosition().x, body.getPosition().y);
    }
}

package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.melihkacaman.snakesandladders.GameMain;
import helpers.GameInfo;

public class Player extends Sprite {
    private String name;
    private GameMain game;
    private World world;     // The world is the actual physics world that we are gonna put our player.
    private Body body;       // it's actual player body, manipulating player

    public Player(String name, World world,GameMain game , float x, float y, PlayerCharacter character) {
        super(new Texture(character == PlayerCharacter.BLUEBIRD ? PlayerCharacter.getBlueBird() : PlayerCharacter.getRedBird()));
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
        //fixtureDef.friction = 2f;
        //fixtureDef.density = 4f;
        // need density = mass

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void updatePlayer(){
        this.setPosition(body.getPosition().x, body.getPosition().y);
    }

    public void movePlayer(){

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            float laterX = getX() + 48;
            if (laterX > GameInfo.WIDTH - 20){
                setPosition(10, getY() + 55f);
            }else {
                setPosition(getX() + 48, getY());
                body.getPosition().set(getX(), getY());
            }


        }
    }

    public void drawPlayer(SpriteBatch batch){
        batch.draw(this, getX(), getY());
    }
}

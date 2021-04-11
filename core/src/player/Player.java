package player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.melihkacaman.snakesandladders.GameMain;
import helpers.GameInfo;

public class Player extends Sprite {
    private String name;
    private PlayerCharacter character;
    private GameMain game;
    private World world;     // The world is the actual physics world that we are gonna put our player.
    private Body body;       // it's actual player body, manipulating player

    private int currentLocation;

    public boolean turn;

    public Player(String name, World world,GameMain game , float x, float y, PlayerCharacter character) {
        super(new Texture(character == PlayerCharacter.BLUEBIRD ? PlayerCharacter.getBlueBird() : PlayerCharacter.getRedBird()));
        setPosition(x - getWidth() / 2f, y - getHeight() / 2f);

        this.character = character;
        this.name = name;
        this.world = world;
        this.game = game;
        this.turn = false;
        this.currentLocation = 1;

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
        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

     public void updatePlayer(){
         this.setPosition(body.getPosition().x, body.getPosition().y);
    }

    public Vector2 getTargetForward(int dice){
        Vector2 result = new Vector2(getX(), getY());
        for (int i = 1; i <= dice; i++) {
            float laterX = result.x + 48;
            if (laterX > GameInfo.WIDTH - 20) {
                result.y = result.y + 55;
                result.x = 3;
            } else {
                result.x += 48;
            }
        }
        currentLocation += dice;
        return result;
    }

    public Vector2 getTargetBackward(int step){
        Vector2 result = new Vector2(getX(), getY());
        for (int i = 1; i <= step; i++) {
            float previousX = result.x - 48;
            if (previousX < 20) {
                result.y = result.y - 55;
                result.x = GameInfo.WIDTH - 3f;
            } else {
                result.x -= 48;
            }
        }
        currentLocation -= step;
        return result;
    }

    public int getCurrentLocation(){
        return this.currentLocation;
    }

    public void drawPlayer(SpriteBatch batch) {
        game.getBatch().begin();
        game.getBatch().draw(this, getX(), getY());
        game.getBatch().end();
    }

    public String getName() {
        return name;
    }

    public PlayerCharacter getCharacter() {
        return character;
    }
}

package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.melihkacaman.snakesandladders.GameMain;
import helpers.GameInfo;
import sceenes.PlayBoard;

public class Player extends Sprite {
    private String name;
    private GameMain game;
    private World world;     // The world is the actual physics world that we are gonna put our player.
    private Body body;       // it's actual player body, manipulating player

    private int currentLocation;

    public Player(String name, World world,GameMain game , float x, float y, PlayerCharacter character) {
        super(new Texture(character == PlayerCharacter.BLUEBIRD ? PlayerCharacter.getBlueBird() : PlayerCharacter.getRedBird()));
        setPosition(x - getWidth() / 2f, y - getHeight() / 2f);
        this.name = name;
        this.world = world;
        this.game = game;

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
        //fixtureDef.friction = 2000f;
        //fixtureDef.density = 1000f;
        // need density = mass

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void updatePlayer(){
        this.setPosition(body.getPosition().x, body.getPosition().y);
    }

    public void updatePlayer(int dice){
        if (dice > 0 && dice <= 6){
            for (int i = 0; i < dice; i++){
                float laterX = getX() + 48;
                if (laterX > GameInfo.WIDTH - 20){
                    setPosition(3, getY() + 55f);
                    body.getPosition().set(getX(), getY());
                }else {
                    moveRight();
                }
            }
        }
    }


    public void moveRight(){
        final Vector2 target = new Vector2(getX() + 48, getY());
        Vector2 direction = target.cpy().sub(getX(), getY()).nor();
        float sclSpeed = 100f;
        Vector2 velocity = direction.cpy().scl(sclSpeed);

        System.out.println("X: " + getX() + "  Y:" + getY());
        System.out.println("Target X: " + target.x + " Target Y:" + target.y);

        body.setLinearVelocity(velocity);

        new Thread(new Runnable() {
            @Override
            public synchronized void run() {
                while (true){
                    Vector2 position = new Vector2(getX() - GameInfo.WIDTH/2f, getY() - GameInfo.HEIGHT / 2f);
                    if ( body.getPosition().dst2(target) < 2){
                        System.out.println("Yakalandı :"+ getX());
                        body.setLinearVelocity(0,0);
                        break;
                    }
                }
            }
        }).start();


    }


    private void moveUp(){

    }

    public int getCurrentLocation(){
        return this.currentLocation;
    }

    public void drawPlayer(SpriteBatch batch) {
        batch.draw(this, getX(), getY());
    }
}

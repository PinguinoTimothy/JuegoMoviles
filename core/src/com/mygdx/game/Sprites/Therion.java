package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Enemies.probateEnemy;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Therion extends Sprite {

    public World world;
    public Body b2body;

    public boolean attack;


    public enum State{
        IDLE,
        RUNNING,
        JUMPING,
        FALLING,
    ATTACKING};
    public  State currentState;
    public State previousState;
    private Animation<TextureRegion> therionRun;
    private Animation<TextureRegion> therionJump;
    private Animation<TextureRegion> therionIdle;
    private  Animation<TextureRegion> therionAttack;
    Texture runSheet;
    Texture idleSheet;
    Texture jumpSheet;
    Texture attackSheet;

    public boolean runningRight;
    private float stateTimer;

    public Therion(World world, PlayScreen screen){
        this.world = world;
        defineTherion();


     setBounds(0,0,35/MyGdxGame.PPM,47/MyGdxGame.PPM);


        runSheet = new Texture("Run-Sheet.png");
        idleSheet = new Texture("Idle-Sheet.png");
        jumpSheet = new Texture("Jump-Start-Sheet.png");
        attackSheet = new Texture("attack-sheet.png");

        TextureRegion[][] tmpRun = TextureRegion.split(runSheet,runSheet.getWidth() / 8,runSheet.getHeight());

        TextureRegion[] runFrames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
                runFrames[i] = tmpRun[0][i];
        }
        therionRun = new Animation<TextureRegion>(1f/8f,runFrames);

        TextureRegion[][] tmpIdle = TextureRegion.split(idleSheet,idleSheet.getWidth() / 4,idleSheet.getHeight());
        TextureRegion[] idleFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            idleFrames[i] = tmpIdle[0][i];
        }
        therionIdle = new Animation<TextureRegion>(1f/4f,idleFrames);


        TextureRegion[][] tmpJump = TextureRegion.split(jumpSheet,jumpSheet.getWidth() / 4,jumpSheet.getHeight());
        TextureRegion[] jumpFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            jumpFrames[i] = tmpJump[0][i];
        }
        therionJump = new Animation<TextureRegion>(1f/4f,jumpFrames);


        TextureRegion[][] tmpAttack = TextureRegion.split(attackSheet,attackSheet.getWidth() / 8,attackSheet.getHeight());
        TextureRegion[] attackFrames = new TextureRegion[8];
        for (int i = 0; i < 8; i++) {
            attackFrames[i] = tmpAttack[0][i];
        }
        therionAttack = new Animation<TextureRegion>(1f/8f,attackFrames);

        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        runningRight = true;


/*
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 4; i < 12; i++) {
            frames.add(new TextureRegion(getTexture(),i*35,0,48,80));
        }
        therionRun = new Animation(0.1f,frames);
        frames.clear();

        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i*35,0,48,80));
        }
        therionIdle = new Animation(0.1f,frames);
        frames.clear();
 */
    }

    public  void update(float dt){
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));


    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;

        switch (currentState){
           case JUMPING:
               region = therionJump.getKeyFrame(stateTimer);
               break;
            case RUNNING:
                region =  therionRun.getKeyFrame(stateTimer,true);
                break;
            case ATTACKING:
                region = therionAttack.getKeyFrame(stateTimer);
                break;
            default:
                region =  therionIdle.getKeyFrame(stateTimer,true);
                break;
        }

        if ((b2body.getLinearVelocity().x < 0  || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
            playerAttackSensor.updateAttackFixture();

        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true,false);
            runningRight = true;
            playerAttackSensor.updateAttackFixture();
        }

        stateTimer =  currentState == previousState ? stateTimer + dt : 0f;
        previousState = currentState;
        return region;


    }

    public  playerAttackSensor playerAttackSensor;

    private State getState() {
        if (currentState == State.ATTACKING && !therionAttack.isAnimationFinished(stateTimer)){
        return State.ATTACKING;
        }else{
        attack = false;
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)){
            return State.JUMPING;
        }
       else if (b2body.getLinearVelocity().y < -1){
            return State.IDLE;
        } else if (b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        }else{
           return State.IDLE;
        }
        }
    }



    //private Fixture attackFixture; // Nueva variable para la fixture de ataque


    public void defineTherion(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32/MyGdxGame.PPM,32/MyGdxGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);
        b2body.setFixedRotation(true);

        // Define la hitbox rectangular
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4 / MyGdxGame.PPM, 8 / MyGdxGame.PPM); // Tamaño de la hitbox

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        b2body.createFixture(fixtureDef);

        FixtureDef fdef2 = new FixtureDef();
        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2(-4 / MyGdxGame.PPM, -12 / MyGdxGame.PPM), new Vector2(4 / MyGdxGame.PPM, -12 / MyGdxGame.PPM));
        fdef2.shape = feet;
        b2body.createFixture(fdef2);

        // Libera los recursos del shape
        shape.dispose();



        // Crear una hitbox rectangular frente al jugador
runningRight = true;

        playerAttackSensor = new playerAttackSensor(this);


    }
/*
    private void createAttackFixture() {
        PolygonShape attackShape = new PolygonShape();
        float offsetX = runningRight ? 16 / MyGdxGame.PPM : -16 / MyGdxGame.PPM;
        attackShape.setAsBox(12 / MyGdxGame.PPM, 8 / MyGdxGame.PPM, new Vector2(offsetX, 0), 0);

        FixtureDef attackFixtureDef = new FixtureDef();
        attackFixtureDef.shape = attackShape;
        attackFixtureDef.isSensor = true; // Configurar la fixture como un sensor
        attackFixture.setUserData("playerAttackSensor");
        attackFixture = b2body.createFixture(attackFixtureDef);

        // Liberar los recursos del shape
        attackShape.dispose();
    }

    private void updateAttackFixture() {
        if (attackFixture != null) {
            // Si la fixture de ataque ya existe, la eliminamos antes de recrearla
            b2body.destroyFixture(attackFixture);
            attackFixture = null;
        }

        // Crear una nueva fixture de ataque
        createAttackFixture();
    }

    public void checkAttack() {

    currentState = State.ATTACKING;


        System.out.println("Ataque");
    }

 */

}

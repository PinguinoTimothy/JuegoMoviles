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

    public boolean is_attacking;
    public int comboAttack;
    public int comboQueue;

    public enum State{
        IDLE,
        RUNNING,
        JUMPING,
        FALLING,
    ATTACKING_01,
        ATTACKING_02,
        ATTACKING_03};
    public  State currentState;
    public State previousState;
    private Animation<TextureRegion> therionRun;
    private Animation<TextureRegion> therionJump;
    private Animation<TextureRegion> therionIdle;
    public    Animation<TextureRegion> therionAttack01;
    public   Animation<TextureRegion> therionAttack02;
    public   Animation<TextureRegion> therionAttack03;

    Texture runSheet;
    Texture idleSheet;
    Texture jumpSheet;
    Texture attack01Sheet;
    Texture attack02Sheet;
    Texture attack03Sheet;


    public boolean runningRight;
    public float stateTimer;

    public PlayScreen screen;
    public Therion(PlayScreen screen){
        this.world = screen.getWorld();
        this.screen = screen;
        defineTherion();


     setBounds(0,10,35/MyGdxGame.PPM,47/MyGdxGame.PPM);


        runSheet = new Texture("Run-Sheet.png");
        idleSheet = new Texture("Idle-Sheet.png");
        jumpSheet = new Texture("Jump-Start-Sheet.png");
        attack01Sheet = new Texture("ataque1.png");
        attack02Sheet = new Texture("ataque2.png");
        attack03Sheet = new Texture("ataque3.png");

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


        TextureRegion[][] tmpAttack01 = TextureRegion.split(attack01Sheet,attack01Sheet.getWidth() / 4,attack01Sheet.getHeight());
        TextureRegion[] attackFrames1 = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            attackFrames1[i] = tmpAttack01[0][i];
        }
        therionAttack01 = new Animation<TextureRegion>(1f/6f,attackFrames1);

        TextureRegion[][] tmpAttack02 = TextureRegion.split(attack02Sheet,attack02Sheet.getWidth() / 4,attack02Sheet.getHeight());
        TextureRegion[] attackFrames2 = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            attackFrames2[i] = tmpAttack02[0][i];
        }
        therionAttack02 = new Animation<TextureRegion>(1f/6f,attackFrames2);

        TextureRegion[] attackFrames3 = new TextureRegion[4];

        TextureRegion[][] tmpAttack03 = TextureRegion.split(attack03Sheet,attack03Sheet.getWidth() / 4,attack03Sheet.getHeight());
        for (int i = 0; i < 4; i++) {
            attackFrames3[i] = tmpAttack03[0][i];
        }
        therionAttack03 = new Animation<TextureRegion>(1f/6f,attackFrames3);

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
        playerAttackSensor.updateScreenAttackFixture();
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
            case ATTACKING_01:
                region = therionAttack01.getKeyFrame(stateTimer);
                break;
            case ATTACKING_02:
                region = therionAttack02.getKeyFrame(stateTimer);
                break;
            case ATTACKING_03:
                region = therionAttack03.getKeyFrame(stateTimer);
                break;
            default:
                region =  therionIdle.getKeyFrame(stateTimer,true);
                break;
        }

        if ((b2body.getLinearVelocity().x < 0  || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;

        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true,false);
            runningRight = true;

        }
        if ((currentState == State.ATTACKING_01 && therionAttack01.isAnimationFinished(stateTimer))){
        atk1Fin = true;
        }else{
            atk1Fin = false;
        }
        if ((currentState == State.ATTACKING_02 && therionAttack02.isAnimationFinished(stateTimer))){
            atk2Fin = true;
        }else{
            atk2Fin = false;
        }
        if ((currentState == State.ATTACKING_03 && therionAttack03.isAnimationFinished(stateTimer))){
            atk3Fin = true;
        }else{
            atk3Fin = false;
        }

            stateTimer =  currentState == previousState ? stateTimer + dt : 0f;

        previousState = currentState;
        return region;


    }

    public boolean atk1Fin;
    public boolean atk2Fin;
    public boolean atk3Fin;

    public  playerAttackSensor playerAttackSensor;

    private State getState() {
        if ((currentState == State.ATTACKING_01 && !therionAttack01.isAnimationFinished(stateTimer)) || (currentState == State.ATTACKING_02 && !therionAttack02.isAnimationFinished(stateTimer)) || (currentState == State.ATTACKING_03 && !therionAttack03.isAnimationFinished(stateTimer))){
        return currentState;
        }else{
        is_attacking = false;
        comboAttack = 0;
        comboQueue = 0;
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
        bodyDef.position.set(12/MyGdxGame.PPM,64/MyGdxGame.PPM);
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
        playerAttackSensor.createScreenAttackFixture();

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

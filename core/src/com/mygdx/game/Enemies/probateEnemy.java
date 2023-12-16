package com.mygdx.game.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class probateEnemy extends Sprite {
    public World world;
    public Body b2body;



    public enum State{
        ALIVE,
        DEAD }
    public State currentState;
    public State previousState;
    public int hp = 100;




    public probateEnemy(World world, PlayScreen screen){
        this.world = world;
        defineEnemy();


        setBounds(0,0,35/ MyGdxGame.PPM,47/MyGdxGame.PPM);



    }

    private boolean destruido = false;
    public  void update(float dt){
    if (muerto && !destruido){
        world.destroyBody(b2body);
        destruido = true;
    }

    }

    public boolean muerto;
    public void recibirDaño(int dmg){
        hp -= dmg;
        System.out.println("Daño recbido " + dmg);
        System.out.println("Vida restante " + hp);
        if (hp <= 0){
           muerto = true;
        }
    }

    private State getState() {


          if (hp <= 0){
              currentState = State.DEAD;
          }
          return currentState;
    }

private Fixture fixture;

    public void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(64 / MyGdxGame.PPM, 32 / MyGdxGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);
        b2body.setFixedRotation(true);

        // Define la hitbox rectangular
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4 / MyGdxGame.PPM, 8 / MyGdxGame.PPM); // Tamaño de la hitbox

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        fixture = b2body.createFixture(fixtureDef);
        fixture.setUserData(this);
        FixtureDef fdef2 = new FixtureDef();
        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2(-4 / MyGdxGame.PPM, -12 / MyGdxGame.PPM), new Vector2(4 / MyGdxGame.PPM, -12 / MyGdxGame.PPM));
        fdef2.shape = feet;
        b2body.createFixture(fdef2);
        // Libera los recursos del shape
        shape.dispose();



    }
    }

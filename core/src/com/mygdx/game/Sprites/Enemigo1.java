package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Enemigo1 extends Enemy{


    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    public Enemigo1(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        Texture txt = new Texture("enemigo2.png");
        TextureRegion[][] tmp = TextureRegion.split(txt,txt.getWidth() / 50,txt.getHeight());

        for (int i = 0; i < 50; i++) {
            frames.add(tmp[0][i]);
        }
        walkAnimation = new Animation<TextureRegion>(1f/50f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),64 / MyGdxGame.PPM,64 / MyGdxGame.PPM);
    }

    public boolean destruido;
    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x  - getWidth()/2,b2body.getPosition().y - getHeight()/2);
        setRegion(walkAnimation.getKeyFrame(stateTime,true));
        if (muerto && !destruido){
            world.destroyBody(b2body);
            destruido = true;
        }

    }



    private Fixture fixture;

    @Override
    protected void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
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
    }

    public void draw(Batch batch){
        if (!destruido){
        super.draw(batch);
        }
    }
}

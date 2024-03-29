package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

public class Brick extends  InteractiveTileObject{

    public Brick(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX()+bounds.getWidth()/2)/ MyGdxGame.PPM,(bounds.getY()+bounds.getHeight()/2)/MyGdxGame.PPM);

        body = world.createBody(bodyDef);
        shape.setAsBox(bounds.getWidth()/2/MyGdxGame.PPM,bounds.getHeight()/2/MyGdxGame.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }
}

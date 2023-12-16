package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Brick;
import com.mygdx.game.Sprites.Coin;
import com.mygdx.game.Sprites.Enemigo1;

public class B2WorldCreator {



    private Array<Enemigo1> enemigos1;
    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //Crear el suelo
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX()+rectangle.getWidth()/2)/ MyGdxGame.PPM,(rectangle.getY()+rectangle.getHeight()/2)/MyGdxGame.PPM);

            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth()/2/MyGdxGame.PPM,rectangle.getHeight()/2/MyGdxGame.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        //Crear pipes
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX()+rectangle.getWidth()/2)/MyGdxGame.PPM,(rectangle.getY()+rectangle.getHeight()/2)/MyGdxGame.PPM);

            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth()/2/MyGdxGame.PPM,rectangle.getHeight()/2/MyGdxGame.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        //Crear enemigos1
        enemigos1 = new Array<Enemigo1>();
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
          enemigos1.add(new Enemigo1(screen,rectangle.x / MyGdxGame.PPM,rectangle.getY()/MyGdxGame.PPM));
        }
        /*
        //Crear coins
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
           new Coin(screen,rectangle);
        }

        //Crear brics
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Brick(screen,rectangle);
        }

         */
    }
    public Array<Enemigo1> getEnemigos1() {
        return enemigos1;
    }
}

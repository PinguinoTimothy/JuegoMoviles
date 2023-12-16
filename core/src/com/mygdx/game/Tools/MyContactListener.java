package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Enemies.probateEnemy;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.Therion;
import com.mygdx.game.Sprites.playerAttackSensor;

public class MyContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {


        Fixture fxA = contact.getFixtureA();
        Fixture fxB = contact.getFixtureB();
        if (fxA.getUserData() instanceof playerAttackSensor || fxB.getUserData() instanceof playerAttackSensor) {

            Fixture attackSensor = fxA.getUserData() instanceof playerAttackSensor ? fxA : fxB;


            Fixture otherFixture = attackSensor == fxA ? fxB : fxA;

            if (otherFixture.getUserData() != null && Enemy.class.isAssignableFrom(otherFixture.getUserData().getClass())) {
                Enemy enemigo = (Enemy) otherFixture.getUserData();
                ((playerAttackSensor) attackSensor.getUserData()).enemigosEnRangoMelee.add(enemigo);
                Gdx.app .log("Enemigo: ", "Entra colision");
            }

        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fxA = contact.getFixtureA();
        Fixture fxB = contact.getFixtureB();
        if (fxA.getUserData() instanceof playerAttackSensor || fxB.getUserData() instanceof playerAttackSensor) {

            Fixture attackSensor = fxA.getUserData() instanceof playerAttackSensor ? fxA : fxB;


            Fixture otherFixture = attackSensor == fxA ? fxB : fxA;

            if (otherFixture.getUserData() != null && Enemy.class.isAssignableFrom(otherFixture.getUserData().getClass())) {
                Enemy enemigo = (Enemy) otherFixture.getUserData();
                ((playerAttackSensor) attackSensor.getUserData()).enemigosEnRangoMelee.removeValue(enemigo,true);
                Gdx.app .log("Enemigo: ", "Sale colision");
            }

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

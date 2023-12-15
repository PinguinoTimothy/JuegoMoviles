package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Enemies.probateEnemy;
import com.mygdx.game.Sprites.Therion;
import com.mygdx.game.Sprites.playerAttackSensor;

public class MyContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {


        Fixture fxA = contact.getFixtureA();
        Fixture fxB = contact.getFixtureB();
        if (fxA.getUserData() instanceof playerAttackSensor || fxB.getUserData() instanceof  playerAttackSensor){

            Fixture attadckSensor = fxA.getUserData() instanceof playerAttackSensor ? fxA : fxB;
           if(((playerAttackSensor) attadckSensor.getUserData()).player.attack == true) {
               ((playerAttackSensor) attadckSensor.getUserData()).player.attack = false;

               Fixture otherFixture = attadckSensor == fxA ? fxB : fxA;

               if (otherFixture.getUserData() instanceof probateEnemy) {
                   probateEnemy enemigo = (probateEnemy) otherFixture.getUserData();
                   enemigo.recibirDaño(25);

               }
           }
        };
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

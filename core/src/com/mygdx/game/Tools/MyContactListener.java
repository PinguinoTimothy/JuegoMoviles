package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Enemies.probateEnemy;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.Therion;
import com.mygdx.game.Sprites.playerAttackSensor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MyContactListener implements ContactListener {

    private Set<FixturePair> handledContacts = new HashSet<>();

    @Override
    public void beginContact(Contact contact) {

        handleContact(contact, true);
    }

    @Override
    public void endContact(Contact contact) {
        handleContact(contact, false);
    }

    private void handleContact(Contact contact, boolean begin) {
        Fixture fxA = contact.getFixtureA();
        Fixture fxB = contact.getFixtureB();

        FixturePair fixturePair = new FixturePair(fxA, fxB);

        if (!handledContacts.contains(fixturePair)) {
            if (fxA.getUserData() instanceof playerAttackSensor || fxB.getUserData() instanceof playerAttackSensor) {
                Fixture attackSensor = fxA.getUserData() instanceof playerAttackSensor ? fxA : fxB;
                Fixture otherFixture = attackSensor == fxA ? fxB : fxA;

                if (otherFixture.getUserData() != null && Enemy.class.isAssignableFrom(otherFixture.getUserData().getClass())) {
                    Enemy enemigo = (Enemy) otherFixture.getUserData();
                    if (begin) {
                        ((playerAttackSensor) attackSensor.getUserData()).enemigosEnRangoMelee.add(enemigo);
                        Gdx.app.log("Enemigo: ", "Entra colisión");
                    } else {
                        ((playerAttackSensor) attackSensor.getUserData()).enemigosEnRangoMelee.removeValue(enemigo, true);
                        Gdx.app.log("Enemigo: ", "Sale colisión");
                    }
                }
            }

            handledContacts.add(fixturePair);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    private static class FixturePair {
        private final Fixture fixtureA;
        private final Fixture fixtureB;

        public FixturePair(Fixture fixtureA, Fixture fixtureB) {
            this.fixtureA = fixtureA;
            this.fixtureB = fixtureB;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FixturePair that = (FixturePair) o;
            return (fixturesEqual(fixtureA, that.fixtureA) && fixturesEqual(fixtureB, that.fixtureB)) ||
                    (fixturesEqual(fixtureA, that.fixtureB) && fixturesEqual(fixtureB, that.fixtureA));
        }

        private boolean fixturesEqual(Fixture fixture1, Fixture fixture2) {
            return fixture1.getBody() == fixture2.getBody() && fixture1.getShape() == fixture2.getShape();
        }

        @Override
        public int hashCode() {
            return Objects.hash(fixtureA, fixtureB);
        }
    }
}


package com.mygdx.game.Sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.MyContactListener;

public class playerAttackSensor{
    private Fixture attackFixture; // Nueva variable para la fixture de ataque

    public Therion player;
    public playerAttackSensor(Therion player){
        this.player = player;
    }
    private void createAttackFixture() {
        PolygonShape attackShape = new PolygonShape();
        float offsetX = player.runningRight ? 16 / MyGdxGame.PPM : -16 / MyGdxGame.PPM;
        attackShape.setAsBox(12 / MyGdxGame.PPM, 8 / MyGdxGame.PPM, new Vector2(offsetX, 0), 0);

        FixtureDef attackFixtureDef = new FixtureDef();
        attackFixtureDef.shape = attackShape;
        attackFixtureDef.isSensor = true; // Configurar la fixture como un sensor
        attackFixture = player.b2body.createFixture(attackFixtureDef);
        attackFixture.setUserData(this);

        // Liberar los recursos del shape
        attackShape.dispose();
    }

    public void updateAttackFixture() {
        if (attackFixture != null) {
            // Si la fixture de ataque ya existe, la eliminamos antes de recrearla
            player.b2body.destroyFixture(attackFixture);
            attackFixture = null;
        }

        // Crear una nueva fixture de ataque
        createAttackFixture();
    }

    public void checkAttack() {

        player.currentState = Therion.State.ATTACKING;
    player.attack = true;
        System.out.println("Ataque");
    }

}

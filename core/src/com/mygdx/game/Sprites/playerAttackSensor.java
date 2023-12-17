package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Enemies.probateEnemy;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.MyContactListener;

public class playerAttackSensor{
    private Fixture attackFixture; // Nueva variable para la fixture de ataque
    private Fixture screenAttackFixture; // Nueva variable para la fixture de ataque


    public Array<Enemy> enemigosEnRangoMelee = new Array<Enemy>();
    public Therion player;
    public playerAttackSensor(Therion player){
        this.player = player;
    }

    private void createAttackFixture() {
        PolygonShape attackShape = new PolygonShape();
        float offsetX = player.runningRight ? 12 / MyGdxGame.PPM : -12 / MyGdxGame.PPM;
        attackShape.setAsBox(12 / MyGdxGame.PPM, 8 / MyGdxGame.PPM, new Vector2(offsetX, 0), 0);

        FixtureDef attackFixtureDef = new FixtureDef();
        attackFixtureDef.shape = attackShape;
        attackFixtureDef.isSensor = true; // Configurar la fixture como un sensor
        attackFixture = player.b2body.createFixture(attackFixtureDef);
        attackFixture.setUserData("no");

        // Liberar los recursos del shape
        attackShape.dispose();
    }
    public void createScreenAttackFixture() {
        PolygonShape attackShape = new PolygonShape();

        attackShape.setAsBox(90 / MyGdxGame.PPM, 50 / MyGdxGame.PPM);

        FixtureDef attackFixtureDef = new FixtureDef();
        attackFixtureDef.shape = attackShape;
        attackFixtureDef.isSensor = true; // Configurar la fixture como un sensor
        screenAttackFixture = player.b2body.createFixture(attackFixtureDef);
        screenAttackFixture.setUserData(this);

        // Liberar los recursos del shape
        attackShape.dispose();
    }

    public void updateScreenAttackFixture(){
        if (screenAttackFixture != null) {
            // Si la fixture de ataque ya existe, la eliminamos antes de recrearla
            player.b2body.destroyFixture(screenAttackFixture);
            screenAttackFixture = null;
        }
        createScreenAttackFixture();
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

    public void gestionAtaque(){
        createAttackFixture();
        MyContactListener myContactListener = new MyContactListener();
        atacar(25);
        player.b2body.destroyFixture(attackFixture);
        enemigosEnRangoMelee.clear();
    }

    public void atacar(int daño){
        for (Enemy enemigo : enemigosEnRangoMelee) {
            enemigo.recibirDaño(daño) ;
        }

    }

    public  boolean qAtack2;
    public  boolean qAtack3;
    public int comboQueueRelative;

    public void checkAttack(float dt) {
    /*
        if (player.comboQueue < 3){
            player.comboQueue++;
            comboQueueRelative++;
        }

        switch (player.comboQueue){
            case 2:
                qAtack2 = true;
                break;
            case 3:
                qAtack3 = true;
                break;
        }




        if (!player.is_attacking) {
            player.is_attacking = true;
            player.comboAttack++;
            player.currentState = Therion.State.ATTACKING_01;
            player.stateTimer = 0; // Reiniciar el temporizador para la nueva animación
           atacar(enemigosEnRangoMelee,25);
            Gdx.app .log("Jugador: ", "Ataque1");

        }

         /*
    while (comboQueueRelative > 0) {


        if (!player.is_attacking) {


            player.is_attacking = true;
            comboQueueRelative--;
            player.comboAttack++;
            player.currentState = Therion.State.ATTACKING_01;
            atacar(enemigosEnRangoMelee, 25);
            System.out.println("Ataque1");
        } else {
            if (qAtack2 == true){


                if (player.atk1Fin) {
                    qAtack2 = false;
                    player.comboAttack++;
                    comboQueueRelative--;
                    player.currentState = Therion.State.ATTACKING_02;
                    atacar(enemigosEnRangoMelee, 25);

                    System.out.println("Ataque2");
                }
        }else{
            if (qAtack3 == true) {


                if (player.atk2Fin) {
                    qAtack3 = false;
                    player.comboAttack++;
                    comboQueueRelative--;
                    player.currentState = Therion.State.ATTACKING_03;
                    atacar(enemigosEnRangoMelee, 25);

                    System.out.println("Ataque3");
                }
            }else{
                player.comboAttack = 0;
                player.is_attacking = false;
                comboQueueRelative = 0;
            }
        }
    }

          */

        }
    }


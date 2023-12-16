package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;

public abstract class Enemy extends Sprite {

   protected World world;
   protected PlayScreen screen;
   public Body b2body;
   protected int hp = 100;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        defineEnemy();
    }


    public abstract void update(float dt);


    public boolean muerto;
    public void recibirDaño(int dmg){
        hp -= dmg;
        System.out.println("Daño recbido " + dmg);
        System.out.println("Vida restante " + hp);
        if (hp <= 0){
            muerto = true;
        }
    }

    protected abstract void defineEnemy();


}

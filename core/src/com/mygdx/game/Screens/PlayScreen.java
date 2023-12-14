package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Therion;
import com.mygdx.game.Tools.B2WorldCreator;

public class PlayScreen implements Screen {

    private MyGdxGame game;
    private OrthographicCamera gameCam;
    private Viewport viewport;
    private Hud hud;


    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    float viewportWidth,viewportHeight;

    //Box2d
    private World world;
    private Box2DDebugRenderer b2dr;
    private Therion player;

    private TextureAtlas textureAtlas;

    private Touchpad touchpad;
    private Skin touchpadSkin;
    private     Skin btnJumpSkin;
    private ImageButton btnJump;

    private  Skin btnAttackSkin;
    private ImageButton btnAttack;
    public PlayScreen(MyGdxGame game){
        this.game = game;
        gameCam = new OrthographicCamera();

        viewportWidth=12*16;
        viewportHeight=10*16;
        viewport = new ExtendViewport(viewportWidth/MyGdxGame.PPM,viewportHeight/MyGdxGame.PPM,gameCam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("nivel.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map,1/MyGdxGame.PPM);

        gameCam.position.set(viewport.getWorldWidth()/2 ,viewport.getWorldHeight()/2,0);



        //Box2d
        world = new World(new Vector2(0,-9.8f ),true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world,map);


        hud = new Hud(game.batch);

        textureAtlas = new TextureAtlas("player.pack");
        player = new Therion(world,this);

        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("Joystick.png"));
        touchpadSkin.add("touchKnob", new Texture("SmallHandleFilled.png"));

        Touchpad.TouchpadStyle  touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
        touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");
        touchpadStyle.knob.setMinWidth(10f);
        touchpadStyle.knob.setMinHeight(10f);
        touchpad = new Touchpad(1f, touchpadStyle);

        touchpad.setBounds(15, 15, 50, 50);
        hud.stage.addActor(touchpad);


        //Boton Salto
        btnJumpSkin = new Skin();
        btnJumpSkin.add("jump",new Texture("jump.png"));
        btnJump = new ImageButton(btnJumpSkin.getDrawable("jump"));
        btnJump.setSize(btnJump.getWidth(), btnJump.getHeight());
        btnJump.setBounds(350, 15, 30, 30);
        btnJump.getImage().setOrigin(Align.center);
        btnJump.getImage().setScale(0.5f);
        btnJump.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Lógica para el salto aquí
                player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
            }
        });
        hud.stage.addActor(btnJump);


        //Boton Ataque
        btnAttackSkin = new Skin();
        btnAttackSkin.add("attack",new Texture("attack.png"));
        btnAttack = new ImageButton(btnAttackSkin.getDrawable("attack"));
        btnAttack.setSize(btnAttack.getWidth(), btnAttack.getHeight());
        btnAttack.setBounds(300, 15, 30, 30);
        btnAttack.getImage().setOrigin(Align.center);
        btnAttack.getImage().setScale(0.5f);
        btnAttack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               player.checkAttack();
            }
        });
        hud.stage.addActor(btnAttack);




        Gdx.input.setInputProcessor(hud.stage);
    }



    public TextureAtlas getAtlas(){
        return textureAtlas;
    }


    public void update(float dt){

        handleInput(dt);
        gameCam.update();
        mapRenderer.setView(gameCam);

        world.step(1/60f,6,2);
            gameCam.position.x = player.b2body.getPosition().x;
            player.update(dt);
    }

    public void handleInput(float dt){
        if (touchpad.isTouched()) {
            float knobPercentX = touchpad.getKnobPercentX();

            // Puedes ajustar la velocidad según tus necesidades
            float velocityX = knobPercentX * 2.0f;

            // Aplica la velocidad al cuerpo del jugador
            player.b2body.setLinearVelocity(new Vector2(velocityX, player.b2body.getLinearVelocity().y));
        }
        /*
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.b2body.applyLinearImpulse(new Vector2(0, 4f ), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2 ) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f , 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f , 0), player.b2body.getWorldCenter(), true);
        }

         */

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gameCam.combined);

        // Renderizar el tilemap antes del HUD
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        mapRenderer.render();


        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
        // Renderizar el HUD después del tilemap
        hud.stage.draw();

        // Renderizar el Box2D Debug Renderer
        b2dr.render(world, gameCam.combined);


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,false);
        viewport.getCamera().position.set(viewportWidth/2f/MyGdxGame.PPM,viewportHeight/2f/MyGdxGame.PPM,0);
        viewport.getCamera().update();
    }



    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    map.dispose();
    mapRenderer.dispose();
    world.dispose();
    b2dr.dispose();
    hud.dispose();
    touchpadSkin.dispose();
btnJumpSkin.dispose();
btnAttackSkin.dispose();
    }
}

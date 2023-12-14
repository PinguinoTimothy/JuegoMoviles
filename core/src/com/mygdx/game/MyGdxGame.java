package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Screens.PlayScreen;

import javax.management.openmbean.CompositeType;
import javax.naming.ldap.Control;

public class MyGdxGame extends Game {

	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public  static  final float PPM = 100;

	public SpriteBatch batch;

	private Skin touchpadSkin;
	private Touchpad.TouchpadStyle touchpadStyle;
	private Touchpad touchpad;

	private OrthographicCamera camara2d;

	private Stage stage;
	private Sprite grafico;
	private float velocidade = 5;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
		/*
		grafico = new Sprite(new Texture("badlogic.jpg"));
		grafico.setBounds(100, 150, 250, 250);



		// TOUCHPAD
		touchpadSkin = new Skin();
		touchpadSkin.add("touchBackground", new Texture("Joystick.png"));
		touchpadSkin.add("touchKnob", new Texture("SmallHandleFilled.png"));

		touchpadStyle = new Touchpad.TouchpadStyle();
		Drawable D_background = touchpadSkin.getDrawable("touchBackground");
		Drawable D_knob = touchpadSkin.getDrawable("touchKnob");

		D_background.setMinHeight(40);
		D_background.setMinWidth(40);

		D_knob.setMinHeight(15);
		D_knob.setMinWidth(15);

		touchpadStyle.background = D_background;
		touchpadStyle.knob = D_knob;

		touchpad = new Touchpad(5, touchpadStyle);
		touchpad.setBounds(40, 40, 300, 300);

		//Stage

		stage = new Stage();
		stage.addActor(touchpad);

		Gdx.input.setInputProcessor(stage);
		*/
	}

	@Override
	public void render() {
super.render();
	/*	Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(grafico.getTexture(), grafico.getX(), grafico.getY(),grafico.getWidth(),grafico.getHeight());
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

		//Movemos o grafico
		grafico.setX(grafico.getX() + touchpad.getKnobPercentX()*velocidade);
		grafico.setY(grafico.getY() + touchpad.getKnobPercentY()*velocidade);

	*/
	}


/*
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
		Gdx.input.setInputProcessor(null);
	}

	 */
}

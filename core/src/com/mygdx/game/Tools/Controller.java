package com.mygdx.game.Tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class Controller extends Touchpad {

        private static TouchpadStyle touchpadStyle;
        private static Skin touchpadSkin;
        private Viewport viewport;
        private   Stage stage;
    public Controller(SpriteBatch sb) {
        super(10f, Controller.getTouchPadStyle());
        setBounds(10f, 10f, 100f, 100f);
        viewport = new FitViewport(MyGdxGame.V_WIDTH,MyGdxGame.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport, sb);
        stage.addActor(this);

    }

    private static TouchpadStyle getTouchPadStyle(){
        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("Joystick.png"));
        touchpadSkin.add("touchKnob", new Texture("SmallHandleFilled.png"));

        touchpadStyle = new TouchpadStyle();
        touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
        touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");
        return touchpadStyle;
    }

    @Override
    public void act (float delta) {
        super.act(delta);
        if(isTouched()){

        }
    }
}

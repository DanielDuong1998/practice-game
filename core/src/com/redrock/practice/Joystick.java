package com.redrock.practice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.redrock.Main;

public class Joystick {
    private Touchpad touchpad;
    private Stage stage;


    public Joystick(Group group) {

//        Gdx.input.setInputProcessor(stage);

        // Load textures for touchpad
//        Texture touchpadBg = Main.asset().getTG("ounner_circle").getTexture();
//        Texture touchpadKnob = Main.asset().getTG("inner_circle").getTexture();

        Texture touchpadBg = Main.asset().getTex("outer");
        Texture touchpadKnob = Main.asset().getTex("inner_circle");

        // Create touchpad style
        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = new TextureRegionDrawable(touchpadBg);
        touchpadStyle.knob = new TextureRegionDrawable(touchpadKnob);

        // Create touchpad
        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(50, 50, 150, 150);  // Position and size of the joystick

        // Add to stage
//        group.addActor(touchpad);
    }

    public void addActor(Group group){
        group.addActor(touchpad);
    }

    public void update() {
        stage.act(Gdx.graphics.getDeltaTime());
    }

    public void render(SpriteBatch batch) {
        stage.draw();
    }

    public float getKnobX() {
        return touchpad.getKnobPercentX();
    }

    public float getKnobY() {
        return touchpad.getKnobPercentY();
    }

    public Stage getStage() {
        return stage;
    }

    public float getRotationAngle() {
        float x = touchpad.getKnobPercentX();
        float y = touchpad.getKnobPercentY();

        if (x == 0 && y == 0) {
            return -1; // No movement
        }

        float angle = (float) Math.toDegrees(Math.atan2(y, x));

        // Convert to 0-360 degrees range
        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }
}

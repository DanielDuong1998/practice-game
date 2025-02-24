package com.redrock.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.redrock.Main;
import com.redrock.manager.SceneMgr;
import com.redrock.practice.GameState;
import com.redrock.practice.Joystick;
import com.redrock.practice.MinimapActor;
import com.redrock.practice.PlayerInput;
import com.redrock.practice.TilemapActor;

import java.io.IOException;

public class StartScene extends ScreenAdapter {

  public static float CENTER_X = Main.getStage().getWidth()/2;
  public static float CENTER_Y = Main.getStage().getHeight()/2;
  private Joystick joystick;
  private Image fish;
  float speed = 200f; // Movement speed
  private float currentRotation = 0f;  // Player's current rotation
  private float rotationSpeed = 5f;    // Rotation speed (higher = faster rotation)
  private float targetRotation = 0;

  private final Group gParent;
  private TilemapActor tilemapActor;
  private MinimapActor minimapActor;
  private OrthographicCamera minimapCamera;
  private Viewport minimapViewport;
  private Client client;
  GameState gameState;

  public StartScene() {
    this.gParent = new Group();
    this.gParent.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());

    Main.layers().init(SceneMgr.START_SCENE, Main.getStage().getWidth(), Main.getStage().getHeight());
    this.createGameClient();
  }

  private void createGameClient(){
    client = new Client();
    client.start();
    try {
      client.connect(5000, "localhost", 54555, 54777); // Kết nối đến server
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    client.getKryo().register(PlayerInput.class);
    client.getKryo().register(GameState.class);

    client.addListener(new Listener() {
      public void received(Connection connection, Object object) {
        if (object instanceof GameState) {
          gameState = (GameState) object;
          // Cập nhật giao diện game
        }
      }
    });
  }

  @Override
  public void show() {
    super.show();

    Main.layers().activeLayersBy(SceneMgr.START_SCENE);
    Main.getStage().addActor(gParent);

    int configTestField = Main.iPlat().getConfigIntValue("configTestField", 5);
    System.out.println("configTest: " + configTestField);

    tilemapActor = new TilemapActor("map/map.tmx", Main.getCamera());
//    this.gParent.addActor(tilemapActor);

    this.minimapActor = new MinimapActor(tilemapActor.getTiledMap(), 300, 300);
    this.gParent.addActor(this.minimapActor);


    this.fish = new Image(Main.asset().getTG("fish"));
    this.fish.setOrigin(Align.center);
    this.gParent.addActor(this.fish);
    this.joystick = new Joystick(this.gParent);
    this.fish.setPosition(800, 200);
    this.gParent.addActor(this.fish);

  }

  @Override
  public void render(float delta) {
    super.render(delta);

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//    this.joystick.update();
    float moveX = joystick.getKnobX();
    float moveY = joystick.getKnobY();

// Move character
    this.fish.setX(this.fish.getX() + moveX * speed * delta);
    this.fish.setY(this.fish.getY() + moveY * speed * delta);


    float targetRotation = (float) Math.toRadians(joystick.getRotationAngle());
    this.updateRotation(targetRotation, delta);

    Main.getCamera().update();

    // Render bản đồ

    Main.getMapRenderer().render();

    PlayerInput input = new PlayerInput();
    input.x = Gdx.input.getX();
    input.y = Gdx.input.getY();
    client.sendTCP(input);

    // Hiển thị gameState lên màn hình
    if (gameState != null) {
//            renderGameState(gameState);
      System.out.println("gameState ne: " + gameState.toString());
    }
  }

  @Override
  public void dispose() {
    super.dispose();

    tilemapActor.dispose();
//    minimapActor.dispose();
  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);

    CENTER_X = Main.getStage().getWidth() / 2;
    CENTER_Y = Main.getStage().getHeight() / 2;

    gParent.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());
  }

  public void updateRotation(float targetRotation, float delta) {
    if (targetRotation != -1) {
//      targetRotation = (Math.abs((float)Math.toDegrees(targetRotation)) > 180) ? (float)Math.toRadians( Math.toDegrees(targetRotation)-360) : targetRotation;

      // Apply smooth rotation using interpolation
//      currentRotation += (targetRotation - currentRotation) * rotationSpeed * delta;
      this.fish.setRotation((float)Math.toDegrees(targetRotation));

      // Set player rotation
//      this.fish.addAction(Actions.rotateTo((float)Math.toDegrees(currentRotation), 0.5f, Interpolation.fastSlow));
    }
  }

  private float getValidRotation(float targetRotation){
//    if(this.fish.getRotation() < 180)
    return 0;
  }
}

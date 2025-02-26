package com.redrock.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Client;
import com.redrock.Main;
import com.redrock.manager.SceneMgr;
import com.redrock.practice.GameState;
import com.redrock.practice.Joystick;
import com.redrock.practice.MinimapActor;
import com.redrock.practice.MinimapActor2;
import com.redrock.practice.TilemapActor;

import java.util.ArrayList;
import java.util.List;

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
  private MinimapActor2 minimapActor;
  private OrthographicCamera minimapCamera;
  private Viewport minimapViewport;
  private Client client;
  GameState gameState;

  public StartScene() {
    this.gParent = new Group();
    this.gParent.setSize(Main.getStage().getWidth(), Main.getStage().getHeight());

    Main.layers().init(SceneMgr.START_SCENE, Main.getStage().getWidth(), Main.getStage().getHeight());
  }

  @Override
  public void show() {
    super.show();

    Main.layers().activeLayersBy(SceneMgr.START_SCENE);
    Main.getStage().addActor(gParent);

    System.out.println("viewport: " + Main.getCamera().viewportWidth + "-" + Main.getCamera().viewportHeight);

    tilemapActor = new TilemapActor("map/map2.tmx", Main.getCamera());
    this.gParent.addActor(tilemapActor);

//    this.minimapActor = new MinimapActor2(tilemapActor.getTiledMap(), Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
//    Main.getStage2().addActor(this.minimapActor);


    this.fish = new Image(Main.asset().getTG("fish"));
    this.fish.setOrigin(Align.center);
    this.gParent.addActor(this.fish);
    this.joystick = new Joystick(this.gParent);
    this.fish.setPosition(800, 200);
    this.gParent.addActor(this.fish);


    MinimapActor minimap = new MinimapActor(tilemapActor.getTiledMap(), 200, 200);
    minimap.setSize(200, 200);
    minimap.setPosition(Main.getStage2().getWidth() - minimap.getWidth(), Main.getStage2().getHeight() - minimap.getHeight());

// Thêm vào Stage
    Main.getStage2().addActor(minimap);

    Vector2 posTest = new Vector2(0, 500);
// Cập nhật danh sách nhân vật trong game loop
    List<Vector2> playerPositions = new ArrayList<>();
    playerPositions.add(posTest); // Ví dụ: nhân vật ở tọa độ (500, 500)
    minimap.setPlayerPositions(playerPositions);

    Image point = new Image(Main.asset().getTG("point_5px"));
    Main.getStage().addActor(point);
    point.setSize(point.getWidth()*10, point.getHeight()*10);
    point.setPosition(posTest.x, posTest.y);
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    float moveX = joystick.getKnobX();
    float moveY = joystick.getKnobY();

// Move character
    this.fish.setX(this.fish.getX() + moveX * speed * delta);
    this.fish.setY(this.fish.getY() + moveY * speed * delta);

    tilemapActor.getCamera().position.x += moveX * speed * delta;
    tilemapActor.getCamera().position.y += moveY * speed * delta;

//    tilemapActor.getCamera().position.x = this.fish.getX();
//    tilemapActor.getCamera().position.y = this.fish.getY();

    float targetRotation = (float) Math.toRadians(joystick.getRotationAngle());
    this.updateRotation(targetRotation, delta);

    Main.getCamera().update();
//    Main.getMapRenderer().render();
  }

  @Override
  public void dispose() {
    super.dispose();

    tilemapActor.dispose();
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

      // Apply smooth rotation using interpolation
//      currentRotation += (targetRotation - currentRotation) * rotationSpeed * delta;
      this.fish.setRotation((float)Math.toDegrees(targetRotation));

      // Set player rotation
//      this.fish.addAction(Actions.rotateTo((float)Math.toDegrees(currentRotation), 0.5f, Interpolation.fastSlow));
    }
  }
}

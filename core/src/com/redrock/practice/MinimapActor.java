package com.redrock.practice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.redrock.Main;

import java.util.List;

public class MinimapActor extends Actor {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera miniMapCamera;
    private FrameBuffer miniMapBuffer;
    private TextureRegion miniMapTexture;
    private ShapeRenderer shapeRenderer;
    private List<Vector2> playerPositions;
    private float worldWidth, worldHeight;
    private float cameraStartPosX, cameraStartPosY;
    private TextureRegion pointTg;
    private Actor target;
    private Joystick joystick;


    public MinimapActor(TiledMap tiledMap, float width, float height, Actor actor, Joystick joystick) {
        this.joystick = joystick;
        this.target = actor;
        float miniMapWidth = 200;
        float miniMapHeight = 200;

        this.tiledMap = tiledMap;
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 0.1f);

        this.miniMapCamera = new OrthographicCamera();
        this.miniMapCamera.setToOrtho(false, width, height);

//        this.worldWidth = width;
//        this.worldHeight = height;
        this.shapeRenderer = new ShapeRenderer();

//        cameraStartPosX = -width/2 + 2.5f*miniMapWidth;
//        cameraStartPosY = -height/2 + miniMapHeight;
//        miniMapCamera.position.set(cameraStartPosX, cameraStartPosY, 0);


        miniMapCamera.zoom = 1f;
        miniMapCamera.update();
        // Tạo camera minimap


//        this.miniMapCamera.position.x = 800;
//        this.miniMapCamera.position.y = 200;

        // Tạo FrameBuffer để vẽ minimap
        this.miniMapBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, (int) miniMapWidth, (int) miniMapHeight, false);
        this.miniMapTexture = new TextureRegion(miniMapBuffer.getColorBufferTexture());
        this.miniMapTexture.flip(false, true);


        this.pointTg = Main.asset().getTG("point_5px");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Vẽ minimap vào FrameBuffer
        miniMapBuffer.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        miniMapCamera.update();
        tiledMapRenderer.setView(miniMapCamera);
        tiledMapRenderer.render();

//        Vector2 pointPos = new Vector2()
//        batch.draw(this.pointTg, 100, 100, this.pointTg.getRegionWidth(), this.pointTg.getRegionHeight());

        drawPlayers(batch);
        // Vẽ nhân vật lên FrameBuffer
        batch.end();  // Phải kết thúc batch trước khi dùng ShapeRenderer
//        drawPlayers();
        batch.begin(); // Bắt đầu lại batch sau khi vẽ xong

//        miniMapBuffer.end();
        miniMapBuffer.end();


        // Cập nhật texture từ FrameBuffer
        miniMapTexture.setRegion(miniMapBuffer.getColorBufferTexture());
        miniMapTexture.flip(false, true);

        // Vẽ minimap lên màn hình
        batch.draw(miniMapTexture, getX(), getY(), getWidth(), getHeight());

        batch.end();  // Phải kết thúc batch trước khi dùng ShapeRenderer
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight()); // Vẽ viền quanh minimap
        shapeRenderer.end();
        batch.begin();
    }

    private void drawPlayers(Batch batch) {
        for (Vector2 worldPos : playerPositions) {
            Vector2 miniMapPos = worldToMiniMap(worldPos);
//            Vector2 miniMapPos = worldPos;
//            shapeRenderer.circle(miniMapPos.x, miniMapPos.y, 50);
            batch.draw(this.pointTg, miniMapPos.x, miniMapPos.y, this.pointTg.getRegionWidth(), this.pointTg.getRegionHeight());
        }
    }

    private Vector2 worldToMiniMap(Vector2 worldPos) {
        float miniMapX = worldPos.x *0.1f;
        float miniMapY = worldPos.y* 0.1f;
//        return new Vector2(getX() + miniMapX, getY() + miniMapY);
        return new Vector2(miniMapX, miniMapY);
    }

    public void setPlayerPositions(List<Vector2> playerPositions) {
        this.playerPositions = playerPositions;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        handleInput();
        updateCamera();
    }

    private void updateCamera(){
        if(this.target == null) return;

        float posX = target.getX();
        float posY = target.getY();
        Vector3 cameraCoords = new Vector3(posX, posY, 0);
//        miniMapCamera.project(cameraCoords);
//        camera.position.set(cameraCoords);
        miniMapCamera.position.x += this.joystick.getKnobX() *Gdx.graphics.getDeltaTime()*50;
        miniMapCamera.position.y += this.joystick.getKnobY() *Gdx.graphics.getDeltaTime()*50;

        // Giữ camera trong giới hạn của bản đồ
        float halfWidth = miniMapCamera.viewportWidth / 2;
        float halfHeight = miniMapCamera.viewportHeight / 2;

//        miniMapCamera.position.x = Math.max(halfWidth, Math.min(miniMapCamera.position.x, mapPixelWidth - halfWidth));
//        miniMapCamera.position.y = Math.max(halfHeight, Math.min(miniMapCamera.position.y, mapPixelHeight - halfHeight));

        miniMapCamera.update();
    }

    private void handleInput() {
        float speed = 100 * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            miniMapCamera.position.x -= speed;
            miniMapCamera.position.x = Math.max(miniMapCamera.position.x, cameraStartPosX);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            miniMapCamera.position.x += speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            miniMapCamera.position.y += speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            miniMapCamera.position.y -= speed;
            miniMapCamera.position.y = Math.max(miniMapCamera.position.y, cameraStartPosY);
        }
    }
}

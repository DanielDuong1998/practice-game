package com.redrock.practice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

public class MinimapActor extends Actor implements DisposableActor{
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera miniMapCamera;
    private Rectangle scissorBounds;
    private ShapeRenderer shapeRenderer;


    public MinimapActor(TiledMap tiledMap, float width, float height) {
        float size = 200; // 150x150 pixels

        this.tiledMap = tiledMap;
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 0.1f);

        miniMapCamera = new OrthographicCamera();
        miniMapCamera.setToOrtho(false, width, height);

        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        float mapWidth = layer.getWidth() * layer.getTileWidth();
        float mapHeight = layer.getHeight() * layer.getTileHeight();
//        System.out.println("mapHeight: " + mapHeight)
//        miniMapCamera.position.set(mapWidth / 2, mapHeight / 2, 0);
//        miniMapCamera.position.set(640, 360, 0);
        float cameraPosX = -width/2 + size*1280/Gdx.graphics.getWidth();
        float cameraPosY = -height/2 + size*720/Gdx.graphics.getHeight();
//        miniMapCamera.position.set(-width/2 + 200, -height/2 + 200, 0);
        miniMapCamera.position.set(cameraPosX, cameraPosY, 0);

//        miniMapCamera.zoom = 2.5f;
        miniMapCamera.zoom = 1f;
        miniMapCamera.update();

        System.out.println("w-h: " + Gdx.graphics.getWidth());

//        setBounds(1600, 900, width, height); // Đặt minimap ở góc màn hình

        scissorBounds = new Rectangle(Gdx.graphics.getWidth() - size, Gdx.graphics.getHeight() - size, size, size);

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        ScissorStack.pushScissors(scissorBounds);
        miniMapCamera.update();
        tiledMapRenderer.setView(miniMapCamera);
        tiledMapRenderer.render();
        drawPlayer(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
        ScissorStack.popScissors();
        drawCircle();
        drawBorder();
        batch.begin();
    }

    private void drawPlayer(Vector2 playerPosition) {
        float miniMapX = playerPosition.x *0.1f + scissorBounds.x;
        float miniMapY = playerPosition.y *0.1f + scissorBounds.y;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(miniMapX, miniMapY, 5); // Chấm đỏ 5px
        shapeRenderer.end();
    }

    private void drawCircle(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // Kiểu Filled để vẽ hình tròn đặc
        shapeRenderer.setColor(1, 0, 0, 1); // Màu đỏ (RGBA)
        shapeRenderer.circle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 50); // Vẽ hình tròn tại (200,200) với bán kính 50
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        tiledMapRenderer.dispose();
    }

    private void handleInput() {
        float speed = 100 * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) miniMapCamera.position.x -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) miniMapCamera.position.x += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) miniMapCamera.position.y += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) miniMapCamera.position.y -= speed;

        System.out.println("camera x-y: " + miniMapCamera.position);
    }

    private void drawBorder() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE); // Màu viền trắng
        shapeRenderer.rect(scissorBounds.x, scissorBounds.y, scissorBounds.width, scissorBounds.height);
        shapeRenderer.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.handleInput();
    }
}

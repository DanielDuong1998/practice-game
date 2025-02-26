package com.redrock.practice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.redrock.Main;

public class TilemapActor extends Actor implements DisposableActor{
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private int mapPixelWidth, mapPixelHeight;

    public TilemapActor(String tmxFile, OrthographicCamera camera) {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
        tiledMap = new TmxMapLoader().load(tmxFile);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1); // Tùy chỉnh scale nếu cần

        int mapWidth = tiledMap.getProperties().get("width", Integer.class);
        int mapHeight = tiledMap.getProperties().get("height", Integer.class);

        int tileWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
        int tileHeight = tiledMap.getProperties().get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tileWidth;
        mapPixelHeight = mapHeight * tileHeight;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Kết thúc batch hiện tại trước khi render bản đồ
        batch.end();

        // Cập nhật camera
        camera.update();
        tiledMapRenderer.setView(camera);

        // Render bản đồ
        tiledMapRenderer.render();

        // Bắt đầu lại batch sau khi render bản đồ
        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Thêm logic cập nhật nếu cần

        this.handleInput();
    }

    private void handleInput() {
        float speed = 1000 * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= speed;
            camera.position.x = Math.max(camera.position.x, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += speed;
            camera.position.x = Math.min(camera.position.x, mapPixelWidth + camera.viewportWidth/2);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y += speed;
            camera.position.y = Math.min(camera.position.y, mapPixelHeight + camera.viewportHeight/2);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y -= speed;
            camera.position.y = Math.max(camera.position.y, 0);
        }
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
    }

    public TiledMap getTiledMap(){return tiledMap;}

    public int getMapPixelWidth(){return mapPixelWidth;}
    public int getMapPixelHeight(){return mapPixelHeight;}

    public OrthographicCamera getCamera(){return this.camera;}
}
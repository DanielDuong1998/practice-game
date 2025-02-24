package com.redrock.practice;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TilemapActor extends Actor implements DisposableActor{
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;

    public TilemapActor(String tmxFile, OrthographicCamera camera) {
        this.camera = camera;
        tiledMap = new TmxMapLoader().load(tmxFile);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,0.1f); // Tùy chỉnh scale nếu cần
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
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
    }

    public TiledMap getTiledMap(){return tiledMap;}
}
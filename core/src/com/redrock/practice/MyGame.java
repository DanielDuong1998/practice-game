package com.redrock.practice;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    @Override
    public void create() {
        // Tạo camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Load bản đồ
        map = new TmxMapLoader().load("map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1); // Scale tiles (nếu map có tile 32x32)
    }

    @Override
    public void render() {
        // Xóa màn hình
        ScreenUtils.clear(0, 0, 0, 1);

        // Cập nhật camera
        camera.update();
        mapRenderer.setView(camera);

        // Render bản đồ
        mapRenderer.render();
        handleInput();
    }

    private void handleInput() {
        float speed = 100 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.position.x -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.position.x += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.position.y += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.position.y -= speed;
    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
    }
}

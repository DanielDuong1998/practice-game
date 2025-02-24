package com.redrock.practice;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMapHandler {
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    public TiledMapHandler(String mapPath) {
        map = new TmxMapLoader().load(mapPath);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 2f); // Scale bản đồ
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public TiledMap getMap(){return this.map;}

    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
    }
}
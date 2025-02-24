package com.redrock.practice;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

public class TileMapDebug {
    private ShapeRenderer shapeRenderer;

    public TileMapDebug() {
        shapeRenderer = new ShapeRenderer();
    }

    public void render(TiledMap map, OrthographicCamera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1); // Màu đỏ để dễ nhìn

        for (MapLayer layer : map.getLayers()) {
            MapObjects objects = layer.getObjects();
            for (RectangleMapObject obj : objects.getByType(RectangleMapObject.class)) {
                Rectangle rect = obj.getRectangle();
                shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            }
        }
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}

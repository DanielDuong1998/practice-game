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
        this.tiledMap = tiledMap;
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 0.05f);

        miniMapCamera = new OrthographicCamera();
        miniMapCamera.setToOrtho(false, width, height);

        // Căn chỉnh camera minimap để hiển thị toàn bộ map
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        float mapWidth = layer.getWidth() * layer.getTileWidth();
        float mapHeight = layer.getHeight() * layer.getTileHeight();
//        System.out.println("mapHeight: " + mapHeight);
//        miniMapCamera.position.set(mapWidth / 2, mapHeight / 2, 0);
        miniMapCamera.position.set(0, 0, 0);
//        miniMapCamera.zoom = 2.5f;
        miniMapCamera.zoom = 3f;
        miniMapCamera.update();

//        setBounds(1600, 900, width, height); // Đặt minimap ở góc màn hình

        float size = 200; // 150x150 pixels
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
        ScissorStack.popScissors();
        drawBorder();
        batch.begin();
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

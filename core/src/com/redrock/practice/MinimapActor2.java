package com.redrock.practice;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.redrock.Main;

public class MinimapActor2 extends Actor implements DisposableActor{
    private final TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private OrthographicCamera miniMapCamera;
    private Rectangle scissorBounds;
    private ShapeRenderer shapeRenderer;
    private float cameraStartPosX, cameraStartPosY;

    private TextureRegion fishTg;



    public MinimapActor2(TiledMap tiledMap, float width, float height) {
        float size = 200; // 150x150 pixels

//        System.out.println("wi height2: " + width + "-" + height);


        this.tiledMap = tiledMap;
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 0.1f);

        miniMapCamera = new OrthographicCamera();
        miniMapCamera.setToOrtho(false, width, height);

        cameraStartPosX = -width/2 + size;
        cameraStartPosY = -height/2 + size;
        miniMapCamera.position.set(cameraStartPosX, cameraStartPosY, 0);

        miniMapCamera.zoom = 1f;
        miniMapCamera.update();


        scissorBounds = new Rectangle(Gdx.graphics.getWidth() - size, Gdx.graphics.getHeight() - size, size, size);

        shapeRenderer = new ShapeRenderer();
        this.fishTg = Main.asset().getTG("fish");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();

//        batch.setProjectionMatrix(miniMapCamera.combined);

//        ScissorStack.pushScissors(scissorBounds);

        miniMapCamera.update();
        tiledMapRenderer.setView(miniMapCamera);
        tiledMapRenderer.render();
        drawPlayer(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2));
        drawPlayer(new Vector2(0, 0));

        drawCircle();
        drawBorder();
        batch.begin();

        batch.draw(this.fishTg, 0, 0, this.fishTg.getRegionWidth()*0.1f, this.fishTg.getRegionHeight()*0.1f);

//        ScissorStack.popScissors();
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

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // Kiểu Filled để vẽ hình tròn đặc
        shapeRenderer.setColor(1, 0, 0, 1); // Màu đỏ (RGBA)
        shapeRenderer.circle(0, 0, 50); // Vẽ hình tròn tại (200,200) với bán kính 50
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        tiledMapRenderer.dispose();
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

    private void drawBorder() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(scissorBounds.x, scissorBounds.y, scissorBounds.width, scissorBounds.height);
        shapeRenderer.end();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        this.handleInput();
    }
}

package com.redrock.viewModule;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.redrock.Main;

public class StartModule extends Actor {

  ShapeRenderer shapeRenderer;
  ShaderProgram shader;
  TextureRegion rg, board;

  Texture tex;

  Batch batch;
  FrameBuffer fbo;

  public StartModule() {
    testMasking();
    batch = Main.getStage().getBatch();

    rg    = Main.asset().getTG("btn_blue");
    board = Main.asset().getTG("frm_board");
//    texBoard.bind(1);

    setupShader();
  }

  private void setupShader() {
    FileHandle vertexShader = Gdx.files.internal("shader/test/vertex.glsl");
    FileHandle fragmentShader = Gdx.files.internal("shader/test/fragment.glsl");

    ShaderProgram.pedantic = false;

    shader = new ShaderProgram(vertexShader, fragmentShader);
    if (!shader.isCompiled()) {
      Gdx.app.log("Shader", shader.getLog());
      Gdx.app.exit();
    }

    /* Tell our shader that u_texture will be in the TEXTURE0 spot and
     * u_mask will be in the TEXTURE1 spot. We can set these now since
     * they'll never change; we don't have to send them every render frame. */
    shader.bind();
    shader.setUniformi("u_texture", 0);
//    shader.setUniformi("u_board", 1);
  }

  private void testMasking() {
    shapeRenderer = new ShapeRenderer();
    shapeRenderer.setAutoShapeType(true);

    /* Increase the OpenGL line thickness for better visualization. */
    Gdx.gl.glLineWidth(2);
  }

  private void drawMasked() {
    /* To activate the scissor test, first enable the GL_SCISSOR_TEST enumerator.
     * Once enabled, pixels outside of the scissor box will be discarded. */
    Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);

    /* To define the scissor box, use this function: */
    Gdx.gl.glScissor(100, 100, 200, 200);
    /* The x and y is the window-space lower-left position of the scissor box,
     * and width and height define the size of the rectangle. */

    /* Draw our circle to be masked, we could also draw sprites with a SpriteBatch. */
    shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(Color.RED);
    shapeRenderer.circle(100, 100, 100);

    /* Remember to flush before changing GL states again. */
    shapeRenderer.flush();

    /* Deactivate the scissor test before continuing with further rendering operations. */
    Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
  }

  private void drawContours() {
    shapeRenderer.set(ShapeRenderer.ShapeType.Line);

    /* Draw the circle's contour for comparison. */
    shapeRenderer.setColor(Color.GREEN);
    shapeRenderer.circle(100, 100, 100);

    /* Draw the clipped area contour for comparison. */
    shapeRenderer.setColor(Color.CYAN);
    shapeRenderer.rect(100, 100, 200, 200);
  }

  private boolean isDrawWithFramebuffer() {
    return tex == null;
  }

  private Texture makeTex(Batch batch) {
    FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int) Main.getStage().getWidth(),
        (int) Main.getStage().getHeight(), false);

    fbo.begin();
    batch.draw(rg, 0, 0);
    fbo.end();

    Texture tex = fbo.getColorBufferTexture();
    tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    return tex;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
//    batch.setShader(shader);
//    fbo.begin();
//    batch.draw(rg, Main.getStage().getWidth() / 2f - rg.getRegionWidth() / 2f,
//        Main.getStage().getHeight() / 2f - rg.getRegionHeight() / 2f);
//    fbo.end();
//
//    Texture t = fbo.getColorBufferTexture();
//    t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
//    batch.draw(t, 0, 0);

    if (isDrawWithFramebuffer())
      tex = makeTex(batch);

    batch.draw(tex, 0, 0);
  }
}

package com.redrock.sdk.wheel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.*;
import com.redrock.Main;

import java.util.HashMap;
import java.util.Map;

public class Wheel extends Group {

  public interface IListener {
    void comeOut(int prize, String textPrize);
  }

  public static String                    WHEEL_TICK                      = "wheel_tick";
  public static TextureRegion             wheelTex                        = Main.asset().getTG("wheel");
  public static TextureRegion             pointer                         = Main.asset().getTG("pointer");
  public static TextureRegion             axis                            = Main.asset().getTG("axis");
  public static BitmapFont                bitmapFont                      = Main.asset().getBMFont("white_border_brown");

  public static int                       PARTITION                       = 8;
  public static float                     DIS_TO_CENTER                   = .8f;                                  //khoảng cách từ item đến tâm
  public static float                     ANGULAR_FLOAT_POINT             = 0.6f;
  public static float                     WHEEL_ARC                       = 0;
  public static float                     SCL_ITEM                        = .7f;
  public static float                     TEXT_SPACE                      = 7.5f;

  public final static boolean POINTER_ON_TOP  = false;
  public final static boolean RENDER_AXIS     = false;

  private final static String RG_BULB        = "bulb";
  private final static String RG_LIGHT_BULB  = "light";

  private Map<Integer, WheelItem>         mItems;
  private Array<Image>                    lightBulb;

  private Vector2                         cw;
  private IListener                       iListener;

  private float                           agreePre;

  public Wheel(String wheelData) {
    setupItems(wheelData);
    init();
  }

  private int getIdItem() {
    return (int) Math.round(Math.random() * PARTITION);
  }

  public void init() {
    combine();
    setSize(wheelTex.getRegionWidth(), wheelTex.getRegionHeight());
    setOrigin(Align.center);

    drawLightBulb();
    effLightBulb(0);
  }

  private void setupItems(String js) {
    mItems              = new HashMap<>();
    JsonReader jsReader = new JsonReader();

    int idx = 0;
    for (JsonValue value : jsReader.parse(js)) {
      int     qty     = value.get("qty").asInt();
      String  name    = value.get("name").asString();
      String  title   = value.get("title").asString();
      String  rg      = value.get("rg").asString();

      mItems.put(idx, WheelItem.newInst(idx, qty, name, title, rg, Main.asset().getTG(rg)));
      idx++;
    }

    WHEEL_ARC = 360f / mItems.size();
    cw        = new Vector2(wheelTex.getRegionWidth() / 2.f, wheelTex.getRegionHeight() / 2.f);
  }

  private void drawLightBulb() {
    Image wheel = new Image(wheelTex);
    addActor(wheel);

    //draw bulb
    float w = wheel.getWidth();
    float h = wheel.getHeight();

    lightBulb = new Array<>();
    for (int i = 0; i < PARTITION; i++) {
      Image bulb  = new Image(Main.asset().getTG(RG_BULB));
      Image light = new Image(Main.asset().getTG(RG_LIGHT_BULB));

      Vector2 pos = new Vector2(0, h * .97f/2).rotate(i * WHEEL_ARC + WHEEL_ARC/2).add(w/2, h/2);
      Vector2 pb  = pos.cpy().sub(bulb.getWidth()/2, bulb.getHeight()/2);
      Vector2 pl  = pos.cpy().sub(light.getWidth()/2, light.getHeight()/2);

      bulb.setPosition(pb.x, pb.y);
      light.setPosition(pl.x, pl.y);

      addActor(bulb);
      lightBulb.add(light);
    }
  }

  private void effLightBulb(int t) {
    if (t % 2 == 0) {
      for (int i = 0; i < lightBulb.size; i++) {
        if (i % 2 == 0)
          addActor(lightBulb.get(i));
        else
          lightBulb.get(i).remove();
      }
    }

    if (t % 2 != 0) {
      for (int i = 0; i < lightBulb.size; i++) {
        if (i % 2 != 0)
          addActor(lightBulb.get(i));
        else
          lightBulb.get(i).remove();
      }
    }

    if (t == 99)
      t = -1;

    final int tt = t;
    addAction(
        Actions.sequence(
            Actions.delay(.5f),
            Actions.run(() -> effLightBulb(tt + 1))
        )
    );
  }

  public void setIListener(IListener iListener) {
    this.iListener = iListener;
  }

  public void robin() {
    int   co      = (int) Math.round(Math.random() * (PARTITION - 1));
    float rollDeg = 360 * 5 - co * WHEEL_ARC;

    setRotation(0);
    agreePre = 0;

    addAction(
        Actions.sequence(
            Actions.rotateTo(rollDeg, 3f, Interpolation.fastSlow),
            Actions.run(() -> iListener.comeOut(mItems.get(co).qty, mItems.get(co).title))
        )
    );
  }

  @Override
  public void act(float delta) {
    super.act(delta);

    if (getRotation() - agreePre >= 30) {
      agreePre = getRotation();

      Main.soundMgr().playSound(WHEEL_TICK);
    }
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);

    float w   = wheelTex.getRegionWidth();
    float h   = wheelTex.getRegionHeight();
    float wa  = axis.getRegionWidth();
    float ha  = axis.getRegionHeight();
    float pw  = pointer.getRegionWidth();
    float ph  = pointer.getRegionHeight();

    if (RENDER_AXIS)
      batch.draw(axis, getX() + w/2 - wa/2, getY() + h/2 - ha/2, wa, ha);

    batch.draw(pointer, getX() + w/2 - pw/2, getY() + (POINTER_ON_TOP ? h/2 - 25 : h - ph / 2), pw, ph);
  }

  private void combine() {
    int             w               = wheelTex.getRegionWidth();
    int             h               = wheelTex.getRegionHeight();

    Matrix4         project         = new Matrix4();
    Batch           batch           = new SpriteBatch();
    FrameBuffer     fbo             = new FrameBuffer(Pixmap.Format.RGBA8888, w, h, false);
    Array<Vector2>  itemsPos        = calcPosItemsOnWheel();

    project.setToOrtho2D(0, 0, w, h);
    batch.setProjectionMatrix(project);

    batch.begin();
    fbo.begin();
    batch.draw(wheelTex, 0, 0);

    //render item
    for (int i = 0; i < PARTITION; i++) {
      TextureRegion rg  = mItems.get(i).rg;
      int           iw  = rg.getRegionWidth();
      int           ih  = rg.getRegionHeight();
      Vector2       pos = itemsPos.get(i);

      batch.draw(rg, pos.x, pos.y, iw / 2.f, ih / 2.f, iw, ih, SCL_ITEM, SCL_ITEM, i * WHEEL_ARC);
    }

    //render text
    for (Map.Entry<Integer, WheelItem> item : mItems.entrySet()) {
      WheelItem it  = item.getValue();
      int       idx = item.getKey();

      AngularSpriteFont angularSpriteFont = new AngularSpriteFont(bitmapFont, it.title + "");
      angularSpriteFont.draw(batch, idx * WHEEL_ARC);
    }

    batch.end();
    fbo.end();
    batch.dispose();

    // recalculate viewport when fbo ended.
    Gdx.app.getApplicationListener().resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    Texture tex = fbo.getColorBufferTexture();
    tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    wheelTex = new TextureRegion(tex);
    wheelTex.flip(false, true);
  }

  private Array<Vector2> calcPosItemsOnWheel() {
    Vector2         roller  = Vector2.Zero.setAngle(0).set(0, wheelTex.getRegionHeight() * DIS_TO_CENTER / 2);
    Array<Vector2>  poses   = new Array<>();

    for (Map.Entry<Integer, WheelItem> item : mItems.entrySet()) {
      int       idx = item.getKey();
      WheelItem wi  = item.getValue();
      Vector2   pos = roller.cpy().rotate(idx * WHEEL_ARC).add(cw).sub(wi.rg.getRegionWidth() / 2.f, wi.rg.getRegionHeight() / 2.f);

      poses.add(pos);
    }

    return poses;
  }

  //-----------------------------------SPRITE-FONT----------------------------------------------------
  public static class AngularSpriteFont {
    Array<Sprite> sprites;
    BitmapFont    bitmapFont;
    String        text;

    protected AngularSpriteFont(BitmapFont bitmapFont, String text) {
      this.bitmapFont = bitmapFont;
      this.text       = text;

      make();
    }

    private Sprite charToSprite(char ch) {
      BitmapFont.Glyph glyph = bitmapFont.getData().getGlyph(ch);
      Sprite s = new Sprite(bitmapFont.getRegion().getTexture(), glyph.srcX, glyph.srcY, glyph.width, glyph.height);
      s.setOrigin(glyph.width / 2.f, glyph.height / 2.f);

      return s;
    }

    private void make() {
      sprites = new Array<>();
      for (char ch : text.toCharArray())
        sprites.add(charToSprite(ch));
    }

    protected void draw(Batch batch, float angle) {
      int     w       = wheelTex.getRegionWidth();
      int     h       = wheelTex.getRegionHeight();
      float   align   = angle + ((sprites.size - 1) / 2.f) * TEXT_SPACE;

      Vector2 roller  = (new Vector2()).set(0, h * ANGULAR_FLOAT_POINT / 2.f);
      Vector2 cp      = Vector2.Zero.set(w/2.f, h/2.f);

      for (Sprite s : sprites) {
        Vector2 p = roller.cpy().rotate(align).add(cp).sub(s.getRegionWidth()/2.f, s.getRegionHeight()/2.f);
        s.setPosition(p.x, p.y);
        s.rotate(angle);
        s.draw(batch);

        align -= TEXT_SPACE;
      }
    }
  }

  //------------------------------------wheel-item-----------------------------------------------------
  public static class WheelItem {
    public int            id;
    public int            qty;

    public String         name;
    public String         title;
    public String         keyRg;

    TextureRegion         rg;

    public static WheelItem newInst(int id, int qty, String name, String title, String keyRg, TextureRegion rg) {
      WheelItem inst = new WheelItem();

      inst.id     = id;
      inst.qty    = qty;
      inst.name   = name;
      inst.title  = title;
      inst.rg     = rg;
      inst.keyRg  = keyRg;

      return inst;
    }
  }
}

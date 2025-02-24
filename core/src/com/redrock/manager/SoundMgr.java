package com.redrock.manager;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.redrock.Main;

import java.util.HashMap;
import java.util.Map;

public class SoundMgr {

  public final static String BTN_CLICK = "btn_click";

  private Map<String, Float>  mVolume;

  public SoundMgr() {
    mVolume = new HashMap<>();
  }

  private void pauseMusic(String key) {
    if (!Main.isWebGL()) {
      Music music = Main.asset().getMusic(key);
      music.stop();
    }
    else
      stopSoundH5(key);
  }

  public void playSound(String key) {
    if (Main.session().profile.muteSound)
      return;

    if (Main.isWebGL())
      playSoundH5(key);
    else
      playSoundCore(key);
  }

  public void playMusic(String key) {
    if (Main.session().profile.muteSound) {
      pauseMusic(key);

      return;
    }

    if (!Main.isWebGL()) {
      Music music = Main.asset().getMusic(key);

      music.setVolume(1f);
      music.setLooping(true);
      music.play();
    }
    else
      playSoundH5(key);
  }

  private void playSoundCore(String key) {
    Sound sound = Main.asset().getSound(key);

    sound.stop();
    sound.play();
  }

  private static native void playSoundH5(String key)/*-{
    $wnd.sounds[key].play();
  }-*/;

  private static native void stopSoundH5(String key)/*-{
    $wnd.sounds[key].stop();
  }-*/;

  public static native void loadSoundH5(String key, String md5Name, boolean loop, boolean autoPlay)/*-{
    if (!$wnd.sounds) {
      $wnd.sounds = [];
    }

    var sound = new $wnd.Howl({
        src: [md5Name],
        autoplay: autoPlay,
        loop: loop
    });
    sound.once('load', function() {
      $wnd.console.log("sound " + md5Name + " loaded");
      $wnd.sounds[key.replace('.mp3', '')] = sound;
    });
  }-*/;
}
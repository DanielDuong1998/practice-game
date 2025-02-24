package com.redrock.sdk.model.dto;

import com.redrock.Main;

import java.util.Date;

public class Profile {
  public String   name;
  public String   token;
  public String   language;

  public int      spinCount;
  public long     dayGetSpinCount;
  public boolean  hadGetSpinCountToDay;

  public int timeWatchAdsGetTurn;

  public long coin;

  public long dayGetDailyReward;
  public int  countGotDailyReward;

  public long dayStartWatchAds;
  public int  watchAdsCount;

  public long dayStartShowFullscreen;
  public int  fullscreenCount;

  public long id;

  public boolean muteMusic;
  public boolean muteSound;
  public boolean newbie;
  public boolean finishedTutorial;

  public static Profile ofDefault() {
    Profile p   = new Profile();
    p.id        = (System.currentTimeMillis() << 20) | (System.currentTimeMillis() & ~9223372036854251520L);
    p.name      = "Player";
    p.token     = "";
    p.language  = "EN";

    p.coin = 1000;

    p.spinCount             = 0;
    p.hadGetSpinCountToDay  = false;

    p.countGotDailyReward = 0;
    p.dayGetDailyReward   = -1;

    p.dayStartWatchAds  = 0;
    p.watchAdsCount     = 0;

    p.dayStartShowFullscreen  = 0;
    p.fullscreenCount         = 0;

    p.muteMusic         = false;
    p.muteSound         = false;
    p.newbie            = true;
    p.finishedTutorial  = false;

    return p;
  }

  public void resetDailyReward() {
    countGotDailyReward = 0;
    dayGetDailyReward   = -1;
  }

  public void getSpinCountToDay() {
    long newDay = checkNewDay(dayGetSpinCount);

    if (newDay > -1) {
      dayGetSpinCount  = newDay;
      spinCount        = 3;
    }
  }

  public long checkNewDay(long dayCheck) {
    Date oldDate  = new Date(dayCheck);
    Date toDate   = new Date();

    int oldDay    = oldDate.getDay();
    int oldMonth  = oldDate.getMonth();
    int oldYear   = oldDate.getYear();

    int toDay   = toDate.getDay();
    int toMonth = toDate.getMonth();
    int toYear  = toDate.getYear();

    if (toDay != oldDay || toMonth != oldMonth || toYear != oldYear)
      return toDate.getTime();

    return -1;
  }

  public void resetWatchAdsCounting() {
    long newDay = checkNewDay(dayStartWatchAds);

    if (newDay != -1) {
      dayStartWatchAds  = newDay;
      watchAdsCount     = 0;
    }
  }

  public boolean canWatchAds() {
    Date date = new Date();

    if (date.getTime() - dayStartWatchAds >= Main.remoteConfig().getTimeLimitAds() * 3600 * 1000) {
      watchAdsCount     = 0;
      dayStartWatchAds  = date.getTime();

      return true;
    }

    return watchAdsCount < Main.remoteConfig().getCountLimitReward();
  }

  public boolean canShowFullscreen() {
    Date date = new Date();

    if (date.getTime() - dayStartShowFullscreen >= Main.remoteConfig().getTimeLimitAds() * 3600 * 1000) {
      fullscreenCount         = 0;
      dayStartShowFullscreen  = date.getTime();

      return true;
    }

    return fullscreenCount < Main.remoteConfig().getCountLimitShowFullscreen();
  }

  public void reBalance() {
    if (name == null)
      name = "Player";
  }

  @Override
  public String toString() {
    return "Profile{" +
        "name='" + name + '\'' +
        ", token='" + token + '\'' +
        ", language='" + language + '\'' +
        ", spinCount=" + spinCount +
        ", dayGetSpinCount=" + dayGetSpinCount +
        ", hadGetSpinCountToDay=" + hadGetSpinCountToDay +
        ", timeWatchAdsGetTurn=" + timeWatchAdsGetTurn +
        ", coin=" + coin +
        ", dayGetDailyReward=" + dayGetDailyReward +
        ", countGotDailyReward=" + countGotDailyReward +
        ", id=" + id +
        ", muteSound=" + muteSound +
        ", newbie=" + newbie +
        '}';
  }
}

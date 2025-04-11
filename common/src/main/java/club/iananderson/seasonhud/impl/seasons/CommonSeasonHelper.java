package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.seasons.mods.FabricSeasonsHelper;
import club.iananderson.seasonhud.impl.seasons.mods.IModHelper;
import club.iananderson.seasonhud.impl.seasons.mods.SereneSeasonsHelper;
import club.iananderson.seasonhud.impl.seasons.mods.TerrafirmaCraftHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class CommonSeasonHelper implements IModHelper {
  //Todo -- Move all to switch statement?

  private CommonSeasonHelper() {
  }

  public static CommonSeasonHelper commonSeasons = new CommonSeasonHelper();
  public static FabricSeasonsHelper fabricSeasons = new FabricSeasonsHelper();
  public static SereneSeasonsHelper sereneSeasons = new SereneSeasonsHelper();
  public static TerrafirmaCraftHelper terrafirmaCraft = new TerrafirmaCraftHelper();


  public boolean isTropicalSeason(Player player) {
    if (Common.sereneSeasonsLoaded()) {
      return sereneSeasons.isTropicalSeason(player);
    }
    else {
      return false;
    }
  }


  public boolean isSeasonTiedWithSystemTime() {
    if (Common.fabricSeasonsLoaded()) {
      return fabricSeasons.isSeasonTiedWithSystemTime();
    }
    else {
      return false;
    }
  }


  public String getCurrentSubSeason(Player player) {
    String subSeason = "MID_NULL"; // Just in case

    if (Common.fabricSeasonsLoaded()) {
      subSeason = fabricSeasons.getCurrentSubSeason(player);
    }

    if (Common.sereneSeasonsLoaded()) {
      subSeason = sereneSeasons.getCurrentSubSeason(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      subSeason = terrafirmaCraft.getCurrentSubSeason(player);
    }
    return subSeason;
  }

  public String getCurrentSeason(Player player) {
    String season = "NULL"; // Just in case

    if (Common.fabricSeasonsLoaded()) {
      season = fabricSeasons.getCurrentSeason(player);
    }

    if (Common.sereneSeasonsLoaded()) {
      season = sereneSeasons.getCurrentSeason(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      season = terrafirmaCraft.getCurrentSeason(player);
    }
    return season;
  }

  /**
   * Gets the current season's file name for the platform.
   *
   * @return The current season's file name for the platform.
   */
  public String getSeasonFileName(Player player) {
    return commonSeasons.getCurrentSeason(player).toLowerCase();
  }

  /**
   * Gets the current day of the season/sub-season.
   *
   * @return The current day of the season/sub-season.
   */
  public long getDate(Player player) {
    long date = 0; // Just in case

    if (Common.fabricSeasonsLoaded()) {
      date = fabricSeasons.getDate(player);
    }

    if (Common.sereneSeasonsLoaded()) {
      date = sereneSeasons.getDate(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      date = terrafirmaCraft.getDate(player);
    }
    return date;
  }

  public int seasonDuration(Player player) {
    int duration = 0; // Just in case

    if (Common.fabricSeasonsLoaded()) {
      duration = fabricSeasons.seasonDuration(player);
    }

    if (Common.sereneSeasonsLoaded()) {
      duration = sereneSeasons.seasonDuration(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      duration = terrafirmaCraft.seasonDuration(player);
    }

    return duration;
  }


  public Item CALENDAR() {
    Item calendar = null;

    if (Common.fabricSeasonsLoaded() && Common.calendarLoaded()) {
      calendar = fabricSeasons.CALENDAR();
    }

    if (Common.sereneSeasonsLoaded()) {
      calendar = sereneSeasons.CALENDAR();
    }

    if (Common.terrafirmacraftLoaded()) {
      calendar = terrafirmaCraft.CALENDAR();
    }

    return calendar;
  }
}

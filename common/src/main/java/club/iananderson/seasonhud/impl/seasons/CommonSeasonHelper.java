package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.mods.FabricSeasonsHelper;
import club.iananderson.seasonhud.impl.seasons.mods.SereneSeasonsHelper;
import club.iananderson.seasonhud.impl.seasons.mods.TerrafirmaCraftHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class CommonSeasonHelper {
  private CommonSeasonHelper() {
  }

  //Todo -- Move all to switch statement?

  /**
   * Checks if the tropical season should be displayed (SereneSeasons only). Always false for FabricSeasons.
   *
   * @return If the tropical season should be displayed for the platform.
   */
  public static boolean isTropicalSeason(Player player) {
    if (Common.sereneSeasonsLoaded()) {
      return SereneSeasonsHelper.isTropicalSeason(player);
    }
    else {
      return false;
    }
  }

  /**
   * Checks if "isSeasonTiedWithSystemTime" config option is enabled (FabricSeasons only). Always false for
   * SereneSeasons.
   *
   * @return If "isSeasonTiedWithSystemTime" config option is enabled for the platform.
   */
  public static boolean isSeasonTiedWithSystemTime() {
    if (Common.fabricSeasonsLoaded()) {
      return FabricSeasonsHelper.isSeasonTiedWithSystemTime();
    }
    else {
      return false;
    }
  }

  /**
   * Gets the name of the current sub-season for the platform (if applicable).
   *
   * @return The name of the current season for the platform.
   */
  public static String getCurrentSubSeason(Player player) {
    String subSeason = "MID_NULL"; // Just in case

    if (Common.fabricSeasonsLoaded()) {
      subSeason = FabricSeasonsHelper.getCurrentSubSeason(player);
    }

    if (Common.sereneSeasonsLoaded()) {
      subSeason = SereneSeasonsHelper.getCurrentSubSeason(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      subSeason = TerrafirmaCraftHelper.getCurrentSubSeason(player);
    }
    return subSeason;
  }

  /**
   * Gets the name of the current season for the platform.
   *
   * @return The name of the current season for the platform.
   */
  public static String getCurrentSeason(Player player) {
    String season = "NULL"; // Just in case

    if (Common.fabricSeasonsLoaded()) {
      season = FabricSeasonsHelper.getCurrentSeason(player);
    }

    if (Common.sereneSeasonsLoaded()) {
      season = SereneSeasonsHelper.getCurrentSeason(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      season = TerrafirmaCraftHelper.getCurrentSeason(player);
    }
    return season;
  }

  /**
   * Gets the current season's file name for the platform.
   *
   * @return The current season's file name for the platform.
   */
  public static String getSeasonFileName(Player player) {
    return CommonSeasonHelper.getCurrentSeason(player).toLowerCase();
  }

  /**
   * Gets the current day of the season/sub-season.
   *
   * @return The current day of the season/sub-season.
   */
  public static long getDate(Player player) {
    long date = 0; // Just in case

    if (Common.fabricSeasonsLoaded()) {
      date = FabricSeasonsHelper.getDate(player);
    }

    if (Common.sereneSeasonsLoaded()) {
      date = SereneSeasonsHelper.getDate(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      date = TerrafirmaCraftHelper.getDate(player);
    }
    return date;
  }

  /**
   * Checks the duration of the current season/sub-season.
   *
   * @return The duration of the current season/sub-season.
   */
  public static int seasonDuration(Player player) {
    int duration = 0; // Just in case

    if (Common.fabricSeasonsLoaded()) {
      duration = FabricSeasonsHelper.seasonDuration(player);
    }

    if (Common.sereneSeasonsLoaded()) {
      duration = SereneSeasonsHelper.seasonDuration(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      duration = TerrafirmaCraftHelper.seasonDuration(player);
    }

    return duration;
  }

  /**
   * @return The calendar item for the loaded season mod.
   */
  public static Item calendar() {
    Item calendar = null;

    if (Common.fabricSeasonsLoaded() && Common.extrasLoaded()) {
      calendar = FabricSeasonsHelper.CALENDAR();
    }

    if (Common.sereneSeasonsLoaded()) {
      calendar = SereneSeasonsHelper.CALENDAR;
    }

    if (Common.terrafirmacraftLoaded()) {
      calendar = TerrafirmaCraftHelper.CALENDAR;
    }

    return calendar;
  }
}

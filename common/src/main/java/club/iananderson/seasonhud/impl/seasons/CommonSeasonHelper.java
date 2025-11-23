package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.seasons.mods.EclipticSeasonsHelper;
import club.iananderson.seasonhud.impl.seasons.mods.FabricSeasonsHelper;
import club.iananderson.seasonhud.impl.seasons.mods.ISeasonModHelper;
import club.iananderson.seasonhud.impl.seasons.mods.SereneSeasonsHelper;
import club.iananderson.seasonhud.impl.seasons.mods.TerrafirmaCraftHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class CommonSeasonHelper implements ISeasonModHelper {
  //Todo -- Move all to switch statement?

  public static CommonSeasonHelper commonSeasons = new CommonSeasonHelper();
  public static FabricSeasonsHelper fabricSeasons = new FabricSeasonsHelper();
  public static SereneSeasonsHelper sereneSeasons = new SereneSeasonsHelper();
  public static TerrafirmaCraftHelper terrafirmaCraft = new TerrafirmaCraftHelper();
  public static EclipticSeasonsHelper eclipticSeasons = new EclipticSeasonsHelper();

  private CommonSeasonHelper() {
  }

  @Override
  public boolean isTropicalSeason(Player player) {
    if (Common.sereneSeasonsLoaded()) {
      return sereneSeasons.isTropicalSeason(player);
    }
    else {
      return false;
    }
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    if (Common.fabricSeasonsLoaded()) {
      return fabricSeasons.isSeasonTiedWithSystemTime();
    }
    else {
      return false;
    }
  }

  @Override
  public String getCurrentSubSeason(Player player) {
    String subSeason = "MID_NULL"; // Just in case

    if (Common.fabricSeasonsLoaded()) {
      subSeason = fabricSeasons.getCurrentSubSeason(player);
    }

    if (Common.sereneSeasonsLoaded() && !Common.eclipticSeasonsLoaded()) {
      subSeason = sereneSeasons.getCurrentSubSeason(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      subSeason = terrafirmaCraft.getCurrentSubSeason(player);
    }

    if (Common.eclipticSeasonsLoaded()) {
      subSeason = eclipticSeasons.getCurrentSubSeason(player);
    }
    return subSeason;
  }

  @Override
  public String getCurrentSeason(Player player) {
    String season = "NULL"; // Just in case

    if (Common.fabricSeasonsLoaded()) {
      season = fabricSeasons.getCurrentSeason(player);
    }

    if (Common.sereneSeasonsLoaded() && !Common.eclipticSeasonsLoaded()) {
      season = sereneSeasons.getCurrentSeason(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      season = terrafirmaCraft.getCurrentSeason(player);
    }

    if (Common.eclipticSeasonsLoaded()) {
      season = eclipticSeasons.getCurrentSeason(player);
    }

    return season;
  }

  @Override
  public long getDate(Player player) {
    long date = 0; // Just in case

    if (Common.fabricSeasonsLoaded()) {
      date = fabricSeasons.getDate(player);
    }

    if (Common.sereneSeasonsLoaded() && !Common.eclipticSeasonsLoaded()) {
      date = sereneSeasons.getDate(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      date = terrafirmaCraft.getDate(player);
    }

    if (Common.eclipticSeasonsLoaded()) {
      date = eclipticSeasons.getDate(player);
    }

    return date;
  }

  @Override
  public int seasonDuration(Player player) {
    int duration = 0; // Just in case

    if (Common.fabricSeasonsLoaded()) {
      duration = fabricSeasons.seasonDuration(player);
    }

    if (Common.sereneSeasonsLoaded() && !Common.eclipticSeasonsLoaded()) {
      duration = sereneSeasons.seasonDuration(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      duration = terrafirmaCraft.seasonDuration(player);
    }

    if (Common.eclipticSeasonsLoaded()) {
      duration = eclipticSeasons.seasonDuration(player);
    }

    return duration;
  }

  @Override
  public Item CALENDAR() {
    Item calendar = null;

    if (Common.fabricSeasonsLoaded() && Common.hasCalendarLoaded()) {
      calendar = fabricSeasons.CALENDAR();
    }

    if (Common.sereneSeasonsLoaded()) {
      calendar = sereneSeasons.CALENDAR();
    }

    if (Common.terrafirmacraftLoaded()) {
      calendar = terrafirmaCraft.CALENDAR();
    }

    if (Common.eclipticSeasonsLoaded()) {
      calendar = eclipticSeasons.CALENDAR();
    }

    return calendar;
  }

  /**
   * Gets the current season's file name for the platform.
   *
   * @return The current season's file name for the platform.
   */
  public String getSeasonFileName(Player player) {
    return commonSeasons.getCurrentSeason(player).toLowerCase();
  }

  @Override
  public boolean infertileBiome(Player player) {
    if (Common.fabricSeasonsLoaded()) {
      return fabricSeasons.infertileBiome(player);
    }

    if (Common.sereneSeasonsLoaded() && !Common.eclipticSeasonsLoaded()) {
      return sereneSeasons.infertileBiome(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      return terrafirmaCraft.infertileBiome(player);
    }

    if (Common.eclipticSeasonsLoaded()) {
      return eclipticSeasons.infertileBiome(player);
    }

    else {
      return false;
    }
  }

  @Override
  public boolean alwaysWinterBiome(Player player) {
    if (Common.fabricSeasonsLoaded()) {
      return fabricSeasons.alwaysWinterBiome(player);
    }

    if (Common.sereneSeasonsLoaded() && !Common.eclipticSeasonsLoaded()) {
      return sereneSeasons.alwaysWinterBiome(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      return terrafirmaCraft.alwaysWinterBiome(player);
    }

    if (Common.eclipticSeasonsLoaded()) {
      return eclipticSeasons.alwaysWinterBiome(player);
    }

    else {
      return false;
    }
  }

  @Override
  public boolean undergroundFertile(Player player) {
    if (Common.fabricSeasonsLoaded()) {
      return fabricSeasons.undergroundFertile(player);
    }

    if (Common.sereneSeasonsLoaded() && !Common.eclipticSeasonsLoaded()) {
      return sereneSeasons.undergroundFertile(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      return terrafirmaCraft.undergroundFertile(player);
    }

    if (Common.eclipticSeasonsLoaded()) {
      return eclipticSeasons.undergroundFertile(player);
    }

    else {
      return true;
    }
  }

  @Override
  public Fertility fertility(Player player) {
    if (Common.fabricSeasonsLoaded()) {
      return fabricSeasons.fertility(player);
    }

    if (Common.sereneSeasonsLoaded() && !Common.eclipticSeasonsLoaded()) {
      return sereneSeasons.fertility(player);
    }

    if (Common.terrafirmacraftLoaded()) {
      return terrafirmaCraft.fertility(player);
    }

    if (Common.eclipticSeasonsLoaded()) {
      return eclipticSeasons.fertility(player);
    }

    else {
      return Fertility.FERTILE;
    }
  }
}

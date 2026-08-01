package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.impl.season.components.Months;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.platform.services.SeasonHelper;
import homeostaticseasons.api.HomeostaticSeasonsAPI;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import sereneseasons.init.ModConfig;

public class FabricSeasonHelper implements SeasonHelper {
  // FabricSeasons
  @Override
  public boolean validFabricSeasonsDim(ResourceKey<Level> currentDim) {
    // return FabricSeasons.CONFIG.isValidInDimension(currentDim);
    return true;
  }

  @Override
  public boolean fabricSeasonsTiedWithSystemTime() {
    // return FabricSeasons.CONFIG.isSeasonTiedWithSystemTime();
    return false;
  }

  @Override
  public Optional<Item> fabricSeasonsCalendar() {
    // if (Common.fabricSeasonsExtrasLoaded()) {
    //   return Optional.of(FabricSeasonsExtras.SEASON_CALENDAR_ITEM);
    // }
    return Optional.empty();
  }

  @Override
  public SubSeasons currentFabricSubSeason(Player player) {
    // long dayLengthTick = SeasonHudServer.getDayLength();
    // long seasonLengthTick = Services.SEASON.currentFabricSeasonLength(player);
    // long seasonLengthDay = seasonLengthTick / dayLengthTick; // Current season length (Default 28)
    // long timeToNextSeason = Services.SEASON.timeToNextFabricSeason(player);
    // long seasonDay = ((seasonLengthTick - timeToNextSeason) / dayLengthTick) + 1;
    //
    // int seasonPercent = (int) ((seasonDay * 100.0f) / seasonLengthDay);
    //
    // if (seasonPercent <= 33) {
    //   return SubSeasons.EARLY;
    // } else if (seasonPercent <= 66) {
    //   return SubSeasons.MID;
    // } else {
    //   return SubSeasons.LATE;
    // }

    return SubSeasons.EARLY;
  }

  @Override
  public Seasons currentFabricSeason(Player player) {
    // Season currentSeasonState = FabricSeasons.getCurrentSeason(player.level());
    // String currentSeason = currentSeasonState.toString();
    //
    // if (currentSeasonState.toString().equalsIgnoreCase("fall")) {
    //   currentSeason = "Autumn";
    // }
    //
    // return Seasons.valueOf(currentSeason.toUpperCase(Locale.ROOT));
    return Seasons.SPRING;
  }

  @Override
  public int currentFabricSeasonLength(Player player) {
    // return FabricSeasons.getCurrentSeason(player.level()).getSeasonLength();
    return 1;
  }

  @Override
  public long timeToNextFabricSeason(Player player) {
    // return FabricSeasons.getTimeToNextSeason(player.level());
    return 1;
  }

  // SereneSeasons
  @Override
  public boolean validSereneSeasonsDim(ResourceKey<Level> currentDim) {
    return ModConfig.seasons.isDimensionWhitelisted(currentDim);
  }

  // EclipticSeasons
  @Override
  public boolean validEclipticSeasonsDim(ResourceKey<Level> currentDim) {
    return false;
  }

  @Override
  public Component eclipticSeasonComponent(Player player) {
    return null;
  }

  @Override
  public SubSeasons currentEclipticSubSeason(Player player) {
    return null;
  }

  @Override
  public Seasons currentEclipticSeason(Player player) {
    return null;
  }

  @Override
  public long currentEclipticSeasonDate(Player player) {
    return 0;
  }

  @Override
  public int currentEclipticSeasonDuration(Player player) {
    return 0;
  }

  // TerrafirmaCraft
  @Override
  public Months currentTerraFirmaCraftMonth() {
    return null;
  }

  @Override
  public int terraFirmaCraftCurrentDayOfMonth() {
    return 0;
  }

  @Override
  public int terraFirmaCraftTotalDaysInMonth() {
    return 0;
  }

  @Override
  public boolean validHomeostaticSeasonsDim(ResourceKey<Level> currentDim) {
    return HomeostaticSeasonsAPI.isSeasonalDimension(currentDim);
  }

  @Override
  public Optional<Item> protoManlyWeatherCalendar() {
    return Optional.empty();
  }

  @Override
  public Months protoManlyWeatherMonth(Player player) {
    return null;
  }

  @Override
  public int protoManlyWeatherCurrentDayOfMonth(Player player) {
    return 0;
  }

  @Override
  public int protoManlyWeatherTotalDaysInMonth(Player player) {
    return 0;
  }
}

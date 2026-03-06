package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.impl.season.components.Months;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.platform.services.SeasonHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class FabricSeasonHelper implements SeasonHelper {
  // FabricSeasons
  @Override
  public boolean validFabricSeasonsDim(ResourceKey<Level> currentDim) {
    return true;
  }

  @Override
  public boolean fabricSeasonsTiedWithSystemTime() {
    return FabricSeasons.CONFIG.isSeasonTiedWithSystemTime();
  }

  @Override
  public Optional<Item> fabricSeasonsCalendar() {
    if (Common.fabricSeasonsExtrasLoaded()) {
      return Optional.of(Registry.ITEM.get(new ResourceLocation("seasons", "season_calendar")));
    }
    return Optional.empty();
  }

  @Override
  public SubSeasons currentFabricSubSeason(Player player) {
    long dayLengthTick = SeasonHudServer.getDayLength();
    long seasonLengthTick = Services.SEASON.currentFabricSeasonLength(player);
    long seasonLengthDay = seasonLengthTick / dayLengthTick; // Current season length (Default 28)
    long timeToNextSeason = Services.SEASON.timeToNextFabricSeason(player);
    long seasonDay = ((seasonLengthTick - timeToNextSeason) / dayLengthTick) + 1;

    int seasonPercent = (int) ((seasonDay * 100.0f) / seasonLengthDay);

    if (seasonPercent <= 33) {
      return SubSeasons.EARLY;
    } else if (seasonPercent <= 66) {
      return SubSeasons.MID;
    } else {
      return SubSeasons.LATE;
    }
  }

  @Override
  public Seasons currentFabricSeason(Player player) {
    Season currentSeasonState = FabricSeasons.getCurrentSeason(player.level);
    String currentSeason = currentSeasonState.toString();

    if (currentSeasonState.toString().equalsIgnoreCase("fall")) {
      currentSeason = "Autumn";
    }

    return Seasons.valueOf(currentSeason.toUpperCase(Locale.ROOT));
  }

  @Override
  public int currentFabricSeasonLength(Player player) {
    return FabricSeasons.CONFIG.getSeasonLength();
  }

  @Override
  public long timeToNextFabricSeason(Player player) {
    int yearLength = FabricSeasons.CONFIG.getSeasonLength() * 4;

    long springTime = player.level.getDayTime() % (long) yearLength;
    long summerTime = springTime - (long) FabricSeasons.CONFIG.getSeasonLength();
    long fallTime = summerTime - (long) FabricSeasons.CONFIG.getSeasonLength();
    long winterTime = fallTime - (long) FabricSeasons.CONFIG.getSeasonLength();
    long seasonTime;
    switch (FabricSeasons.getCurrentSeason(player.level)) {
      case SPRING:
        seasonTime = springTime;
        break;
      case SUMMER:
        seasonTime = summerTime;
        break;
      case FALL:
        seasonTime = fallTime;
        break;
      case WINTER:
        seasonTime = winterTime;
        break;
      default:
        throw new IncompatibleClassChangeError();
    }

    return currentFabricSeasonLength(player) - seasonTime;
  }

  // SereneSeasons
  @Override
  public boolean isTropicalSereneSeason(Player player) {
    return false;
  }

  @Override
  public SubSeasons getCurrentSereneSubSeason(Player player) {
    return SubSeasons.NONE;
  }

  @Override
  public Seasons getCurrentSereneSeason(Player player) {
    return Seasons.NULL;
  }

  @Override
  public long getSereneDate(Player player) {
    return 1;
  }

  @Override
  public int sereneSeasonDurationDays(Player player) {
    return 1;
  }

  @Override
  public boolean validSereneSeasonsDim(ResourceKey<Level> currentDim) {
    // No Fabric Serene Seasons for 1.19 and below
    return true;
  }

  @Override
  public boolean infertileSereneBiome(Player player) {
    return false;
  }

  @Override
  public boolean alwaysWinterBiomeSereneBiome(Player player) {
    return false;
  }

  @Override
  public boolean undergroundFertileSereneBiome(Player player) {
    return false;
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
    // Disabled for 1.20.1 and below
    // return !HomeostaticSeasonsAPI.isSeasonalDimension(currentDim);
    return true;
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

  @Override
  public void protoManlyDebug(PoseStack graphics) {

  }
}

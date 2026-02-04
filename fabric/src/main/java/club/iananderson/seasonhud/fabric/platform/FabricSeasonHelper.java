package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.seasons.Months;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.impl.seasons.SubSeasons;
import club.iananderson.seasonhud.impl.seasons.mods.FabricSeasonsHelper;
import club.iananderson.seasonhud.platform.services.SeasonHelper;
import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import io.github.lucaargolo.seasonsextras.FabricSeasonsExtras;
import java.util.Locale;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import sereneseasons.init.ModConfig;

public class FabricSeasonHelper implements SeasonHelper {
  // FabricSeasons
  @Override
  public boolean validFabricSeasonsDim(ResourceKey<Level> currentDim) {
    return FabricSeasons.CONFIG.isValidInDimension(currentDim);
  }

  @Override
  public Item fabricSeasonsCalendar() {
    if (Common.fabricSeasonsExtrasLoaded()) {
      return FabricSeasonsExtras.SEASON_CALENDAR_ITEM;
    } else {
      return null;
    }
  }

  @Override
  public SubSeasons currentFabricSubSeason(Player player) {
    FabricSeasonsHelper fabricSeasonsHelper = new FabricSeasonsHelper();

    int currentSeasonDurationDays = fabricSeasonsHelper.seasonDurationDays(player);
    long currentSeasonDate = fabricSeasonsHelper.getDate(player);

    // TODO: Check this math
    int seasonPercent = (int) ((currentSeasonDate * 100.0f) / currentSeasonDurationDays);

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
    Season currentSeasonState = FabricSeasons.getCurrentSeason(player.level());
    String currentSeason = currentSeasonState.toString();

    if (currentSeasonState.toString().equalsIgnoreCase("fall")) {
      currentSeason = "Autumn";
    }

    // TODO: double check this
    return Seasons.valueOf(currentSeason.toUpperCase(Locale.ROOT));
  }

  @Override
  public int currentFabricSeasonLength(Player player) {
    return FabricSeasons.getCurrentSeason(player.level()).getSeasonLength();
  }

  @Override
  public long timeToNextFabricSeason(Player player) {
    return FabricSeasons.getTimeToNextSeason(player.level());
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

  // TerrafirmaCraft
  @Override
  public Months currentTerraFirmaCraftMonth() {
    return null;
  }

  @Override
  public int terraFirmaCraftCurrentDayofMonth() {
    return 0;
  }

  @Override
  public int terraFirmaCraftTotalDaysInMonth() {
    return 0;
  }
}

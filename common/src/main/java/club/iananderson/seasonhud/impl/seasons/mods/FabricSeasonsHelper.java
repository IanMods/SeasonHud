package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import io.github.lucaargolo.seasonsextras.FabricSeasonsExtras;
import java.time.LocalDateTime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class FabricSeasonsHelper implements IModHelper {
  public FabricSeasonsHelper() {
  }

  @Override
  public Item CALENDAR() {
    if (Common.fabricSeasonsLoaded() && Common.hasCalendarLoaded()) {
      return FabricSeasonsExtras.SEASON_CALENDAR_ITEM;
    }
    else {
      return null;
    }
  }

  @Override
  public boolean isTropicalSeason(Player player) {
    return false;
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    if (Common.fabricSeasonsLoaded()) {
      return FabricSeasons.CONFIG.isSeasonTiedWithSystemTime();
    }
    else {
      return false;
    }
  }

  @Override
  public String getCurrentSubSeason(Player player) {
    Season currentSeasonState = FabricSeasons.getCurrentSeason(player.level());

    if (currentSeasonState.toString().equalsIgnoreCase("fall")) {
      return "Autumn";
    }
    else {
      return currentSeasonState.toString();
    }
  }

  @Override
  public String getCurrentSeason(Player player) {
    Season currentSeasonState = FabricSeasons.getCurrentSeason(player.level());

    if (currentSeasonState.toString().equalsIgnoreCase("fall")) {
      return "Autumn";
    }
    else {
      return currentSeasonState.toString();
    }
  }

  @Override
  public long getDate(Player player) {
    long dayLength = Config.getDayLength();
    long seasonLength = FabricSeasons.CONFIG.getSpringLength();
    long timeToNextSeason = FabricSeasons.getTimeToNextSeason(player.level());

    // Get the current day of month from the system. Used with fabric seasons' system time tied with season option
    if (isSeasonTiedWithSystemTime()) {
      return LocalDateTime.now().getDayOfMonth();
    }
    else {
      return ((seasonLength - timeToNextSeason) / dayLength) + 1;
    }
  }

  @Override
  public int seasonDuration(Player player) {
    int dayLength = Config.getDayLength();

    return FabricSeasons.CONFIG.getSpringLength() / dayLength;
  }
}

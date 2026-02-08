package club.iananderson.seasonhud.impl.season.mods.fabricseasons;

import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.impl.season.components.Fertility;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.impl.season.mods.SeasonModHelper;
import club.iananderson.seasonhud.platform.Services;
import java.time.LocalDateTime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class FabricSeasonsHelper implements SeasonModHelper {
  public FabricSeasonsHelper() {
  }

  @Override
  public Item calendar() {
    return Services.SEASON.fabricSeasonsCalendar();
  }

  @Override
  public boolean isTropicalSeason(Player player) {
    return false;
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    return Services.SEASON.fabricSeasonsTiedWithSystemTime();
  }

  @Override
  public SubSeasons getCurrentSubSeason(Player player) {
    return Services.SEASON.currentFabricSubSeason(player);
  }

  @Override
  public Seasons getCurrentSeason(Player player) {
    return Services.SEASON.currentFabricSeason(player);
  }

  @Override
  public long getDate(Player player) {
    long dayLength = SeasonHudServer.getDayLength();
    int seasonLength = Services.SEASON.currentFabricSeasonLength(player);
    long timeToNextSeason = Services.SEASON.timeToNextFabricSeason(player);

    // Get the current day of month from the system. Used with fabric season' system time tied with season option
    if (isSeasonTiedWithSystemTime()) {
      return LocalDateTime.now().getDayOfMonth();
    } else {
      return ((seasonLength - timeToNextSeason) / dayLength) + 1;
    }
  }

  @Override
  public int seasonDurationDays(Player player) {
    int dayLength = SeasonHudServer.getDayLength();
    int seasonLength = Services.SEASON.currentFabricSeasonLength(player);

    return seasonLength / dayLength;
  }

  @Override
  public boolean infertileBiome(Player player) {
    return false;
  }

  @Override
  public boolean alwaysWinterBiome(Player player) {
    return false;
  }

  @Override
  public boolean undergroundFertile(Player player) {
    return true;
  }

  @Override
  public Fertility fertility(Player player) {
    return Fertility.FERTILE;
  }
}

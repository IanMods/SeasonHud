package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import java.time.LocalDateTime;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class FabricSeasonsHelper {
  public static Item CALENDAR(){
    if (Common.fabricSeasonsLoaded()){
      return Registry.ITEM.get(new ResourceLocation("seasons", "season_calendar"));
    }
    else return null;
  }

  public static boolean isSeasonTiedWithSystemTime() {
    if(Common.fabricSeasonsLoaded()) {
      return FabricSeasons.CONFIG.isSeasonTiedWithSystemTime();
    }
    else return false;
  }

  private FabricSeasonsHelper() {
  }

  public static String getCurrentSubSeason(Player player) {
    Season currentSeasonState = FabricSeasons.getCurrentSeason(player.level);

    if (currentSeasonState.toString().equalsIgnoreCase("fall")) {
      return "Autumn";
    }
    else {
      return currentSeasonState.toString();
    }
  }

  public static String getCurrentSeason(Player player) {
    Season currentSeasonState = FabricSeasons.getCurrentSeason(player.level);

    if (currentSeasonState.toString().equalsIgnoreCase("fall")) {
      return "Autumn";
    }
    else {
      return currentSeasonState.toString();
    }
  }

  public static long getDate(Player player) {
    long dayLength = Config.getDayLength();
    long seasonLength = FabricSeasons.CONFIG.getSeasonLength();
    long worldTime = Math.toIntExact(player.level.getDayTime());

    // Get the current day of month from the system. Used with fabric seasons' system time tied with season option
    if (FabricSeasonsHelper.isSeasonTiedWithSystemTime()) {
      return LocalDateTime.now().getDayOfMonth();
    }
    else {
      return ((worldTime - (worldTime / seasonLength * seasonLength)) % seasonLength / dayLength) + 1;
    }
  }

  public static int seasonDuration(Player player) {
    int dayLength = Config.getDayLength();

    return FabricSeasons.CONFIG.getSeasonLength() / dayLength;
  }
}

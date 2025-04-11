package club.iananderson.seasonhud.impl.seasons.mods;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public interface IModHelper {

  /**
   * @return The calendar item for the loaded season mod.
   */
  Item CALENDAR();

  /**
   * Checks if the tropical season should be displayed (SereneSeasons only). Always false for FabricSeasons.
   *
   * @return If the tropical season should be displayed for the platform.
   */
  boolean isTropicalSeason(Player player);

  /**
   * Checks if "isSeasonTiedWithSystemTime" config option is enabled (FabricSeasons only). Always false for
   * SereneSeasons.
   *
   * @return If "isSeasonTiedWithSystemTime" config option is enabled for the platform.
   */
  boolean isSeasonTiedWithSystemTime();

  /**
   * Gets the name of the current sub-season for the platform (if applicable).
   *
   * @return The name of the current season for the platform.
   */
  String getCurrentSubSeason(Player player);

  /**
   * Gets the name of the current season for the platform.
   *
   * @return The name of the current season for the platform.
   */
  String getCurrentSeason(Player player);

  /**
   * Gets the current season's file name for the platform.
   *
   * @return The current season's file name for the platform.
   */
  long getDate(Player player);

  /**
   * Checks the duration of the current season/sub-season.
   *
   * @return The duration of the current season/sub-season.
   */
  int seasonDuration(Player player);
}

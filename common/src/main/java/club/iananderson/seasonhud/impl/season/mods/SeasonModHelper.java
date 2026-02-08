package club.iananderson.seasonhud.impl.season.mods;

import club.iananderson.seasonhud.impl.season.components.Fertility;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public interface SeasonModHelper {

  /**
   * The calendar item the season mod uses (if available).
   *
   * @return The calendar item for the loaded season mod.
   */
  Optional<Item> calendar();

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
  SubSeasons getCurrentSubSeason(Player player);

  /**
   * Gets the name of the current season for the platform.
   *
   * @return The name of the current season for the platform.
   */
  Seasons getCurrentSeason(Player player);

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
  int seasonDurationDays(Player player);

  /**
   * Checks if the current biome the player is in is marked as infertile.
   *
   * @return If the current biome the player in is marked as infertile.
   */
  boolean infertileBiome(Player player);

  /**
   * Checks if the current biome the player is in is considered always winter.
   *
   * @return If the current biome the player in is considered always winter.
   */
  boolean alwaysWinterBiome(Player player);

  /**
   * Checks if the crops are fertile at the players current y position.
   *
   * @return If the players current y position is considered fertile
   */
  boolean undergroundFertile(Player player);

  /**
   * Checks if the current biome the player is in is marked as fertile. Is mainly used to check for "always winter" and
   * "infertile" biomes.
   *
   * @return If the current biome the player in is considered fertile
   */
  Fertility fertility(Player player);

}

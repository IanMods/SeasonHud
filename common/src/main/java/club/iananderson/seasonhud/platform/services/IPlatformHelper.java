package club.iananderson.seasonhud.platform.services;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public interface IPlatformHelper {

  /**
   * Gets the name of the current platform.
   *
   * @return The name of the current platform.
   */
  String getPlatformName();

  /**
   * Checks if a mod with the given id is loaded.
   *
   * @param modId The mod to check if it is loaded.
   * @return True if the mod is loaded, false otherwise.
   */
  boolean isModLoaded(String modId);

  String getModVersion(String modId);

  /**
   * Check if the game is currently in a development environment.
   *
   * @return True if in a development environment, false otherwise.
   */
  boolean isDevelopmentEnvironment();

  boolean curiosFound(Player player, Item item);

  String getCurrentSereneSeason(Player player);

  String getCurrentSereneSubSeason(Player player);

  long getSereneSeasonDate(Player player);

  boolean sereneSeasonTropicalBiome(Player player);

  boolean sereneSeasonInfertileBiome(Player player);

  boolean sereneSeasonBiomeSeasonalEffects(Player player);

  String getCurrentEclipticSeason(Player player);

  String getCurrentEclipticSubSeason(Player player);

  long getEclipticSeasonDate(Player player);
}
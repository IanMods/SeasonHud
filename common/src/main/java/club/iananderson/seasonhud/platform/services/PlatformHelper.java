package club.iananderson.seasonhud.platform.services;

public interface PlatformHelper {

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

  @SuppressWarnings({"unused"})
  String getModVersion(String modId);

  /**
   * Gets the mod name for the given id.
   *
   * @param modId The mod id for the mod.
   * @return Mod name.
   */
  String getModName(String modId);

  /**
   * Check if the game is currently in a development environment.
   *
   * @return True if in a development environment, false otherwise.
   */
  @SuppressWarnings({"unused"})
  boolean isDevelopmentEnvironment();
}

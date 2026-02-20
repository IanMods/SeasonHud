package club.iananderson.seasonhud.platform.services;

import net.minecraft.client.Minecraft;

public interface MinimapHelper {
  /**
   * Needed to do differences in Forge and Fabric versions, depending on the Minecraft version.
   *
   * @return If the MapAtlases minimap is not displayed
   */
  boolean hideMapAtlases(Minecraft mc);

  boolean hideJourneyMap(Minecraft mc);

  boolean hideXaero(Minecraft mc);
}

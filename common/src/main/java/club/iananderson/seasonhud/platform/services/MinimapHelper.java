package club.iananderson.seasonhud.platform.services;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public interface MinimapHelper {
  /**
   * Needed to do differences in Forge and Fabric versions, depending on the Minecraft version.
   *
   * @return If the MapAtlases minimap is not displayed
   */
  boolean hideMapAtlases(Minecraft mc);

  boolean hideJourneyMap(Minecraft mc);

  boolean hideXaero(Minecraft mc);

  boolean hideHudInCurrentDimension();

  void openJourneyMapOptions(Screen returnScreen);
}
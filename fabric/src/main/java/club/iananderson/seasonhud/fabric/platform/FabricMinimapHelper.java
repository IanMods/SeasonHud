package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.platform.services.MinimapHelper;

public class FabricMinimapHelper implements MinimapHelper {
  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases() {
    return false;
  }
}
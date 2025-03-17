package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.platform.services.IMinimapHelper;

public class FabricMinimapHelper implements IMinimapHelper {
  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases() {
    return false;
  }
}
package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.platform.services.MinimapHelper;

public class ForgeMinimapHelper implements MinimapHelper {
  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases() {
    return false;
  }
}

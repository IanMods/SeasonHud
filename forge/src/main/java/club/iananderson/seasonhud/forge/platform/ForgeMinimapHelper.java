package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.platform.services.IMinimapHelper;

public class ForgeMinimapHelper implements IMinimapHelper {
  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases() {
    return false;
  }
}

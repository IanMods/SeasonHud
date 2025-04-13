package club.iananderson.seasonhud.neoforge.platform;

import club.iananderson.seasonhud.platform.services.IMinimapHelper;

public class NeoForgeMinimapHelper implements IMinimapHelper {
  @Override
  public boolean hideMapAtlases() {
    return false;
  }
}
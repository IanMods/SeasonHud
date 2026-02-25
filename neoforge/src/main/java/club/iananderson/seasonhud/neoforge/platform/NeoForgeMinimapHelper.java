package club.iananderson.seasonhud.neoforge.platform;

import club.iananderson.seasonhud.platform.services.MinimapHelper;

public class NeoForgeMinimapHelper implements MinimapHelper {
  @Override
  public boolean hideMapAtlases() {
    return false;
  }
}
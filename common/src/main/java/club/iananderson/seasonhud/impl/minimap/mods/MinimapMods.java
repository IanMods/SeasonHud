package club.iananderson.seasonhud.impl.minimap.mods;

import club.iananderson.seasonhud.platform.Services;

public enum MinimapMods {
  XAERO("xaerominimap"),

  XAERO_FAIRPLAY("xaerominimapfair"),

  JOURNEYMAP("journeymap"),

  FTB_CHUNKS("ftbchunks"),

  MAP_ATLASES("map_atlases");

  private final String modId;

  MinimapMods(String modId) {
    this.modId = modId;
  }

  public String getModId() {
    return this.modId;
  }

  public String getModName() {
    return Services.PLATFORM.getModName(modId);
  }
}

package club.iananderson.seasonhud.impl.minimap.mods;

import club.iananderson.seasonhud.platform.Services;
import java.util.ArrayList;
import java.util.List;

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

  public static List<MinimapMods> getLoaded() {
    List<MinimapMods> values = new ArrayList<>(List.of(MinimapMods.values()));
    List<MinimapMods> loaded = new ArrayList<>();

    values.forEach(minimaps -> {
      if (minimaps.modLoaded()) {
        loaded.add(minimaps);
      }
    });
    return loaded;
  }

  public String getModId() {
    return this.modId;
  }

  public String getModName() {
    return Services.PLATFORM.getModName(this.modId);
  }

  public boolean modLoaded() {
    return Services.PLATFORM.isModLoaded(this.modId);
  }
}

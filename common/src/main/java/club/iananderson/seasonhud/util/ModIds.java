package club.iananderson.seasonhud.util;

import java.util.ArrayList;
import java.util.List;

public class ModIds {
  public static class SeasonMods {
    public static String sereneSeasons = "sereneseasons";
    public static String fabricSeasons = "seasons";
    public static String fabricSeasonsExtras = "seasonsextras";
    public static String terrafirmacraft = "tfc";
    public static String eclipticSeasons = "eclipticseasons";
  }

  public static class AccessoryMods {
    public static String curios = "curios";
    public static String trinkets = "trinkets";
    public static String accessories = "accessories";
  }

  public static class MinimapMods {
    public static List<String> allMods = new ArrayList<>(
        List.of(MinimapMods.xaeroMinimap, MinimapMods.xaeroMinimapFairplay, MinimapMods.journeymap,
            MinimapMods.xaeroMinimapFairplay, MinimapMods.ftbChunks, MinimapMods.mapAtlases));

    public static String xaeroMinimap = "xaerominimap";
    public static String xaeroMinimapFairplay = "xaerominimapfair";
    public static String journeymap = "journeymap";
    public static String ftbChunks = "ftbchunks";
    public static String mapAtlases = "map_atlases";
  }
}

package club.iananderson.seasonhud.util;

import java.util.ArrayList;
import java.util.List;

public class ModIds {
  public static class SeasonMods {
    public static String sereneSeasonsId = "sereneseasons";
    public static String fabricSeasonsId = "seasons";
    public static String fabricSeasonsExtrasId = "seasonsextras";
    public static String terrafirmacraftId = "tfc";
    public static String eclipticSeasonsId = "eclipticseasons";
  }

  public static class AccessoryMods {
    public static String curiosId = "curios";
    public static String trinketsId = "trinkets";
    public static String accessoriesId = "accessories";
  }

  public static class MinimapMods {
    public static List<String> allMods = new ArrayList<>(
        List.of(MinimapMods.xaeroMinimapId, MinimapMods.xaeroMinimapFairplayId, MinimapMods.journeymapId,
            MinimapMods.xaeroMinimapFairplayId, MinimapMods.ftbChunksId, MinimapMods.mapAtlasesId));

    public static String xaeroMinimapId = "xaerominimap";
    public static String xaeroMinimapFairplayId = "xaerominimapfair";
    public static String journeymapId = "journeymap";
    public static String ftbChunksId = "ftbchunks";
    public static String mapAtlasesId = "map_atlases";
  }
}

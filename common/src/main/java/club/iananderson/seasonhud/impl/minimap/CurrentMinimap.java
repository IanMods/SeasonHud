package club.iananderson.seasonhud.impl.minimap;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import club.iananderson.seasonhud.impl.minimap.mods.MinimapMods;
import club.iananderson.seasonhud.platform.Services;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public class CurrentMinimap {
  private static boolean minimapLoaded(MinimapMods minimap) {
    String modId = minimap.getModId();
    return Services.PLATFORM.isModLoaded(modId);
  }

  public static boolean xaeroLoaded() {
    return minimapLoaded(MinimapMods.XAERO) || minimapLoaded(MinimapMods.XAERO_FAIRPLAY);
  }

  public static boolean journeyMapLoaded() {
    return minimapLoaded(MinimapMods.JOURNEYMAP);
  }

  public static boolean ftbChunksLoaded() {
    return minimapLoaded(MinimapMods.FTB_CHUNKS);
  }

  public static boolean mapAtlasesLoaded() {
    return minimapLoaded(MinimapMods.MAP_ATLASES);
  }

  private static List<MinimapMods> getLoadedMinimaps() {
    List<MinimapMods> values = new ArrayList<>(List.of(MinimapMods.values()));
    List<MinimapMods> loaded = new ArrayList<>();

    values.forEach(minimaps -> {
      if (minimapLoaded(minimaps)) {
        loaded.add(minimaps);
      }
    });
    return loaded;
  }

  public static boolean noMinimapLoaded() {
    return getLoadedMinimaps().isEmpty();
  }

  /* TODO:
   ** Double check all logic
   ** Add option to display current loaded integration
   ** Add a dropdown to override this if more than one are loaded
   */

  /**
   * Finds if the minimap is hidden.
   *
   * @param minimap Current loaded minimap mod
   * @return True if the minimap is not currently displayed
   */
  public static boolean hiddenMinimap(MinimapMods minimap) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return false;
    }

    switch (minimap) {
      case JOURNEYMAP -> {
        return Services.MINIMAP.hideJourneyMap();
      }
      case FTB_CHUNKS -> {
        return !FTBChunksClientConfig.MINIMAP_ENABLED.get() || mc.options.renderDebug;
      }
      case XAERO, XAERO_FAIRPLAY -> {
        return Services.MINIMAP.hideXaero();
      }
      case MAP_ATLASES -> {
        return Services.MINIMAP.hideMapAtlases();
      }
      default -> {
        return false;
      }
    }
  }

  /**
   * Used incase FtbChunks or Journeymap are loaded, but not used for the minimap.
   *
   * @return True if all the loaded minimap are hidden.
   */
  public static boolean allMinimapsHidden() {
    List<MinimapMods> loadedMinimaps = CurrentMinimap.getLoadedMinimaps();
    List<Boolean> hiddenMinimaps = new ArrayList<>();

    loadedMinimaps.forEach(minimap -> hiddenMinimaps.add(hiddenMinimap(minimap)));

    return Common.allTrue(hiddenMinimaps);
  }

  /**
   * Determines if the season hud should be drawn.
   *
   * @param minimap Current loaded minimap mod
   * @return True if the minimap version of the HUD should be drawn instead of the default
   */
  public static boolean shouldDrawMinimapHud(MinimapMods minimap) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return false;
    }

    boolean enabled = SeasonHudClient.getEnableMod() && SeasonHudClient.getEnableMinimapIntegration();
    boolean hiddenMinimap = Common.hideHudInCurrentDimension() || hiddenMinimap(minimap);

    return enabled && Calendar.validNeedCalendar() && !mc.options.hideGui && !hiddenMinimap;
  }
}
package club.iananderson.seasonhud.impl.minimaps;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.platform.Services;
import com.mamiyaotaru.voxelmap.MapSettingsManager;
import com.mamiyaotaru.voxelmap.VoxelMap;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import xaero.common.HudMod;
import xaero.lib.client.gui.ScreenBase;

public class CurrentMinimap {
  private static boolean minimapLoaded(Minimap minimap) {
    String modId = minimap.getModId();
    return Services.PLATFORM.isModLoaded(modId);
  }

  public static boolean xaeroLoaded() {
    return minimapLoaded(Minimap.XAERO) || minimapLoaded(Minimap.XAERO_FAIRPLAY);
  }

  public static boolean journeyMapLoaded() {
    return minimapLoaded(Minimap.JOURNEYMAP);
  }

  public static boolean ftbChunksLoaded() {
    return minimapLoaded(Minimap.FTB_CHUNKS);
  }

  public static boolean mapAtlasesLoaded() {
    return minimapLoaded(Minimap.MAP_ATLASES);
  }

  private static List<Minimap> getLoadedMinimaps() {
    List<Minimap> values = Arrays.asList(Minimap.values());
    List<Minimap> loaded = new ArrayList<>();

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
   * Determines if the minimap is currently hidden.
   *
   * @param minimap Current loaded minimap mod.
   * @return True if the minimap is not currently displayed
   */
  public static boolean hiddenMinimap(Minimap minimap) {
    Minecraft mc = Minecraft.getInstance();
    boolean hidden = false;

    if (mc.level == null || mc.player == null) {
      return false;
    }

    switch (minimap) {
      case FTB_CHUNKS:
        hidden = !FTBChunksClientConfig.MINIMAP_ENABLED.get() || mc.options.renderDebug;
        break;

      case XAERO:
        hidden = !HudMod.INSTANCE.getSettings().getMinimap() || mc.options.renderDebug || !(mc.screen == null
            || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen);
        break;

      case XAERO_FAIRPLAY:
        hidden = !HudMod.INSTANCE.getSettings().getMinimap() || mc.options.renderDebug || !(mc.screen == null
            || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen);
        break;

      case MAP_ATLASES:
        hidden = Services.MINIMAP.hideMapAtlases();
        break;

      case VOXELMAP:
        MapSettingsManager voxelOptions = VoxelMap.getInstance().getMapOptions();
        hidden = voxelOptions.hide || (!voxelOptions.showUnderMenus && (mc.screen != null || mc.options.renderDebug));
        break;
    }
    return hidden;
  }

  /* Todo:
   ** Double check all logic
   ** Add option to display current loaded integration
   ** Add a dropdown to override this if more than one are loaded
   */

  /**
   * Used incase FtbChunks or Journeymap are loaded, but not used for the minimap.
   *
   * @return True if all the loaded minimaps are hidden.
   */
  public static boolean allMinimapsHidden() {
    List<Minimap> loadedMinimaps = CurrentMinimap.getLoadedMinimaps();
    List<Boolean> hiddenMinimaps = new ArrayList<>();

    loadedMinimaps.forEach(minimap -> hiddenMinimaps.add(CurrentMinimap.hiddenMinimap(minimap)));

    return Common.allTrue(hiddenMinimaps);
  }

  /**
   * Determines if the minimap version of the season hud should be used.
   *
   * @param minimap Current loaded minimap mod.
   * @return True if the minimap version of the HUD should be drawn instead of the default.
   */
  public static boolean shouldDrawMinimapHud(Minimap minimap) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return false;
    }

    boolean enabled = SeasonHudClient.getEnableMod() && SeasonHudClient.getEnableMinimapIntegration();
    boolean hiddenMinimap = Common.hideHudInCurrentDimension() || CurrentMinimap.hiddenMinimap(minimap);

    return enabled && Calendar.validNeedCalendar() && !mc.options.hideGui && !hiddenMinimap;
  }

  public enum Minimap {
    XAERO("xaerominimap"),

    XAERO_FAIRPLAY("xaerominimapfair"),

    JOURNEYMAP("journeymap"),

    FTB_CHUNKS("ftbchunks"),

    MAP_ATLASES("map_atlases"),

    MAP_ATLASES_FORGE("mapatlases"),

    VOXELMAP("voxelmap");

    private final String modId;

    Minimap(String modId) {
      this.modId = modId;
    }

    public String getModId() {
      return this.modId;
    }
  }
}
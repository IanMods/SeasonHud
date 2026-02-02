package club.iananderson.seasonhud.impl.minimaps;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.util.ModIds.MinimapMods;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import java.util.ArrayList;
import java.util.List;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.dialog.MinimapOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import xaero.common.HudMod;
import xaero.lib.client.gui.ScreenBase;

public class CurrentMinimap {
  private static boolean minimapLoaded(String minimapModId) {
    return Services.PLATFORM.isModLoaded(minimapModId);
  }

  public static boolean xaeroLoaded() {
    return minimapLoaded(MinimapMods.xaeroMinimap) || minimapLoaded(MinimapMods.xaeroMinimapFairplay);
  }

  public static boolean journeyMapLoaded() {
    return minimapLoaded(MinimapMods.journeymap);
  }

  public static boolean ftbChunksLoaded() {
    return minimapLoaded(MinimapMods.ftbChunks);
  }

  public static boolean mapAtlasesLoaded() {
    return minimapLoaded(MinimapMods.mapAtlases);
  }

  private static List<String> getLoadedMinimaps() {
    List<String> values = MinimapMods.allMods;
    List<String> loaded = new ArrayList<>();

    values.forEach(modId -> {
      if (minimapLoaded(modId)) {
        loaded.add(modId);
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
   * @param minimapModId Current loaded minimap's modId.
   * @return True if the minimap is not currently displayed
   */
  public static boolean hiddenMinimap(String minimapModId) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return false;
    }

    if (minimapModId.equals(MinimapMods.journeymap)) {
      MiniMapProperties properties = UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties();

      return !properties.enabled.get() || (!properties.isActive() && mc.isPaused()) || mc.player.isScoping() || !(
          mc.screen == null || mc.screen instanceof ChatScreen || mc.screen instanceof MinimapOptions);
    }

    if (minimapModId.equals(MinimapMods.ftbChunks)) {
      return !FTBChunksClientConfig.MINIMAP_ENABLED.get() || mc.options.renderDebug;
    }

    if (minimapModId.equals(MinimapMods.xaeroMinimap) || minimapModId.equals(MinimapMods.xaeroMinimapFairplay)) {
      return !HudMod.INSTANCE.getSettings().getMinimap() || mc.options.renderDebug || !(mc.screen == null
          || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen || mc.screen instanceof ScreenBase);
    }

    if (minimapModId.equals(MinimapMods.mapAtlases)) {
      return Services.MINIMAP.hideMapAtlases();
    } else {
      return false;
    }
  }

  /**
   * Used incase FtbChunks or Journeymap are loaded, but not used for the minimap.
   *
   * @return True if all the loaded minimaps are hidden.
   */
  public static boolean allMinimapsHidden() {
    List<String> loadedMinimaps = CurrentMinimap.getLoadedMinimaps();
    List<Boolean> hiddenMinimaps = new ArrayList<>();

    loadedMinimaps.forEach(minimap -> hiddenMinimaps.add(hiddenMinimap(minimap)));

    return Common.allTrue(hiddenMinimaps);
  }

  /**
   * Determines if the minimap version of the season hud should be used.
   *
   * @param minimapModId Current loaded minimap's modId
   * @return True if the minimap version of the HUD should be drawn instead of the default.
   */
  public static boolean shouldDrawMinimapHud(String minimapModId) {
    Minecraft mc = Minecraft.getInstance();

    if (mc.level == null || mc.player == null) {
      return false;
    }

    boolean enabled = SeasonHudClient.getEnableMod() && SeasonHudClient.getEnableMinimapIntegration();
    boolean hiddenMinimap = Common.hideHudInCurrentDimension() || hiddenMinimap(minimapModId);

    return enabled && Calendar.validNeedCalendar() && !mc.options.hideGui && !hiddenMinimap;
  }
}
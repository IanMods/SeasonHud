package club.iananderson.seasonhud;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.Services;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Common {
  public static final String MOD_ID = "seasonhud";
  public static final String MOD_NAME = "SeasonHUD";

  public static final Logger LOG = LogManager.getLogger(MOD_NAME);

  public static final ResourceLocation SEASON_ICONS = location("season_icons");
  public static final Style SEASON_ICON_STYLE = Style.EMPTY.withFont(SEASON_ICONS);
  public static ResourceLocation slotIcon = new ResourceLocation(MOD_ID, "slot/calendarslot");
  private static String platformName;
  private static boolean sereneSeasonsLoaded;
  private static boolean fabricSeasonsLoaded;
  private static boolean fabricSeasonsExtrasLoaded;
  private static boolean terrafirmacraftLoaded;
  private static boolean eclipticSeasonsLoaded;
  private static boolean curiosLoaded;
  private static boolean trinketsLoaded;
  private static boolean accessoriesLoaded;

  private Common() {
  }

  public static void init() {
    platformName = Services.PLATFORM.getPlatformName();
    sereneSeasonsLoaded = Services.PLATFORM.isModLoaded("sereneseasons");
    fabricSeasonsLoaded = Services.PLATFORM.isModLoaded("seasons");
    fabricSeasonsExtrasLoaded = Services.PLATFORM.isModLoaded("seasonsextras");
    terrafirmacraftLoaded = Services.PLATFORM.isModLoaded("tfc");
    eclipticSeasonsLoaded = Services.PLATFORM.isModLoaded("eclipticseasons");
    curiosLoaded = Services.PLATFORM.isModLoaded("curios");
    trinketsLoaded = Services.PLATFORM.isModLoaded("trinkets");
    accessoriesLoaded = Services.PLATFORM.isModLoaded("accessories");
  }

  public static String platformName() {
    return Common.platformName;
  }

  public static boolean sereneSeasonsLoaded() {
    return Common.sereneSeasonsLoaded;
  }

  public static boolean fabricSeasonsLoaded() {
    return Common.fabricSeasonsLoaded;
  }

  public static boolean fabricSeasonsExtrasLoaded() {
    return Common.fabricSeasonsExtrasLoaded;
  }

  public static boolean terrafirmacraftLoaded() {
    return Common.terrafirmacraftLoaded;
  }

  public static boolean eclipticSeasonsLoaded() {
    return Common.eclipticSeasonsLoaded;
  }

  public static boolean curiosLoaded() {
    return Common.curiosLoaded;
  }

  public static boolean trinketsLoaded() {
    return Common.trinketsLoaded;
  }

  public static boolean accessoriesLoaded() {
    return Common.accessoriesLoaded;
  }

  public static boolean hasCalendarLoaded() {
    return Common.fabricSeasonsExtrasLoaded() || Common.sereneSeasonsLoaded();
  }

  public static boolean hasSubSeasons() {
    return Common.sereneSeasonsLoaded() || Common.terrafirmacraftLoaded() || Common.eclipticSeasonsLoaded();
  }

  public static boolean vanillaShouldDrawHud() {
    Minecraft mc = Minecraft.getInstance();

    if (mc.player == null) {
      return false;
    }

    return (mc.screen == null || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen)
        && !mc.options.renderDebug && !mc.options.hideGui;
  }

  public static boolean drawDefaultHud() {
    return (Config.getEnableMod() && (CurrentMinimap.noMinimapLoaded() || !Config.getEnableMinimapIntegration() || (
        CurrentMinimap.allMinimapsHidden() && Config.getShowDefaultWhenMinimapHidden())));
  }

  /**
   * Checks if the current dimension is whitelisted in the season mod's config.
   *
   * @return True if the current dimension is whitelisted in the season mod's config.
   */
  public static boolean hideHudInCurrentDimension() {
    return Services.MINIMAP.hideHudInCurrentDimension();
  }

  public static boolean allTrue(List<Boolean> values) {
    for (boolean value : values) {
      if (!value) {
        return false;
      }
    }
    return true;
  }

  public static ResourceLocation location(String path) {
    return new ResourceLocation(MOD_ID, path);
  }
}
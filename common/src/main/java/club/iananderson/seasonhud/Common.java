package club.iananderson.seasonhud;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.util.ModIds.AccessoryMods;
import club.iananderson.seasonhud.util.ModIds.SeasonMods;
import com.demonwav.mcdev.annotations.Translatable;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Common {
  public static final String MOD_ID = "seasonhud";
  public static final String MOD_NAME = "SeasonHUD";
  public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
  public static final ResourceLocation SEASON_ICONS = location("season_icons");
  public static final Style SEASON_ICON_STYLE = Style.EMPTY.withFont(SEASON_ICONS);
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
    sereneSeasonsLoaded = Services.PLATFORM.isModLoaded(SeasonMods.sereneSeasonsId);
    fabricSeasonsLoaded = Services.PLATFORM.isModLoaded(SeasonMods.fabricSeasonsId);
    fabricSeasonsExtrasLoaded = Services.PLATFORM.isModLoaded(SeasonMods.fabricSeasonsExtrasId);
    terrafirmacraftLoaded = Services.PLATFORM.isModLoaded(SeasonMods.terrafirmacraftId);
    eclipticSeasonsLoaded = Services.PLATFORM.isModLoaded(SeasonMods.eclipticSeasonsId);
    curiosLoaded = Services.PLATFORM.isModLoaded(AccessoryMods.curiosId);
    trinketsLoaded = Services.PLATFORM.isModLoaded(AccessoryMods.trinketsId);
    accessoriesLoaded = Services.PLATFORM.isModLoaded(AccessoryMods.accessoriesId);
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
    return Common.fabricSeasonsExtrasLoaded() || Common.sereneSeasonsLoaded() || Common.eclipticSeasonsLoaded();
  }

  public static boolean hasTropicalSeasons() {
    return Common.sereneSeasonsLoaded() || Common.terrafirmacraftLoaded() || Common.eclipticSeasonsLoaded();
  }

  public static boolean clientSideConfig() {
    Minecraft mc = Minecraft.getInstance();

    return (mc.getCurrentServer() == null);
  }

  public static boolean vanillaShouldDrawHud() {
    Minecraft mc = Minecraft.getInstance();

    if (mc.player == null) {
      return false;
    }

    return (mc.screen == null || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen)
        && !mc.options.renderDebug && !mc.options.hideGui && !mc.player.isScoping();
  }

  public static boolean minimapIntegrationHidden() {
    return SeasonHudClient.getEnableMinimapIntegration() && (CurrentMinimap.allMinimapsHidden()
        && SeasonHudClient.getShowDefaultWhenMinimapHidden());
  }

  public static boolean drawDefaultHud() {
    Minecraft mc = Minecraft.getInstance();

    if (mc.player == null) {
      return false;
    }

    return SeasonHudClient.getEnableMod() && (CurrentMinimap.noMinimapLoaded()
        || !SeasonHudClient.getEnableMinimapIntegration() || minimapIntegrationHidden());
  }

  public static boolean drawDefaultHudMenu() {
    Minecraft mc = Minecraft.getInstance();

    if (mc.player == null) {
      return false;
    }

    return (SeasonHudClient.getEnableMod() && (CurrentMinimap.noMinimapLoaded()
        || !SeasonHudClient.getEnableMinimapIntegration() || SeasonHudClient.getShowDefaultWhenMinimapHidden()));
  }

  public static boolean isDimensionValid(List<? extends String> validDimensions, ResourceKey<Level> dimension) {
    for (String validDimension : validDimensions) {
      if (dimension.location().toString().equals(validDimension)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks if the current dimension is whitelisted in the season mod's config.
   *
   * @return True if the current dimension is whitelisted in the season mod's config.
   */
  public static boolean hideHudInCurrentDimension() {
    Minecraft mc = Minecraft.getInstance();

    if (mc.player == null) {
      return false;
    }

    ResourceKey<Level> currentDim = Objects.requireNonNull(mc.level).dimension();

    if (Common.fabricSeasonsLoaded()) {
      return !Services.SEASON.validFabricSeasonsDim(currentDim);
    }
    if (Common.sereneSeasonsLoaded()) {
      return !Services.SEASON.validSereneSeasonsDim(currentDim);
    }
    if (Common.eclipticSeasonsLoaded()) {
      return !Services.SEASON.validEclipticSeasonsDim(currentDim);
    } else {
      return false;
    }
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

  // Used to make porting new text to older versions easier
  public static MutableComponent literalText(String text) {
    return Component.literal(text);
  }

  // Used to make porting new text to older versions easier
  public static MutableComponent translatedText(@Translatable(foldMethod = true) String key) {
    return Component.translatable(key);
  }

  // Used to make porting new text to older versions easier
  public static MutableComponent translatedText(@Translatable(foldMethod = true) String key, Object... args) {
    return Component.translatable(key, args);
  }

  // Used to make porting new text to older versions easier
  public static Tooltip newTooltip(@Translatable(foldMethod = true) String key) {
    return Tooltip.create(translatedText(key));
  }
}
package club.iananderson.seasonhud;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.accessory.mods.AccessoryMods;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.MinimapMods;
import club.iananderson.seasonhud.impl.season.mods.SeasonMods;
import club.iananderson.seasonhud.platform.Services;
import com.demonwav.mcdev.annotations.Translatable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Common {
  public static final String MOD_ID = "seasonhud";
  public static final String MOD_NAME = "SeasonHUD";
  public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
  public static final ResourceLocation SEASON_ICONS = location("season_icons");
  public static final Style SEASON_ICON_STYLE = Style.EMPTY.withFont(SEASON_ICONS);
  public static ResourceLocation slotIcon = new ResourceLocation(MOD_ID, "slot/calendarslot");
  private static boolean sereneSeasonsLoaded;
  private static boolean fabricSeasonsLoaded;
  private static boolean fabricSeasonsExtrasLoaded;
  private static boolean terrafirmacraftLoaded;
  private static boolean eclipticSeasonsLoaded;
  private static boolean homeostaticSeasonsLoaded;
  private static boolean protomanlyWeatherLoaded;
  private static boolean curiosLoaded;
  private static boolean trinketsLoaded;
  private static boolean accessoriesLoaded;
  private static boolean ftbChunksLoaded;

  private Common() {
  }

  public static void init() {
    sereneSeasonsLoaded = SeasonMods.SERENE.modLoaded();
    fabricSeasonsLoaded = SeasonMods.FABRIC.modLoaded();
    fabricSeasonsExtrasLoaded = SeasonMods.FABRIC_EXTRAS.modLoaded();
    terrafirmacraftLoaded = SeasonMods.TERRAFIRMACRAFT.modLoaded();
    eclipticSeasonsLoaded = SeasonMods.ECLIPTIC.modLoaded();
    homeostaticSeasonsLoaded = SeasonMods.HOMEOSTATIC.modLoaded();
    protomanlyWeatherLoaded = SeasonMods.PROTOMANLY_WEATHER.modLoaded();
    curiosLoaded = AccessoryMods.CURIOS.modLoaded();
    trinketsLoaded = AccessoryMods.TRINKETS.modLoaded();
    accessoriesLoaded = AccessoryMods.ACCESSORIES.modLoaded();
    ftbChunksLoaded = MinimapMods.FTB_CHUNKS.modLoaded();

    if (!SeasonMods.getLoaded().isEmpty()) {
      Common.LOG.info("Loading [{}] Compat", SeasonMods.getLoaded().get(0).getModName());
    }

    if (!MinimapMods.getLoaded().isEmpty()) {
      Common.LOG.info("Loading [{}] Compat", MinimapMods.getLoaded().get(0).getModName());
    }
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

  public static boolean homeostaticSeasonsLoaded() {
    return Common.homeostaticSeasonsLoaded;
  }

  public static boolean protomanlyWeatherLoaded() {
    return Common.protomanlyWeatherLoaded;
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

  public static boolean ftbChunksLoaded() {
    return Common.ftbChunksLoaded;
  }

  public static boolean hasCalendarLoaded() {
    return Common.fabricSeasonsExtrasLoaded() || Common.sereneSeasonsLoaded() || Common.eclipticSeasonsLoaded();
  }

  public static boolean hasTropicalSeasons() {
    return Common.sereneSeasonsLoaded() || Common.terrafirmacraftLoaded() || Common.eclipticSeasonsLoaded();
  }

  public static boolean clientSideConfig(Minecraft mc) {
    return (mc.getCurrentServer() == null);
  }

  public static boolean vanillaShouldDrawHud(Minecraft mc) {
    if (mc.player == null) {
      return false;
    }

    return (mc.screen == null || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen)
        && !mc.options.renderDebug && !mc.options.hideGui && !mc.player.isScoping();
  }

  public static boolean minimapIntegrationHidden(Minecraft mc) {
    return SeasonHudClient.getEnableMinimapIntegration() && (CurrentMinimap.allMinimapsHidden(mc)
        && SeasonHudClient.getShowDefaultWhenMinimapHidden());
  }

  public static boolean drawDefaultHud(Minecraft mc) {
    if (mc.player == null) {
      return false;
    }

    return SeasonHudClient.getEnableMod() && (CurrentMinimap.noMinimapLoaded()
        || !SeasonHudClient.getEnableMinimapIntegration() || minimapIntegrationHidden(mc));
  }

  public static boolean drawDefaultHudMenu() {
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
  public static boolean hideHudInCurrentDimension(Minecraft mc) {
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
    return new TextComponent(text);
  }

  // Used to make porting new text to older versions easier
  public static MutableComponent translatedText(@Translatable(foldMethod = true) String key) {
    return new TranslatableComponent(key);
  }

  // Used to make porting new text to older versions easier
  public static MutableComponent translatedText(@Translatable(foldMethod = true) String key, Object... args) {
    return new TranslatableComponent(key, args);
  }

  // Used to make porting new text to older versions easier
  public static List<FormattedCharSequence> newTooltip(@Translatable(foldMethod = true) String key) {
    List<FormattedCharSequence> list = new ArrayList<>();
    list.add(translatedText(key).getVisualOrderText());

    return list;
  }

  // Used to make porting new text to older versions easier
  public static List<FormattedCharSequence> newTooltip(@Translatable(foldMethod = true) String key, Object... args) {
    List<FormattedCharSequence> list = new ArrayList<>();
    list.add(translatedText(key, args).getVisualOrderText());

    return list;
  }
}
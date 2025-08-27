package club.iananderson.seasonhud.config;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.ShowDay;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class SeasonHudClient {
  //Config Builder
  public static final ForgeConfigSpec CLIENT_SPEC;
  public static final boolean DEFAULT_ENABLE_MOD = true;
  public static final Location DEFAULT_HUD_LOCATION = Location.TOP_LEFT;
  public static final int DEFAULT_X_OFFSET = 2;
  public static final int DEFAULT_Y_OFFSET = 2;
  public static final double DEFAULT_HUD_SCALE = 1.0;
  public static final double HUD_SCALE_MIN = 0.5;
  public static final double HUD_SCALE_MAX = 10;
  public static final boolean DEFAULT_SEASON_NAME_COLOR = true;
  public static final int COLOR_MIN = 0;
  public static final int COLOR_MAX = 16777215;
  public static final int DEFAULT_SPRING_COLOR = 16753595;
  public static final int DEFAULT_SUMMER_COLOR = 16705834;
  public static final int DEFAULT_AUTUMN_COLOR = 12344871;
  public static final int DEFAULT_WINTER_COLOR = 14679292;
  public static final int DEFAULT_DRY_COLOR = 16745216;
  public static final int DEFAULT_WET_COLOR = 2068975;
  public static final boolean DEFAULT_SHOW_TROPICAL_SEASON = true;
  public static final boolean DEFAULT_SHOW_SUB_SEASON = true;
  public static final ShowDay DEFAULT_SHOW_DAY = ShowDay.SHOW_DAY;
  public static final boolean DEFAULT_ENABLE_MINIMAP_INTEGRATION = true;
  public static final boolean DEFAULT_SHOW_DEFAULT_WHEN_MINIMAP_HIDDEN = false;
  public static final boolean DEFAULT_JOURNEYMAP_ABOVE_MAP = false;
  public static final boolean DEFAULT_JOURNEYMAP_MAC_OS = false;
  private static ConfigValue<Boolean> enableMod;
  private static ConfigValue<Location> hudLocation;
  private static ConfigValue<Integer> hudX;
  private static ConfigValue<Integer> hudY;
  private static ConfigValue<Double> hudScale;
  private static ConfigValue<Boolean> enableSeasonNameColor;
  private static ConfigValue<Integer> springColor;
  private static ConfigValue<Integer> summerColor;
  private static ConfigValue<Integer> autumnColor;
  private static ConfigValue<Integer> winterColor;
  private static ConfigValue<Integer> dryColor;
  private static ConfigValue<Integer> wetColor;
  private static ConfigValue<Boolean> showTropicalSeason;
  private static ConfigValue<Boolean> showSubSeason;
  private static ConfigValue<ShowDay> showDay;
  private static ConfigValue<Boolean> enableMinimapIntegration;
  private static ConfigValue<Boolean> showDefaultWhenMinimapHidden;
  private static ConfigValue<Boolean> journeyMapAboveMap;
  private static ConfigValue<Boolean> journeyMapMacOS;

  static {
    ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    setupConfig(builder);
    CLIENT_SPEC = builder.build();
  }

  private SeasonHudClient() {
  }

  private static void setupConfig(ForgeConfigSpec.Builder builder) {
    builder.push("SeasonHUD");
    enableMod = builder.comment("Enable the mod?\n" + "(true/false)\n" + "Default is " + DEFAULT_ENABLE_MOD + ".")
        .define("enable_mod", DEFAULT_ENABLE_MOD);

    builder.push("HUD");
    hudLocation = builder.comment(
            "Where to display the Hud when no minimap is installed.\n" + "Default is " + DEFAULT_HUD_LOCATION + ".")
        .defineEnum("hud_location", DEFAULT_HUD_LOCATION);

    hudX = builder.comment(
        "The horizontal offset of the HUD when no minimap is installed (in pixels)\n" + "'hudLocation' must be set to"
            + " 'CUSTOM'\n" + "Default is " + DEFAULT_X_OFFSET + ".").define("hud_x_position", DEFAULT_X_OFFSET);

    hudY = builder.comment(
        "The vertical offset of the HUD when no minimap is installed (in pixels)\n" + "'hudLocation' must be set to"
            + " 'CUSTOM'\n" + "Default is " + DEFAULT_Y_OFFSET + ".").define("hud_y_position", DEFAULT_Y_OFFSET);

    hudScale = builder.comment(
            "The scale of the HUD when no minimap is installed.\n" + "Default is " + DEFAULT_HUD_SCALE + ".")
        .defineInRange("hud_scale", DEFAULT_HUD_SCALE, HUD_SCALE_MIN, HUD_SCALE_MAX);

    builder.push("Colors");

    enableSeasonNameColor = builder.comment(
            "Display the season name in a color?\n" + "(true/false)" + "Default is " + DEFAULT_SEASON_NAME_COLOR + ".")
        .define("season_name_color", DEFAULT_SEASON_NAME_COLOR);

    springColor = builder.comment(
        "The RGB color (decimal) for spring.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n" + "Default is "
            + DEFAULT_SPRING_COLOR + ".").defineInRange("spring_color", DEFAULT_SPRING_COLOR, COLOR_MIN, COLOR_MAX);

    summerColor = builder.comment(
        "The RGB color (decimal) for summer.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n" + "Default is "
            + DEFAULT_SUMMER_COLOR + ".").defineInRange("summer_color", DEFAULT_SUMMER_COLOR, COLOR_MIN, COLOR_MAX);

    autumnColor = builder.comment(
        "The RGB color (decimal) for autumn.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n" + "Default is "
            + DEFAULT_AUTUMN_COLOR + ".").defineInRange("autumn_color", DEFAULT_AUTUMN_COLOR, COLOR_MIN, COLOR_MAX);

    winterColor = builder.comment(
        "The RGB color (decimal) for winter.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n" + "Default is "
            + DEFAULT_WINTER_COLOR + ".").defineInRange("winter_color", DEFAULT_WINTER_COLOR, COLOR_MIN, COLOR_MAX);

    dryColor = builder.comment(
            " The RGB color (decimal) for dry tropical season.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n"
                + "Default is " + DEFAULT_DRY_COLOR + ".")
        .defineInRange("dry_color", DEFAULT_DRY_COLOR, COLOR_MIN, COLOR_MAX);

    wetColor = builder.comment(
            "The RGB color (decimal) for wet tropical season.\n" + "(256 * 256 * r) + (256 * g) + (b) is the formula.\n"
                + "Default is " + DEFAULT_WET_COLOR + ".")
        .defineInRange("wet_color", DEFAULT_WET_COLOR, COLOR_MIN, COLOR_MAX);
    builder.pop();
    builder.pop();

    builder.push("Season");
    showTropicalSeason = builder.comment("Show the Tropical seasons (Wet/Dry) in Tropical Biomes.\n"
                                             + "Will not change the season behavior in the biomes.\n" + "(true/false)\n"
                                             + "Default is " + DEFAULT_SHOW_TROPICAL_SEASON + ".")
        .define("enable_show_tropical_season", DEFAULT_SHOW_TROPICAL_SEASON);

    showSubSeason = builder.comment(
            "Show sub-season (i.e. Early Winter, Mid Autumn, Late Spring) instead of basic season?\n" + "(true/false)\n"
                + " Default is ." + DEFAULT_SHOW_SUB_SEASON + ".")
        .define("enable_show_sub_season", DEFAULT_SHOW_SUB_SEASON);

    if (Common.fabricSeasonsLoaded()) {
      showDay = builder.comment("Show the current day of the season/sub-season?\n" + "Default is SHOW_DAY.")
          .defineEnum("enable_show_day", ShowDay.SHOW_DAY,
                      Arrays.asList(ShowDay.NONE, ShowDay.SHOW_DAY, ShowDay.SHOW_WITH_TOTAL_DAYS,
                                    ShowDay.SHOW_WITH_MONTH));
    }

    if (!Common.fabricSeasonsLoaded()) {
      showDay = builder.comment(
              "Show the day of the current Season/Sub-Season?\n" + "Default is ." + DEFAULT_SHOW_DAY + ".")
          .defineEnum("enable_show_day", DEFAULT_SHOW_DAY,
                      Arrays.asList(ShowDay.NONE, ShowDay.SHOW_DAY, ShowDay.SHOW_WITH_TOTAL_DAYS));
    }
    builder.pop();

    builder.push("Minimap");
    enableMinimapIntegration = builder.comment(
            "Enable integration with minimap mods?\n" + "(true/false)\n" + "Default is ."
                + DEFAULT_ENABLE_MINIMAP_INTEGRATION + ".")
        .define("enable_minimap_integration", DEFAULT_ENABLE_MINIMAP_INTEGRATION);

    showDefaultWhenMinimapHidden = builder.comment(
            "Show the default SeasonHUD display when the minimap is hidden?\n" + "(true/false)\n" + "Default is ."
                + DEFAULT_SHOW_DEFAULT_WHEN_MINIMAP_HIDDEN + ".")
        .define("enable_show_minimap_hidden", DEFAULT_SHOW_DEFAULT_WHEN_MINIMAP_HIDDEN);

    builder.push("Journeymap");
    journeyMapAboveMap = builder.comment(
        "Display the season above the JourneyMap minimap, instead of below.\n" + "(true/false)\n" + "Default is ."
            + DEFAULT_JOURNEYMAP_ABOVE_MAP + ".").define("enable_above_map", DEFAULT_JOURNEYMAP_ABOVE_MAP);

    journeyMapMacOS = builder.comment("Toggle for macOS retina display scaling when using JourneyMap.\n"
                                          + "Enable if the season line is rendering around the halfway point of the screen.\n"
                                          + "(true/false)\n" + "Default is ." + DEFAULT_JOURNEYMAP_MAC_OS + ".")
        .define("enable_macOS", DEFAULT_JOURNEYMAP_MAC_OS);
    builder.pop();
    builder.pop();
    builder.pop();
  }

  private static Object getDefault(ConfigValue<?> config) {
    Map<List<String>, Object> configOptions = new HashMap<>();
    configOptions.put(enableMod.getPath(), DEFAULT_ENABLE_MOD);
    configOptions.put(hudLocation.getPath(), DEFAULT_HUD_LOCATION);
    configOptions.put(hudX.getPath(), DEFAULT_X_OFFSET);
    configOptions.put(hudY.getPath(), DEFAULT_Y_OFFSET);
    configOptions.put(hudScale.getPath(), DEFAULT_HUD_SCALE);
    configOptions.put(enableSeasonNameColor.getPath(), DEFAULT_SEASON_NAME_COLOR);
    configOptions.put(springColor.getPath(), DEFAULT_SPRING_COLOR);
    configOptions.put(summerColor.getPath(), DEFAULT_SUMMER_COLOR);
    configOptions.put(autumnColor.getPath(), DEFAULT_AUTUMN_COLOR);
    configOptions.put(winterColor.getPath(), DEFAULT_WINTER_COLOR);
    configOptions.put(dryColor.getPath(), DEFAULT_DRY_COLOR);
    configOptions.put(wetColor.getPath(), DEFAULT_WET_COLOR);
    configOptions.put(showTropicalSeason.getPath(), DEFAULT_SHOW_TROPICAL_SEASON);
    configOptions.put(showSubSeason.getPath(), DEFAULT_SHOW_SUB_SEASON);
    configOptions.put(showDay.getPath(), DEFAULT_SHOW_DAY);
    configOptions.put(enableMinimapIntegration.getPath(), DEFAULT_ENABLE_MINIMAP_INTEGRATION);
    configOptions.put(showDefaultWhenMinimapHidden.getPath(), DEFAULT_SHOW_DEFAULT_WHEN_MINIMAP_HIDDEN);
    configOptions.put(journeyMapAboveMap.getPath(), DEFAULT_JOURNEYMAP_ABOVE_MAP);
    configOptions.put(journeyMapMacOS.getPath(), DEFAULT_JOURNEYMAP_MAC_OS);

    return configOptions.get(config.getPath());
  }

  private static Object getOrDefault(ConfigValue<?> config) {
    if (CLIENT_SPEC.isLoaded()) {
      return config.get();
    }
    else {
      return getDefault(config);
    }
  }


  //SeasonHUD
  public static boolean getEnableMod() {
    return (Boolean) getOrDefault(enableMod);
  }

  public static void setEnableMod(boolean enable) {
    SeasonHudClient.enableMod.set(enable);
  }

  //HUD
  public static Location getHudLocation() {
    return (Location) getOrDefault(hudLocation);
  }

  public static void setHudLocation(Location location) {
    SeasonHudClient.hudLocation.set(location);
  }

  public static int getHudX() {
    return (Integer) getOrDefault(hudX);
  }

  public static void setHudX(int x) {
    SeasonHudClient.hudX.set(x);
  }

  public static int getHudY() {
    return (Integer) getOrDefault(hudY);
  }

  public static void setHudY(int y) {
    SeasonHudClient.hudY.set(y);
  }

  public static double getHudScale() {
    return (Double) getOrDefault(hudScale);

  }

  public static void setHudScale(double scale) {
    SeasonHudClient.hudScale.set(scale);
  }

  //Colors
  public static boolean getEnableSeasonNameColor() {
    return (Boolean) getOrDefault(enableSeasonNameColor);
  }

  public static void setEnableSeasonNameColor(boolean enable) {
    SeasonHudClient.enableSeasonNameColor.set(enable);
  }

  public static int getSpringColor() {
    return (Integer) getOrDefault(springColor);
  }

  public static void setSpringColor(int rgbColor) {
    SeasonHudClient.springColor.set(rgbColor);
  }

  public static int getSummerColor() {
    return (Integer) getOrDefault(summerColor);
  }

  public static void setSummerColor(int rgbColor) {
    SeasonHudClient.summerColor.set(rgbColor);
  }

  public static int getAutumnColor() {
    return (Integer) getOrDefault(autumnColor);
  }

  public static void setAutumnColor(int rgbColor) {
    SeasonHudClient.autumnColor.set(rgbColor);
  }

  public static int getWinterColor() {
    return (Integer) getOrDefault(winterColor);
  }

  public static void setWinterColor(int rgbColor) {
    SeasonHudClient.winterColor.set(rgbColor);
  }

  public static int getDryColor() {
    return (Integer) getOrDefault(dryColor);
  }

  public static void setDryColor(int rgbColor) {
    SeasonHudClient.dryColor.set(rgbColor);
  }

  public static int getWetColor() {
    return (Integer) getOrDefault(wetColor);
  }

  public static void setWetColor(int rgbColor) {
    SeasonHudClient.wetColor.set(rgbColor);
  }

  public static boolean getShowTropicalSeason() {
    return (Boolean) getOrDefault(showTropicalSeason);
  }

  public static void setShowTropicalSeason(boolean enable) {
    SeasonHudClient.showTropicalSeason.set(enable);
  }

  public static boolean getShowSubSeason() {
    return (Boolean) getOrDefault(showSubSeason);
  }

  public static void setShowSubSeason(boolean enable) {
    SeasonHudClient.showSubSeason.set(enable);
  }

  public static ShowDay getShowDay() {
    return (ShowDay) getOrDefault(showDay);
  }

  public static void setShowDay(ShowDay showDay) {
    SeasonHudClient.showDay.set(showDay);
  }

  public static boolean getShowDefaultWhenMinimapHidden() {
    return (Boolean) getOrDefault(showDefaultWhenMinimapHidden);
  }

  public static void setShowDefaultWhenMinimapHidden(boolean enable) {
    SeasonHudClient.showDefaultWhenMinimapHidden.set(enable);
  }

  //Minimap
  public static boolean getEnableMinimapIntegration() {
    return (Boolean) getOrDefault(enableMinimapIntegration);
  }

  public static void setEnableMinimapIntegration(boolean enable) {
    SeasonHudClient.enableMinimapIntegration.set(enable);
  }

  //Journeymap
  public static boolean getJourneyMapAboveMap() {
    return (Boolean) getOrDefault(journeyMapAboveMap);
  }

  public static void setJourneyMapAboveMap(boolean enable) {
    SeasonHudClient.journeyMapAboveMap.set(enable);
  }

  public static boolean getJourneyMapMacOS() {
    return (Boolean) getOrDefault(journeyMapMacOS);
  }

  public static void setJourneyMapMacOS(boolean enable) {
    SeasonHudClient.journeyMapMacOS.set(enable);
  }
}
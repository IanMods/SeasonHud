package club.iananderson.seasonhud.impl.season;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.DefaultValues.Client;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.util.Rgb;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import net.minecraft.network.chat.Component;

@SuppressWarnings("checkstyle:AvoidEscapedUnicodeCharacters")
public enum Seasons {
  SPRING(0, "desc.seasonhud.season.spring", "\uEA00", Client.DEFAULT_SPRING_COLOR, SeasonHudClient.getSpringColor(),
      Rgb.seasonMap(SeasonHudClient.getSpringColor())),

  SUMMER(1, "desc.seasonhud.season.summer", "\uEA01", Client.DEFAULT_SUMMER_COLOR, SeasonHudClient.getSummerColor(),
      Rgb.seasonMap(SeasonHudClient.getSummerColor())),

  AUTUMN(2, "desc.seasonhud.season.autumn", "\uEA02", Client.DEFAULT_AUTUMN_COLOR, SeasonHudClient.getAutumnColor(),
      Rgb.seasonMap(SeasonHudClient.getAutumnColor())),

  WINTER(3, "desc.seasonhud.season.winter", "\uEA03", Client.DEFAULT_WINTER_COLOR, SeasonHudClient.getWinterColor(),
      Rgb.seasonMap(SeasonHudClient.getWinterColor())),

  DRY(4, "desc.seasonhud.season.dry", "\uEA04", Client.DEFAULT_DRY_COLOR, SeasonHudClient.getDryColor(),
      Rgb.seasonMap(SeasonHudClient.getDryColor())),

  WET(5, "desc.seasonhud.season.wet", "\uEA05", Client.DEFAULT_WET_COLOR, SeasonHudClient.getWetColor(),
      Rgb.seasonMap(SeasonHudClient.getWetColor())),

  NULL(100, "desc.seasonhud.season.null", "\uEA99", 16777215, 16777215, Rgb.seasonMap(16777215));

  public static final EnumSet<Seasons> SEASONS_ENUM_LIST = EnumSet.allOf(Seasons.class);
  private final int id;
  private final String key;
  private final String seasonIconChar;
  private final int defaultColor;
  private final Map<String, Integer> rgbMap;
  private final String seasonNameSerialized;
  private final Component seasonNameTranslated;
  private int seasonColor;

  Seasons(int id, String key, String iconChar, int defaultColor, int seasonColor, Map<String, Integer> rgbMap) {
    this.id = id;
    this.key = key;
    this.seasonIconChar = iconChar;
    this.defaultColor = defaultColor;
    this.seasonColor = seasonColor;
    this.rgbMap = rgbMap;
    this.seasonNameSerialized = this.toString().toLowerCase(Locale.ROOT);
    this.seasonNameTranslated = Common.translatedText(key);
  }

  public int getId() {
    return this.id;
  }

  public String getTranslationKey() {
    return this.key;
  }

  public String getIconChar() {
    return this.seasonIconChar;
  }

  public int getSeasonColor() {
    return this.seasonColor;
  }

  public void setSeasonColor(int rgbColor) {
    Seasons season = this;
    this.seasonColor = rgbColor;

    switch (season) {
      case SPRING -> SeasonHudClient.setSpringColor(rgbColor);
      case SUMMER -> SeasonHudClient.setSummerColor(rgbColor);
      case AUTUMN -> SeasonHudClient.setAutumnColor(rgbColor);
      case WINTER -> SeasonHudClient.setWinterColor(rgbColor);
      case DRY -> SeasonHudClient.setDryColor(rgbColor);
      case WET -> SeasonHudClient.setWetColor(rgbColor);
      default -> throw new IllegalStateException("Unexpected value: " + season);
    }
  }

  public int getDefaultColor() {
    return this.defaultColor;
  }

  public Map<String, Integer> getRgbMap() {
    return this.rgbMap;
  }

  public String getSeasonNameSerialized() {
    return this.seasonNameSerialized;
  }

  public Component getSeasonNameTranslated() {
    return this.seasonNameTranslated;
  }
}
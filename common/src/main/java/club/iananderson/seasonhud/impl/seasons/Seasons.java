package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.util.Rgb;
import java.util.EnumSet;
import java.util.Map;
import net.minecraft.network.chat.Component;

public enum Seasons {
  SPRING(0, Component.translatable("desc.seasonhud.season.spring"), "spring", "\uEA00", SeasonHudClient.DEFAULT_SPRING_COLOR,
         SeasonHudClient.getSpringColor(), Rgb.seasonMap(SeasonHudClient.getSpringColor())),

  SUMMER(1, Component.translatable("desc.seasonhud.season.summer"), "summer", "\uEA01", SeasonHudClient.DEFAULT_SUMMER_COLOR,
         SeasonHudClient.getSummerColor(), Rgb.seasonMap(SeasonHudClient.getSummerColor())),

  AUTUMN(2, Component.translatable("desc.seasonhud.season.autumn"), "autumn", "\uEA02", SeasonHudClient.DEFAULT_AUTUMN_COLOR,
         SeasonHudClient.getAutumnColor(), Rgb.seasonMap(SeasonHudClient.getAutumnColor())),

  WINTER(3, Component.translatable("desc.seasonhud.season.winter"), "winter", "\uEA03", SeasonHudClient.DEFAULT_WINTER_COLOR,
         SeasonHudClient.getWinterColor(), Rgb.seasonMap(SeasonHudClient.getWinterColor())),

  DRY(4, Component.translatable("desc.seasonhud.season.dry"), "dry", "\uEA04", SeasonHudClient.DEFAULT_DRY_COLOR,
      SeasonHudClient.getDryColor(), Rgb.seasonMap(SeasonHudClient.getDryColor())),

  WET(5, Component.translatable("desc.seasonhud.season.wet"), "wet", "\uEA05", SeasonHudClient.DEFAULT_WET_COLOR,
      SeasonHudClient.getWetColor(), Rgb.seasonMap(SeasonHudClient.getWetColor())),

  NULL(100, Component.translatable("desc.seasonhud.season.null"), "null", "\uEA99", 16777215, 16777215,
       Rgb.seasonMap(16777215));

  public static final EnumSet<Seasons> SEASONS_ENUM_LIST = EnumSet.allOf(Seasons.class);
  private final int id;
  private final Component seasonName;
  private final String seasonFileName;
  private final String seasonIconChar;
  private final int defaultColor;
  private final Map<String, Integer> rgbMap;
  private int seasonColor;

  Seasons(int id, Component seasonName, String fileName, String iconChar, int defaultColor, int seasonColor,
      Map<String, Integer> rgbMap) {
    this.id = id;
    this.seasonName = seasonName;
    this.seasonFileName = fileName;
    this.seasonIconChar = iconChar;
    this.defaultColor = defaultColor;
    this.seasonColor = seasonColor;
    this.rgbMap = rgbMap;
  }

  public int getId() {
    return this.id;
  }

  public Component getSeasonName() {
    return this.seasonName;
  }

  public String getFileName() {
    return this.seasonFileName;
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
    }
  }

  public int getDefaultColor() {
    return this.defaultColor;
  }

  public Map<String, Integer> getRgbMap() {
    return this.rgbMap;
  }
}
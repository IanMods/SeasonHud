package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.config.SeasonHudClient;
import java.time.LocalDateTime;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;

public class CurrentSeason {
  private final String currentSeason;
  private final String currentSubSeason;
  private final String seasonFileName;
  private final long seasonDate;
  private final int seasonDuration;
  private Style seasonFormat;

  public CurrentSeason(Minecraft mc) {
    Player player = mc.player;
    this.seasonFormat = Style.EMPTY;
    this.currentSeason = CommonSeasonHelper.commonSeasons.getCurrentSeason(player);
    this.currentSubSeason = CommonSeasonHelper.commonSeasons.getCurrentSubSeason(player);
    this.seasonFileName = CommonSeasonHelper.commonSeasons.getSeasonFileName(player);
    this.seasonDate = CommonSeasonHelper.commonSeasons.getDate(player);
    this.seasonDuration = CommonSeasonHelper.commonSeasons.seasonDuration(player);
  }

  public static CurrentSeason getInstance(Minecraft mc) {
    return new CurrentSeason(mc);
  }

  public String getSubSeasonLowerCase() {
    String lowerSubSeason = currentSubSeason.toLowerCase();
    if (Common.fabricSeasonsLoaded()) {
      return currentSeason.toLowerCase();
    }
    else {
      return currentSeason.toLowerCase() + "." + lowerSubSeason.substring(0, lowerSubSeason.indexOf("_"));
    }
  }

  public String getSeasonLowerCase() {
    return currentSeason.toLowerCase();
  }

  public Component getSeasonKey(boolean showSubSeason) {
    String season = showSubSeason ? getSubSeasonLowerCase() : getSeasonLowerCase();

    if (!Calendar.validDetailedMode() || Common.fabricSeasonsLoaded()) {
      season = getSeasonLowerCase();
    }

    if (Common.eclipticSeasonsLoaded() && Calendar.validDetailedMode() && SeasonHudClient.getShowSubSeason()) {
      season = currentSubSeason;

      if (currentSubSeason.equals("MID_NULL")) {
        return Common.translatedText("desc.seasonhud.season" + "." + getSubSeasonLowerCase());
      }

      else {
        return Common.translatedText("info.eclipticseasons.environment.solar_term" + "." + season);
      }
    }

    return Common.translatedText("desc.seasonhud.season" + "." + season);
  }

  //Get the current season and match it to the icon for the font
  public String getSeasonIcon() {
    for (Seasons season : Seasons.values()) {
      if (season.getFileName().equals(seasonFileName)) {
        return season.getIconChar();
      }
    }
    return "Icon Error";
  }

  //Localized name with icon
  public Component getText(ShowDay showDay, boolean showSubSeason) {
    Component text = Common.literalText("");
    Component seasonKey = getSeasonKey(showSubSeason);

    switch (showDay) {
      case NONE:
        text = Common.translatedText(ShowDay.NONE.getKey(), seasonKey);
        break;

      case SHOW_DAY:
        text = Common.translatedText(ShowDay.SHOW_DAY.getKey(), seasonKey, seasonDate);
        break;

      case SHOW_WITH_TOTAL_DAYS:
        text = Common.translatedText(ShowDay.SHOW_WITH_TOTAL_DAYS.getKey(), seasonKey, seasonDate, seasonDuration);
        break;

      case SHOW_WITH_MONTH:
        if (CommonSeasonHelper.commonSeasons.isSeasonTiedWithSystemTime()) {
          int systemMonth = LocalDateTime.now().getMonth().getValue();
          String systemMonthString = String.valueOf(systemMonth);

          if (systemMonth < 10) {
            systemMonthString = "0" + systemMonthString;
          }

          Component currentMonth = Common.translatedText("desc.seasonhud.month" + "." + systemMonthString);

          text = Common.translatedText(ShowDay.SHOW_WITH_MONTH.getKey(), seasonKey, currentMonth, seasonDate);

          if (!Calendar.validDetailedMode()) {
            text = Common.translatedText(ShowDay.NONE.getKey(), seasonKey);
          }
        }
        else {
          text = Common.translatedText(ShowDay.SHOW_DAY.getKey(), seasonKey, seasonDate);
        }
        break;
    }

    return text;
  }

  //Get the current season and match it to the icon for the font
  public int getTextColor() {
    for (Seasons season : Seasons.values()) {
      if (season.getFileName().equals(seasonFileName)) {
        return season.getSeasonColor();
      }
    }
    return 16777215;
  }

  public MutableComponent getSeasonHudTextNoFormat() {
    Component seasonIcon = Common.translatedText("desc.seasonhud.hud.icon", getSeasonIcon())
        .withStyle(Common.SEASON_ICON_STYLE);
    ShowDay showDay = SeasonHudClient.getShowDay();
    boolean showSubSeason = SeasonHudClient.getShowSubSeason();

    MutableComponent seasonText = getText(showDay, showSubSeason).copy();

    return Common.translatedText("desc.seasonhud.hud.combined", seasonIcon, seasonText);
  }

  public MutableComponent getSeasonHudText() {
    MutableComponent seasonIcon = Common.translatedText("desc.seasonhud.hud.icon", getSeasonIcon());
    ShowDay showDay = SeasonHudClient.getShowDay();
    boolean showSubSeason = SeasonHudClient.getShowSubSeason();

    MutableComponent seasonText = getText(showDay, showSubSeason).copy();

    if (SeasonHudClient.getEnableSeasonNameColor()) {
      seasonFormat = Style.EMPTY.withColor(getTextColor());
    }

    return Common.translatedText("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
                                 seasonText.withStyle(seasonFormat));
  }

  public MutableComponent getSeasonMenuText(Seasons season, int newRgb, boolean seasonShort) {
    MutableComponent seasonIcon = Common.translatedText("desc.seasonhud.hud.icon", season.getIconChar());
    MutableComponent seasonText = Common.translatedText(ShowDay.NONE.getKey(), season.getSeasonName());

    if (SeasonHudClient.getEnableSeasonNameColor()) {
      seasonFormat = Style.EMPTY.withColor(newRgb);
    }

    if (season == Seasons.DRY && seasonShort) {
      seasonText = Common.translatedText("menu.seasonhud.color.season.dry.editbox");
    }

    if (season == Seasons.WET && seasonShort) {
      seasonText = Common.translatedText("menu.seasonhud.color.season.wet.editbox");
    }

    return Common.translatedText("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
                                 seasonText.withStyle(seasonFormat));
  }

  public MutableComponent getSeasonHudConfigText(ShowDay showDay, boolean showSubSeason) {
    MutableComponent seasonIcon = Common.translatedText("desc.seasonhud.hud.icon", getSeasonIcon());
    MutableComponent seasonText = getText(showDay, showSubSeason).copy();

    if (SeasonHudClient.getEnableSeasonNameColor()) {
      seasonFormat = Style.EMPTY.withColor(getTextColor());
    }

    return Common.translatedText("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
                                 seasonText.withStyle(seasonFormat));
  }
}
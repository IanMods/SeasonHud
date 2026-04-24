package club.iananderson.seasonhud.impl.season;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.impl.season.mods.CommonSeasonHelper;
import club.iananderson.seasonhud.impl.season.mods.SeasonModHelper;
import club.iananderson.seasonhud.impl.season.mods.SeasonMods;
import club.iananderson.seasonhud.platform.Services;
import club.iananderson.seasonhud.util.Rgb;
import java.time.LocalDateTime;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;

public class CurrentSeason {
  private final Minecraft mc;
  private final Player player;
  private final SeasonModHelper seasonModHelper;
  private final Seasons currentSeason;
  private final SubSeasons currentSubSeason;
  private final Style seasonIconFormat;
  private Style seasonFormat;

  private CurrentSeason(Minecraft mc) {
    this.mc = mc;
    this.player = mc.player;
    this.seasonFormat = Style.EMPTY;
    this.seasonIconFormat = Common.SEASON_ICON_STYLE;
    this.seasonModHelper = CommonSeasonHelper.commonSeasons.getHelper();
    this.currentSeason = CommonSeasonHelper.commonSeasons.getHelper().getCurrentSeason(player);
    this.currentSubSeason = CommonSeasonHelper.commonSeasons.getHelper().getCurrentSubSeason(player);
  }

  public static CurrentSeason getInstance(Minecraft mc) {
    return new CurrentSeason(mc);
  }

  public static Component getTranslation(Seasons season, SubSeasons subSeasons, boolean showSubSeason) {
    String seasonKey = season.getTranslationKey();
    String subSeasonKey = subSeasons.getSubSeasonKey();
    Component translatedText = Common.translatedText(seasonKey);

    if (showSubSeason) {
      translatedText = Common.translatedText(seasonKey + subSeasonKey);
    }

    return translatedText;
  }

  public static Component getText(Seasons season, SubSeasons subSeasons, ShowDay showDay, long seasonDate,
      int seasonDuration, boolean showSubSeason) {
    Component text;
    Component seasonTranslation = CurrentSeason.getTranslation(season, subSeasons, showSubSeason);

    switch (showDay) {
      case NONE:
        text = Common.translatedText(ShowDay.NONE.getKey(), seasonTranslation);
        break;

      case SHOW_DAY:
        text = Common.translatedText(ShowDay.SHOW_DAY.getKey(), seasonTranslation, seasonDate);
        break;

      case SHOW_WITH_TOTAL_DAYS:
        text = Common.translatedText(ShowDay.SHOW_WITH_TOTAL_DAYS.getKey(), seasonTranslation, seasonDate,
                                     seasonDuration);
        break;

      case SHOW_WITH_MONTH:
        if (CommonSeasonHelper.commonSeasons.getHelper().isSeasonTiedWithSystemTime()) {
          int systemMonth = LocalDateTime.now().getMonth().getValue();
          String systemMonthString = String.valueOf(systemMonth);

          if (systemMonth < 10) {
            systemMonthString = "0" + systemMonthString;
          }

          Component currentMonth = Common.translatedText("desc.seasonhud.month" + "." + systemMonthString);

          text = Common.translatedText(ShowDay.SHOW_WITH_MONTH.getKey(), seasonTranslation, currentMonth, seasonDate);

        } else {
          text = Common.translatedText(ShowDay.SHOW_DAY.getKey(), seasonTranslation, seasonDate);
        }
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + showDay);
    }

    return text;
  }

  private Component getTranslation(boolean showSubSeason) {
    String seasonKey = currentSeason.getTranslationKey();
    String subSeasonKey = currentSubSeason.getSubSeasonKey();
    Component translatedText = Common.translatedText(seasonKey);

    if (Calendar.validDetailedMode(player) && showSubSeason) {
      if (SeasonMods.ECLIPTIC.modLoaded()) {
        translatedText = Services.SEASON.eclipticSeasonComponent(player);
      } else {
        translatedText = Common.translatedText(seasonKey + subSeasonKey);
      }
    }

    if (CurrentFertility.getInstance(mc).shouldOverwriteSeason()) {
      translatedText = CurrentFertility.getInstance(mc).getHudTextNoFormat();
    }

    return translatedText;
  }

  // Localized name with icon
  private Component getText(ShowDay showDay, boolean showSubSeason) {
    Component text;
    Component seasonTranslation = getTranslation(showSubSeason);

    long seasonDate = seasonModHelper.getDate(player);
    int seasonDuration = seasonModHelper.seasonDurationDays(player);

    switch (showDay) {
      case NONE:
        text = Common.translatedText(ShowDay.NONE.getKey(), seasonTranslation);
        break;

      case SHOW_DAY:
        text = Common.translatedText(ShowDay.SHOW_DAY.getKey(), seasonTranslation, seasonDate);
        break;

      case SHOW_WITH_TOTAL_DAYS:
        text = Common.translatedText(ShowDay.SHOW_WITH_TOTAL_DAYS.getKey(), seasonTranslation, seasonDate,
                                     seasonDuration);
        break;

      case SHOW_WITH_MONTH:
        if (seasonModHelper.isSeasonTiedWithSystemTime()) {
          int systemMonth = LocalDateTime.now().getMonth().getValue();
          String systemMonthString = String.valueOf(systemMonth);

          if (systemMonth < 10) {
            systemMonthString = "0" + systemMonthString;
          }

          Component currentMonth = Common.translatedText("desc.seasonhud.month" + "." + systemMonthString);

          text = Common.translatedText(ShowDay.SHOW_WITH_MONTH.getKey(), seasonTranslation, currentMonth, seasonDate);

          if (!Calendar.validDetailedMode(player)) {
            text = Common.translatedText(ShowDay.NONE.getKey(), seasonTranslation);
          }
        } else {
          text = Common.translatedText(ShowDay.SHOW_DAY.getKey(), seasonTranslation, seasonDate);
        }
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + showDay);
    }

    return text;
  }

  public MutableComponent getHudTextNoFormat() {
    MutableComponent seasonIcon = Common.translatedText("desc.seasonhud.hud.icon", currentSeason.getIconChar());
    MutableComponent seasonText = getText(SeasonHudClient.getShowDay(), SeasonHudClient.getShowSubSeason()).copy();

    return Common.translatedText("desc.seasonhud.hud.combined", seasonIcon.withStyle(seasonIconFormat), seasonText);
  }

  public MutableComponent getHudText() {
    MutableComponent seasonText = getText(SeasonHudClient.getShowDay(), SeasonHudClient.getShowSubSeason()).copy();
    MutableComponent seasonIcon = Common.translatedText("desc.seasonhud.hud.icon", currentSeason.getIconChar());

    if (SeasonHudClient.getEnableSeasonNameColor()) {
      if (CurrentFertility.getInstance(mc).shouldOverwriteSeason()) {
        int mixedColor = Rgb.mixRgb(currentSeason, CommonSeasonHelper.commonSeasons.getHelper().fertility(player));

        seasonFormat = Style.EMPTY.withColor(mixedColor);
      } else {
        seasonFormat = Style.EMPTY.withColor(currentSeason.getSeasonColor());
      }
    }

    return Common.translatedText("desc.seasonhud.hud.combined", seasonIcon.withStyle(seasonIconFormat),
                                 seasonText.withStyle(seasonFormat));
  }

  public MutableComponent getConfigText(ShowDay showDay, boolean showSubSeason, boolean seasonColor) {
    MutableComponent seasonIcon = Common.translatedText("desc.seasonhud.hud.icon", currentSeason.getIconChar());
    MutableComponent seasonText = getText(showDay, showSubSeason).copy();

    if (seasonColor) {
      if (SeasonHudClient.getEnableSeasonNameColor()) {
        if (CurrentFertility.getInstance(mc).shouldOverwriteSeason()) {
          int mixedColor = Rgb.mixRgb(currentSeason, CommonSeasonHelper.commonSeasons.getHelper().fertility(player));

          seasonFormat = Style.EMPTY.withColor(mixedColor);
        } else {
          seasonFormat = Style.EMPTY.withColor(currentSeason.getSeasonColor());
        }
      }
    }

    return Common.translatedText("desc.seasonhud.hud.combined", seasonIcon.withStyle(seasonIconFormat),
                                 seasonText.withStyle(seasonFormat));
  }

  public MutableComponent journeymapText() {
    MutableComponent seasonText = getText(SeasonHudClient.getShowDay(), SeasonHudClient.getShowSubSeason()).copy();

    if (SeasonHudClient.getEnableSeasonNameColor()) {
      if (CurrentFertility.getInstance(mc).shouldOverwriteSeason()) {
        int mixedColor = Rgb.mixRgb(currentSeason, CommonSeasonHelper.commonSeasons.getHelper().fertility(player));

        seasonFormat = Style.EMPTY.withColor(mixedColor);
      } else {
        seasonFormat = Style.EMPTY.withColor(currentSeason.getSeasonColor());
      }
    }

    return Common.literalText("  ").append(seasonText).withStyle(seasonFormat);
  }
}
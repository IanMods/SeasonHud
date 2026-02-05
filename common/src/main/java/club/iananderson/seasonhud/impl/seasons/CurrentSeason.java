package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.config.SeasonHudClient;
import java.time.LocalDateTime;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;

public class CurrentSeason {
  private final Seasons currentSeason;
  private final SubSeasons currentSubSeason;
  private final long seasonDate;
  private final int seasonDuration;
  private Style seasonFormat;

  public CurrentSeason(Minecraft mc) {
    Player player = mc.player;
    this.seasonFormat = Style.EMPTY;
    this.currentSeason = CommonSeasonHelper.commonSeasons.getCurrentSeason(player);
    this.currentSubSeason = CommonSeasonHelper.commonSeasons.getCurrentSubSeason(player);
    this.seasonDate = CommonSeasonHelper.commonSeasons.getDate(player);
    this.seasonDuration = CommonSeasonHelper.commonSeasons.seasonDurationDays(player);
  }

  public static CurrentSeason getInstance(Minecraft mc) {
    return new CurrentSeason(mc);
  }

  public Component getTranslation(boolean showSubSeason) {
    String seasonKey = currentSeason.getTranslationKey();
    String subSeasonKey = currentSubSeason.getSubSeasonKey();

    if (Calendar.validDetailedMode() && showSubSeason) {
      return Common.translatedText(seasonKey + subSeasonKey);
    } else {
      return Common.translatedText(seasonKey);

    }
  }

  // Localized name with icon
  public Component getText(ShowDay showDay, boolean showSubSeason) {
    Component text;
    Component seasonKey = getTranslation(showSubSeason);

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
        } else {
          text = Common.translatedText(ShowDay.SHOW_DAY.getKey(), seasonKey, seasonDate);
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

    // TODO: Double check that the icon style is still applies
    return Common.translatedText("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
        seasonText);
  }

  public MutableComponent getHudText() {
    MutableComponent seasonText = getText(SeasonHudClient.getShowDay(), SeasonHudClient.getShowSubSeason()).copy();
    MutableComponent seasonIcon = Common.translatedText("desc.seasonhud.hud.icon", currentSeason.getIconChar());

    if (SeasonHudClient.getEnableSeasonNameColor()) {
      seasonFormat = Style.EMPTY.withColor(currentSeason.getSeasonColor());
    }

    return Common.translatedText("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
        seasonText.withStyle(seasonFormat));
  }

  public MutableComponent getMenuText(Seasons season, int newRgb, boolean seasonShort) {
    if (SeasonHudClient.getEnableSeasonNameColor()) {
      seasonFormat = Style.EMPTY.withColor(newRgb);
    }

    MutableComponent seasonText = Common.translatedText(ShowDay.NONE.getKey(), season.getSeasonNameTranslated());
    MutableComponent seasonIcon = Common.translatedText("desc.seasonhud.hud.icon", season.getIconChar());

    // Removes "Season" from Dry Season if the total color widget width is too large for the screen
    if (season == Seasons.DRY && seasonShort) {
      seasonText = Common.translatedText("menu.seasonhud.color.season.dry.editbox");
    }

    // Removes "Season" from Wet Season if the total color widget width is too large for the screen
    if (season == Seasons.WET && seasonShort) {
      seasonText = Common.translatedText("menu.seasonhud.color.season.wet.editbox");
    }

    return Common.translatedText("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
        seasonText.withStyle(seasonFormat));
  }

  public MutableComponent getConfigText(ShowDay showDay, boolean showSubSeason, boolean seasonColor) {
    MutableComponent seasonIcon = Common.translatedText("desc.seasonhud.hud.icon", currentSeason.getIconChar());
    MutableComponent seasonText = getText(showDay, showSubSeason).copy();

    if (seasonColor) {
      seasonFormat = Style.EMPTY.withColor(currentSeason.getSeasonColor());
    }

    return Common.translatedText("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
        seasonText.withStyle(seasonFormat));
  }
}
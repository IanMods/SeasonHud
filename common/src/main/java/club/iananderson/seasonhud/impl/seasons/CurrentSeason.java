package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.config.Config;
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
    return currentSeason.toLowerCase() + "." + lowerSubSeason.substring(0, lowerSubSeason.indexOf("_"));
  }

  public String getSeasonLowerCase() {
    return currentSeason.toLowerCase();
  }

  public Component getSeasonKey() {
    String season = Config.getShowSubSeason() ? getSubSeasonLowerCase() : getSeasonLowerCase();

    if (!Calendar.validDetailedMode() || Common.fabricSeasonsLoaded()) {
      season = getSeasonLowerCase();
    }

    if(Common.eclipticSeasonsLoaded() && Calendar.validDetailedMode() && Config.getShowSubSeason()){
      season = currentSubSeason;
      return Component.translatable("info.eclipticseasons.environment.solar_term." + season);
    }

    return new TranslatableComponent("desc.seasonhud.season." + season);
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

  //Localized name for the hud with icon
  public Component getText() {
    Component text = new TextComponent("");

    switch (Config.getShowDay()) {
      case NONE:
        text = new TranslatableComponent(ShowDay.NONE.getKey(), getSeasonKey());
        break;

      case SHOW_DAY:
        text = new TranslatableComponent(ShowDay.SHOW_DAY.getKey(), getSeasonKey(), seasonDate);

        if (!Calendar.validDetailedMode()) {
          text = new TranslatableComponent(ShowDay.NONE.getKey(), getSeasonKey());
        }
        break;

      case SHOW_WITH_TOTAL_DAYS:
        text = new TranslatableComponent(ShowDay.SHOW_WITH_TOTAL_DAYS.getKey(), getSeasonKey(), seasonDate,
                                         seasonDuration);

        if (!Calendar.validDetailedMode()) {
          text = new TranslatableComponent(ShowDay.NONE.getKey(), getSeasonKey());
        }
        break;

      case SHOW_WITH_MONTH:
        if (CommonSeasonHelper.commonSeasons.isSeasonTiedWithSystemTime()) {
          int systemMonth = LocalDateTime.now().getMonth().getValue();
          String systemMonthString = String.valueOf(systemMonth);

          if (systemMonth < 10) {
            systemMonthString = "0" + systemMonthString;
          }

          Component currentMonth = new TranslatableComponent("desc.seasonhud.month." + systemMonthString);

          text = new TranslatableComponent(ShowDay.SHOW_WITH_MONTH.getKey(), getSeasonKey(), currentMonth, seasonDate);

          if (!Calendar.validDetailedMode()) {
            text = new TranslatableComponent(ShowDay.NONE.getKey(), getSeasonKey());
          }
        }
        else {
          text = new TranslatableComponent(ShowDay.SHOW_DAY.getKey(), getSeasonKey(), seasonDate);
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
    Component seasonIcon = new TranslatableComponent("desc.seasonhud.hud.icon", getSeasonIcon()).withStyle(
        Common.SEASON_ICON_STYLE);
    MutableComponent seasonText = getText().copy();

    return new TranslatableComponent("desc.seasonhud.hud.combined", seasonIcon, seasonText);
  }

  public MutableComponent getSeasonHudText() {
    MutableComponent seasonIcon = new TranslatableComponent("desc.seasonhud.hud.icon", getSeasonIcon());
    MutableComponent seasonText = getText().copy();

    if (Config.getEnableSeasonNameColor()) {
      seasonFormat = Style.EMPTY.withColor(getTextColor());
    }

    return new TranslatableComponent("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
                                     seasonText.withStyle(seasonFormat));
  }

  public MutableComponent getSeasonMenuText(Seasons season, int newRgb, boolean seasonShort) {
    MutableComponent seasonIcon = new TranslatableComponent("desc.seasonhud.hud.icon", season.getIconChar());
    MutableComponent seasonText = new TranslatableComponent(ShowDay.NONE.getKey(), season.getSeasonName());

    if (Config.getEnableSeasonNameColor()) {
      seasonFormat = Style.EMPTY.withColor(newRgb);
    }

    if (season == Seasons.DRY && seasonShort) {
      seasonText = new TranslatableComponent("menu.seasonhud.color.season.dry.editbox");
    }

    if (season == Seasons.WET && seasonShort) {
      seasonText = new TranslatableComponent("menu.seasonhud.color.season.wet.editbox");
    }

    return new TranslatableComponent("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
                                     seasonText.withStyle(seasonFormat));
  }
}
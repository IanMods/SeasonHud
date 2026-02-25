package club.iananderson.seasonhud.impl.season.mods.protomanlyweather;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import club.iananderson.seasonhud.impl.season.components.Fertility;
import club.iananderson.seasonhud.impl.season.components.Months;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.impl.season.mods.SeasonModHelper;
import club.iananderson.seasonhud.platform.Services;
import java.util.Optional;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class ProtoManlyWeatherHelper implements SeasonModHelper {
  @Override
  public Optional<Item> calendar() {
    return Services.SEASON.protoManlyWeatherCalendar();
  }

  @Override
  public boolean isTropicalSeason(Player player) {
    return false;
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    return false;
  }

  @Override
  public SubSeasons getCurrentSubSeason(Player player) {
    return Services.SEASON.protoManlyWeatherMonth(player).getSubSeason();
  }

  @Override
  public Seasons getCurrentSeason(Player player) {
    return Services.SEASON.protoManlyWeatherMonth(player).getSeason();
  }

  @Override
  public long getDate(Player player) {
    Months currentMonth = Services.SEASON.protoManlyWeatherMonth(player);
    SubSeasons currentSubSeason = currentMonth.getSubSeason();

    int dayOfMonth = Services.SEASON.protoManlyWeatherCurrentDayOfMonth(player);
    int daysInMonth = Services.SEASON.protoManlyWeatherTotalDaysInMonth(player);

    // Assumes that there are 3 months per season
    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode(player)) {
      return dayOfMonth;
    } else {
      // Early = 0; Mid = 1; Late = 2
      return dayOfMonth + ((long) currentSubSeason.ordinal() * daysInMonth);
    }
  }

  @Override
  public int seasonDurationDays(Player player) {
    int subSeasonDuration = Services.SEASON.protoManlyWeatherTotalDaysInMonth(player);
    int seasonDuration = subSeasonDuration * 3;

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode(player)) {
      return subSeasonDuration;
    }

    return seasonDuration;
  }

  @Override
  public boolean infertileBiome(Player player) {
    return false;
  }

  @Override
  public boolean alwaysWinterBiome(Player player) {
    return false;
  }

  @Override
  public boolean undergroundFertile(Player player) {
    return false;
  }

  @Override
  public Fertility fertility(Player player) {
    return SeasonModHelper.super.fertility(player);
  }

  @Override
  public void debugHud(GuiGraphics graphics) {
    Services.SEASON.protoManlyDebug(graphics);
  }
}

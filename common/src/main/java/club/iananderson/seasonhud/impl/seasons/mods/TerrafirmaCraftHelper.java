package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.Fertility;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.impl.seasons.SubSeasons;
import club.iananderson.seasonhud.platform.Services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class TerrafirmaCraftHelper implements SeasonModHelper {
  public TerrafirmaCraftHelper() {
  }

  @Override
  public Item calendar() {
    return null;
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
    return Services.SEASON.currentTerraFirmaCraftSeason();
  }

  @Override
  public Seasons getCurrentSeason(Player player) {
    return Services.SEASON.currentTerraFirmaCraftSeason();
  }

  @Override
  public long getDate(Player player) {
    Month currentMonth = Calendars.CLIENT.getCalendarMonthOfYear();
    Season currentSeason = currentMonth.getSeason();
    List<Month> currentSeasonMonths = getSeasonMonths(currentSeason);

    int subSeasonPos = currentSeasonMonths.indexOf(currentMonth);
    int dayOfMonth = Calendars.CLIENT.getCalendarDayOfMonth();
    int daysInMonth = Calendars.CLIENT.getCalendarDaysInMonth();

    if (SeasonHudClient.getShowSubSeason()) {
      return dayOfMonth;
    } else {
      return dayOfMonth + ((long) subSeasonPos * daysInMonth);
    }
  }

  @Override
  public int seasonDuration(Player player) {
    int daysInMonth = Calendars.CLIENT.getCalendarDaysInMonth();

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode()) {
      return daysInMonth;
    } else {
      return daysInMonth * 3;
    }
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
    return true;
  }

  @Override
  public Fertility fertility(Player player) {
    return Fertility.FERTILE;
  }

}
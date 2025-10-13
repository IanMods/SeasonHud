package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.dries007.tfc.client.ClientHelpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.calendar.Season;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class TerrafirmaCraftHelper implements IModHelper {
  public TerrafirmaCraftHelper() {
  }

  private Month getCurrentMonth() {
    return Calendars.get().getHemispheralCalendarMonthOfYear(ClientHelpers.inNorthernHemisphere());
  }

  private List<Month> getSeasonMonths(Season season) {
    List<Month> SEASON = new ArrayList<>();

    switch (season) {
      case WINTER -> {
        SEASON.add(SubSeason.EARLY.getWinter());
        SEASON.add(SubSeason.MID.getWinter());
        SEASON.add(SubSeason.LATE.getWinter());
      }
      case SPRING -> {
        SEASON.add(SubSeason.EARLY.getSpring());
        SEASON.add(SubSeason.MID.getSpring());
        SEASON.add(SubSeason.LATE.getSpring());
      }
      case SUMMER -> {
        SEASON.add(SubSeason.EARLY.getSummer());
        SEASON.add(SubSeason.MID.getSummer());
        SEASON.add(SubSeason.LATE.getSummer());
      }
      case FALL -> {
        SEASON.add(SubSeason.EARLY.getAutumn());
        SEASON.add(SubSeason.MID.getAutumn());
        SEASON.add(SubSeason.LATE.getAutumn());
      }
    }

    return SEASON;
  }

  private String getSeasonPrefix() {
    Season season = getCurrentMonth().getSeason();
    HashMap<Month, String> SEASON = new HashMap<>();

    switch (season) {
      case WINTER -> {
        SEASON.put(SubSeason.EARLY.getWinter(), SubSeason.EARLY.getPrefix());
        SEASON.put(SubSeason.MID.getWinter(), SubSeason.MID.getPrefix());
        SEASON.put(SubSeason.LATE.getWinter(), SubSeason.LATE.getPrefix());
      }
      case SPRING -> {
        SEASON.put(SubSeason.EARLY.getSpring(), SubSeason.EARLY.getPrefix());
        SEASON.put(SubSeason.MID.getSpring(), SubSeason.MID.getPrefix());
        SEASON.put(SubSeason.LATE.getSpring(), SubSeason.LATE.getPrefix());
      }
      case SUMMER -> {
        SEASON.put(SubSeason.EARLY.getSummer(), SubSeason.EARLY.getPrefix());
        SEASON.put(SubSeason.MID.getSummer(), SubSeason.MID.getPrefix());
        SEASON.put(SubSeason.LATE.getSummer(), SubSeason.LATE.getPrefix());
      }
      case FALL -> {
        SEASON.put(SubSeason.EARLY.getAutumn(), SubSeason.EARLY.getPrefix());
        SEASON.put(SubSeason.MID.getAutumn(), SubSeason.MID.getPrefix());
        SEASON.put(SubSeason.LATE.getAutumn(), SubSeason.LATE.getPrefix());
      }
    }

    return SEASON.get(getCurrentMonth());
  }

  @Override
  public Item CALENDAR() {
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
  public String getCurrentSubSeason(Player player) {
    Season season = getCurrentMonth().getSeason();
    String prefix = getSeasonPrefix();

    if (season == Season.FALL) {
      return prefix + "AUTUMN";
    }
    else {
      return prefix + season.getSerializedName();
    }
  }

  @Override
  public String getCurrentSeason(Player player) {
    Season season = getCurrentMonth().getSeason();

    if (season == Season.FALL) {
      return "AUTUMN";
    }

    else {
      return season.getSerializedName();
    }
  }

  @Override
  public long getDate(Player player) {
    Season currentSeason = getCurrentMonth().getSeason();
    List<Month> currentSeasonMonths = getSeasonMonths(currentSeason);

    int subSeasonPos = currentSeasonMonths.indexOf(getCurrentMonth());
    int dayOfMonth = Calendars.get().getCalendarDayOfMonth();
    int daysInMonth = Calendars.get().getCalendarDaysInMonth();

    if (SeasonHudClient.getShowSubSeason()) {
      return dayOfMonth;
    }

    else {
      return dayOfMonth + ((long) subSeasonPos * daysInMonth);
    }
  }

  @Override
  public int seasonDuration(Player player) {
    int daysInMonth = Calendars.get().getCalendarDaysInMonth();

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode()) {
      return daysInMonth;
    }

    else {
      return daysInMonth * 3;
    }
  }

  /**
   * <h1>TerrafirmaCraft Seasons</h1>
   * <pre>
   * | Season | Early     | Mid     | Late     |
   * |--------|-----------|---------|----------|
   * | Winter | DECEMBER  | JANUARY | FEBRUARY |
   * | Spring | MARCH     | APRIL   | MAY      |
   * | Summer | JUNE      | JULY    | AUGUST   |
   * | Autumn | SEPTEMBER | OCTOBER | NOVEMBER |
   * </pre>
   **/
  private enum SubSeason {
    EARLY("EARLY_", Month.DECEMBER, Month.MARCH, Month.JUNE, Month.SEPTEMBER),

    MID("MID_", Month.JANUARY, Month.APRIL, Month.JULY, Month.OCTOBER),

    LATE("LATE_", Month.FEBRUARY, Month.MAY, Month.AUGUST, Month.NOVEMBER);

    private final String prefix;
    private final Month winter;
    private final Month spring;
    private final Month summer;
    private final Month autumn;

    SubSeason(String prefix, Month winter, Month spring, Month summer, Month autumn) {
      this.prefix = prefix;
      this.winter = winter;
      this.spring = spring;
      this.summer = summer;
      this.autumn = autumn;
    }

    public String getPrefix() {
      return this.prefix;
    }

    public Month getWinter() {
      return this.winter;
    }

    public Month getSpring() {
      return this.spring;
    }

    public Month getSummer() {
      return this.summer;
    }

    public Month getAutumn() {
      return this.autumn;
    }
  }
}
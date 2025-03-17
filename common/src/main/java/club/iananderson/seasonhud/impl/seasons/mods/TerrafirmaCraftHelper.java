package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.calendar.Season;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class TerrafirmaCraftHelper {
  public static Item CALENDAR = null;

  private TerrafirmaCraftHelper() {
  }

  //DECEMBER(-0.866F, Season.WINTER) -> Early Winter
  //JANUARY(-1.0F, Season.WINTER) -> Mid Winter
  //FEBRUARY(-0.866F, Season.WINTER) -> Late Winter

  //MARCH(-0.5F, Season.SPRING) -> Early Spring
  //APRIL(0.0F, Season.SPRING) -> Mid Spring
  //MAY(0.5F, Season.SPRING) -> Late Spring

  //JUNE(0.866F, Season.SUMMER) -> Early Summer
  //JULY(1.0F, Season.SUMMER) -> Mid Summer
  //AUGUST(0.866F, Season.SUMMER) -> Late Summer

  private static List<Month> getSeasonMonths(Season season) {
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

  private static String getSeasonPrefix(Month currentMonth) {
    Season season = currentMonth.getSeason();
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

    return SEASON.get(currentMonth);
  }

  public static String getCurrentSubSeason(Player player) {
    Month month = Calendars.CLIENT.getCalendarMonthOfYear();
    Season season = month.getSeason();
    String prefix = getSeasonPrefix(month);

    if (season == Season.FALL) {
      return prefix + "AUTUMN";
    }
    else {
      return prefix + season.getSerializedName();
    }
  }

  public static String getCurrentSeason(Player player) {
    Month month = Calendars.CLIENT.getCalendarMonthOfYear();
    Season season = month.getSeason();

    if (season == Season.FALL) {
      return "AUTUMN";
    }

    else {
      return Calendars.CLIENT.getCalendarMonthOfYear().getSeason().getSerializedName();
    }
  }

  public static int getDate(Player player) {
    Month currentMonth = Calendars.CLIENT.getCalendarMonthOfYear();
    Season currentSeason = currentMonth.getSeason();
    List<Month> currentSeasonMonths = getSeasonMonths(currentSeason);

    int subSeasonPos = currentSeasonMonths.indexOf(currentMonth);
    int dayOfMonth = Calendars.CLIENT.getCalendarDayOfMonth();
    int daysInMonth = Calendars.CLIENT.getCalendarDaysInMonth();

    if (Config.getShowSubSeason()) {
      return dayOfMonth;
    }

    else {
      return dayOfMonth + (subSeasonPos * daysInMonth);
    }
  }

  public static int seasonDuration(Player player) {
    int daysInMonth = Calendars.CLIENT.getCalendarDaysInMonth();

    if (Config.getShowSubSeason() && Calendar.validDetailedMode()) {
      return daysInMonth;
    }

    else {
      return daysInMonth * 3;
    }
  }

  //SEPTEMBER(0.5F, Season.FALL) -> Early Fall
  //OCTOBER(0.0F, Season.FALL) -> Mid Fall
  //NOVEMBER(-0.5F, Season.FALL) -> Late Fall
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
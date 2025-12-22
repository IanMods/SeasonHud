package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.Fertility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.calendar.Season;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class TerrafirmaCraftHelper implements SeasonModHelper {
  public TerrafirmaCraftHelper() {
  }

  private List<Month> getSeasonMonths(Season season) {
    List<Month> seasonMonths = new ArrayList<>();

    switch (season) {
      case WINTER -> {
        seasonMonths.add(SubSeason.EARLY.getWinter());
        seasonMonths.add(SubSeason.MID.getWinter());
        seasonMonths.add(SubSeason.LATE.getWinter());
      }
      case SPRING -> {
        seasonMonths.add(SubSeason.EARLY.getSpring());
        seasonMonths.add(SubSeason.MID.getSpring());
        seasonMonths.add(SubSeason.LATE.getSpring());
      }
      case SUMMER -> {
        seasonMonths.add(SubSeason.EARLY.getSummer());
        seasonMonths.add(SubSeason.MID.getSummer());
        seasonMonths.add(SubSeason.LATE.getSummer());
      }
      case FALL -> {
        seasonMonths.add(SubSeason.EARLY.getAutumn());
        seasonMonths.add(SubSeason.MID.getAutumn());
        seasonMonths.add(SubSeason.LATE.getAutumn());
      }
      default -> throw new IllegalStateException("Unexpected value: " + season);
    }

    return seasonMonths;
  }

  private String getSeasonPrefix(Month currentMonth) {
    Season season = currentMonth.getSeason();
    HashMap<Month, String> seasonPrefix = new HashMap<>();

    switch (season) {
      case WINTER -> {
        seasonPrefix.put(SubSeason.EARLY.getWinter(), SubSeason.EARLY.getPrefix());
        seasonPrefix.put(SubSeason.MID.getWinter(), SubSeason.MID.getPrefix());
        seasonPrefix.put(SubSeason.LATE.getWinter(), SubSeason.LATE.getPrefix());
      }
      case SPRING -> {
        seasonPrefix.put(SubSeason.EARLY.getSpring(), SubSeason.EARLY.getPrefix());
        seasonPrefix.put(SubSeason.MID.getSpring(), SubSeason.MID.getPrefix());
        seasonPrefix.put(SubSeason.LATE.getSpring(), SubSeason.LATE.getPrefix());
      }
      case SUMMER -> {
        seasonPrefix.put(SubSeason.EARLY.getSummer(), SubSeason.EARLY.getPrefix());
        seasonPrefix.put(SubSeason.MID.getSummer(), SubSeason.MID.getPrefix());
        seasonPrefix.put(SubSeason.LATE.getSummer(), SubSeason.LATE.getPrefix());
      }
      case FALL -> {
        seasonPrefix.put(SubSeason.EARLY.getAutumn(), SubSeason.EARLY.getPrefix());
        seasonPrefix.put(SubSeason.MID.getAutumn(), SubSeason.MID.getPrefix());
        seasonPrefix.put(SubSeason.LATE.getAutumn(), SubSeason.LATE.getPrefix());
      }
      default -> throw new IllegalStateException("Unexpected value: " + season);
    }

    return seasonPrefix.get(currentMonth);
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
  public String getCurrentSubSeason(Player player) {
    Month month = Calendars.CLIENT.getCalendarMonthOfYear();
    Season season = month.getSeason();
    String prefix = getSeasonPrefix(month);

    if (season == Season.FALL) {
      return prefix + "AUTUMN";
    } else {
      return prefix + season.getSerializedName();
    }
  }

  @Override
  public String getCurrentSeason(Player player) {
    Month month = Calendars.CLIENT.getCalendarMonthOfYear();
    Season season = month.getSeason();

    if (season == Season.FALL) {
      return "AUTUMN";
    } else {
      return Calendars.CLIENT.getCalendarMonthOfYear().getSeason().getSerializedName();
    }
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

  /** Sub-seasons for each month.
   * <h1>TerrafirmaCraft Seasons:</h1>
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
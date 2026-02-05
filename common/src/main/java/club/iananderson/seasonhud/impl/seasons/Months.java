package club.iananderson.seasonhud.impl.seasons;

/**
 * <h1>Sub-seasons for each month.</h1>
 * <pre>
 * | Season | Early     | Mid     | Late     |
 * |--------|-----------|---------|----------|
 * | Winter | DECEMBER  | JANUARY | FEBRUARY |
 * | Spring | MARCH     | APRIL   | MAY      |
 * | Summer | JUNE      | JULY    | AUGUST   |
 * | Autumn | SEPTEMBER | OCTOBER | NOVEMBER |
 * </pre>
 **/
public enum Months {
  JANUARY(1, "desc.seasonhud.month.01", Seasons.WINTER, SubSeasons.MID),

  FEBRUARY(2, "desc.seasonhud.month.02", Seasons.WINTER, SubSeasons.LATE),

  MARCH(3, "desc.seasonhud.month.03", Seasons.SPRING, SubSeasons.EARLY),

  APRIL(4, "desc.seasonhud.month.04", Seasons.SPRING, SubSeasons.MID),

  MAY(5, "desc.seasonhud.month.05", Seasons.SPRING, SubSeasons.LATE),

  JUNE(6, "desc.seasonhud.month.06", Seasons.SUMMER, SubSeasons.EARLY),

  JULY(7, "desc.seasonhud.month.07", Seasons.SUMMER, SubSeasons.MID),

  AUGUST(8, "desc.seasonhud.month.08", Seasons.SUMMER, SubSeasons.LATE),

  SEPTEMBER(9, "desc.seasonhud.month.09", Seasons.AUTUMN, SubSeasons.EARLY),

  OCTOBER(10, "desc.seasonhud.month.10", Seasons.AUTUMN, SubSeasons.MID),

  NOVEMBER(11, "desc.seasonhud.month.11", Seasons.AUTUMN, SubSeasons.LATE),

  DECEMBER(12, "desc.seasonhud.month.12", Seasons.WINTER, SubSeasons.EARLY);

  private static final Months[] VALUES = values();
  private final int id;
  private final String key;
  private final Seasons season;
  private final SubSeasons subSeason;

  Months(int id, String key, Seasons season, SubSeasons subSeason) {
    this.id = id;
    this.key = key;
    this.season = season;
    this.subSeason = subSeason;
  }

  public static Months getById(int monthNumber) {
    // Month number is one more than ordinal
    return VALUES[monthNumber - 1];
  }

  public int getId() {
    return this.id;
  }

  public Months next() {
    return this == DECEMBER
           ? JANUARY
           : VALUES[this.ordinal() + 1];
  }

  public String getTranslationKey() {
    return this.key;
  }

  public Seasons getSeason() {
    return this.season;
  }

  public SubSeasons getSubSeason() {
    return this.subSeason;
  }
}

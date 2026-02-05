package club.iananderson.seasonhud.impl.season;

public enum SubSeasons {

  EARLY(0, "EARLY_", ".early"),

  MID(1, "MID_", ".mid"),

  LATE(2, "LATE_", ".late"),

  NONE(100, "", "");

  public static final SubSeasons[] VALUES = values();
  private final int id;
  private final String prefix;
  private final String subSeasonKey;

  SubSeasons(int id, String prefix, String subSeasonKey) {
    this.id = id;
    this.prefix = prefix;
    this.subSeasonKey = subSeasonKey;
  }

  public static SubSeasons getById(int idNum) {
    // Month number is one more than ordinal
    return VALUES[idNum];
  }

  public int getId() {
    return this.id;
  }

  public String getSubSeasonKey() {
    return this.subSeasonKey;
  }
}
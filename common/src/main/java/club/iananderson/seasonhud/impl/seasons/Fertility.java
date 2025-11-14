package club.iananderson.seasonhud.impl.seasons;

public enum Fertility {
  FERTILE(0, "desc.seasonhud.fertility.fertile"),

  INFERTILE(1, "desc.seasonhud.fertility.infertile"),

  ALWAYS_WINTER(2, "desc.seasonhud.fertility.always_winter"),

  UNDERGROUND(3, "desc.seasonhud.fertility.underground");

  private final int id;
  private final String key;

  Fertility(int id, String key) {
    this.id = id;
    this.key = key;
  }

  public int getId() {
    return this.id;
  }

  public String getKey() {
    return this.key;
  }
}

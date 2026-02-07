package club.iananderson.seasonhud.impl.season.mods;

public enum SeasonMods {
  SERENE("sereneseasons"),

  FABRIC("season"),

  FABRIC_EXTRAS("seasonsextras"),

  TERRAFIRMACRAFT("tfc"),

  ECLIPTIC("eclipticseasons");

  private final String modId;

  SeasonMods(String modId) {
    this.modId = modId;
  }

  public String getModId() {
    return modId;
  }
}

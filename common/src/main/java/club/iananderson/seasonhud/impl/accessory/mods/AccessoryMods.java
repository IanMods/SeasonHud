package club.iananderson.seasonhud.impl.accessory.mods;

public enum AccessoryMods {
  CURIOS("curios"),

  TRINKETS("trinkets"),

  ACCESSORIES("accessory");

  private final String modId;

  AccessoryMods(String modId) {
    this.modId = modId;
  }

  public String getModId() {
    return modId;
  }
}

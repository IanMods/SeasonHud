package club.iananderson.seasonhud.impl.accessory.mods;

import club.iananderson.seasonhud.platform.Services;

public enum AccessoryMods {
  CURIOS("curios"),

  TRINKETS("trinkets"),

  ACCESSORIES("accessory");

  private final String modId;

  AccessoryMods(String modId) {
    this.modId = modId;
  }

  public String getModId() {
    return this.modId;
  }

  public boolean modLoaded(){
    return Services.PLATFORM.isModLoaded(this.modId);
  }
}

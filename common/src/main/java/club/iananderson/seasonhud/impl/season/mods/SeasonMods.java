package club.iananderson.seasonhud.impl.season.mods;

import club.iananderson.seasonhud.impl.season.mods.eclipticseasons.EclipticSeasonsHelper;
import club.iananderson.seasonhud.impl.season.mods.fabricseasons.FabricSeasonsHelper;
import club.iananderson.seasonhud.impl.season.mods.sereneseasons.SereneSeasonsHelper;
import club.iananderson.seasonhud.impl.season.mods.terrafirmacraft.TerrafirmaCraftHelper;
import club.iananderson.seasonhud.platform.Services;

public enum SeasonMods {
  SERENE("sereneseasons", new SereneSeasonsHelper()),

  FABRIC("seasons", new FabricSeasonsHelper()),

  FABRIC_EXTRAS("seasonsextras", new FabricSeasonsHelper()),

  TERRAFIRMACRAFT("tfc", new TerrafirmaCraftHelper()),

  ECLIPTIC("eclipticseasons", new EclipticSeasonsHelper());

  private final String modId;
  private final SeasonModHelper seasonModHelper;

  SeasonMods(String modId, SeasonModHelper seasonModHelper) {
    this.modId = modId;
    this.seasonModHelper = seasonModHelper;
  }

  public String getModId() {
    return modId;
  }

  public String getModName() {
    return Services.PLATFORM.getModName(modId);
  }

  public SeasonModHelper getSeasonModHelper() {
    return seasonModHelper;
  }
}

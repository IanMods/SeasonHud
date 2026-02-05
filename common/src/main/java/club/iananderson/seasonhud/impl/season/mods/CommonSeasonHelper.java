package club.iananderson.seasonhud.impl.season.mods;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.season.mods.eclipticseasons.EclipticSeasonsHelper;
import club.iananderson.seasonhud.impl.season.mods.fabricseasons.FabricSeasonsHelper;
import club.iananderson.seasonhud.impl.season.mods.sereneseasons.SereneSeasonsHelper;
import club.iananderson.seasonhud.impl.season.mods.terrafirmacraft.TerrafirmaCraftHelper;

public class CommonSeasonHelper {

  public static CommonSeasonHelper commonSeasons = new CommonSeasonHelper();

  private CommonSeasonHelper() {
  }

  public SeasonModHelper getHelper(){
    if (Common.fabricSeasonsLoaded()) {
      return new FabricSeasonsHelper();
    }

    if (Common.sereneSeasonsLoaded() && !Common.eclipticSeasonsLoaded()) {
      return new SereneSeasonsHelper();
    }

    if (Common.terrafirmacraftLoaded()) {
      return new TerrafirmaCraftHelper();
    }

    if (Common.eclipticSeasonsLoaded()) {
      return new EclipticSeasonsHelper();
    }
    else throw new RuntimeException("No supported Season mods are loaded");
  }

}

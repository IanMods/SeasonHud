package club.iananderson.seasonhud.impl.season.mods.eclipticseasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.season.components.Fertility;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.impl.season.mods.SeasonModHelper;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class EclipticSeasonsHelper implements SeasonModHelper {

  @Override
  public Item calendar() {
    if (Common.eclipticSeasonsLoaded()) {
      return BuiltInRegistries.ITEM.get(new ResourceLocation("eclipticseasons", "calendar"));
    } else {
      return null;
    }
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
  public SubSeasons getCurrentSubSeason(Player player) {
    return Services.SEASON.currentEclipticSubSeason(player);
  }

  @Override
  public Seasons getCurrentSeason(Player player) {
    return Services.SEASON.currentEclipticSeason(player);
  }

  @Override
  public long getDate(Player player) {
    return Services.SEASON.currentEclipticSeasonDate(player);
  }

  @Override
  public int seasonDurationDays(Player player) {
    return Services.SEASON.currentEclipticSeasonDuration(player);
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
}

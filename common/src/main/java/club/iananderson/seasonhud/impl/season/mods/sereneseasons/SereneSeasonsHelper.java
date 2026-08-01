package club.iananderson.seasonhud.impl.season.mods.sereneseasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.season.components.Fertility;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.impl.season.mods.SeasonModHelper;
import club.iananderson.seasonhud.platform.Services;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class SereneSeasonsHelper implements SeasonModHelper {
  public SereneSeasonsHelper() {
  }

  @Override
  public Optional<Item> calendar() {
    if (Common.sereneSeasonsLoaded()) {
      return Optional.ofNullable(Registry.ITEM.get(new ResourceLocation("sereneseasons", "calendar")));
    } else {
      return Optional.empty();
    }
  }

  @Override
  public boolean isTropicalSeason(Player player) {
    return Services.SEASON.isTropicalSereneSeason(player);
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    return false;
  }

  @Override
  public SubSeasons getCurrentSubSeason(Player player) {
    return Services.SEASON.getCurrentSereneSubSeason(player);
  }

  @Override
  public Seasons getCurrentSeason(Player player) {
    return Services.SEASON.getCurrentSereneSeason(player);
  }

  @Override
  public long getDate(Player player) {
    return Services.SEASON.getSereneDate(player);
  }

  @Override
  public int seasonDurationDays(Player player) {
    return Services.SEASON.sereneSeasonDurationDays(player);
  }

  @Override
  public boolean infertileBiome(Player player) {
    return Services.SEASON.infertileSereneBiome(player);
  }

  @Override
  public boolean alwaysWinterBiome(Player player) {
    return Services.SEASON.alwaysWinterBiomeSereneBiome(player);
  }

  @Override
  public boolean undergroundFertile(Player player) {
    return Services.SEASON.undergroundFertileSereneBiome(player);
  }

  @Override
  public Fertility fertility(Player player) {
    if (infertileBiome(player)) {
      return Fertility.INFERTILE_BIOME;
    }

    if (alwaysWinterBiome(player)) {
      return Fertility.ALWAYS_WINTER;
    } else if (!undergroundFertile(player)) {
      return Fertility.UNDERGROUND;
    }

    return Fertility.FERTILE;
  }
}

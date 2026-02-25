package club.iananderson.seasonhud.impl.season.mods.homeostaticseasons;

import club.iananderson.seasonhud.impl.season.components.Fertility;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.impl.season.mods.SeasonModHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class HomeostaticSeasonsHelper implements SeasonModHelper {
  @Override
  public Optional<Item> calendar() {
    return Optional.empty();
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
    return SeasonModHelper.super.getCurrentSubSeason(player);
  }

  @Override
  public Seasons getCurrentSeason(Player player) {
    return SeasonModHelper.super.getCurrentSeason(player);

  }

  @Override
  public long getDate(Player player) {
    return 0;
  }

  @Override
  public int seasonDurationDays(Player player) {
    return 0;
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
    return false;
  }

  @Override
  public Fertility fertility(Player player) {
    return SeasonModHelper.super.fertility(player);
  }

  @Override
  public void debugHud(PoseStack graphics) {

  }
}

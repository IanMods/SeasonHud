package club.iananderson.seasonhud.impl.season.mods;

import club.iananderson.seasonhud.impl.season.components.Fertility;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class CommonSeasonHelper {

  public static CommonSeasonHelper commonSeasons = new CommonSeasonHelper();

  private CommonSeasonHelper() {
  }

  public SeasonModHelper getHelper() {
    if (SeasonMods.getLoaded().iterator().hasNext()) {
      return SeasonMods.getLoaded().iterator().next().getSeasonModHelper();
    } else {
      return new NoSeasonModHelper();
    }
  }

  private static class NoSeasonModHelper implements SeasonModHelper {
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
      return SubSeasons.NONE;
    }

    @Override
    public Seasons getCurrentSeason(Player player) {
      return Seasons.NULL;
    }

    @Override
    public long getDate(Player player) {
      return 1;
    }

    @Override
    public int seasonDurationDays(Player player) {
      return 2;
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
      return Fertility.FERTILE;
    }
  }
}

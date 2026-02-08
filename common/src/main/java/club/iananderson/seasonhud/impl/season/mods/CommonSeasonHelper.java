package club.iananderson.seasonhud.impl.season.mods;

import club.iananderson.seasonhud.impl.season.components.Fertility;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.platform.Services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class CommonSeasonHelper {

  public static CommonSeasonHelper commonSeasons = new CommonSeasonHelper();

  private CommonSeasonHelper() {
  }

  private boolean seasonModLoaded(SeasonMods seasonMod) {
    String modId = seasonMod.getModId();
    return Services.PLATFORM.isModLoaded(modId);
  }

  public List<SeasonMods> getLoadedSeasonMods() {
    List<SeasonMods> values = new ArrayList<>(List.of(SeasonMods.values()));
    List<SeasonMods> loaded = new ArrayList<>();

    values.forEach(seasonMod -> {
      if (seasonModLoaded(seasonMod)) {
        loaded.add(seasonMod);
      }
    });
    return loaded;
  }

  public SeasonModHelper getHelper() {
    if (this.getLoadedSeasonMods().iterator().hasNext()) {
      return this.getLoadedSeasonMods().iterator().next().getSeasonModHelper();
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

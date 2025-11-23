package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.Fertility;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import sereneseasons.config.BiomeConfig;
import sereneseasons.config.FertilityConfig;
import sereneseasons.config.SeasonsConfig;


public class SereneSeasonsHelper implements ISeasonModHelper {
  public SereneSeasonsHelper() {
  }

  @Override
  public Item CALENDAR() {
    return Registry.ITEM.get(new ResourceLocation("sereneseasons", "calendar"));
  }

  @Override
  public boolean isTropicalSeason(Player player) {
    return Services.PLATFORM.sereneSeasonTropicalBiome(player);
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    return false;
  }

  @Override
  public String getCurrentSubSeason(Player player) {
    return Services.PLATFORM.getCurrentSereneSubSeason(player); //1.16.5 Forge weirdness
  }

  @Override
  public String getCurrentSeason(Player player) {
    return Services.PLATFORM.getCurrentSereneSeason(player); //1.16.5 Forge weirdness
  }

  @Override
  public long getDate(Player player) {
    return Services.PLATFORM.getSereneSeasonDate(player); //1.16.5 Forge weirdness
  }

  @Override
  public int seasonDuration(Player player) {
    int duration = SeasonsConfig.subSeasonDuration.get() * 3;

    if (isTropicalSeason(player)) {
      duration *= 2; //Tropical seasons are twice as long (Default 48 days)
    }
    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode()) {
      duration /= 3; //3 sub-seasons per season
    }

    return duration;
  }

  @Override
  public boolean infertileBiome(Player player) {
    if ((!FertilityConfig.seasonalCrops.get() || !Services.PLATFORM.sereneSeasonBiomeSeasonalEffects(player) || Services.MINIMAP.hideHudInCurrentDimension())) {
      return false;
    }

    else {
      return (Services.PLATFORM.sereneSeasonInfertileBiome(player));
    }
  }

  @Override
  public boolean alwaysWinterBiome(Player player) {
    return false;
  }

  @Override
  public boolean undergroundFertile(Player player) {
    Level level = player.level;
    BlockPos pos = player.blockPosition();;

    if ((!FertilityConfig.seasonalCrops.get() || !Services.PLATFORM.sereneSeasonBiomeSeasonalEffects(player)
        || Services.MINIMAP.hideHudInCurrentDimension())) {
      return true;
    }

    if (!level.canSeeSky(pos.above())) {
      return (pos.getY() > FertilityConfig.undergroundFertilityLevel.get());
    }

    else {
      return true;
    }
  }

  @Override
  public Fertility fertility(Player player) {
    if (infertileBiome(player)) {
      return Fertility.INFERTILE_BIOME;
    }

    if (alwaysWinterBiome(player)) {
      return Fertility.ALWAYS_WINTER;
    }

    else if (!undergroundFertile(player)) {
      return Fertility.UNDERGROUND;
    }

    return Fertility.FERTILE;
  }
}

package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.Fertility;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sereneseasons.api.SSItems;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.config.ServerConfig;
import sereneseasons.init.ModTags;

public class SereneSeasonsHelper implements ISeasonModHelper {
  public SereneSeasonsHelper() {
  }

  @Override
  public Item CALENDAR() {
    return Registry.ITEM.get(new ResourceLocation("sereneseasons", "calendar"));
  }

  @Override
  public boolean isTropicalSeason(Player player) {
    boolean showTropicalSeasons = SeasonHudClient.getShowTropicalSeason();
    boolean isInTropicalSeason = sereneseasons.api.season.SeasonHelper.usesTropicalSeasons(
        player.level.getBiome(player.getOnPos()));

    return showTropicalSeasons && isInTropicalSeason;
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    return false;
  }

  @Override
  public String getCurrentSubSeason(Player player) {
    ISeasonState currentSeasonState = sereneseasons.api.season.SeasonHelper.getSeasonState(player.level);

    if (isTropicalSeason(player)) {
      return currentSeasonState.getTropicalSeason().toString();
    }
    else {
      return currentSeasonState.getSubSeason().toString();
    }
  }

  @Override
  public String getCurrentSeason(Player player) {
    ISeasonState currentSeasonState = sereneseasons.api.season.SeasonHelper.getSeasonState(player.level);
    if (isTropicalSeason(player)) {
      // Removes the "Early", "Mid", "Late" from the tropical season.
      String currentSubSeason = getCurrentSubSeason(player);

      return currentSubSeason.substring(currentSubSeason.length() - 3);
    }
    else {
      return currentSeasonState.getSeason().toString();
    }
  }

  @Override
  public long getDate(Player player) {
    ISeasonState currentSeasonState = SeasonHelper.getSeasonState(player.level);
    long seasonDay = currentSeasonState.getDay(); //Current day out of the year (Default 24 days * 4 = 96 days)
    long subSeasonDuration = ServerConfig.subSeasonDuration.get(); //In case the default duration is changed
    long subSeasonDate = (seasonDay % subSeasonDuration) + 1; //Default 8 days in each sub-season (1 week)
    long seasonDate = (seasonDay % (subSeasonDuration * 3)) + 1; //Default 24 days in a season (8 days * 3)

    if (SeasonHudClient.getShowSubSeason()) {
      if (isTropicalSeason(player)) {
        // Default 16 days in each tropical "sub-season".
        // Starts are "Early Dry" (Summer 1), so need to offset Spring 1 -> Summer 1 (subSeasonDuration * 3)
        subSeasonDate = ((seasonDay + (subSeasonDuration * 3)) % (subSeasonDuration * 2)) + 1;
      }
      return subSeasonDate;
    }
    else {
      if (isTropicalSeason(player)) {
        // Default 48 days in each tropical season.
        // Starts are "Early Dry" (Summer 1), so need to offset Spring 1 -> Summer 1 (subSeasonDuration * 3)
        seasonDate = ((seasonDay + (subSeasonDuration * 3)) % (subSeasonDuration * 6)) + 1;
      }
      return seasonDate;
    }
  }

  @Override
  public int seasonDuration(Player player) {
    int duration = ServerConfig.subSeasonDuration.get() * 3;

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
    Level level = player.level();
    BlockPos pos = player.getOnPos();
    Holder<Biome> biome = level.getBiome(pos);

    if ((!ModConfig.fertility.seasonalCrops || biome.is(ModTags.Biomes.BLACKLISTED_BIOMES)
        || !ModConfig.seasons.isDimensionWhitelisted(level.dimension()))) {
      return false;
    }

    else {
      return (biome.is(ModTags.Biomes.INFERTILE_BIOMES));
    }
  }

  @Override
  public boolean alwaysWinterBiome(Player player) {
    Level level = player.level();
    BlockPos pos = player.getOnPos();
    Holder<Biome> biome = level.getBiome(pos);

    if ((!ModConfig.fertility.seasonalCrops || biome.is(ModTags.Biomes.BLACKLISTED_BIOMES)
        || !ModConfig.seasons.isDimensionWhitelisted(level.dimension()))) {
      return false;
    }

    else {
      return !biome.value().warmEnoughToRain(pos);
    }
  }

  @Override
  public boolean undergroundFertile(Player player) {
    Level level = player.level();
    BlockPos pos = player.getOnPos();
    Holder<Biome> biome = level.getBiome(pos);

    if ((!ModConfig.fertility.seasonalCrops || biome.is(ModTags.Biomes.BLACKLISTED_BIOMES)
        || !ModConfig.seasons.isDimensionWhitelisted(level.dimension()))) {
      return true;
    }

    if (!level.canSeeSky(pos.above())) {
      return (pos.getY() > ModConfig.fertility.undergroundFertilityLevel);
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

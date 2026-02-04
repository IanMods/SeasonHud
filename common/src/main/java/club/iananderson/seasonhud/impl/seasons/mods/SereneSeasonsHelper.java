package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.Fertility;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.impl.seasons.SubSeasons;
import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sereneseasons.api.SSItems;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.init.ModConfig;
import sereneseasons.init.ModTags;

public class SereneSeasonsHelper implements SeasonModHelper {
  public SereneSeasonsHelper() {
  }

  @Override
  public Item calendar() {
    return SSItems.CALENDAR;
  }

  @Override
  public boolean isTropicalSeason(Player player) {
    boolean showTropicalSeasons = SeasonHudClient.getShowTropicalSeason();
    boolean isInTropicalSeason = SeasonHelper.usesTropicalSeasons(player.level().getBiome(player.getOnPos()));

    return showTropicalSeasons && isInTropicalSeason;
  }

  @Override
  public boolean isSeasonTiedWithSystemTime() {
    return false;
  }

  @Override
  public SubSeasons getCurrentSubSeason(Player player) {
    ISeasonState currentSeasonState = SeasonHelper.getSeasonState(player.level());
    String currentSubSeasonFull = currentSeasonState.getTropicalSeason().toString();

    if (isTropicalSeason(player)) {
      currentSubSeasonFull = currentSeasonState.getTropicalSeason().toString();
    }

    // TODO: double check this
    String currentSubSeason = currentSubSeasonFull.substring(0, currentSubSeasonFull.indexOf("_"));

    // TODO: double check this
    return SubSeasons.valueOf(currentSubSeason.toUpperCase(Locale.ROOT));
  }

  @Override
  public Seasons getCurrentSeason(Player player) {
    ISeasonState currentSeasonState = sereneseasons.api.season.SeasonHelper.getSeasonState(player.level());
    String currentSeason = currentSeasonState.getSeason().toString();

    if (isTropicalSeason(player)) {
      String currentSubSeason = currentSeasonState.getTropicalSeason().toString();

      // Removes the "Early_", "Mid_", "Late_" from the tropical season.
      // TODO: double check this
      currentSeason = currentSubSeason.substring(currentSubSeason.indexOf("_") + 1);
    }

    // TODO: double check this
    return Seasons.valueOf(currentSeason.toUpperCase(Locale.ROOT));
  }

  @Override
  public long getDate(Player player) {
    ISeasonState currentSeasonState = SeasonHelper.getSeasonState(player.level());
    long seasonDay = currentSeasonState.getDay(); // Current day out of the year (Default 24 days * 4 = 96 days)
    long subSeasonDuration = ModConfig.seasons.subSeasonDuration; // In case the default duration is changed
    long subSeasonDate = (seasonDay % subSeasonDuration) + 1; // Default 8 days in each sub-season (1 week)
    long seasonDate = (seasonDay % (subSeasonDuration * 3)) + 1; // Default 24 days in a season (8 days * 3)

    if (SeasonHudClient.getShowSubSeason()) {
      if (isTropicalSeason(player)) {
        // Default 16 days in each tropical "sub-season".
        // Starts are "Early Dry" (Summer 1), so need to offset Spring 1 -> Summer 1 (subSeasonDuration * 3)
        subSeasonDate = ((seasonDay + (subSeasonDuration * 3)) % (subSeasonDuration * 2)) + 1;
      }
      return subSeasonDate;
    } else {
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
    int duration = ModConfig.seasons.subSeasonDuration * 3;

    if (isTropicalSeason(player)) {
      duration *= 2; // Tropical seasons are twice as long (Default 48 days)
    }
    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode()) {
      duration /= 3; // 3 sub-seasons per season
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
    } else {
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
    } else {
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
    } else {
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
    } else if (!undergroundFertile(player)) {
      return Fertility.UNDERGROUND;
    }

    return Fertility.FERTILE;
  }
}

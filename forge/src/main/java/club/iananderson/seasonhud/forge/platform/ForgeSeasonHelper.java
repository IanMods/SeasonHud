package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import club.iananderson.seasonhud.impl.season.components.Months;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.platform.services.SeasonHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.config.CommonConfig;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.config.BiomeConfig;
import sereneseasons.config.FertilityConfig;
import sereneseasons.config.SeasonsConfig;
import sereneseasons.util.biome.BiomeUtil;

public class ForgeSeasonHelper implements SeasonHelper {
  // FabricSeasons
  @Override
  public boolean validFabricSeasonsDim(ResourceKey<Level> currentDim) {
    return false;
  }

  @Override
  public boolean fabricSeasonsTiedWithSystemTime() {
    return false;
  }

  @Override
  public Optional<Item> fabricSeasonsCalendar() {
    return Optional.empty();
  }

  @Override
  public SubSeasons currentFabricSubSeason(Player player) {
    return SubSeasons.NONE;
  }

  @Override
  public Seasons currentFabricSeason(Player player) {
    return Seasons.NULL;
  }

  @Override
  public int currentFabricSeasonLength(Player player) {
    return 0;
  }

  @Override
  public long timeToNextFabricSeason(Player player) {
    return 0;
  }

  // SereneSeasons
  @Override
  public boolean isTropicalSereneSeason(Player player) {
    Level level = player.level;
    BlockPos pos = player.blockPosition();
    Biome biome = level.getBiome(pos);
    ResourceKey<Biome> biomeKey = BiomeUtil.getBiomeKey(biome);

    if (!SeasonHudClient.getShowTropicalSeason()) {
      return false;
    } else {
      return BiomeConfig.usesTropicalSeasons(biomeKey);
    }
  }

  @Override
  public SubSeasons getCurrentSereneSubSeason(Player player) {
    ISeasonState currentSeasonState = sereneseasons.api.season.SeasonHelper.getSeasonState(player.level);
    String currentSubSeasonFull = currentSeasonState.getSubSeason().toString();

    if (isTropicalSereneSeason(player)) {
      currentSubSeasonFull = currentSeasonState.getTropicalSeason().toString();
    }

    String currentSubSeason = currentSubSeasonFull.substring(0, currentSubSeasonFull.indexOf("_"));

    return SubSeasons.valueOf(currentSubSeason.toUpperCase(Locale.ROOT));
  }

  @Override
  public Seasons getCurrentSereneSeason(Player player) {
    ISeasonState currentSeasonState = sereneseasons.api.season.SeasonHelper.getSeasonState(player.level);
    String currentSeason = currentSeasonState.getSeason().toString();
    if (isTropicalSereneSeason(player)) {
      String currentSubSeason = currentSeasonState.getTropicalSeason().toString();

      // Removes the "Early_", "Mid_", "Late_" from the tropical season.
      currentSeason = currentSubSeason.substring(currentSubSeason.indexOf("_") + 1);
    }

    return Seasons.valueOf(currentSeason.toUpperCase(Locale.ROOT));
  }

  @Override
  public long getSereneDate(Player player) {
    ISeasonState currentSeasonState = sereneseasons.api.season.SeasonHelper.getSeasonState(player.level);
    long seasonDay = currentSeasonState.getDay(); // Current day out of the year (Default 24 days * 4 = 96 days)
    long subSeasonDuration = SeasonsConfig.subSeasonDuration.get(); // In case the default duration is changed
    long subSeasonDate = (seasonDay % subSeasonDuration) + 1; // Default 8 days in each sub-season (1 week)
    long seasonDate = (seasonDay % (subSeasonDuration * 3)) + 1; // Default 24 days in a season (8 days * 3)

    if (subSeasonDuration != SeasonHudServer.getSubSeasonLength()) {
      subSeasonDuration = SeasonHudServer.getSubSeasonLength();
    }

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode(player)) {
      if (isTropicalSereneSeason(player)) {
        // Default 16 days in each tropical "sub-season".
        // Starts are "Early Dry" (Summer 1), so need to offset Spring 1 -> Summer 1 (subSeasonDuration * 3)
        subSeasonDate = ((seasonDay + (subSeasonDuration * 3)) % (subSeasonDuration * 2)) + 1;
      }
      return subSeasonDate;
    } else {
      if (isTropicalSereneSeason(player)) {
        // Default 48 days in each tropical season.
        // Starts are "Early Dry" (Summer 1), so need to offset Spring 1 -> Summer 1 (subSeasonDuration * 3)
        seasonDate = ((seasonDay + (subSeasonDuration * 3)) % (subSeasonDuration * 6)) + 1;
      }
      return seasonDate;
    }
  }

  @Override
  public int sereneSeasonDurationDays(Player player) {
    int subSeasonDuration = SeasonsConfig.subSeasonDuration.get();

    if (subSeasonDuration != SeasonHudServer.getSubSeasonLength()) {
      subSeasonDuration = SeasonHudServer.getSubSeasonLength();
    }

    int seasonDuration = subSeasonDuration * 3;

    if (isTropicalSereneSeason(player)) {
      seasonDuration *= 2; // Tropical season are twice as long (Default 48 days)
    }
    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode(player)) {
      seasonDuration /= 3; // 3 sub-season per season
    }

    return seasonDuration;
  }

  @Override
  public boolean validSereneSeasonsDim(ResourceKey<Level> currentDim) {
    return SeasonsConfig.isDimensionWhitelisted(currentDim);
  }

  @Override
  public boolean infertileSereneBiome(Player player) {
    Level level = player.level;
    BlockPos pos = player.blockPosition();
    Biome biome = level.getBiome(pos);
    ResourceKey<Biome> biomeKey = BiomeUtil.getBiomeKey(biome);

    return BiomeConfig.infertileBiome(biomeKey);
  }

  @Override
  public boolean alwaysWinterBiomeSereneBiome(Player player) {
    return false;
  }

  @Override
  public boolean undergroundFertileSereneBiome(Player player) {
    Level level = player.level;
    BlockPos pos = player.blockPosition();
    Biome biome = level.getBiome(pos);
    ResourceKey<Biome> biomeKey = BiomeUtil.getBiomeKey(biome);

    if ((!FertilityConfig.seasonalCrops.get() || !BiomeConfig.enablesSeasonalEffects(biomeKey)
        || !SeasonsConfig.isDimensionWhitelisted(level.dimension()))) {
      return true;
    }

    if (!level.canSeeSky(pos.above())) {
      return (pos.getY() > FertilityConfig.undergroundFertilityLevel.get());
    } else {
      return true;
    }
  }

  // EclipticSeasons
  @Override
  public boolean validEclipticSeasonsDim(ResourceKey<Level> currentDim) {
    // List<? extends String> validDimensions = CommonConfig.Season.validDimensions.get();

    // return isDimensionValid(validDimensions, currentDim);

    return true;
  }

  @Override
  public Component eclipticSeasonComponent(Player player) {
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    if (validEclipticSeasonsDim(currentDim)) {
      return EclipticUtil.INSTANCE.getSolarTerm(player.level).getTranslation();
    } else {
      return Seasons.NULL.getSeasonNameTranslated();
    }
  }

  @Override
  public SubSeasons currentEclipticSubSeason(Player player) {
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    if (validEclipticSeasonsDim(currentDim)) {
      int currentSolarTermNumber = EclipticUtil.INSTANCE.getSolarTerm(player.level).ordinal();

      // 6 solar terms per season -> 2 solar terms per sub-season
      return SubSeasons.getById((currentSolarTermNumber % 6) / 2);
    } else {
      return SubSeasons.NONE;
    }
  }

  @Override
  public Seasons currentEclipticSeason(Player player) {
    String currentSeason = "NULL";
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    if (validEclipticSeasonsDim(currentDim)) {
      currentSeason = EclipticUtil.INSTANCE.getSolarTerm(player.level).getSeason().getName();

      if (currentSeason.equals("none")) {
        currentSeason = "null";
      }
    }

    return Seasons.valueOf(currentSeason.toUpperCase(Locale.ROOT));
  }

  @Override
  public long currentEclipticSeasonDate(Player player) {
    long seasonDay = EclipticUtil.getNowSolarDay(player.level); // Day out of the year (42 days * 4 = 168 days)
    long subSeasonDay = EclipticUtil.getTimeInSolarTerm(player.level); // Day out of the sub season (7 days)
    long subSeasonDuration = CommonConfig.Season.lastingDaysOfEachTerm.get(); // In case the default duration is changed
    long subSeasonDate = (subSeasonDay % (subSeasonDuration)) + 1; // Default 7 days in each sub-season (1 week)
    long seasonDate = (seasonDay % (subSeasonDuration * 6)) + 1; // Default 42 days in a season (7 days * 6)

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode(player)) {
      return subSeasonDate;
    } else {
      return seasonDate;
    }
  }

  @Override
  public int currentEclipticSeasonDuration(Player player) {
    int duration = CommonConfig.Season.lastingDaysOfEachTerm.get() * 6;

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode(player)) {
      duration /= 6; // 6 sub-season per season
    }

    return duration;
  }

  // TerrafirmaCraft
  @Override
  public Months currentTerraFirmaCraftMonth() {
    Month terraFirmaCraftmonth = Calendars.CLIENT.getCalendarMonthOfYear();

    // Starts at '0', so need to adjust by 1
    int monthNumber = terraFirmaCraftmonth.ordinal() + 1;

    return Months.getById(monthNumber);
  }

  @Override
  public int terraFirmaCraftCurrentDayOfMonth() {
    return Calendars.CLIENT.getCalendarDayOfMonth();
  }

  @Override
  public int terraFirmaCraftTotalDaysInMonth() {
    return Calendars.CLIENT.getCalendarDaysInMonth();
  }

  @Override
  public boolean validHomeostaticSeasonsDim(ResourceKey<Level> currentDim) {
    // Disabled for 1.20.1 and below
    // return !HomeostaticSeasonsAPI.isSeasonalDimension(currentDim);
    return true;
  }

  @Override
  public Optional<Item> protoManlyWeatherCalendar() {
    return Optional.empty();
  }

  @Override
  public Months protoManlyWeatherMonth(Player player) {
    return null;
  }

  @Override
  public int protoManlyWeatherCurrentDayOfMonth(Player player) {
    return 0;
  }

  @Override
  public int protoManlyWeatherTotalDaysInMonth(Player player) {
    return 0;
  }

  @Override
  public void protoManlyDebug(PoseStack graphics) {

  }
}

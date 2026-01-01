package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.Fertility;
import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.config.CommonConfig;
import com.teamtea.eclipticseasons.config.CommonConfig.Season;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class EclipticSeasonsHelper implements SeasonModHelper {
  @Override
  public Item calendar() {
    if (Common.eclipticSeasonsLoaded()) {
      return BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("eclipticseasons", "calendar"));
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
  public String getCurrentSubSeason(Player player) {
    String currentSolarTerm = "MID_NULL"; // Just in case
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();
    List<? extends String> validDimensions = Season.validDimensions.get();

    if (Common.isDimensionValid(validDimensions, currentDim)) {
      currentSolarTerm = EclipticUtil.INSTANCE.getSolarTerm(player.level()).getName();

    }
    return currentSolarTerm;
  }

  @Override
  public String getCurrentSeason(Player player) {
    String currentSeason = "NULL";
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();
    List<? extends String> validDimensions = Season.validDimensions.get();

    if (Common.isDimensionValid(validDimensions, currentDim)) {
      currentSeason = EclipticUtil.INSTANCE.getSolarTerm(player.level()).getSeason().toString();
    }

    return currentSeason;
  }

  @Override
  public long getDate(Player player) {
    long seasonDay = EclipticUtil.getNowSolarDay(player.level()); // Day out of the year (42 days * 4 = 168 days)
    long subSeasonDay = EclipticUtil.getTimeInSolarTerm(player.level()); // Day out of the sub season (7 days)
    long subSeasonDuration = CommonConfig.Season.lastingDaysOfEachTerm.get(); // In case the default duration is changed
    long subSeasonDate = (subSeasonDay % (subSeasonDuration)) + 1; // Default 7 days in each sub-season (1 week)
    long seasonDate = (seasonDay % (subSeasonDuration * 6)) + 1; // Default 42 days in a season (7 days * 6)

    if (SeasonHudClient.getShowSubSeason()) {
      return subSeasonDate;
    } else {
      return seasonDate;
    }
  }

  @Override
  public int seasonDuration(Player player) {
    int duration = CommonConfig.Season.lastingDaysOfEachTerm.get() * 6;

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode()) {
      duration /= 6; // 6 terms per season
    }

    return duration;
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

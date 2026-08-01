package club.iananderson.seasonhud.impl.season.mods.homeostaticseasons;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import club.iananderson.seasonhud.impl.season.components.Fertility;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.impl.season.mods.SeasonModHelper;
import homeostaticseasons.api.HomeostaticSeasonsAPI;
import homeostaticseasons.api.Season;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.client.gui.GuiGraphics;
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
    String currentSubSeasonFull = HomeostaticSeasonsAPI.getCurrentSeason(player.level()).getSerializedName();
    String currentSubSeason = currentSubSeasonFull.substring(0, currentSubSeasonFull.indexOf("_"));

    return SubSeasons.valueOf(currentSubSeason.toUpperCase(Locale.ROOT));
  }

  @Override
  public Seasons getCurrentSeason(Player player) {
    String currentSubSeasonFull = HomeostaticSeasonsAPI.getCurrentSeason(player.level()).getSerializedName();
    String currentSeason = currentSubSeasonFull.substring(currentSubSeasonFull.indexOf("_") + 1);

    return Seasons.valueOf(currentSeason.toUpperCase(Locale.ROOT));
  }

  private Season nextFullSeason(Season subSeason) {
    switch (subSeason) {
      case Season.EARLY_SPRING, Season.MID_SPRING, Season.LATE_SPRING -> {
        return Season.EARLY_SUMMER;
      }
      case Season.EARLY_SUMMER, Season.MID_SUMMER, Season.LATE_SUMMER -> {
        return Season.EARLY_AUTUMN;
      }
      case Season.EARLY_AUTUMN, Season.MID_AUTUMN, Season.LATE_AUTUMN -> {
        return Season.EARLY_WINTER;
      }
      case Season.EARLY_WINTER, Season.MID_WINTER, Season.LATE_WINTER -> {
        return Season.EARLY_SPRING;
      }
      default -> throw new IllegalStateException("Unexpected value: " + subSeason);
    }
  }

  @Override
  public long getDate(Player player) {
    long dayLength = 24000L;
    Season subSeason = HomeostaticSeasonsAPI.getCurrentSeason(player.level());

    long timeToNextSubSeason = HomeostaticSeasonsAPI.getTimeUntilNextSeason(player.level());
    long timeToNextSeason = HomeostaticSeasonsAPI.getTimeUntilSeason(player.level(), nextFullSeason(subSeason));
    long seasonDuration = fullSeasonDuration(subSeason);
    long subSeasonDuration = subSeason.getSeasonLength();

    long subSeasonDate = ((subSeasonDuration - timeToNextSubSeason) / dayLength) + 1; // Default 3 days in each
    // sub-season (1 week)
    long seasonDate = ((seasonDuration - timeToNextSeason) / dayLength) + 1; // Default 9 days in a season (3 days * 3)

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode(player)) {
      return subSeasonDate;
    } else {
      return seasonDate;
    }
  }

  @Override
  public int seasonDurationDays(Player player) {
    long dayLength = 24000L;

    Season subSeason = HomeostaticSeasonsAPI.getCurrentSeason(player.level());
    long subSeasonDuration = subSeason.getSeasonLength();
    long seasonDuration = fullSeasonDuration(subSeason);

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode(player)) {
      return (int) (subSeasonDuration / dayLength);
    } else {
      return (int) (seasonDuration / dayLength);
    }
  }

  private long fullSeasonDuration(Season subSeason) {
    switch (subSeason) {
      case Season.EARLY_SPRING, Season.MID_SPRING, Season.LATE_SPRING -> {
        return (Season.EARLY_SPRING.getSeasonLength() + Season.MID_SPRING.getSeasonLength()
            + Season.LATE_SPRING.getSeasonLength());
      }
      case Season.EARLY_SUMMER, Season.MID_SUMMER, Season.LATE_SUMMER -> {
        return (Season.EARLY_SUMMER.getSeasonLength() + Season.MID_SUMMER.getSeasonLength()
            + Season.LATE_SUMMER.getSeasonLength());
      }
      case Season.EARLY_AUTUMN, Season.MID_AUTUMN, Season.LATE_AUTUMN -> {
        return (Season.EARLY_AUTUMN.getSeasonLength() + Season.MID_AUTUMN.getSeasonLength()
            + Season.LATE_AUTUMN.getSeasonLength());
      }
      case Season.EARLY_WINTER, Season.MID_WINTER, Season.LATE_WINTER -> {
        return (Season.EARLY_WINTER.getSeasonLength() + Season.MID_WINTER.getSeasonLength()
            + Season.LATE_WINTER.getSeasonLength());
      }
      default -> throw new IllegalStateException("Unexpected value: " + subSeason);
    }
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
}

package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.Fertility;
import homeostaticseasons.api.HomeostaticSeasonsAPI;
import homeostaticseasons.api.Season;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class HomeostaticSeasonsHelper implements SeasonModHelper {
  @Override
  public Item calendar() {
    return null;
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
    return HomeostaticSeasonsAPI.getCurrentSeason(player.level()).getSerializedName();
  }

  @Override
  public String getCurrentSeason(Player player) {
    // Removes the "Early", "Mid", "Late" from the season.
    String currentSubSeason = getCurrentSubSeason(player);
    return currentSubSeason.substring(currentSubSeason.indexOf("_") + 1);
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

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode()) {
      return subSeasonDate;
    } else {
      return seasonDate;
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
  public int seasonDuration(Player player) {
    long dayLength = 24000L;

    Season subSeason = HomeostaticSeasonsAPI.getCurrentSeason(player.level());
    long subSeasonDuration = subSeason.getSeasonLength();
    long seasonDuration = fullSeasonDuration(subSeason);

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode()) {
      return (int) (subSeasonDuration / dayLength);
    } else {
      return (int) (seasonDuration / dayLength);
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
    return Fertility.FERTILE;
  }
}

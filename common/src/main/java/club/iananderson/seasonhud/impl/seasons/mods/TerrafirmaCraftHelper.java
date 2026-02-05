package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.Fertility;
import club.iananderson.seasonhud.impl.seasons.Months;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.impl.seasons.SubSeasons;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class TerrafirmaCraftHelper implements SeasonModHelper {
  public TerrafirmaCraftHelper() {
  }

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
  public SubSeasons getCurrentSubSeason(Player player) {
    return Services.SEASON.currentTerraFirmaCraftMonth().getSubSeason();
  }

  @Override
  public Seasons getCurrentSeason(Player player) {
    return Services.SEASON.currentTerraFirmaCraftMonth().getSeason();
  }

  @Override
  public long getDate(Player player) {
    Months currentMonth = Services.SEASON.currentTerraFirmaCraftMonth();
    SubSeasons currentSubSeason = currentMonth.getSubSeason();

    int dayOfMonth = Services.SEASON.terraFirmaCraftCurrentDayOfMonth();
    int daysInMonth = Services.SEASON.terraFirmaCraftTotalDaysInMonth();

    // Assumes that there are 3 months per season
    if (SeasonHudClient.getShowSubSeason()) {
      return dayOfMonth;
    } else {
      // TODO: Double check this
      // Early = 0; Mid = 1; Late = 2
      return dayOfMonth + ((long) currentSubSeason.ordinal() * daysInMonth);
    }
  }

  @Override
  public int seasonDurationDays(Player player) {
    int daysInMonth = Services.SEASON.terraFirmaCraftTotalDaysInMonth();

    // Currently the days in a month is 8 by default, and determined by the 'yearLength' config value divided by 12
    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode()) {
      return daysInMonth;
    } else {
      return daysInMonth * 3;
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
    return true;
  }

  @Override
  public Fertility fertility(Player player) {
    return Fertility.FERTILE;
  }

}
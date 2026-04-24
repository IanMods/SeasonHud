package club.iananderson.seasonhud.impl.season.mods.terrafirmacraft;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import club.iananderson.seasonhud.impl.season.components.Fertility;
import club.iananderson.seasonhud.impl.season.components.Months;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.impl.season.mods.SeasonModHelper;
import club.iananderson.seasonhud.platform.Services;
import java.util.Optional;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class TerrafirmaCraftHelper implements SeasonModHelper {
  public TerrafirmaCraftHelper() {
  }

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
    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode(player)) {
      return dayOfMonth;
    } else {
      // Early = 0; Mid = 1; Late = 2
      return dayOfMonth + ((long) currentSubSeason.ordinal() * daysInMonth);
    }
  }

  @Override
  public int seasonDurationDays(Player player) {
    int daysInMonth = Services.SEASON.terraFirmaCraftTotalDaysInMonth();

    // Currently the days in a month is 8 by default, and determined by the 'yearLength' config value divided by 12
    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode(player)) {
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
    return SeasonModHelper.super.fertility(player);
  }

  @Override
  public void debugHud(GuiGraphicsExtractor graphics) {

  }

}
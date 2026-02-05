package club.iananderson.seasonhud.forge.platform;

import static club.iananderson.seasonhud.Common.isDimensionValid;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.Months;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.impl.seasons.SubSeasons;
import club.iananderson.seasonhud.platform.services.SeasonHelper;
import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.config.CommonConfig;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import sereneseasons.init.ModConfig;

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
  public Item fabricSeasonsCalendar() {
    return null;
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
  public boolean validSereneSeasonsDim(ResourceKey<Level> currentDim) {
    return ModConfig.seasons.isDimensionWhitelisted(currentDim);
  }

  // EclipticSeasons
  @Override
  public boolean validEclipticSeasonsDim(ResourceKey<Level> currentDim) {
    List<? extends String> validDimensions = CommonConfig.Season.validDimensions.get();

    return isDimensionValid(validDimensions, currentDim);
  }

  @Override
  public SubSeasons currentEclipticSubSeason(Player player) {
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    if (validEclipticSeasonsDim(currentDim)) {
      int currentSolarTermNumber = EclipticUtil.INSTANCE.getSolarTerm(player.level()).ordinal();

      // TODO: Check this
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
      currentSeason = EclipticUtil.INSTANCE.getSolarTerm(player.level()).getSeason().getSerializedName();

      if (currentSeason.equals("none")) {
        currentSeason = "null";
      }
    }

    return Seasons.valueOf(currentSeason.toUpperCase(Locale.ROOT));
  }

  @Override
  public long currentEclipticSeasonDate(Player player) {
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
  public int currentEclipticSeasonDuration(Player player) {
    int duration = CommonConfig.Season.lastingDaysOfEachTerm.get() * 6;

    // TODO: Check this and make sure it is working correctly with sub-seasons
    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode()) {
      duration /= 3; // 3 sub-seasons per season
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
}

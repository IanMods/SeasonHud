package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import com.teamtea.eclipticseasons.api.constant.solar.Season;
import com.teamtea.eclipticseasons.api.constant.solar.SolarTerm;
import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.config.CommonConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class EclipticSeasonsHelper implements IModHelper {
  @Override
  public Item CALENDAR() {
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
    SolarTerm currentSolarTerm = EclipticUtil.INSTANCE.getSolarTerm(player.level());
    return currentSolarTerm.getName();
  }

  @Override
  public String getCurrentSeason(Player player) {
    Season currentSeason = EclipticUtil.INSTANCE.getSolarTerm(player.level()).getSeason();
    return currentSeason.toString();
  }

  @Override
  public long getDate(Player player) {
    long seasonDay = EclipticUtil.getNowSolarDay(player.level()); //Day out of the year (42 days * 4 = 168 days)
    long subSeasonDay = EclipticUtil.getTimeInSolarTerm(player.level()); //Day out of the sub season (7 days)
    long subSeasonDuration = CommonConfig.Season.lastingDaysOfEachTerm.get(); //In case the default duration is changed
    long subSeasonDate = (subSeasonDay % (subSeasonDuration)) + 1; //Default 7 days in each sub-season (1 week)
    long seasonDate =  (seasonDay % (subSeasonDuration * 6)) + 1; //Default 42 days in a season (7 days * 6)

    if (Config.getShowSubSeason()) {
      return subSeasonDate;
    }

    else return seasonDate;
  }

  @Override
  public int seasonDuration(Player player) {
    int duration = CommonConfig.Season.lastingDaysOfEachTerm.get() * 6;

    if (Config.getShowSubSeason() && Calendar.validDetailedMode()) {
      duration /= 6; //6 terms per season
    }

    return duration;
  }
}

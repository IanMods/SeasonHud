package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.platform.Services;
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
    return Services.PLATFORM.getCurrentEclipticSubSeason(player); //1.16.5 Forge weirdness
  }

  @Override
  public String getCurrentSeason(Player player) {
    return Services.PLATFORM.getCurrentEclipticSeason(player); //1.16.5 Forge weirdness
  }

  @Override
  public long getDate(Player player) {
    return Services.PLATFORM.getEclipticSeasonDate(player); //1.16.5 Forge weirdness
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

package club.iananderson.seasonhud.impl.seasons.mods;

import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import sereneseasons.config.SeasonsConfig;

public class SereneSeasonsHelper implements IModHelper {
  public SereneSeasonsHelper() {
  }

  @Override
  public Item CALENDAR() {
    return Registry.ITEM.get(new ResourceLocation("sereneseasons", "calendar"));
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
    return Services.PLATFORM.getCurrentSubSeason(player); //1.16.5 Forge weirdness
  }

  @Override
  public String getCurrentSeason(Player player) {
    return Services.PLATFORM.getCurrentSeason(player); //1.16.5 Forge weirdness
  }

  @Override
  public long getDate(Player player) {
    return Services.PLATFORM.getSeasonDate(player); //1.16.5 Forge weirdness
  }

  @Override
  public int seasonDuration(Player player) {
    int duration = SeasonsConfig.subSeasonDuration.get() * 3;

    if (isTropicalSeason(player)) {
      duration *= 2; //Tropical seasons are twice as long (Default 48 days)
    }
    if (Config.getShowSubSeason() && Calendar.validDetailedMode()) {
      duration /= 3; //3 sub-seasons per season
    }

    return duration;
  }
}

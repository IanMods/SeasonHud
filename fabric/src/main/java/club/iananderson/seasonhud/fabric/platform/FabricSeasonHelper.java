package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.platform.services.SeasonHelper;
import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import io.github.lucaargolo.seasonsextras.FabricSeasonsExtras;
import java.util.Locale;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import sereneseasons.init.ModConfig;

public class FabricSeasonHelper implements SeasonHelper {
  @Override
  public boolean validFabricSeasonsDim(ResourceKey<Level> currentDim) {
    return FabricSeasons.CONFIG.isValidInDimension(currentDim);
  }

  @Override
  public boolean validSereneSeasonsDim(ResourceKey<Level> currentDim) {
    return ModConfig.seasons.isDimensionWhitelisted(currentDim);
  }

  @Override
  public boolean validEclipticSeasonsDim(ResourceKey<Level> currentDim) {
    return false;
  }

  @Override
  public Seasons currentTerraFirmaCraftSeason() {
    return Seasons.NULL;
  }

  @Override
  public Item fabricSeasonsCalendar() {
    if (Common.fabricSeasonsExtrasLoaded()) {
      return FabricSeasonsExtras.SEASON_CALENDAR_ITEM;
    } else {
      return null;
    }
  }

  @Override
  public Seasons currentFabricSeason(Player player) {
    Season currentSeasonState = FabricSeasons.getCurrentSeason(player.level());
    String currentSeason = currentSeasonState.toString();

    if (currentSeasonState.toString().equalsIgnoreCase("fall")) {
      currentSeason = "Autumn";
    }

    // TODO: double check this
    return Seasons.valueOf(currentSeason.toUpperCase(Locale.ROOT));
  }

  @Override
  public int currentFabricSeasonLength(Player player) {
    return FabricSeasons.getCurrentSeason(player.level()).getSeasonLength();
  }

  @Override
  public long timeToNextFabricSeason(Player player) {
    return FabricSeasons.getTimeToNextSeason(player.level());
  }
}

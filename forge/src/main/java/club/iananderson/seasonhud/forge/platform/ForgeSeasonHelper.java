package club.iananderson.seasonhud.forge.platform;

import static club.iananderson.seasonhud.Common.isDimensionValid;

import club.iananderson.seasonhud.platform.services.SeasonHelper;
import com.teamtea.eclipticseasons.config.CommonConfig;
import java.util.List;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import sereneseasons.init.ModConfig;

public class ForgeSeasonHelper implements SeasonHelper {
  @Override
  public boolean validFabricSeasonsDim(ResourceKey<Level> currentDim) {
    return false;
  }

  @Override
  public boolean validSereneSeasonsDim(ResourceKey<Level> currentDim) {
    return ModConfig.seasons.isDimensionWhitelisted(currentDim);
  }

  @Override
  public boolean validEclipticSeasonsDim(ResourceKey<Level> currentDim) {
    List<? extends String> validDimensions = CommonConfig.Season.validDimensions.get();

    return isDimensionValid(validDimensions, currentDim);
  }

  @Override
  public Item fabricSeasonsCalendar() {
    return null;
  }

  @Override
  public String currentFabricSeason(Player player) {
    return "";
  }

  @Override
  public int currentFabricSeasonLength(Player player) {
    return 0;
  }

  @Override
  public long timeToNextFabricSeason(Player player) {
    return 0;
  }
}

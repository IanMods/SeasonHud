package club.iananderson.seasonhud.platform.services;

import club.iananderson.seasonhud.impl.seasons.Seasons;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public interface SeasonHelper {
  boolean validFabricSeasonsDim(ResourceKey<Level> currentDim);

  Item fabricSeasonsCalendar();

  Seasons currentFabricSeason(Player player);

  int currentFabricSeasonLength(Player player);

  long timeToNextFabricSeason(Player player);

  boolean validSereneSeasonsDim(ResourceKey<Level> currentDim);

  boolean validEclipticSeasonsDim(ResourceKey<Level> currentDim);

  Seasons currentTerraFirmaCraftSeason();

}

package club.iananderson.seasonhud.platform.services;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public interface SeasonHelper {
  boolean validFabricSeasonsDim(ResourceKey<Level> currentDim);

  boolean validSereneSeasonsDim(ResourceKey<Level> currentDim);

  boolean validEclipticSeasonsDim(ResourceKey<Level> currentDim);

  Item fabricSeasonsCalendar();

  String currentFabricSeason(Player player);

  int currentFabricSeasonLength(Player player);

  long timeToNextFabricSeason(Player player);
}

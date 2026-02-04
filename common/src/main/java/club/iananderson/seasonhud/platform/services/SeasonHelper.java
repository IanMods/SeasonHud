package club.iananderson.seasonhud.platform.services;

import club.iananderson.seasonhud.impl.seasons.Months;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.impl.seasons.SubSeasons;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public interface SeasonHelper {
  // FabricSeasons
  boolean validFabricSeasonsDim(ResourceKey<Level> currentDim);

  Item fabricSeasonsCalendar();

  SubSeasons currentFabricSubSeason(Player player);

  Seasons currentFabricSeason(Player player);

  int currentFabricSeasonLength(Player player);

  long timeToNextFabricSeason(Player player);

  // SereneSeasons
  boolean validSereneSeasonsDim(ResourceKey<Level> currentDim);

  // EclipticSeasons
  boolean validEclipticSeasonsDim(ResourceKey<Level> currentDim);

  // TerrafirmaCraft
  Months currentTerraFirmaCraftMonth();

  int terraFirmaCraftCurrentDayofMonth();

  int terraFirmaCraftTotalDaysInMonth();
}

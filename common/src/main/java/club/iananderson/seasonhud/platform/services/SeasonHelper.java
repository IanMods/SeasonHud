package club.iananderson.seasonhud.platform.services;

import club.iananderson.seasonhud.impl.season.Months;
import club.iananderson.seasonhud.impl.season.Seasons;
import club.iananderson.seasonhud.impl.season.SubSeasons;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public interface SeasonHelper {
  // FabricSeasons
  boolean validFabricSeasonsDim(ResourceKey<Level> currentDim);

  boolean fabricSeasonsTiedWithSystemTime();

  Item fabricSeasonsCalendar();

  SubSeasons currentFabricSubSeason(Player player);

  Seasons currentFabricSeason(Player player);

  int currentFabricSeasonLength(Player player);

  long timeToNextFabricSeason(Player player);

  // SereneSeasons
  boolean validSereneSeasonsDim(ResourceKey<Level> currentDim);

  // EclipticSeasons
  boolean validEclipticSeasonsDim(ResourceKey<Level> currentDim);

  SubSeasons currentEclipticSubSeason(Player player);

  Seasons currentEclipticSeason(Player player);

  long currentEclipticSeasonDate(Player player);

  int currentEclipticSeasonDuration(Player player);

  // TerrafirmaCraft
  Months currentTerraFirmaCraftMonth();

  int terraFirmaCraftCurrentDayOfMonth();

  int terraFirmaCraftTotalDaysInMonth();
}

package club.iananderson.seasonhud.platform.services;

import club.iananderson.seasonhud.impl.season.components.Months;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public interface SeasonHelper {
  // FabricSeasons
  boolean validFabricSeasonsDim(ResourceKey<Level> currentDim);

  boolean fabricSeasonsTiedWithSystemTime();

  Optional<Item> fabricSeasonsCalendar();

  SubSeasons currentFabricSubSeason(Player player);

  Seasons currentFabricSeason(Player player);

  int currentFabricSeasonLength(Player player);

  long timeToNextFabricSeason(Player player);

  // SereneSeasons
  boolean isTropicalSereneSeason(Player player);

  SubSeasons getCurrentSereneSubSeason(Player player);

  Seasons getCurrentSereneSeason(Player player);

  long getSereneDate(Player player);

  int sereneSeasonDurationDays(Player player);

  boolean validSereneSeasonsDim(ResourceKey<Level> currentDim);

  boolean infertileSereneBiome(Player player);

  boolean alwaysWinterBiomeSereneBiome(Player player);

  boolean undergroundFertileSereneBiome(Player player);

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

  // HomeostaticSeasons
  boolean validHomeostaticSeasonsDim(ResourceKey<Level> currentDim);

  // ProtoManly's Weather
  Optional<Item> protoManlyWeatherCalendar();

  Months protoManlyWeatherMonth(Player player);

  int protoManlyWeatherCurrentDayOfMonth(Player player);

  int protoManlyWeatherTotalDaysInMonth(Player player);

  void protoManlyDebug(PoseStack graphics);
}

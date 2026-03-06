package club.iananderson.seasonhud.neoforge.platform;

import static club.iananderson.seasonhud.Common.isDimensionValid;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import club.iananderson.seasonhud.impl.season.components.Months;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.impl.season.components.SubSeasons;
import club.iananderson.seasonhud.platform.services.SeasonHelper;
import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.config.CommonConfig;
import dev.protomanly.pmweather.config.ServerConfig;
import dev.protomanly.pmweather.item.ModItems;
import dev.protomanly.pmweather.seasons.SeasonHandler;
import homeostaticseasons.api.HomeostaticSeasonsAPI;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import net.dries007.tfc.client.ClientHelpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import sereneseasons.init.ModConfig;

public class NeoForgeSeasonHelper implements SeasonHelper {
  // FabricSeasons
  @Override
  public boolean validFabricSeasonsDim(ResourceKey<Level> currentDim) {
    return false;
  }

  @Override
  public boolean fabricSeasonsTiedWithSystemTime() {
    return false;
  }

  @Override
  public Optional<Item> fabricSeasonsCalendar() {
    return Optional.empty();
  }

  @Override
  public SubSeasons currentFabricSubSeason(Player player) {
    return SubSeasons.NONE;
  }

  @Override
  public Seasons currentFabricSeason(Player player) {
    return Seasons.NULL;
  }

  @Override
  public int currentFabricSeasonLength(Player player) {
    return 0;
  }

  @Override
  public long timeToNextFabricSeason(Player player) {
    return 0;
  }

  // SereneSeasons
  @Override
  public boolean validSereneSeasonsDim(ResourceKey<Level> currentDim) {
    return ModConfig.seasons.isDimensionWhitelisted(currentDim);
  }

  // EclipticSeasons
  @Override
  public boolean validEclipticSeasonsDim(ResourceKey<Level> currentDim) {
    List<? extends String> validDimensions = CommonConfig.Season.validDimensions.get();

    return isDimensionValid(validDimensions, currentDim);
  }

  @Override
  public Component eclipticSeasonComponent(Player player) {
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    if (validEclipticSeasonsDim(currentDim)) {
      return EclipticUtil.INSTANCE.getSolarTerm(player.level()).getTranslation();
    } else {
      return Seasons.NULL.getSeasonNameTranslated();
    }
  }

  @Override
  public SubSeasons currentEclipticSubSeason(Player player) {
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    if (validEclipticSeasonsDim(currentDim)) {
      int currentSolarTermNumber = EclipticUtil.INSTANCE.getSolarTerm(player.level()).ordinal();

      // 6 solar terms per season -> 2 solar terms per sub-season
      return SubSeasons.getById((currentSolarTermNumber % 6) / 2);
    } else {
      return SubSeasons.NONE;
    }
  }

  @Override
  public Seasons currentEclipticSeason(Player player) {
    String currentSeason = "NULL";
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    if (validEclipticSeasonsDim(currentDim)) {
      currentSeason = EclipticUtil.INSTANCE.getSolarTerm(player.level()).getSeason().getSerializedName();

      if (currentSeason.equals("none")) {
        currentSeason = "null";
      }
    }

    return Seasons.valueOf(currentSeason.toUpperCase(Locale.ROOT));
  }

  @Override
  public long currentEclipticSeasonDate(Player player) {
    long seasonDay = EclipticUtil.getNowSolarDay(player.level()); // Day out of the year (42 days * 4 = 168 days)
    long subSeasonDay = EclipticUtil.getTimeInSolarTerm(player.level()); // Day out of the sub season (7 days)
    long subSeasonDuration = CommonConfig.Season.lastingDaysOfEachTerm.get(); // In case the default duration is changed
    long subSeasonDate = (subSeasonDay % (subSeasonDuration)) + 1; // Default 7 days in each sub-season (1 week)
    long seasonDate = (seasonDay % (subSeasonDuration * 6)) + 1; // Default 42 days in a season (7 days * 6)

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode(player)) {
      return subSeasonDate;
    } else {
      return seasonDate;
    }
  }

  @Override
  public int currentEclipticSeasonDuration(Player player) {
    int duration = CommonConfig.Season.lastingDaysOfEachTerm.get() * 6;

    if (SeasonHudClient.getShowSubSeason() && Calendar.validDetailedMode(player)) {
      duration /= 6; // 6 sub-season per season
    }

    return duration;
  }

  // TerrafirmaCraft
  @Override
  public Months currentTerraFirmaCraftMonth() {
    Month terraFirmaCraftmonth = Calendars.get()
        .getHemispheralCalendarMonthOfYear(ClientHelpers.inNorthernHemisphere());

    // Starts at '0', so need to adjust by 1
    int monthNumber = terraFirmaCraftmonth.ordinal() + 1;

    return Months.getById(monthNumber);
  }

  @Override
  public int terraFirmaCraftCurrentDayOfMonth() {
    return Calendars.CLIENT.getCalendarDayOfMonth();
  }

  @Override
  public int terraFirmaCraftTotalDaysInMonth() {
    return Calendars.CLIENT.getCalendarDaysInMonth();
  }

  // HomeostaticSeasons
  @Override
  public boolean validHomeostaticSeasonsDim(ResourceKey<Level> currentDim) {
    return !HomeostaticSeasonsAPI.isSeasonalDimension(currentDim);
  }

  // ProtoManly's Weather
  @Override
  public Optional<Item> protoManlyWeatherCalendar() {
    return ModItems.CALENDAR.asOptional();
  }

  @Override
  public Months protoManlyWeatherMonth(Player player) {
    int protomanlyWeatherMonth = SeasonHandler.getMonth(player.level());

    return Months.getById(protomanlyWeatherMonth);
  }

  @Override
  public int protoManlyWeatherCurrentDayOfMonth(Player player) {
    return SeasonHandler.getDayInMonth(player.level());
  }

  @Override
  public int protoManlyWeatherTotalDaysInMonth(Player player) {
    return ServerConfig.monthLength;
  }

  @Override
  public void protoManlyDebug(GuiGraphics graphics) {
    Minecraft mc = Minecraft.getInstance();
    int screenWidth = mc.getWindow().getGuiScaledWidth();
    int y = 32;

    if (mc.player != null) {
      String month = protoManlyWeatherMonth(mc.player).getTranslatedText().getString();
      int day = protoManlyWeatherCurrentDayOfMonth(mc.player);
      int monthLength = protoManlyWeatherTotalDaysInMonth(mc.player);

      y += 8;
      graphics.drawString(mc.font, "Month: " + month + " | " + "Day: " + day + "/" + monthLength, screenWidth / 2, y,
                          0xffffff);

      Level level = mc.player.level();
      y += 8;
      float springAmount = (float) Math.pow((SeasonHandler.getSeasonEffectSine(level, -3.5F) + 1.0F) / 2.0F, 4.0F);
      graphics.drawString(mc.font, "springAmount: " + springAmount, screenWidth / 2, y, 0xffffff);

      y += 8;
      float summerAmount = (float) Math.pow((SeasonHandler.getSeasonEffectSine(level, 0.0F) + 1.0F) / 2.0F, 4.0F);
      graphics.drawString(mc.font, "summerAmount: " + summerAmount, screenWidth / 2, y, 0xffffff);

      y += 8;
      float fallAmount = (float) Math.pow((SeasonHandler.getSeasonEffectSine(level, 3.5F) + 1.0F) / 2.0F, 4.0F);
      graphics.drawString(mc.font, "fallAmount: " + fallAmount, screenWidth / 2, y, 0xffffff);

      y += 8;
      float winterAmount = (float) Math.pow((SeasonHandler.getSeasonEffectSine(level, 6.0F) + 1.0F) / 2.0F, 4.0F);
      graphics.drawString(mc.font, "winterAmount: " + winterAmount, screenWidth / 2, y, 0xffffff);
    }
  }
}

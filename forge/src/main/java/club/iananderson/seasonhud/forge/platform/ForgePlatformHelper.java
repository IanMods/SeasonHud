package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.services.IPlatformHelper;
import com.teamtea.eclipticseasons.api.constant.solar.Season;
import com.teamtea.eclipticseasons.api.util.EclipticUtil;
import com.teamtea.eclipticseasons.config.CommonConfig;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;
import sereneseasons.config.SeasonsConfig;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class ForgePlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {
    return "Forge";
  }

  @Override
  public boolean isModLoaded(String modId) {
    return ModList.get().isLoaded(modId);
  }

  @Override
  public String getModVersion(String modId) {
    Optional<? extends ModContainer> mod = ModList.get().getModContainerById(modId);

    if (mod.isPresent()) {
      return mod.get().getModInfo().getVersion().toString();
    }
    else {
      return "Not Loaded";
    }
  }

  @Override
  public boolean isDevelopmentEnvironment() {
    return !FMLLoader.isProduction();
  }

  @Override
  public boolean curiosFound(Player player, Item item) {
    boolean curioEquipped = false;

    if (Common.curiosLoaded() && !Common.accessoriesLoaded()) {
      List<SlotResult> findCalendar = CuriosApi.getCuriosHelper().findCurios(player, item.getItem());

      curioEquipped = !findCalendar.isEmpty();
    }
    return curioEquipped;
  }

  @Override
  public String getCurrentSereneSeason(Player player) {
    ISeasonState currentSeasonState = sereneseasons.api.season.SeasonHelper.getSeasonState(player.level);

    return currentSeasonState.getSeason().toString();
  }

  @Override
  public String getCurrentSereneSubSeason(Player player) {
    ISeasonState currentSeasonState = sereneseasons.api.season.SeasonHelper.getSeasonState(player.level);

    return currentSeasonState.getSubSeason().toString();
  }

  @Override
  public long getSereneSeasonDate(Player player) {
    ISeasonState currentSeasonState = SeasonHelper.getSeasonState(player.level);
    long seasonDay = currentSeasonState.getDay(); //Current day out of the year (Default 24 days * 4 = 96 days)
    long subSeasonDuration = SeasonsConfig.subSeasonDuration.get(); //In case the default duration is changed
    long subSeasonDate = (seasonDay % subSeasonDuration) + 1; //Default 8 days in each sub-season (1 week)
    long seasonDate = (seasonDay % (subSeasonDuration * 3)) + 1; //Default 24 days in a season (8 days * 3)

    if (Config.getShowSubSeason()) {
      return subSeasonDate;
    }
    else {
      return seasonDate;
    }
  }

  @Override
  public String getCurrentEclipticSeason(Player player) {
    Season currentSeason = EclipticUtil.INSTANCE.getSolarTerm(player.level).getSeason();
    return currentSeason.toString();
  }

  @Override
  public String getCurrentEclipticSubSeason(Player player) {
    Season currentSeason = EclipticUtil.INSTANCE.getSolarTerm(player.level).getSeason();
    return currentSeason.toString();
  }

  @Override
  public long getEclipticSeasonDate(Player player) {
    long seasonDay = EclipticUtil.getNowSolarDay(player.level); //Day out of the year (42 days * 4 = 168 days)
    long subSeasonDay = EclipticUtil.getTimeInSolarTerm(player.level); //Day out of the sub season (7 days)
    long subSeasonDuration = CommonConfig.Season.lastingDaysOfEachTerm.get(); //In case the default duration is changed
    long subSeasonDate = (subSeasonDay % (subSeasonDuration)) + 1; //Default 7 days in each sub-season (1 week)
    long seasonDate = (seasonDay % (subSeasonDuration * 6)) + 1; //Default 42 days in a season (7 days * 6)

    if (Config.getShowSubSeason()) {
      return subSeasonDate;
    }

    else {
      return seasonDate;
    }
  }
}

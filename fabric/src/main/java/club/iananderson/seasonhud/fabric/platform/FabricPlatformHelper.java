package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.platform.services.PlatformHelper;
import java.util.Optional;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class FabricPlatformHelper implements PlatformHelper {

  @Override
  public String getPlatformName() {
    return "Fabric";
  }

  @Override
  public boolean isModLoaded(String modId) {
    return FabricLoader.getInstance().isModLoaded(modId);
  }

  @Override
  public String getModVersion(String modId) {
    Optional<? extends ModContainer> mod = FabricLoader.getInstance().getModContainer(modId);

    if (mod.isPresent()) {
      return mod.get().getMetadata().getVersion().toString();
    } else {
      return "Not Loaded";
    }
  }

  @Override
  public boolean isDevelopmentEnvironment() {
    return FabricLoader.getInstance().isDevelopmentEnvironment();
  }

  @Override
  public boolean curiosFound(Player player, Item item) {
    return false;
  }

  @Override
  public String getCurrentSereneSeason(Player player) {
    return "";
  }

  @Override
  public String getCurrentSereneSubSeason(Player player) {
    return "";
  }

  @Override
  public long getSereneSeasonDate(Player player) {
    return 0;
  }

  @Override
  public boolean sereneSeasonTropicalBiome(Player player) {
    return false;
  }

  @Override
  public boolean sereneSeasonInfertileBiome(Player player) {
    return false;
  }

  @Override
  public boolean sereneSeasonBiomeSeasonalEffects(Player player) {
    return true;
  }

  @Override
  public String getCurrentEclipticSeason(Player player) {
    return "";
  }

  @Override
  public String getCurrentEclipticSubSeason(Player player) {
    return "";
  }

  @Override
  public long getEclipticSeasonDate(Player player) {
    return 0;
  }
}

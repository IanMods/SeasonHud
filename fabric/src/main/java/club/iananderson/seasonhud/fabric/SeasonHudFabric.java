package club.iananderson.seasonhud.fabric;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudCommon;
import club.iananderson.seasonhud.impl.accessories.AccessoriesCompat;
import club.iananderson.seasonhud.platform.Services;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig.Type;

public class SeasonHudFabric implements ModInitializer {

  public SeasonHudFabric() {
  }

  /**
   * Runs the mod initializer.
   */
  @Override
  public void onInitialize() {
    Common.init();

    if(Services.PLATFORM.getModVersion("forgeconfigapiport").startsWith("21.5")) {
      ConfigRegistry.INSTANCE.register(Common.MOD_ID, Type.COMMON, SeasonHudCommon.GENERAL_SPEC,
                                       "seasonhud-common.toml");
    }

    else{
      NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, Type.COMMON, SeasonHudCommon.GENERAL_SPEC,
                                               "seasonhud-common.toml");
    }

    if (Common.accessoriesLoaded() && !Common.trinketsLoaded()) {
      AccessoriesCompat.init();
    }
  }
}
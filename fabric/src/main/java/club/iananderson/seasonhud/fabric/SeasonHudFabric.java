package club.iananderson.seasonhud.fabric;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.impl.accessories.AccessoriesCompat;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.fml.config.ModConfig;

public class SeasonHudFabric implements ModInitializer {

  public SeasonHudFabric() {
  }

  /**
   * Runs the mod initializer.
   */
  @Override
  public void onInitialize() {
    Common.init();

    ForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.SERVER, SeasonHudServer.GENERAL_SPEC,
                                          "seasonhud-server.toml");

    if (Common.accessoriesLoaded() && !Common.trinketsLoaded()) {
      AccessoriesCompat.init();
    }
  }
}
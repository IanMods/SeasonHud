package club.iananderson.seasonhud.fabric.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.fabric.event.ClientEvents;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.SeasonComponent;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.neoforged.fml.config.ModConfig;

public class SeasonHudClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.CLIENT, Config.GENERAL_SPEC,
                                             "seasonhud-client.toml");
    ClientEvents.register();

    if (CurrentMinimap.ftbChunksLoaded()) {
      Common.LOG.info("Loading FTB Chunks Season Component");
      EnvExecutor.runInEnv(Env.CLIENT, () -> SeasonComponent.INSTANCE::registerFtbSeason);
    }

//    if (Common.accessoriesLoaded() && Common.calendarLoaded() && !Common.curiosLoaded()) {
//      AccessoriesCompat.clientInit();
//    }
  }
}

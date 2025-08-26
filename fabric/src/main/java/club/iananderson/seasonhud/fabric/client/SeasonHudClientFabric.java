package club.iananderson.seasonhud.fabric.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.fabric.event.ClientEvents;
import club.iananderson.seasonhud.impl.accessories.AccessoriesCompat;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.SeasonComponent;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraftforge.fml.config.ModConfig;

public class SeasonHudClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    ClientEvents.register();

    if (CurrentMinimap.ftbChunksLoaded()) {
      Common.LOG.info("Loading FTB Chunks Season Component");
      EnvExecutor.runInEnv(Env.CLIENT, () -> SeasonComponent.INSTANCE::registerFtbSeason);
    }

    if (Common.accessoriesLoaded() && !Common.trinketsLoaded()) {
      AccessoriesCompat.clientInit();
    }
  }
}

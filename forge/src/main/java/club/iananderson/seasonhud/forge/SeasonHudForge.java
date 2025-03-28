package club.iananderson.seasonhud.forge;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.forge.impl.curios.CuriosCompat;
import club.iananderson.seasonhud.impl.accessories.AccessoriesCompat;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.SeasonComponent;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Common.MOD_ID)
public class SeasonHudForge {
  public SeasonHudForge() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    MinecraftForge.EVENT_BUS.register(this);
    Common.init();

    ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.GENERAL_SPEC, "seasonhud-client.toml");

    modEventBus.addListener(SeasonHudForge::onInitialize);
    modEventBus.addListener(SeasonHudForge::ftbChunkSetup);
  }

  public static void onInitialize(FMLCommonSetupEvent event) {
    if (Common.curiosLoaded()) {
      Common.LOG.info("Talking to Curios");
      CuriosCompat.init();
    }
    else if (Common.accessoriesLoaded()) {
      AccessoriesCompat.init();
    }
  }

  public static void ftbChunkSetup(FMLCommonSetupEvent event) {
    if (CurrentMinimap.ftbChunksLoaded()) {
      Common.LOG.info("Loading FTB Chunks Season Component");
      EnvExecutor.runInEnv(Env.CLIENT, () -> SeasonComponent.INSTANCE::registerFtbSeason);
    }
  }
}
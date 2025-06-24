package club.iananderson.seasonhud.forge;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.forge.event.ClientEvents;
import club.iananderson.seasonhud.forge.event.ClientEvents.ClientForgeEvents;
import club.iananderson.seasonhud.forge.event.ClientEvents.ClientModBusEvents;
import fuzs.forgeconfigapiport.forge.api.v5.NeoForgeConfigRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Common.MOD_ID)
public class SeasonHudForge {
  public SeasonHudForge(FMLJavaModLoadingContext context) {
    BusGroup modEventBus = context.getModBusGroup();
    MinecraftForge.EVENT_BUS.register(this);
    Common.init();

    NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.CLIENT, Config.GENERAL_SPEC,
                                             "seasonhud-client.toml");

    FMLCommonSetupEvent.getBus(modEventBus).addListener(SeasonHudForge::onInitialize);
    FMLCommonSetupEvent.getBus(modEventBus).addListener(SeasonHudForge::ftbChunkSetup);
  }

  public static void onInitialize(FMLCommonSetupEvent event) {
//    if (Common.curiosLoaded()) {
//      Common.LOG.info("Talking to Curios");
//      CuriosCompat.init();
//    }
//    else if (Common.accessoriesLoaded()) {
//      AccessoriesCompat.init();
//    }
  }

  public static void ftbChunkSetup(FMLCommonSetupEvent event) {
//    if (CurrentMinimap.ftbChunksLoaded()) {
//      Common.LOG.info("Loading FTB Chunks Season Component");
//      EnvExecutor.runInEnv(Env.CLIENT, () -> SeasonComponent.INSTANCE::registerFtbSeason);
//    }
  }
}
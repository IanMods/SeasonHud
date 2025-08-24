package club.iananderson.seasonhud.forge;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.config.SeasonHudCommon;
import club.iananderson.seasonhud.platform.Services;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Common.MOD_ID)
public class SeasonHudForge {
  public SeasonHudForge(FMLJavaModLoadingContext context) {
    var modEventBus = context.getModEventBus();
    MinecraftForge.EVENT_BUS.register(this);
    Common.init();

    if (Services.PLATFORM.getModVersion("forgeconfigapiport").startsWith("21.5")) {
      fuzs.forgeconfigapiport.forge.api.v5.NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, Type.CLIENT,
                                                                                    SeasonHudClient.GENERAL_SPEC,
                                                                                    "seasonhud-client.toml");

      fuzs.forgeconfigapiport.forge.api.v5.NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, Type.COMMON,
                                                                                    SeasonHudCommon.GENERAL_SPEC,
                                                                                    "seasonhud-client.toml");
    }

    else {
      fuzs.forgeconfigapiport.forge.api.neoforge.v4.NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, Type.CLIENT,
                                                                                             SeasonHudClient.GENERAL_SPEC,
                                                                                             "seasonhud-common.toml");

      fuzs.forgeconfigapiport.forge.api.neoforge.v4.NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, Type.COMMON,
                                                                                             SeasonHudCommon.GENERAL_SPEC,
                                                                                             "seasonhud-common.toml");
    }

    modEventBus.addListener(SeasonHudForge::onInitialize);
    modEventBus.addListener(SeasonHudForge::ftbChunkSetup);
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
package club.iananderson.seasonhud.forge;
import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.platform.Services;
import java.lang.invoke.MethodHandles;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Common.MOD_ID)
public class SeasonHudForge {
  public SeasonHudForge(FMLJavaModLoadingContext context) {
    var modEventBus  = context.getModBusGroup();
    modEventBus.register(MethodHandles.lookup(), this);
    Common.init();

    NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.CLIENT,
                                                                                    SeasonHudClient.CLIENT_SPEC,
                                                                                    "seasonhud-client.toml");

    NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.SERVER,
                                                                                    SeasonHudServer.SERVER_SPEC,
                                                                                    "seasonhud-server.toml");


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
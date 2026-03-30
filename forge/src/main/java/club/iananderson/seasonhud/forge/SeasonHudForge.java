package club.iananderson.seasonhud.forge;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.SeasonHudClientCommon;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.config.SeasonHudServer;
import fuzs.forgeconfigapiport.forge.api.neoforge.v4.NeoForgeConfigRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Common.MOD_ID)
public class SeasonHudForge {
  public SeasonHudForge(FMLJavaModLoadingContext context) {
    Common.init();
    MinecraftForge.EVENT_BUS.register(this);
    var modEventBus = context.getModEventBus();

    NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.CLIENT, SeasonHudClient.CLIENT_SPEC,
                                             "seasonhud-client.toml");

    NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.SERVER, SeasonHudServer.SERVER_SPEC,
                                             "seasonhud-server.toml");

    modEventBus.addListener(SeasonHudForge::onInitialize);

  }

  public static void onInitialize(FMLCommonSetupEvent event) {
    // if (Common.ftbChunksLoaded()) {
    //   SeasonHudClientCommon.ftbChunkSetup();
    // }
    //
    // if (Common.curiosLoaded()) {
    //   Common.LOG.info("Talking to Curios");
    //   CuriosCompat.init();
    // } else if (Common.accessoriesLoaded()) {
    //   AccessoriesCompat.init();
    // }
  }
}
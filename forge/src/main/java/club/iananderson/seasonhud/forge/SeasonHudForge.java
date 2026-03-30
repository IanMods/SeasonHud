package club.iananderson.seasonhud.forge;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.SeasonHudClientCommon;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.forge.impl.accessory.mods.curios.CuriosCompat;
import club.iananderson.seasonhud.impl.accessory.mods.accessories.AccessoriesCompat;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Common.MOD_ID)
public class SeasonHudForge {
  public SeasonHudForge() {
    Common.init();
    MinecraftForge.EVENT_BUS.register(this);
    var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SeasonHudClient.CLIENT_SPEC, "seasonhud-client.toml");

    ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SeasonHudServer.SERVER_SPEC, "seasonhud-server.toml");

    modEventBus.addListener(SeasonHudForge::onInitialize);

  }

  public static void onInitialize(FMLCommonSetupEvent event) {
    if (Common.ftbChunksLoaded()) {
      SeasonHudClientCommon.ftbChunkSetup();
    }

    if (Common.curiosLoaded()) {
      Common.LOG.info("Talking to Curios");
      CuriosCompat.init();
    } else if (Common.accessoriesLoaded()) {
      AccessoriesCompat.init();
    }
  }
}
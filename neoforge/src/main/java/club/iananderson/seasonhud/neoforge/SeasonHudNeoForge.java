package club.iananderson.seasonhud.neoforge;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.impl.accessory.mods.accessories.AccessoriesCompat;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.ftbchunks.SeasonComponent;
import club.iananderson.seasonhud.neoforge.impl.curios.CuriosCompat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Common.MOD_ID)
public class SeasonHudNeoForge {

  public SeasonHudNeoForge(IEventBus modEventBus, ModContainer modContainer) {
    Common.init();

    modContainer.registerConfig(ModConfig.Type.CLIENT, SeasonHudClient.CLIENT_SPEC, "seasonhud-client.toml");
    modContainer.registerConfig(ModConfig.Type.SERVER, SeasonHudServer.SERVER_SPEC, "seasonhud-server.toml");

    modEventBus.addListener(SeasonHudNeoForge::onInitialize);
    modEventBus.addListener(SeasonHudNeoForge::ftbChunkSetup);
  }

  public static void onInitialize(FMLCommonSetupEvent event) {
    if (Common.curiosLoaded() && !Common.accessoriesLoaded()) {
      Common.LOG.info("Talking to Curios");
      CuriosCompat.init();
    } else if (Common.accessoriesLoaded()) {
      AccessoriesCompat.init();
    }
  }

  public static void ftbChunkSetup(FMLCommonSetupEvent event) {
    if (CurrentMinimap.ftbChunksLoaded()) {
      // Disabled until FTBChunks is updated
      // SeasonComponent.ftbChunkSetup();
    }
  }
}
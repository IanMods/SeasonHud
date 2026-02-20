package club.iananderson.seasonhud.forge;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.forge.client.SeasonHudForgeClient;
import club.iananderson.seasonhud.forge.event.ClientEvents.ClientModBusEvents;
import club.iananderson.seasonhud.forge.impl.accessory.mods.curios.CuriosCompat;
import club.iananderson.seasonhud.impl.accessory.mods.accessories.AccessoriesCompat;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.ftbchunks.SeasonComponent;
import fuzs.forgeconfigapiport.forge.api.v5.NeoForgeConfigRegistry;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Common.MOD_ID)
public class SeasonHudForge {
  public SeasonHudForge(FMLJavaModLoadingContext context) {
    var modBusGroup = context.getModBusGroup();

    FMLCommonSetupEvent.getBus(modBusGroup).addListener(SeasonHudForge::commonSetupEvent);
    FMLClientSetupEvent.getBus(modBusGroup).addListener(SeasonHudForgeClient::onInitializeClient);

    NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.CLIENT, SeasonHudClient.CLIENT_SPEC,
                                             "seasonhud-client.toml");

    NeoForgeConfigRegistry.INSTANCE.register(Common.MOD_ID, ModConfig.Type.SERVER, SeasonHudServer.SERVER_SPEC,
                                             "seasonhud-server.toml");

    RegisterKeyMappingsEvent.getBus(context.getModBusGroup()).addListener(ClientModBusEvents::onKeyRegister);
    AddGuiOverlayLayersEvent.getBus(context.getModBusGroup()).addListener(ClientModBusEvents::registerGuiOverlays);
  }

  public static void commonSetupEvent(FMLCommonSetupEvent event) {
    event.enqueueWork(() -> {
      Common.LOG.info("SeasonHud Client Initializing");
      Common.init();
      SeasonHudForge.ftbChunkSetup(event);

      if (Common.curiosLoaded()) {
        Common.LOG.info("Talking to Curios");
        CuriosCompat.init();
      } else if (Common.accessoriesLoaded()) {
        AccessoriesCompat.init();
      }
    });
  }

  private static void ftbChunkSetup(FMLCommonSetupEvent event) {
    if (CurrentMinimap.ftbChunksLoaded()) {
      event.enqueueWork(SeasonComponent::ftbChunkSetup);
    }
  }
}
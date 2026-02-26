package club.iananderson.seasonhud.forge;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.forge.impl.accessory.mods.curios.CuriosCompat;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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

    ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, SeasonHudClient.CLIENT_SPEC, "seasonhud-client.toml");

    ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SeasonHudServer.SERVER_SPEC, "seasonhud-server.toml");

    modEventBus.addListener(ClientModEvents::onInitialize);
  }

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void onInitialize(FMLCommonSetupEvent event) {
      if (Common.curiosLoaded() && Common.hasCalendarLoaded()) {
        Common.LOG.info("Talking to Curios");
        new CuriosCompat().setup(event);
      }
    }
  }
}
package club.iananderson.seasonhud.forge.client;

import club.iananderson.seasonhud.Common;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SeasonHudForgeClient {
  SeasonHudForgeClient() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    modEventBus.addListener(SeasonHudForgeClient::curioTexture);
  }

  @SubscribeEvent
  public static void onInitializeClient(FMLClientSetupEvent event) {

  }

  @SubscribeEvent
  public static void curioTexture(TextureStitchEvent.Pre evt) {
    evt.addSprite(Common.slotIcon);
  }
}
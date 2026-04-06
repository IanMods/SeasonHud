package club.iananderson.seasonhud.forge.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.SeasonHudClientCommon;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SeasonHudForgeClient {

  @SubscribeEvent
  public static void onInitializeClient(FMLClientSetupEvent event) {
    SeasonHudClientCommon.initAccessoriesClient();
    SeasonHudClientCommon.ftbChunkSetup();
  }
}

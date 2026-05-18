package club.iananderson.seasonhud.neoforge.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.SeasonHudClientCommon;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT)
public class SeasonHudNeoForgeClient {

  @SubscribeEvent
  public static void onInitializeClient(FMLClientSetupEvent event) {
    Common.init();
    SeasonHudClientCommon.initAccessoriesClient();
    SeasonHudClientCommon.ftbChunkSetup();
  }
}

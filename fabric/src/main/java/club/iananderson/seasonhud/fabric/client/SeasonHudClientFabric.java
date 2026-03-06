package club.iananderson.seasonhud.fabric.client;

import club.iananderson.seasonhud.client.SeasonHudClientCommon;
import club.iananderson.seasonhud.fabric.event.ClientEvents;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import net.fabricmc.api.ClientModInitializer;

public class SeasonHudClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    ClientEvents.register();

    if (CurrentMinimap.ftbChunksLoaded()) {
      // Disabled until FTBChunks is updated
      // SeasonComponent.ftbChunkSetup();
    }

    SeasonHudClientCommon.initAccessoriesClient();
  }
}

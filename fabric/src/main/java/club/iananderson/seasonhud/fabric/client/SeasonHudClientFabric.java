package club.iananderson.seasonhud.fabric.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.SeasonHudClientCommon;
import club.iananderson.seasonhud.fabric.event.ClientEvents;
import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import club.iananderson.seasonhud.impl.accessory.mods.accessories.AccessoriesCompat;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.ftbchunks.SeasonComponent;
import net.fabricmc.api.ClientModInitializer;

public class SeasonHudClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    ClientEvents.register();

    if (CurrentMinimap.ftbChunksLoaded()) {
      SeasonComponent.ftbChunkSetup();
    }

    SeasonHudClientCommon.initAccessoriesClient();
  }
}

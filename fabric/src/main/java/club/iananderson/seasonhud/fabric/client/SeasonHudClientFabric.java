package club.iananderson.seasonhud.fabric.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.fabric.event.ClientEvents;
import club.iananderson.seasonhud.impl.accessories.AccessoriesCompat;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.SeasonComponent;
import net.fabricmc.api.ClientModInitializer;

public class SeasonHudClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    ClientEvents.register();

    if (CurrentMinimap.ftbChunksLoaded()) {
      SeasonComponent.ftbChunkSetup();
    }

    if (Common.accessoriesLoaded() && !Common.trinketsLoaded()) {
      AccessoriesCompat.clientInit();
    }
  }
}

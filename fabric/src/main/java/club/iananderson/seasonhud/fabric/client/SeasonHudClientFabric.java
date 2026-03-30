package club.iananderson.seasonhud.fabric.client;

import club.iananderson.seasonhud.fabric.event.ClientEvents;
import net.fabricmc.api.ClientModInitializer;

public class SeasonHudClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {
    ClientEvents.register();
  }
}

package club.iananderson.seasonhud.fabric.event;

import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.SeasonHudClientCommon;
import club.iananderson.seasonhud.fabric.client.overlays.SeasonHudOverlay;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;

public class ClientEvents {
  private ClientEvents() {
  }

  // Key Bindings
  private static void registerKeyInputs() {
    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      SeasonHudClientCommon.optionsKeyInput();
    });
  }

  private static void registerKeyMappings() {
    KeyBindings.seasonhudOptionsKeyMapping = KeyMappingHelper.registerKeyMapping(
        KeyBindings.seasonhudOptionsKeyMapping);
  }

  private static void registerHud() {
    SeasonHudOverlay.init();
  }

  public static void register() {
    registerKeyMappings();
    registerKeyInputs();
    registerHud();
  }
}


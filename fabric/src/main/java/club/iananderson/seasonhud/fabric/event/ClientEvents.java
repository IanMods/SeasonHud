package club.iananderson.seasonhud.fabric.event;

import club.iananderson.seasonhud.SeasonHudClientCommon;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.fabric.client.overlays.JourneyMap;
import club.iananderson.seasonhud.fabric.client.overlays.MapAtlases;
import club.iananderson.seasonhud.fabric.client.overlays.SeasonHudOverlay;
import club.iananderson.seasonhud.fabric.impl.minimaps.XaeroInfoDisplays;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

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
    KeyBindings.seasonhudOptionsKeyMapping = KeyBindingHelper.registerKeyBinding(
        KeyBindings.seasonhudOptionsKeyMapping);
  }

  private static void registerHud() {
    SeasonHudOverlay.init();

    if (CurrentMinimap.journeyMapLoaded()) {
      JourneyMap.init();
    }

    if (CurrentMinimap.mapAtlasesLoaded()) {
      MapAtlases.init();
    }
  }

  public static void register() {
    registerKeyMappings();
    registerKeyInputs();
    registerHud();
  }
}


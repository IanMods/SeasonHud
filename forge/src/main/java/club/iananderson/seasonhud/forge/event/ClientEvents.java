package club.iananderson.seasonhud.forge.event;

import static club.iananderson.seasonhud.client.KeyBindings.seasonhudOptionsKeyMapping;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.SeasonHudClientCommon;
import club.iananderson.seasonhud.forge.client.overlays.SeasonHudOverlay;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.ForgeLayeredDraw;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

@ParametersAreNonnullByDefault
public class ClientEvents {

  @SubscribeEvent
  public static void onKeyInput(InputEvent.Key event) {
    SeasonHudClientCommon.optionsKeyInput();
  }

  // Overlays
  @SubscribeEvent
  public static void registerGuiOverlays(AddGuiOverlayLayersEvent event) {
    SeasonHudOverlay.init();

    ForgeLayeredDraw layeredDraw = event.getLayeredDraw();
    layeredDraw.addAbove(ForgeLayeredDraw.PRE_SLEEP_STACK, Common.location("seasonhud"),
                         ForgeLayeredDraw.CAMERA_OVERLAY, SeasonHudOverlay.HUD_INSTANCE);
  }

  // Key Bindings
  @SubscribeEvent
  public static void onKeyRegister(RegisterKeyMappingsEvent event) {
    event.register(seasonhudOptionsKeyMapping);
  }
}


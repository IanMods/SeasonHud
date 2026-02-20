package club.iananderson.seasonhud.forge.event;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.SeasonHudClientCommon;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.forge.client.overlays.SeasonHudOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.AddGuiOverlayLayersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.ForgeLayeredDraw;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT)
  public static class ClientForgeEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
      SeasonHudClientCommon.optionsKeyInput();
    }
  }

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModBusEvents {
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
      event.register(KeyBindings.seasonhudOptionsKeyMapping);
    }
  }
}


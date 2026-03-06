package club.iananderson.seasonhud.forge.event;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.SeasonHudClientCommon;
import club.iananderson.seasonhud.forge.client.overlays.JourneyMap;
import club.iananderson.seasonhud.forge.client.overlays.MapAtlases;
import club.iananderson.seasonhud.forge.client.overlays.SeasonHudOverlay;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT)
  public static class ClientForgeEvents {
    @SubscribeEvent
    public static void renderSeasonHudOverlay(RenderGameOverlayEvent.Post event) {
      if (event.getType() == ElementType.ALL) {
        PoseStack graphics = event.getMatrixStack();

        if (CurrentMinimap.journeyMapLoaded()) {
          JourneyMap.init();
          JourneyMap.HUD_INSTANCE.render(graphics);
        }

        if (CurrentMinimap.mapAtlasesLoaded()) {
          MapAtlases.init();
          MapAtlases.HUD_INSTANCE.render(graphics);
        }

        SeasonHudOverlay.init();
        SeasonHudOverlay.HUD_INSTANCE.render(graphics);
      }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
      SeasonHudClientCommon.optionsKeyInput();
    }
  }

  @Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class ClientModBusEvents {
    // Key Bindings
    @SubscribeEvent
    public static void onKeyRegister(FMLClientSetupEvent event) {
      ClientRegistry.registerKeyBinding(KeyBindings.seasonhudOptionsKeyMapping);
    }
  }
}
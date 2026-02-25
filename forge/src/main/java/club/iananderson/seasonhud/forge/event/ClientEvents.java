package club.iananderson.seasonhud.forge.event;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.SeasonHudClientCommon;
import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.forge.client.overlays.JourneyMap;
import club.iananderson.seasonhud.forge.client.overlays.MapAtlases;
import club.iananderson.seasonhud.forge.client.overlays.SeasonHudOverlay;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraftforge.client.gui.ForgeIngameGui.FROSTBITE_ELEMENT;

public class ClientEvents {

	@Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT)
	public static class ClientForgeEvents {

		@SubscribeEvent
		public static void onKeyInput(InputEvent.KeyInputEvent event) {
			SeasonHudClientCommon.optionsKeyInput();
		}
	}

	@Mod.EventBusSubscriber(modid = Common.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModBusEvents {

		// Overlays
		@SubscribeEvent
		public static void registerGuiOverlays(FMLClientSetupEvent event) {
			SeasonHudOverlay.init();
			OverlayRegistry.registerOverlayAbove(FROSTBITE_ELEMENT, "seasonhud", SeasonHudOverlay.HUD_INSTANCE);
		}

		@SubscribeEvent
		public static void registerJourneyMapOverlay(FMLClientSetupEvent event) {
			if (CurrentMinimap.journeyMapLoaded()) {
				JourneyMap.init();
				OverlayRegistry.registerOverlayAbove(FROSTBITE_ELEMENT, "journeymap", JourneyMap.HUD_INSTANCE);
			}
		}

		@SubscribeEvent
		public static void registerMapAtlasesOverlay(FMLClientSetupEvent event) {
			if (CurrentMinimap.mapAtlasesLoaded()) {
				MapAtlases.init();
				OverlayRegistry.registerOverlayAbove(FROSTBITE_ELEMENT, "mapatlases", MapAtlases.HUD_INSTANCE);
			}
		}

		// Key Bindings
		@SubscribeEvent
		public static void onKeyRegister(FMLClientSetupEvent event) {
			ClientRegistry.registerKeyBinding(KeyBindings.seasonhudOptionsKeyMapping);
		}
	}
}
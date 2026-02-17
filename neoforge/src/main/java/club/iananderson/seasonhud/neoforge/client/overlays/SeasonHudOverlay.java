package club.iananderson.seasonhud.neoforge.client.overlays;

import club.iananderson.seasonhud.client.overlays.SeasonHudOverlayCommon;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;

public class SeasonHudOverlay implements LayeredDraw.Layer {
  public static SeasonHudOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new SeasonHudOverlay();
  }

  public void render(GuiGraphics seasonStack, DeltaTracker deltaTracker) {
    SeasonHudOverlayCommon.render(seasonStack);
  }
}
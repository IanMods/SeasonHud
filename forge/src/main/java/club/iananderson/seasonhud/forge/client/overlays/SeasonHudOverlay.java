package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.SeasonHudOverlayCommon;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import org.jetbrains.annotations.NotNull;

public class SeasonHudOverlay implements LayeredDraw.Layer {
  public static SeasonHudOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new SeasonHudOverlay();
  }

  @Override
  public void render(@NotNull GuiGraphics graphics, @NotNull DeltaTracker deltaTracker) {
    SeasonHudOverlayCommon.render(graphics);
  }
}

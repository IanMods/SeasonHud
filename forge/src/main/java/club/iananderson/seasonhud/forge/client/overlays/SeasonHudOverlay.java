package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.SeasonHudOverlayCommon;
import javax.annotation.Nonnull;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeLayer;

public class SeasonHudOverlay implements ForgeLayer {
  public static SeasonHudOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new SeasonHudOverlay();
  }

  @Override
  public void render(@Nonnull GuiGraphicsExtractor graphics, @Nonnull DeltaTracker tickCounter) {
    SeasonHudOverlayCommon.render(graphics, tickCounter);
  }
}

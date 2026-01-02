package club.iananderson.seasonhud.neoforge.client.overlays;

import club.iananderson.seasonhud.client.overlays.SeasonHudOverlayCommon;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.client.gui.GuiLayer;
import org.jetbrains.annotations.NotNull;

public class SeasonHUDOverlay implements GuiLayer {
  public static SeasonHUDOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new SeasonHUDOverlay();
  }

  public void render(@NotNull GuiGraphics seasonStack, @NotNull DeltaTracker deltaTracker) {
    SeasonHudOverlayCommon.render(seasonStack);
  }
}
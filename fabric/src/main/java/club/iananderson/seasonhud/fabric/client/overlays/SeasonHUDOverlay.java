package club.iananderson.seasonhud.fabric.client.overlays;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.overlays.SeasonHUDOverlayCommon;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public class SeasonHUDOverlay implements HudElement {
  public static SeasonHUDOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new SeasonHUDOverlay();
    HudElementRegistry.addLast(Common.location("hud"), HUD_INSTANCE);
  }

  @Override
  public void render(GuiGraphics graphics, DeltaTracker tickCounter) {
    SeasonHUDOverlayCommon.render(graphics);
  }

}
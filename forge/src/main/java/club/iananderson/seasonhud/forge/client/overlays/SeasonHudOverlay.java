package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.SeasonHudOverlayCommon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;

public class SeasonHudOverlay extends GuiComponent {
  public static SeasonHudOverlay HUD_INSTANCE;

  public SeasonHudOverlay() {
  }

  public static void init() {
    HUD_INSTANCE = new SeasonHudOverlay();
  }

  public void render(PoseStack graphics) {
    SeasonHudOverlayCommon.render(graphics);
  }
}

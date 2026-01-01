package club.iananderson.seasonhud.fabric.client.overlays;

import club.iananderson.seasonhud.client.overlays.SeasonHudOverlayCommon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class SeasonHudOverlay implements HudRenderCallback {
  public static SeasonHudOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new SeasonHudOverlay();
    HudRenderCallback.EVENT.register(HUD_INSTANCE);
  }

  @Override
  public void onHudRender(PoseStack graphics, float alpha) {
    SeasonHudOverlayCommon.render(graphics);
  }
}
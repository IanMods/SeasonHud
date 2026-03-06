package club.iananderson.seasonhud.fabric.client.overlays;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class JourneyMap implements HudRenderCallback {
  public static JourneyMap HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
    HudRenderCallback.EVENT.register(HUD_INSTANCE);
  }

  @Override
  public void onHudRender(PoseStack graphics, float alpha) {
    //  Minecraft mc = Minecraft.getInstance();
    //
    // JourneyMapCommon.renderHud(graphics, mc);
  }
}

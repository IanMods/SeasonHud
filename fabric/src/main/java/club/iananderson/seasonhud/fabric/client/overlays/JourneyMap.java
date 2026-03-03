package club.iananderson.seasonhud.fabric.client.overlays;

import club.iananderson.seasonhud.client.overlays.JourneyMapCommon;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class JourneyMap implements HudRenderCallback {
  public static JourneyMap HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
    HudRenderCallback.EVENT.register(HUD_INSTANCE);
  }

  @Override
  public void onHudRender(GuiGraphics graphics, float alpha) {
    Minecraft mc = Minecraft.getInstance();

    JourneyMapCommon.renderHud(graphics, mc);
  }
}

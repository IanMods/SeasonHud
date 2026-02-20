package club.iananderson.seasonhud.fabric.client.overlays;

import club.iananderson.seasonhud.client.overlays.JourneyMapCommon;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.MinimapMods;
import journeymap.client.render.draw.DrawUtil;
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

    if (CurrentMinimap.journeyMapLoaded() && CurrentMinimap.shouldDrawMinimapHud(MinimapMods.JOURNEYMAP, mc)) {
      JourneyMapCommon journeyMapCommon = JourneyMapCommon.getInstance(Minecraft.getInstance());

      graphics.pose().pushPose();
      graphics.pose().scale(1 / journeyMapCommon.getFontScale(), 1 / journeyMapCommon.getFontScale(), 0);
      DrawUtil.sizeDisplay(graphics.pose(), journeyMapCommon.getScreenWidth(), journeyMapCommon.getScreenHeight());
      graphics.pose().popPose();
      journeyMapCommon.drawSeasonLabel(graphics);
    }
  }
}

package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.MinimapMods;
import com.mojang.blaze3d.vertex.PoseStack;
import journeymap.client.render.draw.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

public class JourneyMap extends GuiComponent {
  public static JourneyMap HUD_INSTANCE;

  public JourneyMap() {
  }

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
  }

  public void render(PoseStack graphics) {
    Minecraft mc = Minecraft.getInstance();

    if (CurrentMinimap.journeyMapLoaded() && CurrentMinimap.shouldDrawMinimapHud(MinimapMods.JOURNEYMAP, mc)) {
      JourneyMapCommon journeyMapCommon = JourneyMapCommon.getInstance(Minecraft.getInstance());

      graphics.pushPose();
      graphics.scale(1 / journeyMapCommon.getFontScale(), 1 / journeyMapCommon.getFontScale(), 0);
      DrawUtil.sizeDisplay(journeyMapCommon.getScreenWidth(), journeyMapCommon.getScreenHeight());
      graphics.popPose();
      journeyMapCommon.drawSeasonLabel(graphics);
    }
  }
}
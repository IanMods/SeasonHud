package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.SeasonHudOverlayCommon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class SeasonHudOverlay implements IIngameOverlay {
  public static SeasonHudOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new SeasonHudOverlay();
  }

  public void render(ForgeIngameGui gui, PoseStack graphics, float partialTick, int width, int height) {
    SeasonHudOverlayCommon.render(graphics);
  }
}

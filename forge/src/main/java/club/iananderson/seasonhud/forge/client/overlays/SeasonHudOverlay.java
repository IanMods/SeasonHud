package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.SeasonHudOverlayCommon;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SeasonHudOverlay implements IGuiOverlay {
  public static SeasonHudOverlay HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new SeasonHudOverlay();
  }

  public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
    SeasonHudOverlayCommon.render(graphics);
  }
}

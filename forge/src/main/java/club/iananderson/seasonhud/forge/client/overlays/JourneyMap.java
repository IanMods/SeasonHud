package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.JourneyMapCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class JourneyMap implements IGuiOverlay {
  public static JourneyMap HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
  }

  @Override
  public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int scaledWidth, int scaledHeight) {
    Minecraft mc = Minecraft.getInstance();

    JourneyMapCommon.renderHud(graphics, mc);
  }
}
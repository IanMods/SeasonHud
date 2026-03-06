package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.JourneyMapCommon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

public class JourneyMap implements IIngameOverlay {
  public static JourneyMap HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
  }

  @Override
  public void render(ForgeIngameGui gui, PoseStack graphics, float partialTick, int scaledWidth, int scaledHeight) {
    Minecraft mc = Minecraft.getInstance();

    JourneyMapCommon.renderHud(graphics, mc);
  }
}
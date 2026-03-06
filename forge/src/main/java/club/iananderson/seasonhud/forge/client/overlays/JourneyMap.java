package club.iananderson.seasonhud.forge.client.overlays;

import club.iananderson.seasonhud.client.overlays.JourneyMapCommon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

public class JourneyMap extends GuiComponent {
  public static JourneyMap HUD_INSTANCE;

  public static void init() {
    HUD_INSTANCE = new JourneyMap();
  }

  public void render(PoseStack graphics) {
    Minecraft mc = Minecraft.getInstance();

    JourneyMapCommon.renderHud(graphics, mc);
  }
}
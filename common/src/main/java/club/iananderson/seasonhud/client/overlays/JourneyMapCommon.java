package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.MinimapMods;
import club.iananderson.seasonhud.impl.minimap.mods.journeymap.JourneymapSeasonPlugin;
import com.mojang.blaze3d.vertex.PoseStack;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.minimap.DisplayVars;
import net.minecraft.client.Minecraft;

public class JourneyMapCommon {
  public static void renderHud(PoseStack graphics, Minecraft mc) {
    if (CurrentMinimap.journeyMapLoaded() && CurrentMinimap.shouldDrawMinimapHud(MinimapMods.JOURNEYMAP, mc)) {
      JourneymapSeasonPlugin jmPlugin = JourneymapSeasonPlugin.getInstance();
      DisplayVars dv = UIManager.INSTANCE.getMiniMap().getDisplayVars();

      jmPlugin.drawSeasonInfoSlot(graphics, dv);
    }
  }
}
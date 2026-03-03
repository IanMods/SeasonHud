package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.MinimapMods;
import club.iananderson.seasonhud.impl.minimap.mods.journeymap.JourneymapSeasonPlugin;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.minimap.DisplayVars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class JourneyMapCommon {
  public static void renderHud(GuiGraphics graphics, Minecraft mc) {
    if (CurrentMinimap.journeyMapLoaded() && CurrentMinimap.shouldDrawMinimapHud(MinimapMods.JOURNEYMAP, mc)) {
      JourneymapSeasonPlugin jmPlugin = JourneymapSeasonPlugin.getInstance();
      DisplayVars dv = UIManager.INSTANCE.getMiniMap().getDisplayVars();

      jmPlugin.drawSeasonInfoSlot(graphics, dv);
    }
  }
}
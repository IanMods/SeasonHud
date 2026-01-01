package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.services.MinimapHelper;
import lilypuree.mapatlases.MapAtlasesMod;
import lilypuree.mapatlases.util.MapAtlasesAccessUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class ForgeMinimapHelper implements MinimapHelper {
  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases() {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      Minecraft mc = Minecraft.getInstance();

      if (mc.level == null || mc.player == null) {
        return true;
      }

      ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(mc.player.getInventory());

      boolean drawMinimapHud = MapAtlasesMod.CONFIG.drawMiniMapHUD.get();
      ;
      boolean hasAtlas = atlas.getCount() > 0;

      return !drawMinimapHud || !hasAtlas;
    } else {
      return false;
    }
  }
}

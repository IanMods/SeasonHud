package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.platform.services.MinimapHelper;
import lilypuree.mapatlases.MapAtlasesMod;
import lilypuree.mapatlases.util.MapAtlasesAccessUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.world.item.ItemStack;
import xaero.common.HudMod;
import xaero.lib.client.gui.ScreenBase;

public class ForgeMinimapHelper implements MinimapHelper {
  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases(Minecraft mc) {
    if (mc.level == null || mc.player == null) {
      return true;
    }

    ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(mc.player.getInventory());

    boolean drawMinimapHud = MapAtlasesMod.CONFIG.drawMiniMapHUD.get();
    ;
    boolean hasAtlas = atlas.getCount() > 0;

    return !drawMinimapHud || !hasAtlas;
  }

  @Override
  public boolean hideJourneyMap(Minecraft mc) {
    if (mc.level == null || mc.player == null) {
      return true;
    }

    MiniMapProperties properties = UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties();

    return !properties.enabled.get() || (!properties.isActive() && mc.isPaused()) || mc.player.isScoping() || !(
        mc.screen == null || mc.screen instanceof ChatScreen || mc.screen instanceof MinimapOptions);
  }

  @Override
  public boolean hideXaero(Minecraft mc) {
    if (mc.level == null || mc.player == null) {
      return true;
    }

    boolean minimapDisplayed = HudMod.INSTANCE.getSettings().getMinimap();

    return !minimapDisplayed || mc.options.renderDebug || !(mc.screen == null || mc.screen instanceof ChatScreen
        || mc.screen instanceof DeathScreen || mc.screen instanceof ScreenBase);
  }
}

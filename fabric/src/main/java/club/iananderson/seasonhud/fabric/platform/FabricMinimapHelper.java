package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.platform.services.MinimapHelper;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.option.MinimapOptions;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.option.MinimapOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.utils.MapAtlasesAccessUtils;
import xaero.common.HudMod;
import xaero.lib.client.gui.ScreenBase;

public class FabricMinimapHelper implements MinimapHelper {
  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases(Minecraft mc) {
    if (mc.level == null || mc.player == null || mc.player.level.dimension() != Level.OVERWORLD) {
      return true;
    }

    ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(mc.player.inventory);

    boolean drawMinimapHud = MapAtlasesMod.CONFIG.drawMiniMapHUD;

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

  @Override
  public boolean hideHudInCurrentDimension() {
    return false;
  }

  @Override
  public void openJourneyMapOptions(Screen returnScreen) {
  }
}
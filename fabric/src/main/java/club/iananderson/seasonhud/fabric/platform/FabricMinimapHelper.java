package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.platform.services.MinimapHelper;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.option.MinimapOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import xaero.common.HudMod;
import xaero.lib.client.gui.ScreenBase;

public class FabricMinimapHelper implements MinimapHelper {
  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases(Minecraft mc) {
    if (mc.level == null || mc.player == null || mc.getDebugOverlay().showDebugScreen()) {
      return true;
    }

    // Item atlasItem = MapAtlasesMod.MAP_ATLAS.get();
    //
    // boolean drawMinimapHud = MapAtlasesClientConfig.drawMiniMapHUD.get();
    // boolean emptyAtlas = MapAtlasesClient.getCurrentActiveAtlas().isEmpty();
    // boolean hideInHand = MapAtlasesClientConfig.hideWhenInHand.get();
    // boolean hasAtlas = (mc.player.getMainHandItem().is(atlasItem) || mc.player.getOffhandItem().is(atlasItem));
    //
    // return !drawMinimapHud || emptyAtlas || (hideInHand && hasAtlas);

    return false;
  }

  @Override
  public boolean hideJourneyMap(Minecraft mc) {
    if (mc.level == null || mc.player == null) {
      return true;
    }

    MiniMapProperties properties = UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties();

    return !properties.enabled.get() || (!properties.isActive() && mc.isPaused()) || mc.player.isScoping() || !(
        mc.gui.screen() == null || mc.gui.screen() instanceof ChatScreen || mc.gui.screen() instanceof MinimapOptions);
  }

  @Override
  public boolean hideXaero(Minecraft mc) {
    if (mc.level == null || mc.player == null) {
      return true;
    }

    boolean minimapDisplayed = HudMod.INSTANCE.getSettings().getMinimap();

    return !minimapDisplayed || mc.getDebugOverlay().showDebugScreen() || !(mc.gui.screen() == null
        || mc.gui.screen() instanceof ChatScreen || mc.gui.screen() instanceof DeathScreen || mc.gui.screen() instanceof ScreenBase);
  }
}
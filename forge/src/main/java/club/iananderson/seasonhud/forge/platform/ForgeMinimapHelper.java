package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.platform.services.MinimapHelper;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.option.MinimapOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.world.item.Item;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.client.MapAtlasesClient;
import pepjebs.mapatlases.config.MapAtlasesClientConfig;
import xaero.common.HudMod;
import xaero.lib.client.gui.ScreenBase;

public class ForgeMinimapHelper implements MinimapHelper {
  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases(Minecraft mc) {
    if (mc.level == null || mc.player == null) {
      return true;
    }

    Item atlasItem = MapAtlasesMod.MAP_ATLAS.get();

    boolean drawMinimapHud = MapAtlasesClientConfig.drawMiniMapHUD.get();
    boolean emptyAtlas = MapAtlasesClient.getCurrentActiveAtlas().isEmpty();
    boolean hideInHand = MapAtlasesClientConfig.hideWhenInHand.get();
    boolean hasAtlas = (mc.player.getMainHandItem().is(atlasItem) || mc.player.getOffhandItem().is(atlasItem));

    return !drawMinimapHud || emptyAtlas || (hideInHand && hasAtlas);
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

    return !minimapDisplayed || mc.getDebugOverlay().showDebugScreen() || !(mc.screen == null
        || mc.screen instanceof ChatScreen || mc.screen instanceof DeathScreen || mc.screen instanceof ScreenBase);
  }
}

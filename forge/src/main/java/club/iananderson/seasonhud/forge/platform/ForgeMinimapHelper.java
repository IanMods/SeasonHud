package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.platform.services.MinimapHelper;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.dialog.MinimapOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.world.item.Item;
import pepjebs.mapatlases.MapAtlasesMod;
import pepjebs.mapatlases.client.MapAtlasesClient;
import pepjebs.mapatlases.config.MapAtlasesClientConfig;
import xaero.common.HudMod;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.lib.client.gui.ScreenBase;

public class ForgeMinimapHelper implements MinimapHelper {
  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases() {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      Minecraft mc = Minecraft.getInstance();

      if (mc.level == null || mc.player == null) {
        return true;
      }

      Item atlasItem = MapAtlasesMod.MAP_ATLAS.get();

      boolean drawMinimapHud = MapAtlasesClientConfig.drawMiniMapHUD.get();
      boolean emptyAtlas = MapAtlasesClient.getCurrentActiveAtlas().isEmpty();
      boolean hideInHand = MapAtlasesClientConfig.hideWhenInHand.get();
      boolean hasAtlas = (mc.player.getMainHandItem().is(atlasItem) || mc.player.getOffhandItem().is(atlasItem));

      return !drawMinimapHud || emptyAtlas || (hideInHand && hasAtlas);
    } else {
      return false;
    }
  }

  @Override
  public boolean hideJourneyMap() {
    if (CurrentMinimap.journeyMapLoaded()) {
      Minecraft mc = Minecraft.getInstance();
      MiniMapProperties properties = UIManager.INSTANCE.getMiniMap().getCurrentMinimapProperties();

      return !properties.enabled.get() || (!properties.isActive() && mc.isPaused()) || mc.player.isScoping() || !(
          mc.screen == null || mc.screen instanceof ChatScreen || mc.screen instanceof MinimapOptions);
    } else {
      return false;
    }
  }

  @Override
  public boolean hideXaero() {
    if (CurrentMinimap.xaeroLoaded()) {
      Minecraft mc = Minecraft.getInstance();

      boolean minimapDisplayed = HudMod.INSTANCE.getHudConfigs().getClientConfigManager()
          .getEffective(MinimapProfiledConfigOptions.DISPLAY_MINIMAP);

      return !minimapDisplayed || mc.options.renderDebug || !(mc.screen == null || mc.screen instanceof ChatScreen
          || mc.screen instanceof DeathScreen || mc.screen instanceof ScreenBase);
    } else {
      return false;
    }
  }
}

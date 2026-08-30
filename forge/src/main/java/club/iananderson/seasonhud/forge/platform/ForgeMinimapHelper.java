package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.platform.services.MinimapHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.ui.UIManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pepjebs.dicemc.util.MapAtlasesAccessUtils;
import sereneseasons.config.SeasonsConfig;
import xaero.common.HudMod;
import xaero.lib.client.gui.ScreenBase;

public class ForgeMinimapHelper implements MinimapHelper {
  public static boolean isDimensionValid(List<? extends String> validDimensions, ResourceKey<Level> dimension) {
    for (String validDimension : validDimensions) {
      if (dimension.toString().equals(validDimension)) {
        return true;
      }
    }

    return false;
  }

  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases(Minecraft mc) {
    if (mc.level == null || mc.player == null) {
      return true;
    }

    ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(mc.player.inventory);

    boolean drawMinimapHud = pepjebs.dicemc.config.Config.DRAW_MINIMAP_HUD.get();

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
        mc.screen == null || mc.screen instanceof ChatScreen
            || mc.screen instanceof journeymap.client.ui.option.MinimapOptions);
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
    ResourceKey<Level> currentDim = Objects.requireNonNull(Minecraft.getInstance().level).dimension();

    if (Common.sereneSeasonsLoaded()) {
      return !SeasonsConfig.isDimensionWhitelisted(currentDim);
    }
    if (Common.eclipticSeasonsLoaded()) {
      List<String> validDimensions = new ArrayList<>();
      validDimensions.add(Level.OVERWORLD.location().toString());

      return !isDimensionValid(validDimensions, currentDim);
    } else {
      return false;
    }
  }
}
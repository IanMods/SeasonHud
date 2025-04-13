package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.services.IMinimapHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import pepjebs.dicemc.util.MapAtlasesAccessUtils;
import sereneseasons.config.SeasonsConfig;

public class ForgeMinimapHelper implements IMinimapHelper {
  // Needed for older versions. Makes it easier to port.
  @Override
  public boolean hideMapAtlases() {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      Minecraft mc = Minecraft.getInstance();

      if (mc.level == null || mc.player == null) {
        return true;
      }

      ItemStack atlas = MapAtlasesAccessUtils.getAtlasFromPlayerByConfig(mc.player.inventory);

      boolean drawMinimapHud = pepjebs.dicemc.config.Config.DRAW_MINIMAP_HUD.get();

      boolean hasAtlas = atlas.getCount() > 0;

      return !drawMinimapHud || !hasAtlas;
    }
    else {
      return false;
    }
  }

  public static boolean isDimensionValid(List<? extends String> validDimensions, ResourceKey<Level> dimension) {
    for (String validDimension : validDimensions) {
      if (dimension.location().toString().equals(validDimension)) {
        return true;
      }
    }

    return false;
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
    }
    else {
      return false;
    }
  }
}

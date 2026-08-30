package club.iananderson.seasonhud.mixin.mapatlases;

import club.iananderson.seasonhud.client.overlays.MapAtlasesCommon;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.MinimapMods;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import pepjebs.mapatlases.client.Anchoring;
import pepjebs.mapatlases.client.ui.MapAtlasesHUD;
import pepjebs.mapatlases.config.MapAtlasesClientConfig;
import pepjebs.mapatlases.utils.MapDataHolder;

@Mixin(MapAtlasesHUD.class)
public class MapAtlasHudMixin {
  @SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName"})
  @Shadow
  protected final int BG_SIZE = 64;

  @Shadow
  private float globalScale;

  @Shadow
  @Final
  private Minecraft mc;

  @SuppressWarnings("checkstyle:ParameterName")
  @Inject(remap = false, method = "renderText", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
  private void renderText(GuiGraphics graphics, int x, int y, Anchoring anchorLocation, CallbackInfo ci,
      float textScaling, int textHeightOffset, int actualBgSize, Font font, PoseStack poseStack) {

    if (CurrentMinimap.mapAtlasesLoaded() && CurrentMinimap.shouldDrawMinimapHud(MinimapMods.MAP_ATLASES, mc)) {
      if (MapAtlasesClientConfig.drawMinimapBiome.get()) {
        textHeightOffset += (int) (10.0F * textScaling);
      }

      poseStack.pushPose();

      MapAtlasesCommon.drawMapComponentSeason(graphics, font, x,
                                              (int) (y + BG_SIZE + (textHeightOffset / globalScale)),
                                              actualBgSize, textScaling, globalScale);

      poseStack.popPose();
    }
  }
}

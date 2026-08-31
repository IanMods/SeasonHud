package club.iananderson.seasonhud.mixin.mapatlases;

// @Mixin(MapAtlasesHUD.class)
public class MapAtlasHudMixin {
  // @SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MemberName"})
  // @Shadow
  // protected final int BG_SIZE = 64;
  //
  // @Shadow
  // private float globalScale;
  //
  // @Shadow
  // @Final
  // private Minecraft mc;
  //
  // @SuppressWarnings("checkstyle:ParameterName")
  // @Inject(remap = false, method = "renderText", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
  // private void renderText(GuiGraphics graphics, int x, int y, Anchoring anchorLocation, CallbackInfo ci,
  //     float textScaling, int textHeightOffset, int actualBgSize, Font font, PoseStack poseStack) {
  //
  //   if (CurrentMinimap.mapAtlasesLoaded() && CurrentMinimap.shouldDrawMinimapHud(MinimapMods.MAP_ATLASES, mc)) {
  //     if (MapAtlasesClientConfig.drawMinimapBiome.get()) {
  //       textHeightOffset += (int) (10.0F * textScaling);
  //     }
  //
  //     poseStack.pushPose();
  //
  //     MapAtlasesCommon.drawMapComponentSeason(graphics, font, x,
  //                                             (int) (y + BG_SIZE + (textHeightOffset / globalScale)),
  //                                             actualBgSize, textScaling, globalScale);
  //
  //     poseStack.popPose();
  //   }
  // }
}

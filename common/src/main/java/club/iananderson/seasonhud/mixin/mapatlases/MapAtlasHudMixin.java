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
  // @Inject(remap = false, method = "render", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
  // private void render(GuiGraphics graphics, DeltaTracker partialTick, CallbackInfo ci, Window window, int screenWidth,
  //     int screenHeight, ItemStack atlas, MapDataHolder activeMap, ClientLevel level, LocalPlayer player,
  //     PoseStack poseStack, int mapWidgetSize, Anchoring anchorLocation, int off, int x, int y, float yRot, int light,
  //     int borderSize, float textScaling, int textHeightOffset, int actualBgSize, Font font) {
  //
  //   if (CurrentMinimap.mapAtlasesLoaded() && CurrentMinimap.shouldDrawMinimapHud(MinimapMods.MAP_ATLASES, mc)) {
  //     if (MapAtlasesClientConfig.drawMinimapBiome.get()) {
  //       textHeightOffset += (int) (10.0F * textScaling);
  //     }
  //     MapAtlasesCommon.drawMapComponentSeason(graphics, font, x, (int) (y + BG_SIZE + (textHeightOffset / globalScale)),
  //                                             actualBgSize, textScaling, globalScale);
  //   }
  // }
}

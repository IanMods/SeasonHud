package club.iananderson.seasonhud.impl.minimap.mods.ftbchunks;

// Disabled until FTBChunks is updated
// public class SeasonComponent implements MinimapInfoComponent {
public class SeasonComponent {
  // public static final ResourceLocation ID = Common.location("season");
  // public SeasonComponent() {
  //   super();
  // }

  // public static void ftbChunkSetup() {
  //   Common.LOG.info("Loading FTB Chunks Season Component");
  //
  //   FTBChunksClientAPI clientApi = FTBChunksAPI.clientApi();
  //   clientApi.registerMinimapComponent(new SeasonComponent());
  //   FTBChunksClient.INSTANCE.setupComponents();
  //
  //   Common.LOG.info("FTB Chunks Season Component Loaded");
  // }

  // public ResourceLocation id() {
  //   return ID;
  // }

  // @Override
  // public void render(MinimapContext context, GuiGraphics graphics, Font font) {
  //   MutableComponent seasonCombined = CurrentSeason.getInstance(context.minecraft()).getHudText();
  //   MutableComponent fertility = CurrentFertility.getInstance(context.minecraft()).getMinimapText();
  //   int lineHeight = computeLineHeight(context.minecraft(), 1) + 1;
  //   float scale = FTBChunksClientConfig.MINIMAP_FONT_SCALE.get().floatValue();

  //   this.drawCenteredText(font, graphics, seasonCombined, 0);

  //   if (CurrentFertility.getInstance(context.minecraft()).shouldDrawNewLine()) {
  //     this.drawCenteredText(font, graphics, fertility, (int) (lineHeight / scale));
  //   }
  // }

  // @Override
  // public int height(MinimapContext context) {
  //   int lines = 1;

  //   if (CurrentFertility.getInstance(context.minecraft()).shouldDrawNewLine()) {
  //     lines = 2;
  //   }

  //   return computeLineHeight(context.minecraft(), lines) + 1;
  // }

  // @Override
  // public boolean shouldRender(MinimapContext context) {
  //   return CurrentMinimap.shouldDrawMinimapHud(MinimapMods.FTB_CHUNKS, context.minecraft());
  // }
}

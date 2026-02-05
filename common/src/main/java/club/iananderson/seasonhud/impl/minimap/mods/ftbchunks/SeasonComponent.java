package club.iananderson.seasonhud.impl.minimap.mods.ftbchunks;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.MinimapMods;
import club.iananderson.seasonhud.impl.season.CurrentFertility;
import club.iananderson.seasonhud.impl.season.CurrentSeason;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.client.FTBChunksClientAPI;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapContext;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapInfoComponent;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class SeasonComponent implements MinimapInfoComponent {
  public static final ResourceLocation ID = FTBChunksAPI.rl("season");

  public SeasonComponent() {
    super();
  }

  public static void ftbChunkSetup() {
    Common.LOG.info("Loading FTB Chunks Season Component");

    FTBChunksClientAPI clientApi = FTBChunksAPI.clientApi();
    clientApi.registerMinimapComponent(new SeasonComponent());

    Common.LOG.info("FTB Chunks Season Component Loaded");
  }

  public ResourceLocation id() {
    return ID;
  }

  @Override
  public void render(MinimapContext context, GuiGraphics graphics, Font font) {
    MutableComponent seasonCombined = CurrentSeason.getInstance(context.minecraft()).getHudText();
    MutableComponent fertility = CurrentFertility.getInstance(context.minecraft()).getMinimapText();
    int lineHeight = computeLineHeight(context.minecraft(), 1) + 1;
    float scale = FTBChunksClientConfig.MINIMAP_FONT_SCALE.get().floatValue();

    this.drawCenteredText(font, graphics, seasonCombined, 0);

    if (SeasonHudClient.getShowFertility()) {
      this.drawCenteredText(font, graphics, fertility, (int) (lineHeight / scale));
    }
  }

  @Override
  public int height(MinimapContext context) {
    int lines = 1;

    if (SeasonHudClient.getShowFertility()) {
      lines = 2;
    }

    return computeLineHeight(context.minecraft(), lines) + 1;
  }

  @Override
  public boolean shouldRender(MinimapContext context) {
    return CurrentMinimap.shouldDrawMinimapHud(MinimapMods.FTB_CHUNKS);
  }
}

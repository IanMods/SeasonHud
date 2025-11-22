package club.iananderson.seasonhud.impl.minimaps;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimap;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.client.FTBChunksClientAPI;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapContext;
import dev.ftb.mods.ftbchunks.api.client.minimap.MinimapInfoComponent;
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

    this.drawCenteredText(font, graphics, seasonCombined, 0);
  }

  @Override
  public boolean shouldRender(MinimapContext context) {
    return CurrentMinimap.shouldDrawMinimapHud(Minimap.FTB_CHUNKS);
  }
}

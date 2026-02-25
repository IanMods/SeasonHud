package club.iananderson.seasonhud.mixin.ftbchunks;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.MinimapMods;
import club.iananderson.seasonhud.impl.season.CurrentFertility;
import club.iananderson.seasonhud.impl.season.CurrentSeason;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.client.map.MapDimension;
import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FTBChunksClient.class)
public class FtbChunksClientMixin {

  @Unique
  private static final BooleanValue MINIMAP_SEASON = FTBChunksClientConfig.MINIMAP.getBoolean("season", true)
      .comment(new String[]{"Show season under minimap"});

  @Inject(method = "buildMinimapTextData", at = @At("RETURN"), remap = false, cancellable = true)
  private void buildMinimapTextData(Minecraft mc, double playerX, double playerY, double playerZ, MapDimension dim,
      CallbackInfoReturnable<List<Component>> cir) {
    MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getHudText();
    MutableComponent fertility = CurrentFertility.getInstance(Minecraft.getInstance()).getMinimapText();
    List<Component> res = cir.getReturnValue();

    SeasonHudClient.setEnableMod(MINIMAP_SEASON.get());

    if (CurrentMinimap.shouldDrawMinimapHud(MinimapMods.FTB_CHUNKS, mc)) {
      res.add(seasonCombined);
      if (SeasonHudClient.getShowFertility()) {
        res.add(fertility);
      }
    }

    cir.setReturnValue(res);
  }
}

package club.iananderson.seasonhud.fabric.mixin.ftbchunks;

import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.MinimapMods;
import club.iananderson.seasonhud.impl.season.CurrentFertility;
import club.iananderson.seasonhud.impl.season.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FTBChunksClient.class)
public class FtbChunksClientMixin {

  @Shadow
  private static final List<Component> MINIMAP_TEXT_LIST = new ArrayList<>(3);

  @Unique
  private static final BooleanValue MINIMAP_SEASON = FTBChunksClientConfig.MINIMAP.getBoolean("season", true)
      .comment(new String[]{"Show season under minimap"});

  @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V"))
  private void renderHud(PoseStack matrixStack, float tickDelta, CallbackInfo ci) {
    MutableComponent seasonCombined = CurrentSeason.getInstance(Minecraft.getInstance()).getHudText();
    MutableComponent fertility = CurrentFertility.getInstance(Minecraft.getInstance()).getMinimapText();

    SeasonHudClient.setEnableMod(MINIMAP_SEASON.get());

    if (CurrentMinimap.shouldDrawMinimapHud(MinimapMods.FTB_CHUNKS, Minecraft.getInstance())) {
      MINIMAP_TEXT_LIST.add(seasonCombined);
      if (SeasonHudClient.getShowFertility()) {
        MINIMAP_TEXT_LIST.add(fertility);
      }
    }
  }
}

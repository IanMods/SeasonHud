package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.season.CurrentFertility;
import club.iananderson.seasonhud.impl.season.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.MutableComponent;

public class MapAtlasesCommon {
  private MapAtlasesCommon() {
  }

  private static void drawSeasonWithLighterShadow(PoseStack graphics, Font font, MutableComponent text,
      MutableComponent shadowText) {
    font.draw(graphics, shadowText, 1, 1, 5855577);
    font.draw(graphics, text, 0, 0, 0xffffff);
  }

  private static void drawScaledComponent(PoseStack graphics, Font font, int x, int y, MutableComponent text,
      MutableComponent shadowText, float textScaling, int maxWidth, int targetWidth) {
    float textWidth = font.width(text);
    float scale = Math.min(1.0F, maxWidth * textScaling / textWidth);
    scale *= textScaling;
    float centerX = x + targetWidth / 2.0F;
    graphics.pushPose();
    graphics.translate(centerX, (y + 4), 5.0F);
    graphics.scale(scale, scale, 1.0F);
    graphics.translate(-textWidth / 2.0F, -4.0F, 0.0F);
    drawSeasonWithLighterShadow(graphics, font, text, shadowText);
    graphics.popPose();
  }

  public static void drawMapComponentSeason(PoseStack graphics, Font font, int x, int y, int targetWidth,
      float textScaling, float globalScale) {
    Minecraft mc = Minecraft.getInstance();
    MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getHudText();
    MutableComponent seasonShadowText = CurrentSeason.getInstance(mc).getHudTextNoFormat();

    drawScaledComponent(graphics, font, x, y, seasonCombined, seasonShadowText, textScaling / globalScale, targetWidth,
                        (int) (targetWidth / globalScale));

    if (CurrentFertility.getInstance(mc).shouldDrawNewLine()) {
      MutableComponent fertility = CurrentFertility.getInstance(mc).getMinimapText();
      MutableComponent fertilityShadowText = CurrentFertility.getInstance(mc).getHudTextNoFormat();

      drawScaledComponent(graphics, font, x, y + font.lineHeight, fertility, fertilityShadowText,
                          textScaling / globalScale, targetWidth, (int) (targetWidth / globalScale));
    }
  }

  public static void drawScaledText(PoseStack graphics, int x, int y, MutableComponent text,
      MutableComponent shadowText, float textScaling, int originOffsetWidth, int originOffsetHeight) {
    Minecraft mc = Minecraft.getInstance();
    float textWidth = (float) mc.font.width(text) * textScaling;
    float textX = (float) ((double) x + (double) originOffsetWidth / 2.0 - (double) textWidth / 2.0);
    float textY = (float) (y + originOffsetHeight);
    if (textX + textWidth >= (float) mc.getWindow().getGuiScaledWidth()) {
      textX = (float) mc.getWindow().getGuiScaledWidth() - textWidth;
    }

    graphics.pushPose();
    graphics.translate(textX, textY, 5.0F);
    graphics.scale(textScaling, textScaling, 1.0F);
    mc.font.draw(graphics, shadowText, 1, 1, Integer.parseInt("595959", 16));
    mc.font.draw(graphics, text, 0, 0, Integer.parseInt("E0E0E0", 16));
    graphics.popPose();
  }

  public static void drawMapComponentSeasonOld(PoseStack graphics, int x, int y, int originOffsetWidth,
      int originOffsetHeight, float textScaling) {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      Minecraft mc = Minecraft.getInstance();
      MutableComponent seasonCombined = CurrentSeason.getInstance(Minecraft.getInstance()).getHudText();
      MutableComponent seasonShadowText = CurrentSeason.getInstance(Minecraft.getInstance()).getHudTextNoFormat();
      MutableComponent fertility = CurrentFertility.getInstance(Minecraft.getInstance()).getMinimapText();
      MutableComponent fertilityShadowText = CurrentFertility.getInstance(Minecraft.getInstance()).getHudTextNoFormat();

      drawScaledText(graphics, x, y, seasonCombined, seasonShadowText, textScaling, originOffsetWidth,
                     originOffsetHeight);

      if (Common.sereneSeasonsLoaded() && SeasonHudClient.getShowFertility()) {
        drawScaledText(graphics, x, y + mc.font.lineHeight, fertility, fertilityShadowText, textScaling,
                       originOffsetWidth, originOffsetHeight);
      }
    }
  }
}

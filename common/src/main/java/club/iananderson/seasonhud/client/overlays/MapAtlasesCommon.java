package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.seasons.CurrentFertility;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import org.joml.Matrix3x2fStack;

public class MapAtlasesCommon {
  private MapAtlasesCommon() {
  }

  private static void drawSeasonWithLighterShadow(GuiGraphics graphics, Font font, MutableComponent text,
      MutableComponent shadowText) {
    graphics.drawString(font, shadowText, 1, 1, 5855577, false);
    graphics.drawString(font, text, 0, 0, 0xffffffff, false);
  }

  private static void drawScaledComponent(GuiGraphics graphics, Font font, int x, int y, MutableComponent text,
      MutableComponent shadowText, float textScaling, int maxWidth, int targetWidth) {
    Matrix3x2fStack pose = graphics.pose();
    float textWidth = font.width(text);
    float scale = Math.min(1.0F, maxWidth * textScaling / textWidth);
    scale *= textScaling;
    float centerX = x + targetWidth / 2.0F;
    pose.pushMatrix();
    pose.translate(centerX, (y + 4));
    pose.scale(scale, scale);
    pose.translate(-textWidth / 2.0F, -4.0F);
    drawSeasonWithLighterShadow(graphics, font, text, shadowText);
    pose.popMatrix();
  }

  public static void drawMapComponentSeason(GuiGraphics graphics, Font font, int x, int y, int targetWidth,
      float textScaling, float globalScale) {
    MutableComponent seasonCombined = CurrentSeason.getInstance(Minecraft.getInstance()).getHudText();
    MutableComponent seasonShadowText = CurrentSeason.getInstance(Minecraft.getInstance()).getHudTextNoFormat();
    MutableComponent fertility = CurrentFertility.getInstance(Minecraft.getInstance()).getMinimapText();
    MutableComponent fertilityShadowText = CurrentFertility.getInstance(Minecraft.getInstance()).getHudTextNoFormat();

    drawScaledComponent(graphics, font, x, y, seasonCombined, seasonShadowText, textScaling / globalScale, targetWidth,
                        (int) (targetWidth / globalScale));

    if (Common.sereneSeasonsLoaded() && SeasonHudClient.getShowFertility()) {
      drawScaledComponent(graphics, font, x, y + font.lineHeight, fertility, fertilityShadowText,
                          textScaling / globalScale, targetWidth, (int) (targetWidth / globalScale));
    }
  }

  public static void drawScaledText(GuiGraphics context, int x, int y, MutableComponent text,
      MutableComponent shadowText, float textScaling, int originOffsetWidth, int originOffsetHeight) {
    Minecraft mc = Minecraft.getInstance();
    Matrix3x2fStack poseStack = context.pose();
    float textWidth = (float) mc.font.width(text) * textScaling;
    float textX = (float) ((double) x + (double) originOffsetWidth / 2.0 - (double) textWidth / 2.0);
    float textY = (float) (y + originOffsetHeight);
    if (textX + textWidth >= (float) mc.getWindow().getGuiScaledWidth()) {
      textX = (float) mc.getWindow().getGuiScaledWidth() - textWidth;
    }

    poseStack.pushMatrix();
    poseStack.translate(textX, textY);
    poseStack.scale(textScaling, textScaling);
    context.drawString(mc.font, shadowText, 1, 1, Integer.parseInt("595959", 16), false);
    context.drawString(mc.font, text, 0, 0, Integer.parseInt("E0E0E0", 16), false);
    poseStack.popMatrix();
  }

  public static void drawMapComponentSeasonOld(GuiGraphics poseStack, int x, int y, int originOffsetWidth,
      int originOffsetHeight, float textScaling) {
    if (CurrentMinimap.mapAtlasesLoaded()) {
      Minecraft mc = Minecraft.getInstance();
      MutableComponent seasonCombined = CurrentSeason.getInstance(Minecraft.getInstance()).getHudText();
      MutableComponent seasonShadowText = CurrentSeason.getInstance(Minecraft.getInstance()).getHudTextNoFormat();
      MutableComponent fertility = CurrentFertility.getInstance(Minecraft.getInstance()).getMinimapText();
      MutableComponent fertilityShadowText = CurrentFertility.getInstance(Minecraft.getInstance()).getHudTextNoFormat();

      drawScaledText(poseStack, x, y, seasonCombined, seasonShadowText, textScaling, originOffsetWidth,
                     originOffsetHeight);

      if (Common.sereneSeasonsLoaded() && SeasonHudClient.getShowFertility()) {
        drawScaledText(poseStack, x, y + mc.font.lineHeight, fertility, fertilityShadowText, textScaling,
                       originOffsetWidth, originOffsetHeight);
      }
    }
  }
}
package club.iananderson.seasonhud.forge.impl.minimap.mods.journeymap;

import club.iananderson.seasonhud.impl.minimap.mods.journeymap.JourneymapSeasonPlugin;
import club.iananderson.seasonhud.impl.minimap.mods.journeymap.LabelPosition;
import club.iananderson.seasonhud.impl.season.CurrentFertility;
import club.iananderson.seasonhud.impl.season.CurrentSeason;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.Objects;
import journeymap.client.JourneymapClient;
import journeymap.client.cartography.color.RGB;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.render.draw.DrawUtil.HAlign;
import journeymap.client.render.draw.DrawUtil.VAlign;
import journeymap.client.ui.minimap.DisplayVars;
import journeymap.client.ui.theme.Theme.LabelSpec;
import journeymap.client.ui.theme.ThemeLabelSource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class DrawUtilSeason {
  public static void drawLabel(PoseStack graphics, Component text, double x, double y, HAlign horiAlign,
      VAlign vertAlign, Integer bgColor, float bgAlpha, Integer color, float alpha, double fontScale,
      boolean fontShadow, double rotation) {
    if (!text.getString().isEmpty()) {
      Minecraft mc = Minecraft.getInstance();

      boolean drawRect = bgColor != null && bgAlpha > 0.0F;
      double width = mc.font.width(text);

      int labelHeight;
      if (drawRect) {
        labelHeight = DrawUtil.getLabelHeight(mc.font, fontShadow);
      } else {
        Objects.requireNonNull(mc.font);
        labelHeight = 9;
      }

      int height = labelHeight;
      if (!drawRect && mc.font.isBidirectional()) {
        --height;
      }

      graphics.pushPose();

      try {
        if (fontScale != (double) 1.0F) {
          x /= fontScale;
          y /= fontScale;
          graphics.scale((float) fontScale, (float) fontScale, 1.0F);
        }

        float textX = (float) x;
        float textY = (float) y;
        double rectX = x;
        double rectY = y;
        switch (horiAlign) {
          case Left:
            textX = (float) (x - width);
            rectX = textX;
            break;
          case Right:
            textX = (float) x;
            rectX = ((float) x);
            break;

          default:
            textX = (float) (x - width / (double) 2.0F + (fontScale > (double) 1.0F
                                                          ? (double) 0.5F
                                                          : (double) 0.0F));
            rectX = ((float) (x - Math.max(1.0F, width) / (double) 2.0F + (fontScale > (double) 1.0F
                                                                           ? (double) 0.5F
                                                                           : (double) 0.0F)));
            break;
        }

        double vertPad;
        if (drawRect) {
          Objects.requireNonNull(mc.font);
          vertPad = (double) (height - 9) / (double) 2.0F;
        } else {
          vertPad = 0.0F;
        }

        switch (vertAlign) {
          case Above:
            rectY = y - (double) height;
            textY = (float) (rectY + vertPad + (double) (mc.font.isBidirectional()
                                                         ? 0
                                                         : 1));
            break;
          case Below:
            rectY = y;
            textY = (float) (y + vertPad);
            break;
          default:
            rectY = y - (double) (height / 2) + (fontScale > (double) 1.0F
                                                 ? (double) 0.5F
                                                 : (double) 0.0F);
            textY = (float) (rectY + vertPad);
            break;
        }

        if (rotation != (double) 0.0F) {
          graphics.translate(x, y, 0.0F);
          graphics.mulPose(Vector3f.ZP.rotationDegrees((float) (-rotation)));
          graphics.translate(-x, -y, 0.0F);
        }

        if (drawRect) {
          float horiPad = 2;
          DrawUtil.drawRectangle(graphics, rectX - (double) horiPad - (double) 0.5F, rectY, width + (2 * horiPad),
                                 height, bgColor, bgAlpha);
        }

        if (alpha < 1.0F) {
          color = RGB.toArbg(color, alpha);
        }

        graphics.translate((double) textX - Math.floor(textX), (double) textY - Math.floor(textY), 0.0F);
        GuiComponent.drawString(graphics, mc.font, text, (int) Math.floor(textX), (int) Math.floor(textY), color);
      } finally {
        graphics.popPose();
      }
    }
  }

  public static void drawSeasonInfoSlot(PoseStack graphics, DisplayVars dv) {
    MiniMapProperties mapProperties = JourneymapClient.getInstance().getActiveMiniMapProperties();
    LabelSpec labelSpec;
    LabelPosition labelPosition = JourneymapSeasonPlugin.getInstance().getClientProperties().position.get();
    Minecraft mc = Minecraft.getInstance();

    int labelsHeight;
    int startY;
    int labelOffset;

    if (labelPosition == LabelPosition.Top) {
      labelSpec = JourneymapSeasonPlugin.getInstance().minimapSpec().labelTop;
      labelsHeight = dv.getInfoLabelAreaHeight(mc.font, JourneymapSeasonPlugin.getInstance().minimapSpec().labelTop,
                                               ThemeLabelSource.values.get(mapProperties.info1Label.get()),
                                               ThemeLabelSource.values.get(mapProperties.info2Label.get()));
      startY = dv.textureY;
      labelOffset = JourneymapSeasonPlugin.getInstance().minimapSpec().labelTopInside
                    ? JourneymapSeasonPlugin.getInstance().minimapSpec().margin
                    : -JourneymapSeasonPlugin.getInstance().minimapSpec().margin - labelsHeight;
    } else {
      labelSpec = JourneymapSeasonPlugin.getInstance().minimapSpec().labelBottom;
      labelsHeight = dv.getInfoLabelAreaHeight(mc.font, JourneymapSeasonPlugin.getInstance().minimapSpec().labelBottom,
                                               ThemeLabelSource.values.get(mapProperties.info3Label.get()),
                                               ThemeLabelSource.values.get(mapProperties.info4Label.get()));
      startY = dv.textureY + mapProperties.getSize();
      labelOffset = JourneymapSeasonPlugin.getInstance().minimapSpec().labelBottomInside
                    ? -JourneymapSeasonPlugin.getInstance().minimapSpec().margin
                    : JourneymapSeasonPlugin.getInstance().minimapSpec().margin + labelsHeight;

    }

    int labelY = startY + labelOffset;
    int labelX = (int) Math.floor((dv.textureX + (double) (dv.minimapWidth / 2)));

    DrawUtil.sizeDisplay(mc.getWindow().getWidth(), mc.getWindow().getHeight());
    RenderSystem.enableBlend();

    MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getHudText();
    DrawUtilSeason.drawLabel(graphics, seasonCombined, labelX, labelY, labelPosition.getHoriAlign(),
                             labelPosition.getVertAlign(), labelSpec.background.getColor(),
                             mapProperties.infoSlotAlpha.get(), labelSpec.foreground.getColor(),
                             labelSpec.foreground.alpha, (double) mapProperties.fontScale.get(), labelSpec.shadow,
                             0.0F);

    if (CurrentFertility.getInstance(mc).shouldDrawNewLine()) {
      int singleLabelHeight = (int) ((double) (DrawUtil.getLabelHeight(mc.font, labelSpec.shadow) + labelSpec.margin)
          * mapProperties.fontScale.get());
      labelY += singleLabelHeight;

      MutableComponent fertility = CurrentFertility.getInstance(mc).getMinimapText();
      DrawUtilSeason.drawLabel(graphics, fertility, labelX, labelY, labelPosition.getHoriAlign(),
                               labelPosition.getVertAlign(), labelSpec.background.getColor(),
                               mapProperties.infoSlotAlpha.get(), labelSpec.foreground.getColor(),
                               labelSpec.foreground.alpha, (double) mapProperties.fontScale.get(), labelSpec.shadow,
                               0.0F);
    }

    RenderSystem.disableBlend();
    DrawUtil.sizeDisplay(mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
  }
}

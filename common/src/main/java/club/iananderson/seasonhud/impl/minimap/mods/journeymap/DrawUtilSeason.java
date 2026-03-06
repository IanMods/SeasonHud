package club.iananderson.seasonhud.impl.minimap.mods.journeymap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.Objects;
import journeymap.client.cartography.color.RGB;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.render.draw.DrawUtil.HAlign;
import journeymap.client.render.draw.DrawUtil.VAlign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;

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
          case Left -> {
            textX = (float) (x - width);
            rectX = textX;
          }

          case Right -> {
            textX = (float) x;
            rectX = ((float) x);
          }

          default -> {
            textX = (float) (x - width / (double) 2.0F + (fontScale > (double) 1.0F
                                                          ? (double) 0.5F
                                                          : (double) 0.0F));
            rectX = ((float) (x - Math.max(1.0F, width) / (double) 2.0F + (fontScale > (double) 1.0F
                                                                           ? (double) 0.5F
                                                                           : (double) 0.0F)));
          }
        }

        double vertPad;
        if (drawRect) {
          Objects.requireNonNull(mc.font);
          vertPad = (double) (height - 9) / (double) 2.0F;
        } else {
          vertPad = 0.0F;
        }

        switch (vertAlign) {
          case Above -> {
            rectY = y - (double) height;
            textY = (float) (rectY + vertPad + (double) (mc.font.isBidirectional()
                                                         ? 0
                                                         : 1));
          }

          case Below -> {
            rectY = y;
            textY = (float) (y + vertPad);
          }

          default -> {
            rectY = y - (double) (height / 2) + (fontScale > (double) 1.0F
                                                 ? (double) 0.5F
                                                 : (double) 0.0F);
            textY = (float) (rectY + vertPad);
          }
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
}

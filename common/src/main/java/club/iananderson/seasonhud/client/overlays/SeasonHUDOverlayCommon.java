package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Calendar;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;

public class SeasonHUDOverlayCommon {
  private SeasonHUDOverlayCommon() {
  }

  public static void render(PoseStack graphics) {
    Minecraft mc = Minecraft.getInstance();
    MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getSeasonHudText();
    int screenWidth = mc.getWindow().getGuiScaledWidth();
    int screenHeight = mc.getWindow().getGuiScaledHeight();
//    int iconWidth = 9;
//    int iconSpace = 2;
    int x = 0;
    int y = 0;
    int xOffset = Config.getHudX();
    int yOffset = Config.getHudY();
    double scale = Config.getHudScale();
    int DEFAULT_X_OFFSET = Config.DEFAULT_X_OFFSET;
    int DEFAULT_Y_OFFSET = Config.DEFAULT_Y_OFFSET;
    int stringWidth = (int) (mc.font.width(seasonCombined) * scale);
    int stringHeight = (int) (mc.font.lineHeight * scale);
//    if (Services.PLATFORM.isModLoaded("modernui")) {
//      x += iconWidth + iconSpace;
//      y += 1;
//      stringWidth += iconWidth + iconSpace;
//    }
    if (Common.drawDefaultHud() && Common.vanillaShouldDrawHud() && Calendar.validNeedCalendar()
        && !Common.hideHudInCurrentDimension()) {
      switch (Config.getHudLocation()) {
        case TOP_LEFT:
          x += DEFAULT_X_OFFSET;
          y += DEFAULT_Y_OFFSET;
          break;

        case TOP_CENTER:
          x += (int) ((((double) screenWidth / 2) - ((double) stringWidth / 2)) / scale);
          y += DEFAULT_Y_OFFSET;
          break;

        case TOP_RIGHT:
          x += (int) ((screenWidth - stringWidth - DEFAULT_X_OFFSET) / scale);
          y += DEFAULT_Y_OFFSET;
          break;

        case BOTTOM_LEFT:
          x += DEFAULT_X_OFFSET;
          y += (int) (((screenHeight - stringHeight - DEFAULT_Y_OFFSET)) / scale);
          break;

        case BOTTOM_RIGHT:
          x += (int) (((screenWidth - stringWidth - DEFAULT_X_OFFSET)) / scale);
          y += (int) (((screenHeight - stringHeight - DEFAULT_Y_OFFSET)) / scale);
          break;

        case CUSTOM:
          x = (int) xOffset;
          y = (int) yOffset;
          break;
      }

      //Text
      graphics.pushPose();
      graphics.scale((float) scale, (float) scale, 1F);
      mc.font.drawShadow(graphics, seasonCombined, x, y, 0xffffff);
//      //Icon
//      if (Services.PLATFORM.isModLoaded("modernui")) {
//        CurrentSeason.getInstance(mc).drawIcon(mc, graphics, 1F, x, y);
//      }
      graphics.popPose();
    }
  }
}

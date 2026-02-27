package club.iananderson.seasonhud.client.overlays;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.DefaultValues.Client;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import club.iananderson.seasonhud.impl.season.CurrentFertility;
import club.iananderson.seasonhud.impl.season.CurrentSeason;
import club.iananderson.seasonhud.impl.season.mods.CommonSeasonHelper;
import club.iananderson.seasonhud.platform.Services;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import org.jspecify.annotations.NonNull;

public class SeasonHudOverlayCommon {
  private SeasonHudOverlayCommon() {
  }

  public static void render(@NonNull PoseStack graphics) {
    Minecraft mc = Minecraft.getInstance();

    if (Common.drawDefaultHud(mc) && Common.vanillaShouldDrawHud(mc) && Calendar.validNeedCalendar(mc.player)
        && !Common.hideHudInCurrentDimension(mc)) {
      int screenWidth = mc.getWindow().getGuiScaledWidth();
      int screenHeight = mc.getWindow().getGuiScaledHeight();
      int offsetX = SeasonHudClient.getHudX();
      int offsetY = SeasonHudClient.getHudY();
      double scale = SeasonHudClient.getHudScale();
      int defaultOffsetX = Client.DEFAULT_X_OFFSET;
      int defaultOffsetY = Client.DEFAULT_Y_OFFSET;
      MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getHudText();
      int stringWidth = (int) (mc.font.width(seasonCombined) * scale);
      int stringHeight = (int) (mc.font.lineHeight * scale);
      int x;
      int y;

      switch (SeasonHudClient.getHudLocation()) {
        case TOP_LEFT:
          x = defaultOffsetX;
          y = defaultOffsetY;
          break;

        case TOP_CENTER:
          x = (int) ((((double) screenWidth / 2) - ((double) stringWidth / 2)) / scale);
          y = defaultOffsetY;
          break;

        case TOP_RIGHT:
          x = (int) ((screenWidth - stringWidth - defaultOffsetX) / scale);
          y = defaultOffsetY;
          break;

        case BOTTOM_LEFT:
          x = defaultOffsetX;
          y = (int) (((screenHeight - stringHeight - defaultOffsetY)) / scale);
          break;

        case BOTTOM_RIGHT:
          x = (int) (((screenWidth - stringWidth - defaultOffsetX)) / scale);
          y = (int) (((screenHeight - stringHeight - defaultOffsetY)) / scale);
          break;

        case CUSTOM:
          x = offsetX;
          y = offsetY;
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + SeasonHudClient.getHudLocation());
      }

      // Text
      graphics.pushPose();
      graphics.scale((float) scale, (float) scale, 1F);
      mc.font.drawShadow(graphics, seasonCombined, x, y, 0xffffff);
      if (CurrentFertility.getInstance(mc).shouldDrawNewLine()) {
        MutableComponent fertility = CurrentFertility.getInstance(mc).getHudText();

        y += stringHeight;
        mc.font.drawShadow(graphics, fertility, x, y, 0xffffff);
      }

      if (Services.PLATFORM.isDevelopmentEnvironment()) {
        CommonSeasonHelper.commonSeasons.getHelper().debugHud(graphics);
      }

      graphics.popPose();
    }
  }
}

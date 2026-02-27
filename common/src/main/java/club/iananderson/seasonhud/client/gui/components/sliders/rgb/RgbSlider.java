package club.iananderson.seasonhud.client.gui.components.sliders.rgb;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.client.gui.components.sliders.BasicSlider;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.util.Rgb;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

public class RgbSlider extends BasicSlider {
  public static final int SLIDER_PADDING = 2;
  protected final Seasons season;
  private final boolean enableColor = SeasonHudClient.getEnableSeasonNameColor();
  protected ColorEditBox seasonBox;
  protected int red;
  protected int green;
  protected int blue;
  protected int rgb;

  public RgbSlider(int x, int y, int initial, ColorEditBox seasonBox, ChatFormatting textColor) {
    super(x, y, seasonBox.getWidth() + 2, seasonBox.getHeight() - 6, true, initial, 0, 255,
          seasonBox.getSeason().getDefaultColor(), textColor);
    this.seasonBox = seasonBox;
    this.season = seasonBox.getSeason();
    this.rgb = Integer.parseInt(seasonBox.getValue());
    this.red = Rgb.red(rgb);
    this.green = Rgb.green(rgb);
    this.blue = Rgb.blue(rgb);
    this.updateMessage();
  }

  @Override
  public void onClick(double x, double y) {
    if (enableColor) {
      super.onClick(x, y);
    }
  }

  @Override
  protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
    if (enableColor) {
      super.onDrag(mouseX, mouseY, dragX, dragY);
    }
  }

  @Override
  public void renderBg(@NonNull PoseStack graphics, Minecraft mc, int mouseX, int mouseY) {
    if (!enableColor) {
      this.active = false;
      this.isHovered = false;
    }

    super.renderBg(graphics, mc, mouseX, mouseY);
  }

  public void setValue(int newValue) {
    double oldValue = this.value;
    this.value = this.snapToNearest((newValue - this.minValue) / (this.maxValue - this.minValue));
    if (!Mth.equal(oldValue, this.value)) {
      this.applyValue();
    }

    this.updateMessage();
  }

  @Override
  public void renderWidget(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    if (!enableColor) {
      this.active = false;
      this.isHovered = false;
    }

    super.renderWidget(graphics, mouseX, mouseY, partialTick);
  }

  @Override
  protected void updateMessage() {
    Component colorString = Common.literalText(this.getValueString());

    this.setMessage(colorString.copy().withStyle(this.textColor));

    if (!enableColor) {
      this.setMessage(colorString.copy().withStyle(ChatFormatting.GRAY));
    }
  }
}
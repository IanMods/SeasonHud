package club.iananderson.seasonhud.client.gui.components.sliders.rgb;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.client.gui.components.sliders.BasicSlider;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.Seasons;
import club.iananderson.seasonhud.util.Rgb;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class RgbSlider extends BasicSlider {
  public static final int SLIDER_PADDING = 2;
  protected final Seasons season;
  private final boolean enableColor = Config.getEnableSeasonNameColor();
  protected ColorEditBox seasonBox;
  protected int r;
  protected int g;
  protected int b;
  protected int rgb;

  public RgbSlider(int x, int y, int initial, ColorEditBox seasonBox, ChatFormatting textColor) {
    super(x, y, seasonBox.getWidth() + 2, seasonBox.getHeight() - 6, true, initial, 0, 255,
          seasonBox.getSeason().getDefaultColor(), textColor);
    this.seasonBox = seasonBox;
    this.season = seasonBox.getSeason();
    this.rgb = Integer.parseInt(seasonBox.getValue());
    this.r = Rgb.rColor(rgb);
    this.g = Rgb.gColor(rgb);
    this.b = Rgb.bColor(rgb);
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
  public void renderBg(@NotNull PoseStack graphics, Minecraft mc, int mouseX, int mouseY) {
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
  protected void updateMessage() {
    Component colorString = new TextComponent(this.getValueString());

    this.setMessage(colorString.copy().withStyle(this.textColor));

    if (!enableColor) {
      this.setMessage(colorString.copy().withStyle(ChatFormatting.GRAY));
    }
    else {
      this.setMessage(new TextComponent(""));
    }
  }
}
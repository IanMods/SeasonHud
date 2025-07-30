package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.util.DrawUtil;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import java.text.DecimalFormat;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BasicSlider extends AbstractSliderButton {
  public static final int SLIDER_PADDING = 2;
  protected static final ResourceLocation SLIDER_LOCATION = new ResourceLocation("seasonhud:textures/gui/slider.png");
  protected boolean drawString;
  protected boolean canChangeValue;
  protected double minValue;
  protected double maxValue;
  protected double defaultValue;
  protected double stepSize;
  protected ChatFormatting textColor;
  private DecimalFormat format;

  private BasicSlider(int x, int y, int width, int height, boolean drawString, double initial) {
    super(x, y, width, height, Component.empty(), 0D);
    this.drawString = drawString;
    this.value = snapToNearest(initial);
  }

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double initial, double minValue,
      double maxValue, double defaultValue, double stepSize, int precision, ChatFormatting textColor) {
    this(x, y, width, height, drawString, initial);
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.defaultValue = defaultValue;
    this.value = this.snapToNearest((initial - minValue) / (maxValue - minValue));
    this.stepSize = Math.abs(stepSize);
    this.textColor = textColor;
    this.drawString = drawString;

    if (stepSize == 0D) {
      precision = Math.min(precision, 4);

      StringBuilder builder = new StringBuilder("0");

      if (precision > 0) {
        builder.append('.');
      }

      while (precision-- > 0) {
        builder.append('0');
      }

      this.format = new DecimalFormat(builder.toString());
    }
    else if (Mth.equal(this.stepSize, Math.floor(this.stepSize))) {
      this.format = new DecimalFormat("0");
    }
    else {
      this.format = new DecimalFormat(Double.toString(this.stepSize).replaceAll("\\d", "0"));
    }

    this.updateMessage();
  }

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double initial, double minValue,
      double maxValue, double defaultValue, ChatFormatting textColor) {
    this(x, y, width, height, drawString, initial, minValue, maxValue, defaultValue, 1D, 0, textColor);
  }

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double initial, double minValue,
      double maxValue, double defaultValue, double stepSize, int precision) {
    this(x, y, width, height, drawString, initial, minValue, maxValue, defaultValue, stepSize, precision,
         ChatFormatting.WHITE);
  }

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double initial, double minValue,
      double maxValue, double defaultValue) {
    this(x, y, width, height, drawString, initial, minValue, maxValue, defaultValue, 1D, 0, ChatFormatting.WHITE);
  }

  protected static void renderScrollingString(PoseStack graphics, Font font, Component component, int i, int j, int k,
      int l, int m, int n) {
    int o = font.width(component);
    int var10000 = k + m;
    Objects.requireNonNull(font);
    int p = (var10000 - 9) / 2 + 1;
    int q = l - j;
    int r;
    if (o > q) {
      r = o - q;
      double d = (double) Util.getMillis() / 1000.0;
      double e = Math.max((double) r * 0.5, 3.0);
      double f = Math.sin(1.5707963267948966 * Math.cos(6.283185307179586 * d / e)) / 2.0 + 0.5;
      double g = Mth.lerp(f, 0.0, (double) r);
      GuiComponent.enableScissor(j, k, l, m);
      GuiComponent.drawString(graphics, font, component, j - (int) g, p, n);
      GuiComponent.disableScissor();
    }
    else {
      r = Mth.clamp(i, j + o / 2, l - o / 2);
      GuiComponent.drawCenteredString(graphics, font, component, r, p, n);
    }
  }

  protected static void renderScrollingString(PoseStack graphics, Font font, Component component, int i, int j, int k,
      int l, int m) {
    renderScrollingString(graphics, font, component, (i + k) / 2, i, j, k, l, m);
  }

  public void onRightClick() {
    this.setValue(defaultValue);
  }

  public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
    if (this.active && this.visible && mouseButton == InputConstants.MOUSE_BUTTON_RIGHT) {
      boolean rightClicked = this.clicked(mouseX, mouseY);
      if (rightClicked) {
        this.playDownSound(Minecraft.getInstance().getSoundManager());
        this.onRightClick();
      }
    }

    return super.mouseClicked(mouseX, mouseY, mouseButton);
  }

  public int getTextureY() {
    int i = this.isFocused() && !this.canChangeValue ? 1 : 0;
    return i * 20;
  }

  public int getHandleTextureY() {
    int i = !this.isHovered && !this.canChangeValue ? 2 : 3;
    return i * 20;
  }

  public int getFGColor() {
    return this.active ? 16777215 : 10526880;
  }

  protected double snapToNearest(double value) {
    if (stepSize <= 0D) {
      return Mth.clamp(value, 0D, 1D);
    }

    value = Mth.lerp(Mth.clamp(value, 0D, 1D), this.minValue, this.maxValue);

    value = (stepSize * Math.round(value / stepSize));

    if (this.minValue > this.maxValue) {
      value = Mth.clamp(value, this.maxValue, this.minValue);
    }
    else {
      value = Mth.clamp(value, this.minValue, this.maxValue);
    }

    return Mth.map(value, this.minValue, this.maxValue, 0D, 1D);
  }

  public double getValue() {
    return this.value * (this.maxValue - this.minValue) + this.minValue;
  }

  public void setValue(double value) {
    this.value = this.snapToNearest((value - this.minValue) / (this.maxValue - this.minValue));
    this.updateMessage();
  }

  public double getValueDouble() {
    return Math.round(this.getValue() * 10.0) / 10.0;
  }

  public long getValueLong() {
    return Math.round(this.getValue());
  }

  public int getValueInt() {
    return (int) this.getValueLong();
  }

  public String getValueString() {
    return this.format.format(this.getValue());
  }

  public void setSliderValue(double value) {
    double oldValue = this.value;
    this.value = this.snapToNearest(value);
    if (!Mth.equal(oldValue, this.value)) {
      this.applyValue();
    }

    this.updateMessage();
  }

  @Override
  protected void applyValue() {
  }

  private void setValueFromMouse(double mouseX) {
    this.setSliderValue((mouseX - (this.x + 4)) / (this.width - 8));
  }

  @Override
  public void onClick(double mouseX, double mouseY) {
    this.setValueFromMouse(mouseX);
  }

  @Override
  protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
    super.onDrag(mouseX, mouseY, dragX, dragY);
    this.setValueFromMouse(mouseX);
  }

  @Override
  public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    boolean bl = keyCode == InputConstants.KEY_LEFT;
    if (bl || keyCode == InputConstants.KEY_RIGHT) {
      if (this.minValue > this.maxValue) {
        bl = !bl;
      }
      float f = bl ? -1F : 1F;
      if (stepSize <= 0D) {
        this.setSliderValue(this.value + (f / (this.width - 8)));
      }
      else {
        this.setValue(this.getValue() + f * this.stepSize);
      }
    }

    return false;
  }

  @Override
  protected void updateMessage() {
    if (this.drawString) {
      this.setMessage(Component.literal(this.getValueString()));
    }
    else {
      this.setMessage(Component.empty());
    }
  }

  protected void renderScrollingString(PoseStack graphics, Font font, int i, int j) {
    int k = this.x + i;
    int l = this.x + this.getWidth() - i;
    renderScrollingString(graphics, font, this.getMessage(), k, this.y, l, this.y + this.getHeight(), j);
  }

  @Override
  protected void renderBg(@NotNull PoseStack graphics, @NotNull Minecraft mc, int mouseX, int mouseY) {
    DrawUtil.blitWithBorder(graphics, this, SLIDER_LOCATION, this.x, this.y, 0, this.getTextureY(), this.width,
                            this.height, 200, 20, 2, 3, 2, 2);
    DrawUtil.blitWithBorder(graphics, this, SLIDER_LOCATION, this.x + (int) (this.value * (double) (this.width - 8)),
                            this.y, 0, this.getHandleTextureY(), 8, this.height, 200, 20, 2, 3, 2, 2);
    this.renderScrollingString(graphics, mc.font, 2, this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
  }
}
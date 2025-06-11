package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.util.DrawUtil;
import com.mojang.blaze3d.platform.InputConstants;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class BasicSlider extends AbstractSliderButton {
  public static final int SLIDER_PADDING = 2;
  protected static final ResourceLocation SLIDER_LOCATION = new ResourceLocation("textures/gui/slider.png");
  protected boolean drawString;
  protected boolean canChangeValue;
  protected double minValue;
  protected double maxValue;
  protected double defaultValue;
  protected double stepSize;
  private DecimalFormat format;

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double initial) {
    super(x, y, width, height, Component.empty(), 0D);
    this.drawString = drawString;
    this.value = snapToNearest(initial);
  }

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double currentValue, double minValue,
      double maxValue, double defaultValue, double stepSize, int precision) {
    this(x, y, width, height, drawString, currentValue);
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.defaultValue = defaultValue;
    this.value = this.snapToNearest((currentValue - minValue) / (maxValue - minValue));
    this.stepSize = Math.abs(stepSize);
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

  protected BasicSlider(int x, int y, int width, int height, boolean drawString, double currentValue, double minValue,
      double maxValue, double defaultValue) {
    this(x, y, width, height, drawString, currentValue, minValue, maxValue, defaultValue, 1D, 0);
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
    return this.value * (maxValue - minValue) + minValue;
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

  public void setValue(double value) {
    this.value = this.snapToNearest((value - this.minValue) / (this.maxValue - this.minValue));
    this.updateMessage();
  }

  private void setSliderValue(double value) {
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

  @Override
  public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    Minecraft mc = Minecraft.getInstance();
    DrawUtil.blitWithBorder(graphics, SLIDER_LOCATION, this.getX(), this.getY(), 0, this.getTextureY(), this.width,
                            this.height, 200, 20, 2, 3, 2, 2);
    DrawUtil.blitWithBorder(graphics, SLIDER_LOCATION, this.getX() + (int) (this.value * (this.width - 8)), this.getY(),
                            0, this.getHandleTextureY(), 8, this.height, 200, 20, 2, 3, 2, 2);
    this.renderScrollingString(graphics, mc.font, 2, this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
  }
}
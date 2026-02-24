package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.Common;
import com.mojang.blaze3d.platform.InputConstants;
import java.text.DecimalFormat;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

public class BasicSlider extends AbstractSliderButton {
  public static final int SLIDER_PADDING = 2;
  protected static final Identifier SLIDER_LOCATION = Common.location("textures/gui/slider.png");
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
    } else if (Mth.equal(this.stepSize, Math.floor(this.stepSize))) {
      this.format = new DecimalFormat("0");
    } else {
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

  protected double snapToNearest(double value) {
    if (stepSize <= 0D) {
      return Mth.clamp(value, 0D, 1D);
    }

    value = Mth.lerp(Mth.clamp(value, 0D, 1D), this.minValue, this.maxValue);

    value = (stepSize * Math.round(value / stepSize));

    if (this.minValue > this.maxValue) {
      value = Mth.clamp(value, this.maxValue, this.minValue);
    } else {
      value = Mth.clamp(value, this.minValue, this.maxValue);
    }

    return Mth.map(value, this.minValue, this.maxValue, 0D, 1D);
  }

  public double getValue() {
    return this.value * (this.maxValue - this.minValue) + this.minValue;
  }

  public void setValue(double value) {
    double oldValue = this.value;
    this.value = this.snapToNearest((value - this.minValue) / (this.maxValue - this.minValue));
    if (!Mth.equal(oldValue, this.value)) {
      this.applyValue();
    }

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

  private void setValueFromMouse(MouseButtonEvent event) {
    this.setSliderValue((event.x() - (double) (this.getX() + 4)) / (double) (this.width - 8));
  }

  @Override
  protected void onDrag(MouseButtonEvent event, double dragX, double dragY) {
    super.onDrag(event, dragX, dragY);
    this.setValueFromMouse(event);
  }

  public void onRightClick() {
    this.setValue(defaultValue);
  }

  @Override
  public void onClick(MouseButtonEvent event, boolean isDoubleClick) {
    boolean rightClick = event.button() == InputConstants.MOUSE_BUTTON_RIGHT;

    if (!rightClick) {
      this.setValueFromMouse(event);
    }

    if (this.active && this.visible && rightClick) {
      this.playDownSound(Minecraft.getInstance().getSoundManager());
      this.onRightClick();
    }
  }

  @Override
  protected boolean isValidClickButton(MouseButtonInfo buttonInfo) {
    return buttonInfo.button() == 0 || buttonInfo.button() == 1;
  }

  @Override
  public boolean keyPressed(KeyEvent event) {
    boolean flag = event.key() == GLFW.GLFW_KEY_LEFT;
    if (flag || event.key() == GLFW.GLFW_KEY_RIGHT) {
      if (this.minValue > this.maxValue) {
        flag = !flag;
      }
      float f = flag
                ? -1F
                : 1F;
      if (stepSize <= 0D) {
        this.setSliderValue(this.value + (f / (this.width - 8)));
      } else {
        this.setValue(this.getValue() + f * this.stepSize);
      }
    }

    return false;
  }

  @Override
  protected void updateMessage() {
    if (this.drawString) {
      this.setMessage(Common.literalText(this.getValueString()));
    } else {
      this.setMessage(Component.empty());
    }
  }
}
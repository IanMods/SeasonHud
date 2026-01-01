package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.Common;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance.TooltipSupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

public class HudScaleSlider extends BasicSlider {
  protected final Component prefix;
  private final TooltipSupplier<List<FormattedCharSequence>> tooltipSupplier;

  protected HudScaleSlider(int x, int y, int width, int height, Component prefix, double initial, double minValue,
      double maxValue, double defaultValue, double stepSize, int precision,
      TooltipSupplier<List<FormattedCharSequence>> tooltipSupplier) {
    super(x, y, width, height, true, initial, minValue, maxValue, defaultValue, stepSize, precision);
    this.prefix = prefix;
    this.tooltipSupplier = tooltipSupplier;
    this.updateMessage();
  }

  public static HudScaleSlider.Builder builder(Component prefix) {
    return new HudScaleSlider.Builder(prefix);
  }

  @Override
  protected void updateMessage() {
    if (this.drawString) {
      this.setMessage(Common.literalText("").append(this.prefix).append(this.getValueString()));
    } else {
      this.setMessage(Component.empty());
    }
  }

  @Override
  public void renderBg(@NotNull PoseStack graphics, @NotNull Minecraft mc, int mouseX, int mouseY) {
    super.renderBg(graphics, mc, mouseX, mouseY);
  }

  public static class Builder {
    protected final Component prefix;
    protected int posX;
    protected int posY;
    protected int width = 180;
    protected int height = 20;
    protected double minValue;
    protected double maxValue;
    protected double initial;
    protected double defaultValue;
    protected TooltipSupplier<List<FormattedCharSequence>> tooltipSupplier;
    protected double stepSize;
    protected int precision;

    public Builder(Component prefix) {
      this.prefix = prefix;
    }

    /**
     * Uses default width = 180 and height = 20.
     *
     * @param x The horizontal position of the slider
     * @param y The vertical position of the slider
     */
    public HudScaleSlider.Builder withPos(int x, int y) {
      this.posX = x;
      this.posY = y;
      return this;
    }

    /**
     * Uses default height = 20.
     *
     * @param width The width of the slider
     */
    public HudScaleSlider.Builder withWidth(int width) {
      this.width = width;
      return this;
    }

    public HudScaleSlider.Builder withBounds(int x, int y, int width, int height) {
      this.posX = x;
      this.posY = y;
      this.width = width;
      this.height = height;
      return this;
    }

    public HudScaleSlider.Builder withValueRange(double minValue, double maxValue) {
      this.minValue = minValue;
      this.maxValue = maxValue;
      return this;
    }

    public HudScaleSlider.Builder withInitialValue(double initial) {
      this.initial = initial;
      return this;
    }

    /**
     * Sets the default value to return to when right-clicked.
     *
     * @param defaultValue The value that the slider will return to if right-clicked.
     */
    public HudScaleSlider.Builder withDefaultValue(double defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    public HudScaleSlider.Builder withStepSize(double stepSize) {
      this.stepSize = stepSize;
      return this;
    }

    public HudScaleSlider.Builder withPrecision(int precision) {
      this.precision = precision;
      return this;
    }

    public HudScaleSlider.Builder withTooltip(TooltipSupplier<List<FormattedCharSequence>> tooltipSupplier) {
      this.tooltipSupplier = tooltipSupplier;
      return this;
    }

    public HudScaleSlider build() {
      HudScaleSlider slider =
          new HudScaleSlider(this.posX, this.posY, this.width, this.height, this.prefix, this.initial, this.minValue,
              this.maxValue, this.defaultValue, this.stepSize, this.precision, this.tooltipSupplier);
      return slider;
    }
  }
}

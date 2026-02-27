package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.Common;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NonNull;

public class HudScaleSlider extends BasicSlider {
  protected final Component prefix;
  private final Supplier<BasicSlider> tooltipSupplier;

  protected HudScaleSlider(int x, int y, int width, int height, Component prefix, double initial, double minValue,
      double maxValue, double defaultValue, double stepSize, int precision, Supplier<BasicSlider> tooltipSupplier) {
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
      this.setMessage(new TextComponent(""));
    }
  }

  @Override
  public void renderBg(@NonNull PoseStack graphics, @NonNull Minecraft mc, int mouseX, int mouseY) {
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
    protected Supplier<BasicSlider> tooltipSupplier;
    protected double stepSize;
    protected int precision;

    public Builder(Component prefix) {
      this.prefix = prefix;
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

    public HudScaleSlider.Builder withTooltip(Supplier<BasicSlider> tooltipSupplier) {
      this.tooltipSupplier = tooltipSupplier;
      return this;
    }

    public HudScaleSlider build() {
      HudScaleSlider slider = new HudScaleSlider(this.posX, this.posY, this.width, this.height, this.prefix,
                                                 this.initial, this.minValue, this.maxValue, this.defaultValue,
                                                 this.stepSize, this.precision, this.tooltipSupplier);
      return slider;
    }
  }
}

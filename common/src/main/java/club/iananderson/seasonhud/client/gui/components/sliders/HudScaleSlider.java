package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.config.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HudScaleSlider extends BasicSlider {
  protected final Component prefix;
  private final boolean showDecimal;

  protected HudScaleSlider(int x, int y, int width, int height, Component prefix, double currentValue, double minValue,
      double maxValue, double defaultValue, boolean showDecimal) {
    super(x, y, width, height, true, currentValue, minValue, maxValue, defaultValue);
    this.prefix = prefix;
    this.showDecimal = showDecimal;
    this.value = snapToNearest(currentValue);
    this.updateMessage();
  }

  public static HudScaleSlider.Builder builder(Component prefix) {
    return new HudScaleSlider.Builder(prefix);
  }

  @Override
  public String getValueString() {
    if (showDecimal) {
      return String.valueOf(this.getValueDouble());
    }
    else {
      return String.valueOf(this.getValueInt());
    }
  }

  @Override
  protected void updateMessage() {
    if (this.drawString) {
      this.setMessage(Component.literal("").append(this.prefix).append(this.getValueString()));
    }
    else {
      this.setMessage(Component.empty());
    }
  }

  @Override
  public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    if (Config.getEnableMinimapIntegration()) {
      this.active = false;
    }

    super.renderWidget(graphics, mouseX, mouseY, partialTick);
  }

  public static class Builder {
    protected final Component prefix;
    protected int x;
    protected int y;
    protected int width = 180;
    protected int height = 20;
    protected double minValue;
    protected double maxValue;
    protected double initial;
    protected double defaultValue;
    protected Tooltip tooltip;
    protected boolean showDecimal;

    public Builder(Component prefix) {
      this.prefix = prefix;
    }

    /**
     * Uses default width = 180 and height = 20
     *
     * @param x The horizontal position of the slider
     * @param y The vertical position of the slider
     */
    public HudScaleSlider.Builder withPos(int x, int y) {
      this.x = x;
      this.y = y;
      return this;
    }

    /**
     * Uses default height = 20
     *
     * @param width The width of the slider
     */
    public HudScaleSlider.Builder withWidth(int width) {
      this.width = width;
      return this;
    }

    public HudScaleSlider.Builder withBounds(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
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
     * @param defaultValue The value that the slider will return to if right-clicked.
     */
    public HudScaleSlider.Builder withDefaultValue(double defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    public HudScaleSlider.Builder withShowDecimal(boolean showDecimal) {
      this.showDecimal = showDecimal;
      return this;
    }

    public HudScaleSlider.Builder withTooltip(@Nullable Tooltip tooltip) {
      this.tooltip = tooltip;

      return this;
    }

    public HudScaleSlider build() {
      HudScaleSlider slider = new HudScaleSlider(this.x, this.y, this.width, this.height, this.prefix, this.initial,
                                                 this.minValue, this.maxValue, this.defaultValue, this.showDecimal);
      slider.setTooltip(this.tooltip);
      return slider;
    }
  }
}

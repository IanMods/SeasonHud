package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HudOffsetSlider extends BasicSlider {
  protected Component prefix;
  protected int initial;

  protected HudOffsetSlider(int x, int y, int width, int height, Component prefix, int initial, int minValue,
      int maxValue, int defaultValue) {
    super(x, y, width, height, true, initial, minValue, maxValue, defaultValue);
    this.prefix = prefix;
    this.value = snapToNearest(initial);
    this.updateMessage();
  }

  public static Builder builder(Component prefix) {
    return new Builder(prefix);
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
    if (Config.getHudLocation() != Location.TOP_LEFT) {
      this.active = false;
      this.setTooltip(Tooltip.create(Component.translatable("menu.seasonhud.season.offsetError.tooltip")));
    }

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
    protected int minValue;
    protected int maxValue;
    protected int initial;
    protected int defaultValue;
    protected Tooltip tooltip;

    public Builder(Component prefix) {
      this.prefix = prefix;
    }

    /**
     * Uses default width = 180 and height = 20
     *
     * @param x The horizontal position of the slider
     * @param y The vertical position of the slider
     */
    public HudOffsetSlider.Builder withPos(int x, int y) {
      this.x = x;
      this.y = y;
      return this;
    }

    /**
     * Uses default height = 20
     *
     * @param width The width of the slider
     */
    public HudOffsetSlider.Builder withWidth(int width) {
      this.width = width;
      return this;
    }

    public HudOffsetSlider.Builder withBounds(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      return this;
    }

    public HudOffsetSlider.Builder withValueRange(int minValue, int maxValue) {
      this.minValue = minValue;
      this.maxValue = maxValue;
      return this;
    }

    public HudOffsetSlider.Builder withInitialValue(int initial) {
      this.initial = initial;
      return this;
    }

    /**
     * @param defaultValue The value that the slider will return to if right-clicked.
     */
    public HudOffsetSlider.Builder withDefaultValue(int defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    public HudOffsetSlider.Builder withValues(int minValue, int maxValue, int initial, int defaultValue){
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.defaultValue = defaultValue;
      this.initial = Mth.clamp(initial, this.minValue, this.maxValue);
      return this;
    }

    public HudOffsetSlider.Builder withTooltip(@Nullable Tooltip tooltip) {
      this.tooltip = tooltip;

      return this;
    }

    public HudOffsetSlider build() {
      HudOffsetSlider slider = new HudOffsetSlider(this.x, this.y, this.width, this.height, this.prefix, this.initial,
                                                   this.minValue, this.maxValue, this.defaultValue);
      slider.setTooltip(this.tooltip);
      return slider;
    }
  }
}


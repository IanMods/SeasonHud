package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.Common;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class HudOffsetSlider extends BasicSlider {
  protected Component prefix;

  protected HudOffsetSlider(int x, int y, int width, int height, Component prefix, int initial, int minValue,
      int maxValue, int defaultValue) {
    super(x, y, width, height, true, initial, minValue, maxValue, defaultValue);
    this.prefix = prefix;
    this.updateMessage();
  }

  public static Builder builder(Component prefix) {
    return new Builder(prefix);
  }

  protected boolean clicked(double d, double e) {
    return this.active && this.visible && d >= (double) this.getX() && e >= (double) this.getY() && d < (double) (
        this.getX() + this.width) && e < (double) (this.getY() + this.height);
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
  public void extractWidgetRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY,
      float partialTick) {
    super.extractWidgetRenderState(graphics, mouseX, mouseY, partialTick);
  }

  public static class Builder {
    protected final Component prefix;
    protected int posX;
    protected int posY;
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

    public HudOffsetSlider.Builder withBounds(int x, int y, int width, int height) {
      this.posX = x;
      this.posY = y;
      this.width = width;
      this.height = height;
      return this;
    }

    public HudOffsetSlider.Builder withValues(int minValue, int maxValue, int initial, int defaultValue) {
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
      HudOffsetSlider slider = new HudOffsetSlider(this.posX, this.posY, this.width, this.height, this.prefix,
                                                   this.initial, this.minValue, this.maxValue, this.defaultValue);
      slider.setTooltip(this.tooltip);
      return slider;
    }
  }
}


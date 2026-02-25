package club.iananderson.seasonhud.client.gui.components.sliders;

import club.iananderson.seasonhud.Common;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance.TooltipSupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class HudOffsetSlider extends BasicSlider {
  protected final Component prefix;
  private final TooltipSupplier<List<FormattedCharSequence>> tooltipSupplier;

  protected HudOffsetSlider(int x, int y, int width, int height, Component prefix, int initial, int minValue,
      int maxValue, int defaultValue, TooltipSupplier<List<FormattedCharSequence>> tooltipSupplier) {
    super(x, y, width, height, true, initial, minValue, maxValue, defaultValue);
    this.prefix = prefix;
    this.tooltipSupplier = tooltipSupplier;
    this.updateMessage();
  }

  public static Builder builder(Component prefix) {
    return new Builder(prefix);
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
  public void renderBg(@Nonnull PoseStack graphics, @Nonnull Minecraft mc, int mouseX, int mouseY) {
    super.renderBg(graphics, mc, mouseX, mouseY);
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
    protected TooltipSupplier<List<FormattedCharSequence>> tooltipSupplier;

    public Builder(Component prefix) {
      this.prefix = prefix;
    }

    /**
     * Uses default width = 180 and height = 20.
     *
     * @param x The horizontal position of the slider
     * @param y The vertical position of the slider
     */
    public HudOffsetSlider.Builder withPos(int x, int y) {
      this.posX = x;
      this.posY = y;
      return this;
    }

    /**
     * Uses default height = 20.
     *
     * @param width The width of the slider
     */
    public HudOffsetSlider.Builder withWidth(int width) {
      this.width = width;
      return this;
    }

    public HudOffsetSlider.Builder withBounds(int x, int y, int width, int height) {
      this.posX = x;
      this.posY = y;
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
     * Sets the default value to return to when right-clicked.
     *
     * @param defaultValue The value that the slider will return to if right-clicked.
     */
    public HudOffsetSlider.Builder withDefaultValue(int defaultValue) {
      this.defaultValue = defaultValue;
      return this;
    }

    public HudOffsetSlider.Builder withValues(int minValue, int maxValue, int initial, int defaultValue) {
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.defaultValue = defaultValue;
      this.initial = Mth.clamp(initial, this.minValue, this.maxValue);
      return this;
    }

    public HudOffsetSlider.Builder withTooltip(TooltipSupplier<List<FormattedCharSequence>> tooltipSupplier) {
      this.tooltipSupplier = tooltipSupplier;
      return this;
    }

    public HudOffsetSlider build() {
      HudOffsetSlider slider = new HudOffsetSlider(this.posX, this.posY, this.width, this.height, this.prefix,
                                                   this.initial, this.minValue, this.maxValue, this.defaultValue,
                                                   this.tooltipSupplier);
      return slider;
    }
  }
}


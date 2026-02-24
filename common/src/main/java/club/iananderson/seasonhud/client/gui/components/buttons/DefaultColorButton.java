package club.iananderson.seasonhud.client.gui.components.buttons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.util.Rgb;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

public class DefaultColorButton extends Button.Plain {
  private static final Component DEFAULT = Common.translatedText("menu.seasonhud.color.default.button");
  private final ColorEditBox colorEditBox;
  private final int defaultColor;

  private DefaultColorButton(int x, int y, ColorEditBox colorEditBox, OnPress onPress) {
    super(x, y, colorEditBox.getWidth() + 2, colorEditBox.getHeight() - 2, DefaultColorButton.DEFAULT, onPress,
          DEFAULT_NARRATION);
    this.colorEditBox = colorEditBox;
    this.defaultColor = colorEditBox.getSeason().getDefaultColor();
  }

  public static Builder builder(ColorEditBox colorEditBox, OnPress onPress) {
    return new Builder(colorEditBox, onPress);
  }

  private boolean inBounds(int color) {
    int minColor = 0;
    int maxColor = 16777215;

    return color >= minColor && color <= maxColor;
  }

  public boolean validate(String colorString) {
    try {
      int colorInt = Integer.parseInt(colorString);
      return this.inBounds(colorInt);
    } catch (NumberFormatException var) {
      return false;
    }
  }

  public int getTextureY() {
    int k = 1;
    if (!this.active) {
      k = 0;
    } else if (this.isHoveredOrFocused()) {
      k = 2;
    }

    return 46 + k * 20;
  }

  public int getFgColor() {
    return this.active
           ? 16777215
           : 10526880;
  }

  @Override
  protected void renderContents(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    refresh();
    String boxValue = this.colorEditBox.getValue();

    if (validate(boxValue) && Integer.parseInt(boxValue) == defaultColor) {
      this.active = false;
    }

    super.renderContents(graphics, mouseX, mouseY, partialTicks);
  }

  private void refresh() {
    Map<String, Integer> defaultColors = Rgb.defaultSeasonMap(this.colorEditBox.getSeason());
    Map<String, Integer> currentColors = this.colorEditBox.getSeason().getRgbMap();

    this.active = defaultColors != currentColors;
  }

  public static class Builder {
    protected final OnPress onPress;
    protected final ColorEditBox colorEditBox;
    protected int posX;
    protected int posY;
    protected Tooltip tooltip;

    public Builder(ColorEditBox colorEditBox, OnPress onPress) {
      this.colorEditBox = colorEditBox;
      this.onPress = onPress;
    }

    /**
     * Uses default width = 150 and height = 20.
     *
     * @param x The horizontal position of the button
     * @param y The vertical position of the button
     */
    public Builder withPos(int x, int y) {
      this.posX = x;
      this.posY = y;
      return this;
    }

    @SuppressWarnings({"unused"})
    public Builder withTooltip(@Nullable Tooltip tooltip) {
      this.tooltip = tooltip;
      return this;
    }

    public DefaultColorButton build() {
      DefaultColorButton button = new DefaultColorButton(this.posX, this.posY, this.colorEditBox, this.onPress);
      button.setTooltip(this.tooltip);
      return button;
    }
  }
}
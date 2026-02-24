package club.iananderson.seasonhud.client.gui.components.buttons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.screens.SeasonHudScreen;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class MenuButton extends Button.Plain {
  protected MenuButton(int x, int y, int width, int height, MenuButtons buttonType, OnPress onPress) {
    super(x, y, width, height, buttonType.getButtonText(), onPress, DEFAULT_NARRATION);
  }

  public static Builder builder(MenuButtons button, OnPress onPress) {
    return new Builder(button, onPress);
  }

  public static Builder builder(MenuButtons button, SeasonHudScreen currentScreen, SeasonHudScreen newScreen) {
    return new Builder(button, b -> {
      currentScreen.saveConfig();
      newScreen.open();
    });
  }

  @Override
  protected void renderContents(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    super.renderContents(graphics, mouseX, mouseY, partialTicks);
  }

  public enum MenuButtons {
    DONE(CommonComponents.GUI_DONE),

    CANCEL(CommonComponents.GUI_CANCEL),

    COLORS(Common.translatedText("menu.seasonhud.main.color.button").append("...")),

    SEASON(Common.translatedText("menu.seasonhud.main.season.button").append("..."));

    private final Component buttonText;

    MenuButtons(Component buttonText) {
      this.buttonText = buttonText;
    }

    public Component getButtonText() {
      return this.buttonText;
    }
  }

  public static class Builder {
    protected final MenuButtons buttonType;
    protected final OnPress onPress;
    protected int posX;
    protected int posY;
    protected int width = 150;
    protected int height = 20;
    protected Tooltip tooltip;

    public Builder(MenuButtons buttonType, OnPress onPress) {
      this.buttonType = buttonType;
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

    /**
     * Uses default height = 20.
     *
     * @param width The width of the button
     */
    public Builder withWidth(int width) {
      this.width = width;
      return this;
    }

    public Builder withBounds(int x, int y, int width, int height) {
      this.withPos(x, y);
      this.width = width;
      this.height = height;
      return this;
    }

    public Builder withTooltip(@Nullable Tooltip tooltip) {
      this.tooltip = tooltip;
      return this;
    }

    public MenuButton build() {
      MenuButton button = new MenuButton(this.posX, this.posY, this.width, this.height, this.buttonType, this.onPress);
      button.setTooltip(this.tooltip);
      return button;
    }
  }
}
package club.iananderson.seasonhud.client.gui.components.buttons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.screens.SeasonHudScreen;
import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class MenuButton extends Button {
  public static final int SMALL_WIDTH = 120;
  public static final int DEFAULT_WIDTH = 150;
  public static final int DEFAULT_HEIGHT = 20;

  protected MenuButton(int x, int y, int width, int height, MenuButtons buttonType, OnPress onPress) {
    super(x, y, width, height, buttonType.getButtonText(), onPress);
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

  public enum MenuButtons {
    DONE(CommonComponents.GUI_DONE),

    CANCEL(CommonComponents.GUI_CANCEL),

    COLORS(Common.translatedText("menu.seasonhud.main.color.button").append("...")),

    SEASON(Common.translatedText("menu.seasonhud.main.season.button").append("...")),

    JOURNEYMAP(Common.translatedText("menu.seasonhud.main.journeymap.options.button").append("..."));

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
    protected List<FormattedCharSequence> tooltip;

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

    public Builder withTooltip(List<FormattedCharSequence> tooltip) {
      this.tooltip = tooltip;
      return this;
    }

    public MenuButton build() {
      MenuButton button = new MenuButton(this.posX, this.posY, this.width, this.height, this.buttonType, this.onPress);
      return button;
    }
  }
}
package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.jspecify.annotations.NonNull;

public class SeasonHudScreen extends Screen {
  public static final int MENU_PADDING = 50;
  public static final int TITLE_PADDING = 10;
  public static final int BUTTON_PADDING = 6;
  public static MenuButton doneButton;
  public static MenuButton cancelButton;
  public final List<AbstractWidget> widgets = new ArrayList<>();
  public final Screen parentScreen;
  public int buttonWidth = 150;
  public int buttonHeight = 20;
  public int leftButtonX;
  public int rightButtonX;
  public int row;
  public int buttonStartY = MENU_PADDING;
  public int offsetY = buttonHeight + BUTTON_PADDING;
  protected boolean hasPendingChanges;
  protected List<ConfigValue<?>> configOptions = new ArrayList<>();

  public SeasonHudScreen(Screen parentScreen, Component title) {
    super(title);
    this.parentScreen = parentScreen;
  }

  public void open() {
    Minecraft.getInstance().setScreen(this);
  }

  @Override
  public boolean isPauseScreen() {
    return true;
  }

  @Override
  public boolean shouldCloseOnEsc() {
    return !this.hasPendingChanges;
  }

  private Stream<ConfigValue<?>> getAllSettings() {
    return this.configOptions.stream();
  }

  private void undoChanges() {
    this.getAllSettings().forEach(ConfigValue::clearCache);
  }

  public void saveConfig() {
  }

  @Override
  public void onClose() {
    Minecraft.getInstance().setScreen(this.parentScreen);
  }

  public void onDone() {
    Minecraft.getInstance().setScreen(this.parentScreen);
  }

  private void clearFocus() {
    ComponentPath componentPath = this.getCurrentFocusPath();
    if (componentPath != null) {
      componentPath.applyFocus(false);
    }
  }

  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  public void rebuildUI() {
    this.rebuildWidgets();
  }

  public void drawColumnHeading(@NonNull GuiGraphics graphics, Component text, Side side, int row) {
    int x;

    switch (side) {
      case LEFT -> x = leftButtonX + (buttonWidth / 2);
      case RIGHT -> x = rightButtonX + (buttonWidth / 2);
      default -> throw new IllegalStateException("Unexpected value: " + side);
    }

    int y = MENU_PADDING + (row * (buttonHeight + BUTTON_PADDING)) - (font.lineHeight + BUTTON_PADDING);

    graphics.pose().pushPose();
    graphics.drawCenteredString(font, text, x, y, 16777215);
    graphics.pose().popPose();
  }

  public void drawHeading(@NonNull GuiGraphics graphics, Component text, int row) {
    int x = this.width / 2;
    int y = MENU_PADDING + (row * (buttonHeight + BUTTON_PADDING)) - (font.lineHeight + BUTTON_PADDING);

    graphics.pose().pushPose();
    graphics.drawCenteredString(font, text, x, y, 16777215);
    graphics.pose().popPose();
  }

  @Override
  public void render(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(graphics);

    graphics.drawCenteredString(font, this.getTitle(), this.width / 2, TITLE_PADDING, 16777215);

    super.render(graphics, mouseX, mouseY, partialTicks);
  }

  @Override
  public void init() {
    super.init();
    this.widgets.clear();
    leftButtonX = (this.width / 2) - (buttonWidth + BUTTON_PADDING);
    rightButtonX = (this.width / 2) + BUTTON_PADDING;

    cancelButton = MenuButton.builder(MenuButtons.CANCEL, press -> this.onClose())
        .withPos((this.width / 2) - (MenuButton.DEFAULT_WIDTH + BUTTON_PADDING),
                 (this.height - MenuButton.DEFAULT_HEIGHT - BUTTON_PADDING))
        .build();

    doneButton = MenuButton.builder(MenuButtons.DONE, press -> this.onDone())
        .withPos(rightButtonX, (this.height - MenuButton.DEFAULT_HEIGHT - BUTTON_PADDING))
        .build();

    this.widgets.addAll(Arrays.asList(cancelButton, doneButton));
  }

  public enum Side {
    LEFT,
    RIGHT
  }
}

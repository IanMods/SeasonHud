package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
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

  protected void rebuildWidgets() {
    this.clearWidgets();
    this.clearFocus();
    this.init();
  }

  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  public void rebuildUI() {
    this.rebuildWidgets();
  }

  public void drawButtonRow() {

  }

  public void drawColumnHeading(@NonNull GuiGraphicsExtractor graphics, Component text, Side side, int row) {
    int x;

    switch (side) {
      case LEFT -> x = leftButtonX + (buttonWidth / 2);
      case RIGHT -> x = rightButtonX + (buttonWidth / 2);
      default -> throw new IllegalStateException("Unexpected value: " + side);
    }

    int y = MENU_PADDING + (row * (buttonHeight + BUTTON_PADDING)) - (font.lineHeight + BUTTON_PADDING);

    graphics.pose().pushMatrix();
    graphics.centeredText(font, text, x, y, 16777215);
    graphics.pose().popMatrix();
  }

  public void drawHeading(@NonNull GuiGraphicsExtractor graphics, Component text, int row) {
    int x = this.width / 2;
    int y = MENU_PADDING + (row * (buttonHeight + BUTTON_PADDING)) - (font.lineHeight + BUTTON_PADDING);

    graphics.pose().pushMatrix();
    graphics.centeredText(font, text, x, y, 16777215);
    graphics.pose().popMatrix();
  }

  @Override
  public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {

    int titleWidth = this.font.width(this.title);

    this.addRenderableWidget(
        new StringWidget(this.width / 2 - titleWidth / 2, TITLE_PADDING, titleWidth, 9, this.title, this.font));
    graphics.centeredText(font, this.getTitle(), this.width / 2, TITLE_PADDING, 16777215);

    super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
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

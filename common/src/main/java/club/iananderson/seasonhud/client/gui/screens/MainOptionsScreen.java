package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.platform.Services;
import java.util.Arrays;
import journeymap.client.ui.UIManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class MainOptionsScreen extends SeasonHudScreen {
  private static final Component SCREEN_TITLE = Common.translatedText("menu.seasonhud.main.title");
  private static final Component MINIMAP_SETTINGS = Common.translatedText("menu.seasonhud.main.minimap.options");
  private static final Component JOURNEYMAP = Common.translatedText("menu.seasonhud.main.journeymap.title");
  MenuButton seasonButton;
  MenuButton colorButton;
  CycleButton<Boolean> enableMinimapIntegrationButton;
  CycleButton<Boolean> showMinimapHiddenButton;
  Button journeyMapButton;
  private boolean enableMod;
  private boolean showMinimapHidden;
  private boolean enableMinimapIntegration;
  private int minimapRow;
  private int journeyMapRow;

  public MainOptionsScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
    loadConfig();
    this.buttonWidth = 170;
  }

  public static MainOptionsScreen getInstance(Screen parentScreen) {
    return new MainOptionsScreen(parentScreen);
  }

  public void loadConfig() {
    enableMod = SeasonHudClient.getEnableMod();
    showMinimapHidden = SeasonHudClient.getShowDefaultWhenMinimapHidden();
    enableMinimapIntegration = SeasonHudClient.getEnableMinimapIntegration();
  }

  public void saveConfig() {
    SeasonHudClient.setEnableMod(enableMod);
    SeasonHudClient.setEnableMinimapIntegration(enableMinimapIntegration);
    SeasonHudClient.setShowDefaultWhenMinimapHidden(showMinimapHidden);
    SeasonHudClient.CLIENT_SPEC.save();
  }

  @Override
  public void onDone() {
    saveConfig();
    super.onDone();
  }

  @Override
  public void onClose() {
    super.onClose();
  }

  @Override
  public void render(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    super.render(graphics, mouseX, mouseY, partialTicks);

    drawHeading(graphics, MINIMAP_SETTINGS, minimapRow);

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      drawHeading(graphics, JOURNEYMAP, journeyMapRow);

      journeyMapButton.active = enableMod;
    }

    seasonButton.active = enableMod;
    colorButton.active = enableMod;
    enableMinimapIntegrationButton.active = enableMod;
    showMinimapHiddenButton.active = enableMod;
  }

  public void seasonHudOptionsButtons() {
    row += 1;
    seasonButton = MenuButton.builder(MenuButtons.SEASON, this, DisplayOptionsScreen.getInstance(this))
        .withTooltip(Common.newTooltip("menu.seasonhud.main.season.tooltip"))
        .withPos(leftButtonX, (buttonStartY + (row * offsetY))).withWidth(buttonWidth)
        .build();

    colorButton = MenuButton.builder(MenuButtons.COLORS, this, ColorsScreen.getInstance(this))
        .withTooltip(Common.newTooltip("menu.seasonhud.main.color.tooltip"))
        .withPos(rightButtonX, (buttonStartY + (row * offsetY))).withWidth(buttonWidth)
        .build();
    widgets.addAll(Arrays.asList(seasonButton, colorButton));
  }

  public void minimapOptionsButtons() {
    row += 2;
    minimapRow = row;

    enableMinimapIntegrationButton = CycleButton.onOffBuilder(enableMinimapIntegration)
        .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.minimapIntegration.tooltip"))
        .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.seasonhud.main.enableMinimapIntegration.button"),
                (b, val) -> enableMinimapIntegration = val);

    showMinimapHiddenButton = CycleButton.onOffBuilder(showMinimapHidden)
        .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.showMinimapHidden.tooltip"))
        .create(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.seasonhud.main.showMinimapHidden.button"),
                (b, val) -> showMinimapHidden = val);

    widgets.addAll(Arrays.asList(enableMinimapIntegrationButton, showMinimapHiddenButton));
  }

  public void journeymapOptions() {
    if (Services.PLATFORM.isModLoaded("journeymap")) {
      row += 2;
      journeyMapRow = row;

      journeyMapButton = MenuButton.builder(MenuButtons.JOURNEYMAP,
                                            (button) -> UIManager.INSTANCE.openAddonOptionsEditor(this, true))
          .withTooltip(Common.newTooltip("menu.seasonhud.main.journeymap.options.tooltip"))
          .withPos(leftButtonX, (buttonStartY + (row * offsetY))).withWidth(buttonWidth)
          .build();

      widgets.add(journeyMapButton);
    }
  }

  @Override
  public void init() {
    super.init();

    int enableModWidth = font.width(Common.translatedText("menu.seasonhud.main.enableMod.button").append(": OFF")) + 8;

    CycleButton<Boolean> enableModButton = CycleButton.onOffBuilder(enableMod)
        .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.enableMod.tooltip"))
        .create(this.width - enableModWidth - TITLE_PADDING / 2, TITLE_PADDING / 2, enableModWidth, buttonHeight,
                Common.translatedText("menu.seasonhud.main.enableMod.button"), (b, val) -> enableMod = val);
    widgets.add(enableModButton);

    row = -1;
    seasonHudOptionsButtons();
    minimapOptionsButtons();
    journeymapOptions();

    widgets.forEach(this::addRenderableWidget);
  }
}
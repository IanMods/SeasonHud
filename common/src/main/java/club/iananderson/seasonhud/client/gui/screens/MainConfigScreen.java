package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.platform.Services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class MainConfigScreen extends SeasonHudScreen {
  private static final Component SCREEN_TITLE = Common.translatedText("menu.seasonhud.main.title");
  private static final Component MINIMAP_SETTINGS = Common.translatedText("menu.seasonhud.main.minimap.options");
  private static final Component JOURNEYMAP = Common.translatedText("menu.seasonhud.main.journeymap.title");
  private final List<AbstractWidget> optionButtons = new ArrayList<>();
  MenuButton seasonButton;
  MenuButton colorButton;
  CycleButton<Boolean> enableMinimapIntegrationButton;
  CycleButton<Boolean> showMinimapHiddenButton;
  CycleButton<Boolean> journeyMapAboveMapButton;
  CycleButton<Boolean> journeyMapMacOSButton;
  private boolean enableMod;
  private boolean showMinimapHidden;
  private boolean enableMinimapIntegration;
  private boolean journeyMapAboveMap;
  private boolean journeyMapMacOS;

  public MainConfigScreen() {
    super(null, SCREEN_TITLE);
    loadConfig();
    this.BUTTON_WIDTH = 170;
  }

  public static MainConfigScreen getInstance() {
    return new MainConfigScreen();
  }

  public void loadConfig() {
    enableMod = SeasonHudClient.getEnableMod();
    showMinimapHidden = SeasonHudClient.getShowDefaultWhenMinimapHidden();
    enableMinimapIntegration = SeasonHudClient.getEnableMinimapIntegration();
    if (CurrentMinimap.journeyMapLoaded()) {
      journeyMapAboveMap = SeasonHudClient.getJourneyMapAboveMap();
      journeyMapMacOS = SeasonHudClient.getJourneyMapMacOS();
    }
  }

  public void saveConfig() {
    SeasonHudClient.setEnableMod(enableMod);
    SeasonHudClient.setEnableMinimapIntegration(enableMinimapIntegration);
    SeasonHudClient.setShowDefaultWhenMinimapHidden(showMinimapHidden);
    if (CurrentMinimap.journeyMapLoaded()) {
      SeasonHudClient.setJourneyMapAboveMap(journeyMapAboveMap);
      SeasonHudClient.setJourneyMapMacOS(journeyMapMacOS);
    }
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
  public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    super.render(graphics, mouseX, mouseY, partialTicks);

    graphics.drawCenteredString(font, MINIMAP_SETTINGS, this.width / 2,
                                MENU_PADDING + (2 * (BUTTON_HEIGHT + BUTTON_PADDING)) - (font.lineHeight
                                    + BUTTON_PADDING), 16777215);

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      graphics.drawCenteredString(font, JOURNEYMAP, this.width / 2,
                                  MENU_PADDING + (4 * (BUTTON_HEIGHT + BUTTON_PADDING)) - (font.lineHeight
                                      + BUTTON_PADDING), 16777215);

      journeyMapAboveMapButton.active = enableMod;
      journeyMapMacOSButton.active = enableMod;
    }
    seasonButton.active = enableMod;
    colorButton.active = enableMod;
    enableMinimapIntegrationButton.active = enableMod;
    showMinimapHiddenButton.active = enableMod;
  }

  @Override
  public void init() {
    super.init();

    int enableModWidth = font.width(Common.translatedText("menu.seasonhud.main.enableMod.button").append(": OFF")) + 8;

    CycleButton<Boolean> enableModButton = CycleButton.onOffBuilder(enableMod)
        .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.enableMod.tooltip"))
        .create(this.width - enableModWidth - TITLE_PADDING / 2, TITLE_PADDING / 2, enableModWidth, BUTTON_HEIGHT,
                Common.translatedText("menu.seasonhud.main.enableMod.button"), (b, val) -> enableMod = val);

    int row = 0;
    seasonButton = MenuButton.builder(MenuButtons.SEASON, b -> {
          this.saveConfig();
          SeasonOptionsScreen.getInstance(this).open();
        })
        .withTooltip(Common.newTooltip("menu.seasonhud.main.season.tooltip"))
        .withPos(leftButtonX, (buttonStartY + (row * yOffset))).withWidth(BUTTON_WIDTH)
        .build();

    colorButton = MenuButton.builder(MenuButtons.COLORS, b -> {
          this.saveConfig();
          ColorScreen.getInstance(this).open();
        })
        .withTooltip(Common.newTooltip("menu.seasonhud.main.color.tooltip"))
        .withPos(rightButtonX, (buttonStartY + (row * yOffset))).withWidth(BUTTON_WIDTH)
        .build();

    row = 2;
    enableMinimapIntegrationButton = CycleButton.onOffBuilder(enableMinimapIntegration)
        .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.minimapIntegration.tooltip"))
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Common.translatedText("menu.seasonhud.main.enableMinimapIntegration.button"),
                (b, val) -> enableMinimapIntegration = val);

    showMinimapHiddenButton = CycleButton.onOffBuilder(showMinimapHidden)
        .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.showMinimapHidden.tooltip"))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Common.translatedText("menu.seasonhud.main.showMinimapHidden.button"),
                (b, val) -> showMinimapHidden = val);

    widgets.addAll(Arrays.asList(seasonButton, colorButton, enableModButton, enableMinimapIntegrationButton,
                                 showMinimapHiddenButton));

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      row += 2;
      journeyMapAboveMapButton = CycleButton.onOffBuilder(journeyMapAboveMap)
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.journeymap.aboveMap.tooltip"))
          .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Common.translatedText("menu.seasonhud.main.journeymap.aboveMap.button"),
                  (b, val) -> journeyMapAboveMap = val);

      journeyMapMacOSButton = CycleButton.onOffBuilder(journeyMapMacOS)
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.journeymap.macOS.tooltip"))
          .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Common.translatedText("menu.seasonhud.main.journeymap.macOS.button"),
                  (b, val) -> journeyMapMacOS = val);

      widgets.add(journeyMapAboveMapButton);
      widgets.add(journeyMapMacOSButton);
    }

    widgets.forEach(this::addRenderableWidget);
  }
}
package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.components.buttons.CycleButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton;
import club.iananderson.seasonhud.client.gui.components.buttons.MenuButton.MenuButtons;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.platform.Services;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

public class MainConfigScreen extends SeasonHudScreen {
  private static final Component SCREEN_TITLE = Common.translatedText("menu.seasonhud.main.title");
  private static final Component MINIMAP_SETTINGS = Common.translatedText("menu.seasonhud.main.minimap.options");
  private static final Component JOURNEYMAP = Common.translatedText("menu.seasonhud.main.journeymap.title");
  MenuButton seasonButton;
  MenuButton colorButton;
  CycleButton<Boolean> enableMinimapIntegrationButton;
  CycleButton<Boolean> showMinimapHiddenButton;
  CycleButton<Boolean> journeyMapAboveMapButton;
  CycleButton<Boolean> journeyMapMacOsButton;
  private boolean enableMod;
  private boolean showMinimapHidden;
  private boolean enableMinimapIntegration;
  private boolean journeyMapAboveMap;
  private boolean journeyMapMacOs;

  public MainConfigScreen() {
    super(null, SCREEN_TITLE);
    loadConfig();
    this.buttonWidth = 170;
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
      journeyMapMacOs = SeasonHudClient.getJourneyMapMacOs();
    }
  }

  public void saveConfig() {
    SeasonHudClient.setEnableMod(enableMod);
    SeasonHudClient.setEnableMinimapIntegration(enableMinimapIntegration);
    SeasonHudClient.setShowDefaultWhenMinimapHidden(showMinimapHidden);
    if (CurrentMinimap.journeyMapLoaded()) {
      SeasonHudClient.setJourneyMapAboveMap(journeyMapAboveMap);
      SeasonHudClient.setJourneyMapMacOs(journeyMapMacOs);
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
  public void render(@NonNull PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
    super.render(graphics, mouseX, mouseY, partialTicks);

    GuiComponent.drawCenteredString(graphics, font, MINIMAP_SETTINGS, this.width / 2,
                                    MENU_PADDING + (2 * (buttonHeight + BUTTON_PADDING)) - (font.lineHeight
                                        + BUTTON_PADDING), 16777215);

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      GuiComponent.drawCenteredString(graphics, font, JOURNEYMAP, this.width / 2,
                                      MENU_PADDING + (4 * (buttonHeight + BUTTON_PADDING)) - (font.lineHeight
                                          + BUTTON_PADDING), 16777215);

      journeyMapAboveMapButton.active = enableMod;
      journeyMapMacOsButton.active = enableMod;
    }
    seasonButton.active = enableMod;
    colorButton.active = enableMod;
    enableMinimapIntegrationButton.active = enableMod;
    showMinimapHiddenButton.active = enableMod;
  }

  @SuppressWarnings("ConstantValue")
  @Override
  public void init() {
    super.init();

    int enableModWidth = font.width(Common.translatedText("menu.seasonhud.main.enableMod.button").append(": OFF")) + 8;

    CycleButton<Boolean> enableModButton = CycleButton.onOffBuilder(enableMod)
        .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.enableMod.tooltip"))
        .create(this.width - enableModWidth - TITLE_PADDING / 2, TITLE_PADDING / 2, enableModWidth, buttonHeight,
                Common.translatedText("menu.seasonhud.main.enableMod.button"), (b, val) -> enableMod = val);
    widgets.add(enableModButton);

    int row = 0;
    seasonButton = MenuButton.builder(MenuButtons.SEASON, this, SeasonOptionsScreen.getInstance(this))
        .withTooltip(Common.newTooltip("menu.seasonhud.main.season.tooltip"))
        .withPos(leftButtonX, (buttonStartY + (row * offsetY))).withWidth(buttonWidth)
        .build();

    colorButton = MenuButton.builder(MenuButtons.COLORS, this, ColorScreen.getInstance(this))
        .withTooltip(Common.newTooltip("menu.seasonhud.main.color.tooltip"))
        .withPos(rightButtonX, (buttonStartY + (row * offsetY))).withWidth(buttonWidth)
        .build();
    widgets.addAll(Arrays.asList(seasonButton, colorButton));

    row = 2;
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

    if (Services.PLATFORM.isModLoaded("journeymap")) {
      row += 2;
      journeyMapAboveMapButton = CycleButton.onOffBuilder(journeyMapAboveMap)
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.journeymap.aboveMap.tooltip"))
          .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                  Common.translatedText("menu.seasonhud.main.journeymap.aboveMap.button"),
                  (b, val) -> journeyMapAboveMap = val);

      journeyMapMacOsButton = CycleButton.onOffBuilder(journeyMapMacOs)
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.main.journeymap.macOS.tooltip"))
          .create(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                  Common.translatedText("menu.seasonhud.main.journeymap.macOS.button"),
                  (b, val) -> journeyMapMacOs = val);

      widgets.addAll(Arrays.asList(journeyMapAboveMapButton, journeyMapMacOsButton));
    }

    widgets.forEach(this::addButton);
  }
}
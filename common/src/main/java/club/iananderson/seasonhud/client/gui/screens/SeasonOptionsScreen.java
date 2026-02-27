package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.client.gui.components.buttons.CycleButton;
import club.iananderson.seasonhud.client.gui.components.sliders.BasicSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.HudOffsetSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.HudScaleSlider;
import club.iananderson.seasonhud.config.DefaultValues.Client;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.impl.season.CurrentFertility;
import club.iananderson.seasonhud.impl.season.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jspecify.annotations.NonNull;

public class SeasonOptionsScreen extends SeasonHudScreen {
  private static final Component SCREEN_TITLE = Common.translatedText("menu.seasonhud.season.title");
  private Location hudLocation;
  private int posX;
  private int posY;
  private double seasonScale;
  private ShowDay showDay;
  private boolean seasonColor;
  private boolean showSubSeason;
  private boolean showTropicalSeason;
  private boolean showFertility;
  private boolean needCalendar;
  private boolean enableCalendarDetail;
  private boolean drawDefaultHud;
  private int dayLength;
  private int newDayLength;
  private CycleButton<Location> hudLocationButton;
  private HudOffsetSlider sliderX;
  private HudOffsetSlider sliderY;
  private HudScaleSlider hudScaleSlider;
  private EditBox dayLengthBox;

  public SeasonOptionsScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
  }

  public static SeasonOptionsScreen getInstance(Screen parentScreen) {
    return new SeasonOptionsScreen(parentScreen);
  }

  public void loadConfig() {
    drawDefaultHud = Common.drawDefaultHudMenu(this.minecraft);
    hudLocation = SeasonHudClient.getHudLocation();
    posX = SeasonHudClient.getHudX();
    posY = SeasonHudClient.getHudY();
    seasonScale = SeasonHudClient.getHudScale();
    showDay = SeasonHudClient.getShowDay();
    seasonColor = SeasonHudClient.getEnableSeasonNameColor();
    showSubSeason = SeasonHudClient.getShowSubSeason();
    showTropicalSeason = SeasonHudClient.getShowTropicalSeason();

    if (Common.hasCalendarLoaded()) {
      needCalendar = SeasonHudServer.getNeedCalendar();
      enableCalendarDetail = SeasonHudServer.getCalendarDetailMode();
    }

    if (Common.fabricSeasonsLoaded()) {
      dayLength = SeasonHudServer.getDayLength();
    }

    if (Common.sereneSeasonsLoaded()) {
      showFertility = SeasonHudClient.getShowFertility();
    }
  }

  public void saveConfig() {
    if (drawDefaultHud) {
      SeasonHudClient.setHudLocation(hudLocationButton.getValue());
      SeasonHudClient.setHudX(sliderX.getValueInt());
      SeasonHudClient.setHudY(sliderY.getValueInt());
      SeasonHudClient.setHudScale(hudScaleSlider.getValueDouble());
    }
    SeasonHudClient.setShowDay(showDay);
    SeasonHudClient.setEnableSeasonNameColor(seasonColor);
    SeasonHudClient.setShowSubSeason(showSubSeason);

    if (Common.hasTropicalSeasons()) {
      SeasonHudClient.setShowTropicalSeason(showTropicalSeason);
    }

    if (Common.clientSideConfig(this.minecraft)) {
      if (Common.hasCalendarLoaded()) {
        SeasonHudServer.setCalendarDetailMode(enableCalendarDetail);
        SeasonHudServer.setNeedCalendar(needCalendar);
      }

      if (Common.fabricSeasonsLoaded()) {
        SeasonHudServer.setDayLength(Integer.parseInt(dayLengthBox.getValue()));
      }
    }

    if (Common.sereneSeasonsLoaded()) {
      SeasonHudClient.setShowFertility(showFertility);
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

  // TODO: Need to fix Tropical Seasons option not updating in config screen
  @Override
  public void render(@NonNull PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
    super.render(graphics, mouseX, mouseY, partialTicks);
    MutableComponent seasonCombined = CurrentSeason.getInstance(this.minecraft)
        .getConfigText(showDay, showSubSeason, seasonColor);

    if (drawDefaultHud) {
      boolean customLocation = (hudLocationButton.getValue() == Location.CUSTOM);
      hudScaleSlider.visible = drawDefaultHud;
      sliderX.active = customLocation;
      sliderX.visible = drawDefaultHud;
      sliderY.active = customLocation;
      sliderY.visible = drawDefaultHud;

      seasonScale = hudScaleSlider.getValueDouble();

      int componentWidth = (int) (this.font.width(seasonCombined) * seasonScale);
      int componentHeight = (int) (this.font.lineHeight * seasonScale);

      switch (hudLocation) {
        case TOP_LEFT:
          posX = Client.DEFAULT_X_OFFSET;
          posY = Client.DEFAULT_Y_OFFSET;
          break;

        case TOP_CENTER:
          posX = (int) ((((double) width / 2) - ((double) componentWidth / 2)) / seasonScale);
          posY = Client.DEFAULT_Y_OFFSET;
          break;

        case TOP_RIGHT:
          posX = (int) ((width - componentWidth - Client.DEFAULT_X_OFFSET) / seasonScale);
          posY = Client.DEFAULT_Y_OFFSET;
          break;

        case BOTTOM_LEFT:
          posX = Client.DEFAULT_X_OFFSET;
          posY = (int) (((height - componentHeight - Client.DEFAULT_Y_OFFSET)) / seasonScale);
          break;

        case BOTTOM_RIGHT:
          posX = (int) (((width - componentWidth - Client.DEFAULT_X_OFFSET)) / seasonScale);
          posY = (int) (((height - componentHeight - Client.DEFAULT_Y_OFFSET)) / seasonScale);
          break;

        case CUSTOM:
          posX = (sliderX.getValueInt());
          posY = (sliderY.getValueInt());
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + hudLocation);
      }

      if (Common.fabricSeasonsLoaded() && Common.clientSideConfig(this.minecraft)) {
        int row = 4;

        if (Common.fabricSeasonsExtrasLoaded()) {
          row += 1;
        }

        if (!drawDefaultHud) {
          row -= 2;
        }

        GuiComponent.drawCenteredString(graphics, font, "Day Length", leftButtonX + buttonWidth / 2,
                                        MENU_PADDING + (row * (buttonHeight + BUTTON_PADDING)) - (font.lineHeight
                                            + BUTTON_PADDING), 16777215);
      }
    }

    graphics.pushPose();
    graphics.translate(0, 0, 50);
    graphics.scale((float) seasonScale, (float) seasonScale, 1.0F);
    GuiComponent.drawString(graphics, font, seasonCombined, posX, posY, 0xffffff);
    if (CurrentFertility.getInstance(this.minecraft).shouldDrawNewLine()) {
      MutableComponent fertility = CurrentFertility.getInstance(this.minecraft).getHudText();

      posY += this.font.lineHeight;
      GuiComponent.drawString(graphics, font, fertility, posX, posY, 0xffffff);
    }
    graphics.popPose();
  }

  private int maxWidth(MutableComponent seasonText) {
    int textWidth = this.font.width(seasonText);

    return (int) ((this.width - (textWidth * seasonScale)) / seasonScale);
  }

  private int maxHeight() {
    int textHeight = this.font.lineHeight;

    return (int) ((this.height - (textHeight * seasonScale)) / seasonScale);
  }

  // TODO: Need to add a button for the 'fertilityReplacesSeason' config option

  @Override
  public void init() {
    loadConfig();
    super.init();

    MutableComponent seasonCombined = CurrentSeason.getInstance(this.minecraft).getHudText();

    row = -1;

    if (drawDefaultHud) {
      row += 1; // Row 1
      hudLocationButton = CycleButton.builder(Location::getLocationName)
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.season.hudLocation.tooltip"))
          .withValues(Location.values())
          .withInitialValue(hudLocation)
          .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                  Common.translatedText("menu.seasonhud.season.hudLocation.button"),
                  (b, val) -> this.hudLocation = val);

      hudScaleSlider = HudScaleSlider.builder(Common.translatedText("menu.seasonhud.season.scale.slider"))
          .withValueRange(Client.HUD_SCALE_MIN, Client.HUD_SCALE_MAX)
          .withInitialValue(seasonScale)
          .withDefaultValue(Client.DEFAULT_HUD_SCALE).withStepSize(0.5).withPrecision(1)
          .withBounds(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight)
          .build();

      row += 1; // Row 2
      sliderX = HudOffsetSlider.builder(Common.translatedText("menu.seasonhud.season.xOffset.slider"))
          .withValues(0, this.maxWidth(seasonCombined), posX, Client.DEFAULT_X_OFFSET)
          .withBounds(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth / 2 - BasicSlider.SLIDER_PADDING,
                      buttonHeight)
          .build();

      sliderY = HudOffsetSlider.builder(Common.translatedText("menu.seasonhud.season.yOffset.slider"))
          .withValues(0, this.maxHeight(), posY, Client.DEFAULT_Y_OFFSET)
          .withBounds(rightButtonX + buttonWidth / 2 + BasicSlider.SLIDER_PADDING, (buttonStartY + (row * offsetY)),
                      buttonWidth / 2 - BasicSlider.SLIDER_PADDING, buttonHeight)
          .build();

      widgets.addAll(Arrays.asList(hudLocationButton, hudScaleSlider, sliderX, sliderY));
    }

    row += 1; // Row 3 (enableMinimapIntegration -> Row 1)
    CycleButton<ShowDay> showDayButton = CycleButton.builder(ShowDay::getDayDisplayName)
        .withTooltip(t -> Common.newTooltip("menu.seasonhud.season.showDay.tooltip"))
        .withValues(ShowDay.getValues())
        .withInitialValue(showDay)
        .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.seasonhud.season.showDay.button"), (b, val) -> this.showDay = val);

    CycleButton<Boolean> seasonColorButton = CycleButton.onOffBuilder(seasonColor)
        .withTooltip(t -> Common.newTooltip("menu.seasonhud.color.enableSeasonNameColor.tooltip"))
        .create(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.seasonhud.color.enableSeasonNameColor.button"),
                (b, val) -> this.seasonColor = val);
    widgets.addAll(Arrays.asList(showDayButton, seasonColorButton));

    row += 1; // Row 4 (enableMinimapIntegration -> Row 2)
    CycleButton<Boolean> showSubSeasonButton = CycleButton.onOffBuilder(showSubSeason)
        .withTooltip(t -> Common.newTooltip("menu.seasonhud.season.showSubSeason.tooltip"))
        .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                Common.translatedText("menu.seasonhud.season.showSubSeason.button"),
                (b, val) -> this.showSubSeason = val);

    widgets.add(showSubSeasonButton);

    // TODO: Double check this looks okay
    if (Common.hasTropicalSeasons()) {
      CycleButton<Boolean> showTropicalSeasonButton = CycleButton.onOffBuilder(showTropicalSeason)
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.season.showTropicalSeason.tooltip"))
          .create(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                  Common.translatedText("menu.seasonhud.season.showTropicalSeason.button"),
                  (b, val) -> this.showTropicalSeason = val);

      widgets.add(showTropicalSeasonButton);
    }

    if (Common.hasCalendarLoaded()) {
      row += 1; // Row 5 ((enableMinimapIntegration -> Row 3)
      CycleButton<Boolean> needCalendarButton = CycleButton.onOffBuilder(needCalendar)
          .withTooltip(t -> {
            if (!Common.clientSideConfig(this.minecraft)) {
              return Common.newTooltip("menu.seasonhud.season.serverSide.tooltip");
            } else {
              return Common.newTooltip("menu.seasonhud.season.needCalendar.tooltip");
            }
          })
          .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                  Common.translatedText("menu.seasonhud.season.needCalendar.button"),
                  (b, val) -> this.needCalendar = val);

      needCalendarButton.active = Common.clientSideConfig(this.minecraft);

      CycleButton<Boolean> calendarDetailModeButton = CycleButton.onOffBuilder(enableCalendarDetail)
          .withTooltip(t -> {
            if (!Common.clientSideConfig(this.minecraft)) {
              return Common.newTooltip("menu.seasonhud.season.serverSide.tooltip");
            } else {
              return Common.newTooltip("menu.seasonhud.season.calendarDetail.tooltip");
            }
          })
          .create(rightButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                  Common.translatedText("menu.seasonhud.season.calendarDetail.button"),
                  (b, val) -> this.enableCalendarDetail = val);

      if (!Common.clientSideConfig(this.minecraft)) {
        needCalendarButton.active = false;

        calendarDetailModeButton.active = false;
      }

      widgets.addAll(Arrays.asList(needCalendarButton, calendarDetailModeButton));
    }

    if (Common.sereneSeasonsLoaded()) {
      row += 1;
      CycleButton<Boolean> showFertilityButton = CycleButton.onOffBuilder(showFertility)
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.season.showFertility.tooltip"))
          .create(leftButtonX, (buttonStartY + (row * offsetY)), buttonWidth, buttonHeight,
                  Common.translatedText("menu.seasonhud.season.showFertility.button"),
                  (b, val) -> this.showFertility = val);
      widgets.add(showFertilityButton);
    }

    if (Common.fabricSeasonsLoaded()) {
      row += 2; // Row 4 (enableMinimapIntegration -> Row 2)
      dayLengthBox = new EditBox(this.font, leftButtonX + 1, (buttonStartY + (row * offsetY)), buttonWidth - 2,
                                 buttonHeight, Common.literalText(String.valueOf(dayLength)));
      dayLengthBox.setMaxLength(10);
      dayLengthBox.setValue(String.valueOf(dayLength));
      dayLengthBox.setResponder((lengthString) -> {
        if (validate(lengthString)) {
          dayLengthBox.setTextColor(0xffffff);
          int currentLength = Integer.parseInt(lengthString);

          if (currentLength != this.newDayLength) {
            this.newDayLength = currentLength;
            dayLengthBox.setValue(lengthString);
          }

          doneButton.active = true;
        } else {
          dayLengthBox.setTextColor(16733525);
          doneButton.active = false;
        }
      });

      dayLengthBox.visible = Common.clientSideConfig(this.minecraft);

      widgets.add(dayLengthBox);
    }

    widgets.forEach(this::addButton);
  }

  private boolean inBounds(int length) {
    int minInt = 0;

    return length >= minInt;
  }

  public boolean validate(String length) {
    try {
      int dayLength = Integer.parseInt(length);
      return this.inBounds(dayLength);
    } catch (NumberFormatException formatException) {
      return false;
    }
  }
}

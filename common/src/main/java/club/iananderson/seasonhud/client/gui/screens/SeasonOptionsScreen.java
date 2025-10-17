package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.client.gui.components.sliders.BasicSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.HudOffsetSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.HudScaleSlider;
import club.iananderson.seasonhud.config.DefaultValues.Client;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

public class SeasonOptionsScreen extends SeasonHudScreen {
  private static final Component SCREEN_TITLE = Common.translatedText("menu.seasonhud.season.title");
  private Location hudLocation;
  private int xSliderInt;
  private int ySliderInt;
  private double seasonScale;
  private ShowDay showDay;
  private boolean seasonColor;
  private boolean showSubSeason;
  private boolean showTropicalSeason;
  private boolean needCalendar;
  private boolean enableCalendarDetail;
  private boolean drawDefaultHud;
  private int dayLength;
  private int newDayLength;
  private CycleButton<Location> hudLocationButton;
  private HudOffsetSlider xSlider;
  private HudOffsetSlider ySlider;
  private HudScaleSlider hudScaleSlider;
  private EditBox dayLengthBox;

  public SeasonOptionsScreen(Screen parentScreen) {
    super(parentScreen, SCREEN_TITLE);
  }

  public static SeasonOptionsScreen getInstance(Screen parentScreen) {
    return new SeasonOptionsScreen(parentScreen);
  }

  public void loadConfig() {
    drawDefaultHud = Common.drawDefaultHudMenu();
    hudLocation = SeasonHudClient.getHudLocation();
    xSliderInt = SeasonHudClient.getHudX();
    ySliderInt = SeasonHudClient.getHudY();
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
  }

  public void saveConfig() {
    if (drawDefaultHud) {
      SeasonHudClient.setHudLocation(hudLocationButton.getValue());
      SeasonHudClient.setHudX(xSlider.getValueInt());
      SeasonHudClient.setHudY(ySlider.getValueInt());
      SeasonHudClient.setHudScale(hudScaleSlider.getValueDouble());
    }
    SeasonHudClient.setShowDay(showDay);
    SeasonHudClient.setEnableSeasonNameColor(seasonColor);

    if (Common.hasSubSeasons()) {
      SeasonHudClient.setShowSubSeason(showSubSeason);
      SeasonHudClient.setShowTropicalSeason(showTropicalSeason);
    }

    if (Common.clientSideConfig()) {
      if (Common.hasCalendarLoaded()) {
        SeasonHudServer.setCalendarDetailMode(enableCalendarDetail);
        SeasonHudServer.setNeedCalendar(needCalendar);
      }

      if (Common.fabricSeasonsLoaded()) {
        SeasonHudServer.setDayLength(Integer.parseInt(dayLengthBox.getValue()));
      }
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

  //Todo - Need to fix Tropical Seasons option not updating in config screen
  @Override
  public void render(@NotNull PoseStack graphics, int mouseX, int mouseY, float partialTicks) {
    super.render(graphics, mouseX, mouseY, partialTicks);

    int x = 3;
    int y = 3;
    seasonScale = 1;
    MutableComponent seasonCombined = CurrentSeason.getInstance(this.minecraft)
        .getSeasonHudConfigText(showDay, showSubSeason);

    if (drawDefaultHud) {
      int DEFAULT_X_OFFSET_SCALED = Client.DEFAULT_X_OFFSET;
      int DEFAULT_Y_OFFSET_SCALED = Client.DEFAULT_Y_OFFSET;
      seasonScale = hudScaleSlider.getValueDouble();
      int componentWidth = (int) (this.font.width(seasonCombined) * seasonScale);
      int componentHeight = (int) (this.font.lineHeight * seasonScale);

      boolean customLocation = (hudLocationButton.getValue() == Location.CUSTOM);

      hudScaleSlider.visible = drawDefaultHud;

      xSlider.active = customLocation;
      xSlider.visible = drawDefaultHud;

      ySlider.active = customLocation;
      ySlider.visible = drawDefaultHud;

      switch (hudLocation) {
        case TOP_LEFT:
          x = DEFAULT_X_OFFSET_SCALED;
          y = DEFAULT_Y_OFFSET_SCALED;
          break;

        case TOP_CENTER:
          x = (int) ((((double) width / 2) - ((double) componentWidth / 2)) / seasonScale);
          y = DEFAULT_Y_OFFSET_SCALED;
          break;

        case TOP_RIGHT:
          x = (int) ((width - componentWidth - DEFAULT_X_OFFSET_SCALED) / seasonScale);
          y = DEFAULT_Y_OFFSET_SCALED;
          break;

        case BOTTOM_LEFT:
          x = DEFAULT_X_OFFSET_SCALED;
          y = (int) (((height - componentHeight - DEFAULT_Y_OFFSET_SCALED)) / seasonScale);
          break;

        case BOTTOM_RIGHT:
          x = (int) (((width - componentWidth - DEFAULT_X_OFFSET_SCALED)) / seasonScale);
          y = (int) (((height - componentHeight - DEFAULT_Y_OFFSET_SCALED)) / seasonScale);
          break;

        case CUSTOM:
          x = (xSlider.getValueInt());
          y = (ySlider.getValueInt());
          break;
      }
    }

    if (Common.fabricSeasonsLoaded() && Common.clientSideConfig()) {
      int row = 4;

      if (Common.fabricSeasonsExtrasLoaded()) {
        row += 1;
      }

      if (!drawDefaultHud) {
        row -= 2;
      }

      GuiComponent.drawCenteredString(graphics, font, "Day Length", leftButtonX + BUTTON_WIDTH / 2,
                                      MENU_PADDING + (row * (BUTTON_HEIGHT + BUTTON_PADDING)) - (font.lineHeight
                                          + BUTTON_PADDING), 16777215);
    }

    graphics.pushPose();
    graphics.translate(0, 0, 50);
    graphics.scale((float) seasonScale, (float) seasonScale, 1.0F);
    GuiComponent.drawString(graphics, font, seasonCombined, x, y, 0xffffff);
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

  @Override
  public void init() {
    loadConfig();
    super.init();

    MutableComponent seasonCombined = CurrentSeason.getInstance(this.minecraft).getSeasonHudText();

    row = -1;

    if (drawDefaultHud) {
      row += 1; // Row 1
      hudLocationButton = CycleButton.builder(Location::getLocationName)
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.season.hudLocation.tooltip"))
          .withValues(Location.values())
          .withInitialValue(hudLocation)
          .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Common.translatedText("menu.seasonhud.season.hudLocation.button"),
                  (b, val) -> this.hudLocation = val);

      hudScaleSlider = HudScaleSlider.builder(Common.translatedText("menu.seasonhud.season.scale.slider"))
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.season.scale.tooltip"))
          .withValueRange(Client.HUD_SCALE_MIN, Client.HUD_SCALE_MAX)
          .withInitialValue(seasonScale)
          .withDefaultValue(Client.DEFAULT_HUD_SCALE).withStepSize(0.5).withPrecision(1)
          .withBounds(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT)
          .build();

      row += 1; // Row 2
      xSlider = HudOffsetSlider.builder(Common.translatedText("menu.seasonhud.season.xOffset.slider"))
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.season.xOffset.tooltip"))
          .withValues(0, this.maxWidth(seasonCombined), xSliderInt, Client.DEFAULT_X_OFFSET)
          .withBounds(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH / 2 - BasicSlider.SLIDER_PADDING,
                      BUTTON_HEIGHT)
          .build();

      ySlider = HudOffsetSlider.builder(Common.translatedText("menu.seasonhud.season.yOffset.slider"))
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.season.yOffset.tooltip"))
          .withValues(0, this.maxHeight(), ySliderInt, Client.DEFAULT_Y_OFFSET)
          .withBounds(rightButtonX + BUTTON_WIDTH / 2 + BasicSlider.SLIDER_PADDING, (buttonStartY + (row * yOffset)),
                      BUTTON_WIDTH / 2 - BasicSlider.SLIDER_PADDING, BUTTON_HEIGHT)
          .build();

      widgets.addAll(Arrays.asList(hudLocationButton, hudScaleSlider, xSlider, ySlider));
    }

    row += 1; // Row 3 (enableMinimapIntegration -> Row 1)
    CycleButton<ShowDay> showDayButton = CycleButton.builder(ShowDay::getDayDisplayName)
        .withTooltip(t -> Common.newTooltip("menu.seasonhud.season.showDay.tooltip"))
        .withValues(ShowDay.getValues())
        .withInitialValue(showDay)
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Common.translatedText("menu.seasonhud.season.showDay.button"), (b, val) -> this.showDay = val);

    CycleButton<Boolean> seasonColorButton = CycleButton.onOffBuilder(seasonColor)
        .withTooltip(t -> Common.newTooltip("menu.seasonhud.color.enableSeasonNameColor.tooltip"))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Common.translatedText("menu.seasonhud.color.enableSeasonNameColor.button"),
                (b, val) -> this.seasonColor = val);
    widgets.addAll(Arrays.asList(showDayButton, seasonColorButton));

    if (Common.hasSubSeasons()) {
      row += 1; // Row 4 (enableMinimapIntegration -> Row 2)
      CycleButton<Boolean> showSubSeasonButton = CycleButton.onOffBuilder(showSubSeason)
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.season.showSubSeason.tooltip"))
          .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Common.translatedText("menu.seasonhud.season.showSubSeason.button"),
                  (b, val) -> this.showSubSeason = val);

      CycleButton<Boolean> showTropicalSeasonButton = CycleButton.onOffBuilder(showTropicalSeason)
          .withTooltip(t -> Common.newTooltip("menu.seasonhud.season.showTropicalSeason.tooltip"))
          .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Common.translatedText("menu.seasonhud.season.showTropicalSeason.button"),
                  (b, val) -> this.showTropicalSeason = val);

      widgets.addAll(Arrays.asList(showSubSeasonButton, showTropicalSeasonButton));
    }

    if (Common.hasCalendarLoaded()) {
      row += 1; //Row 5 ((enableMinimapIntegration -> Row 3)
      CycleButton<Boolean> needCalendarButton = CycleButton.onOffBuilder(needCalendar)
          .withTooltip(t -> {
            if (!Common.clientSideConfig()) {
              return Common.newTooltip("menu.seasonhud.season.serverSide.tooltip");
            }

            else {
              return Common.newTooltip("menu.seasonhud.season.needCalendar.tooltip");
            }
          })
          .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Common.translatedText("menu.seasonhud.season.needCalendar.button"),
                  (b, val) -> this.needCalendar = val);

      needCalendarButton.active = Common.clientSideConfig();

      CycleButton<Boolean> calendarDetailModeButton = CycleButton.onOffBuilder(enableCalendarDetail)
          .withTooltip(t -> {
            if (!Common.clientSideConfig()) {
              return Common.newTooltip("menu.seasonhud.season.serverSide.tooltip");
            }
            else {
              return Common.newTooltip("menu.seasonhud.season.calendarDetail.tooltip");
            }
          })
          .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Common.translatedText("menu.seasonhud.season.calendarDetail.button"), (b, val) -> {
                this.enableCalendarDetail = val;
              });

      if (!Common.clientSideConfig()) {
        needCalendarButton.active = false;

        calendarDetailModeButton.active = false;
      }

      widgets.addAll(Arrays.asList(needCalendarButton, calendarDetailModeButton));
    }

    if (Common.fabricSeasonsLoaded()) {
      row += 2; //Row 4 (enableMinimapIntegration -> Row 2)
      dayLengthBox = new EditBox(this.font, leftButtonX + 1, (buttonStartY + (row * yOffset)), BUTTON_WIDTH - 2,
                                 BUTTON_HEIGHT, Common.literalText(String.valueOf(dayLength)));
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
        }
        else {
          dayLengthBox.setTextColor(16733525);
          doneButton.active = false;
        }
      });

      dayLengthBox.visible = Common.clientSideConfig();

      widgets.add(dayLengthBox);
    }

    widgets.forEach(this::addRenderableWidget);
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

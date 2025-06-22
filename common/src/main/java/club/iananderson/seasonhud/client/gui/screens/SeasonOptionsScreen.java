package club.iananderson.seasonhud.client.gui.screens;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.client.gui.components.sliders.BasicSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.HudOffsetSlider;
import club.iananderson.seasonhud.client.gui.components.sliders.HudScaleSlider;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import java.util.Arrays;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

public class SeasonOptionsScreen extends SeasonHudScreen {
  private static final Component SCREEN_TITLE = Component.translatable("menu.seasonhud.season.title");
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
    hudLocation = Config.getHudLocation();
    xSliderInt = Config.getHudX();
    ySliderInt = Config.getHudY();
    seasonScale = Config.getHudScale();
    showDay = Config.getShowDay();
    seasonColor = Config.getEnableSeasonNameColor();
    showSubSeason = Config.getShowSubSeason();
    showTropicalSeason = Config.getShowTropicalSeason();
    needCalendar = Config.getNeedCalendar();
    enableCalendarDetail = Config.getCalendarDetailMode();
    dayLength = Config.getDayLength();
  }

  public void saveConfig() {
    if (drawDefaultHud) {
      Config.setHudLocation(hudLocationButton.getValue());
      Config.setHudX(xSlider.getValueInt());
      Config.setHudY(ySlider.getValueInt());
      Config.setHudScale(hudScaleSlider.getValueDouble());
    }
    Config.setShowDay(showDay);
    Config.setEnableSeasonNameColor(seasonColor);

    if (Common.hasSubSeasons()) {
      Config.setShowSubSeason(showSubSeason);
      Config.setShowTropicalSeason(showTropicalSeason);
    }

    if (Common.hasCalendarLoaded()) {
      Config.setNeedCalendar(needCalendar);
      Config.setCalendarDetailMode(enableCalendarDetail);
    }

    if (Common.fabricSeasonsLoaded()) {
      Config.setDayLength(Integer.parseInt(dayLengthBox.getValue()));
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

    if (drawDefaultHud) {
      MutableComponent seasonCombined = CurrentSeason.getInstance(this.minecraft).getSeasonHudText();
      boolean custom = hudLocationButton.getValue() == Location.CUSTOM;
      seasonScale = hudScaleSlider.getValueDouble();

      xSlider.active = custom;
      xSlider.visible = custom;

      ySlider.active = custom;
      ySlider.visible = custom;

      hudScaleSlider.active = drawDefaultHud;

      if (Common.fabricSeasonsLoaded()) {
        graphics.drawCenteredString(font, "Day Length", leftButtonX + BUTTON_WIDTH / 2,
                                    MENU_PADDING + (3 * (BUTTON_HEIGHT + BUTTON_PADDING)) - (font.lineHeight
                                        + BUTTON_PADDING), 16777215);
      }

      int componentWidth = (int) (this.font.width(seasonCombined) * seasonScale);
      int componentHeight = (int) (this.font.lineHeight * seasonScale);
      int DEFAULT_X_OFFSET_SCALED = (int) (Config.DEFAULT_X_OFFSET);
      int DEFAULT_Y_OFFSET_SCALED = (int) (Config.DEFAULT_Y_OFFSET);
      int x = 0;
      int y = 0;

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
          x = (int) ((xSlider.getValueInt()));
          y = (int) ((ySlider.getValueInt()));
          break;
      }

      graphics.pose().pushPose();
      graphics.pose().translate(0, 0, 50);
      graphics.pose().scale((float) seasonScale, (float) seasonScale, 1.0F);
      graphics.drawString(font, seasonCombined, x, y, 0xffffff);
      graphics.pose().popPose();
    }
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
          .withTooltip(t -> Tooltip.create(Component.translatable("menu.seasonhud.season.hudLocation.tooltip")))
          .withValues(Location.values())
          .withInitialValue(hudLocation)
          .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.season.hudLocation.button"),
                  (b, val) -> this.hudLocation = val);

      hudScaleSlider = HudScaleSlider.builder(Component.translatable("menu.seasonhud.season.scale.slider"))
          .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.season.scale.tooltip")))
          .withValueRange(Config.HUD_SCALE_MIN, Config.HUD_SCALE_MAX)
          .withInitialValue(seasonScale)
          .withDefaultValue(Config.DEFAULT_SCALE).withStepSize(0.5).withPrecision(1)
          .withBounds(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT)
          .build();

      row += 1; // Row 2
      xSlider = HudOffsetSlider.builder(Component.translatable("menu.seasonhud.season.xOffset.slider"))
          .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.season.xOffset.tooltip")))
          .withValues(0, this.maxWidth(seasonCombined), xSliderInt, Config.DEFAULT_X_OFFSET)
          .withBounds(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH / 2 - BasicSlider.SLIDER_PADDING,
                      BUTTON_HEIGHT)
          .build();

      ySlider = HudOffsetSlider.builder(Component.translatable("menu.seasonhud.season.yOffset.slider"))
          .withTooltip(Tooltip.create(Component.translatable("menu.seasonhud.season.yOffset.tooltip")))
          .withValues(0, this.maxHeight(), ySliderInt, Config.DEFAULT_Y_OFFSET)
          .withBounds(rightButtonX + BUTTON_WIDTH / 2 + BasicSlider.SLIDER_PADDING, (buttonStartY + (row * yOffset)),
                      BUTTON_WIDTH / 2 - BasicSlider.SLIDER_PADDING, BUTTON_HEIGHT)
          .build();

      widgets.addAll(Arrays.asList(hudLocationButton, hudScaleSlider, xSlider, ySlider));
    }

    row += 1; // Row 3 (enableMinimapIntegration -> Row 1)
    CycleButton<ShowDay> showDayButton = CycleButton.builder(ShowDay::getDayDisplayName)
        .withTooltip(t -> Tooltip.create(Component.translatable("menu.seasonhud.season.showDay.tooltip")))
        .withValues(ShowDay.getValues())
        .withInitialValue(showDay)
        .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.season.showDay.button"), (b, val) -> this.showDay = val);

    CycleButton<Boolean> seasonColorButton = CycleButton.onOffBuilder(seasonColor)
        .withTooltip(t -> Tooltip.create(Component.translatable("menu.seasonhud.color.enableSeasonNameColor.tooltip")))
        .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.translatable("menu.seasonhud.color.enableSeasonNameColor.button"),
                (b, val) -> this.seasonColor = val);

    widgets.addAll(Arrays.asList(showDayButton, seasonColorButton));

    if (Common.hasSubSeasons()) {
      row += 1; // Row 4 (enableMinimapIntegration -> Row 2)
      CycleButton<Boolean> showSubSeasonButton = CycleButton.onOffBuilder(showSubSeason)
          .withTooltip(t -> Tooltip.create(Component.translatable("menu.seasonhud.season.showSubSeason.tooltip")))
          .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.season.showSubSeason.button"),
                  (b, val) -> this.showSubSeason = val);

      CycleButton<Boolean> showTropicalSeasonButton = CycleButton.onOffBuilder(showTropicalSeason)
          .withTooltip(t -> Tooltip.create(Component.translatable("menu.seasonhud.season.showTropicalSeason.tooltip")))
          .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.season.showTropicalSeason.button"),
                  (b, val) -> this.showTropicalSeason = val);

      widgets.addAll(Arrays.asList(showSubSeasonButton, showTropicalSeasonButton));
    }

    if (Common.fabricSeasonsLoaded()) {
      row += 1; //Row 4 (enableMinimapIntegration -> Row 2)
      dayLengthBox = new EditBox(this.font, leftButtonX + 1, (buttonStartY + (row * yOffset)), BUTTON_WIDTH - 2,
                                 BUTTON_HEIGHT, Component.literal(String.valueOf(dayLength)));
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
      dayLengthBox.setHint(Component.literal("" + dayLength).withStyle(ChatFormatting.DARK_GRAY));
      widgets.add(dayLengthBox);
    }

    if (Common.hasCalendarLoaded()) {
      row += 1; //Row 5 ((enableMinimapIntegration -> Row 3)
      CycleButton<Boolean> needCalendarButton = CycleButton.onOffBuilder(needCalendar)
          .withTooltip(t -> Tooltip.create(Component.translatable("menu.seasonhud.main.needCalendar.tooltip")))
          .create(leftButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.main.needCalendar.button"),
                  (b, val) -> this.needCalendar = val);

      CycleButton<Boolean> calendarDetailModeButton = CycleButton.onOffBuilder(enableCalendarDetail)
          .withTooltip(t -> Tooltip.create(Component.translatable("menu.seasonhud.main.calendarDetail.tooltip")))
          .create(rightButtonX, (buttonStartY + (row * yOffset)), BUTTON_WIDTH, BUTTON_HEIGHT,
                  Component.translatable("menu.seasonhud.main.calendarDetail.button"), (b, val) -> {
                this.enableCalendarDetail = val;
                rebuildUI();
              });

      widgets.addAll(Arrays.asList(needCalendarButton, calendarDetailModeButton));
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

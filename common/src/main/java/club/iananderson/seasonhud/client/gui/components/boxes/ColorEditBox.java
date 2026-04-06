package club.iananderson.seasonhud.client.gui.components.boxes;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.ShowDay;
import club.iananderson.seasonhud.client.gui.screens.ColorsScreen;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.season.components.Seasons;
import club.iananderson.seasonhud.util.Rgb;
import java.util.EnumSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.jspecify.annotations.NonNull;

public class ColorEditBox extends EditBox {
  private static final int PADDING = 4;
  private final Seasons boxSeason;
  private final int seasonColor;
  private int newSeasonColor;

  public ColorEditBox(Font font, int x, int y, int width, int height, Seasons season) {
    super(font, x, y, width, height, season.getSeasonNameTranslated());
    this.boxSeason = season;
    this.seasonColor = season.getSeasonColor();
    this.newSeasonColor = seasonColor;
    this.setMaxLength(8);
    this.setValue(String.valueOf(seasonColor));
    this.setResponder(colorString -> {
      if (validate(colorString)) {
        this.setTextColor(0xffffffff);
        int colorInt = Integer.parseInt(colorString);

        if (colorInt != this.newSeasonColor) {
          this.newSeasonColor = colorInt;
          this.setValue(colorString);
        }

        ColorsScreen.doneButton.active = true;
      } else {
        this.setTextColor(16733525);
        ColorsScreen.doneButton.active = false;
      }
    });
    this.setEditable(SeasonHudClient.getEnableSeasonNameColor());
  }

  private static EnumSet<Seasons> seasonListSet() {
    EnumSet<Seasons> set = Seasons.SEASONS_ENUM_LIST.clone();

    if (!SeasonHudClient.getShowTropicalSeason() || Common.fabricSeasonsLoaded()) {
      set.remove(Seasons.DRY);
      set.remove(Seasons.WET);
    }

    return set;
  }

  private boolean inBounds(int color) {
    int minColor = 0;
    int maxColor = 16777215;

    return color >= minColor && color <= maxColor;
  }

  public boolean validate(String colorString) {
    try {
      int colorInt = Integer.parseInt(colorString);
      return this.inBounds(colorInt);
    } catch (NumberFormatException formatException) {
      return false;
    }
  }

  public void save() {
    Rgb.setRgb(this.boxSeason, this.newSeasonColor);
    this.boxSeason.setSeasonColor(this.newSeasonColor);
  }

  public int getColor() {
    return this.seasonColor;
  }

  public int getNewColor() {
    return this.newSeasonColor;
  }

  public Seasons getSeason() {
    return this.boxSeason;
  }

  public MutableComponent getMenuText(Seasons season, int newRgb, boolean seasonShort) {
    Style seasonFormat = Style.EMPTY;

    if (SeasonHudClient.getEnableSeasonNameColor()) {
      seasonFormat = Style.EMPTY.withColor(newRgb);
    }

    MutableComponent seasonText = Common.translatedText(ShowDay.NONE.getKey(), season.getSeasonNameTranslated());
    MutableComponent seasonIcon = Common.translatedText("desc.seasonhud.hud.icon", season.getIconChar());

    // Removes "Season" from Dry Season if the total color widget width is too large for the screen
    if (season == Seasons.DRY && seasonShort) {
      seasonText = Common.translatedText("menu.seasonhud.color.season.dry.editbox");
    }

    // Removes "Season" from Wet Season if the total color widget width is too large for the screen
    if (season == Seasons.WET && seasonShort) {
      seasonText = Common.translatedText("menu.seasonhud.color.season.wet.editbox");
    }

    return Common.translatedText("desc.seasonhud.hud.combined", seasonIcon.withStyle(Common.SEASON_ICON_STYLE),
                                 seasonText.withStyle(seasonFormat));
  }

  @Override
  public void renderWidget(@NonNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
    Minecraft mc = Minecraft.getInstance();
    float textScale = 1;
    int scaledWidth = mc.getWindow().getGuiScaledWidth();
    int widgetTotalSize = ((80 + ColorsScreen.BUTTON_PADDING) * seasonListSet().size());
    boolean seasonShort = (scaledWidth < widgetTotalSize);

    MutableComponent seasonCombined = this.getMenuText(this.boxSeason, this.newSeasonColor, seasonShort);

    graphics.pose().pushMatrix();
    if ((mc.font.width(seasonCombined) > this.getWidth() - PADDING)) {
      textScale = ((float) this.getWidth() - PADDING) / mc.font.width(seasonCombined);
    }
    graphics.pose().scale(textScale, textScale);
    graphics.drawCenteredString(mc.font, seasonCombined, (int) ((getX() + (double) this.getWidth() / 2) / textScale),
                                (int) ((getY() - (mc.font.lineHeight * textScale) - PADDING) / textScale), 0xffffffff);
    graphics.pose().popMatrix();

    super.renderWidget(graphics, mouseX, mouseY, partialTicks);
  }
}
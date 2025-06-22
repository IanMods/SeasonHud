package club.iananderson.seasonhud.client.gui.components.sliders.rgb;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;

public class RedSlider extends RgbSlider {
  public RedSlider(int x, int y, int initial, ColorEditBox seasonBox) {
    super(x, y, initial, seasonBox, ChatFormatting.RED);
    this.seasonBox = seasonBox;
    this.r = Rgb.rColor(Integer.parseInt(seasonBox.getValue()));
    this.defaultValue = Rgb.rColor(seasonBox.getSeason().getDefaultColor());
    this.updateMessage();
  }

  @Override
  protected void applyValue() {
    this.g = Rgb.getGreen(season);
    this.b = Rgb.getBlue(season);
    this.rgb = Rgb.rgbInt(this.getValueInt(), this.g, this.b);

    Rgb.setRgb(season, this.rgb);
    this.seasonBox.setValue(String.valueOf(this.rgb));
  }
}
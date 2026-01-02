package club.iananderson.seasonhud.client.gui.components.sliders.rgb;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;

public class RedSlider extends RgbSlider {
  public RedSlider(int x, int y, int initial, ColorEditBox seasonBox) {
    super(x, y, initial, seasonBox, ChatFormatting.RED);
    this.seasonBox = seasonBox;
    this.red = Rgb.red(Integer.parseInt(seasonBox.getValue()));
    this.defaultValue = Rgb.red(seasonBox.getSeason().getDefaultColor());
    this.updateMessage();
  }

  @Override
  protected void applyValue() {
    this.green = Rgb.getGreen(season);
    this.blue = Rgb.getBlue(season);
    this.rgb = Rgb.rgbInt(this.getValueInt(), this.green, this.blue);

    Rgb.setRgb(season, this.rgb);
    this.seasonBox.setValue(String.valueOf(this.rgb));
  }
}
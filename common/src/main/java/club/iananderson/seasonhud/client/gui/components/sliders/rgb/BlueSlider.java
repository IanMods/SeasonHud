package club.iananderson.seasonhud.client.gui.components.sliders.rgb;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;

public class BlueSlider extends RgbSlider {
  public BlueSlider(int x, int y, int initial, ColorEditBox seasonBox) {
    super(x, y, initial, seasonBox, ChatFormatting.BLUE);
    this.seasonBox = seasonBox;
    this.blue = Rgb.blue(Integer.parseInt(seasonBox.getValue()));
    this.defaultValue = Rgb.blue(seasonBox.getSeason().getDefaultColor());
    this.updateMessage();
  }

  @Override
  public void applyValue() {
    this.red = Rgb.getRed(season);
    this.green = Rgb.getGreen(season);
    this.rgb = Rgb.rgbInt(this.red, this.green, this.getValueInt());

    Rgb.setRgb(season, this.rgb);
    this.seasonBox.setValue(String.valueOf(this.rgb));
  }
}
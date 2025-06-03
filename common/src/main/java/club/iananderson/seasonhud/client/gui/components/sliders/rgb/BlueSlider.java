package club.iananderson.seasonhud.client.gui.components.sliders.rgb;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;

public class BlueSlider extends RgbSlider {
  public BlueSlider(int x, int y, int initial, ColorEditBox seasonBox) {
    super(x, y, initial, seasonBox, ChatFormatting.BLUE);
    this.seasonBox = seasonBox;
    this.b = Rgb.bColor(Integer.parseInt(seasonBox.getValue()));
    this.defaultValue = Rgb.bColor(seasonBox.getSeason().getDefaultColor());
    this.updateMessage();
  }

  @Override
  public void applyValue() {
    this.r = Rgb.getRed(season);
    this.g = Rgb.getGreen(season);
    this.rgb = Rgb.rgbInt(this.r, this.g, this.getValueInt());

    Rgb.setRgb(season, this.rgb);
    this.seasonBox.setValue(String.valueOf(this.rgb));
  }
}
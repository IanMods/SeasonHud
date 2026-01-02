package club.iananderson.seasonhud.client.gui.components.sliders.rgb;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;

public class GreenSlider extends RgbSlider {
  public GreenSlider(int x, int y, int initial, ColorEditBox seasonBox) {
    super(x, y, initial, seasonBox, ChatFormatting.GREEN);
    this.seasonBox = seasonBox;
    this.green = Rgb.green(Integer.parseInt(seasonBox.getValue()));
    this.defaultValue = Rgb.green(seasonBox.getSeason().getDefaultColor());
    this.updateMessage();
  }

  @Override
  protected void applyValue() {
    this.red = Rgb.getRed(season);
    this.blue = Rgb.getBlue(season);
    this.rgb = Rgb.rgbInt(this.red, this.getValueInt(), this.blue);

    Rgb.setRgb(season, this.rgb);
    this.seasonBox.setValue(String.valueOf(this.rgb));
  }
}
package club.iananderson.seasonhud.client.gui.components.sliders.rgb;

import club.iananderson.seasonhud.client.gui.components.boxes.ColorEditBox;
import club.iananderson.seasonhud.util.Rgb;
import net.minecraft.ChatFormatting;

public class GreenSlider extends RgbSlider {
  public GreenSlider(int x, int y, int initial, ColorEditBox seasonBox) {
    super(x, y, initial, seasonBox, ChatFormatting.GREEN);
    this.seasonBox = seasonBox;
    this.g = Rgb.gColor(Integer.parseInt(seasonBox.getValue()));
    this.defaultValue = Rgb.gColor(seasonBox.getSeason().getDefaultColor());
    this.updateMessage();
  }

  @Override
  protected void applyValue() {
    this.r = Rgb.getRed(season);
    this.b = Rgb.getBlue(season);
    this.rgb = Rgb.rgbInt(this.r, this.getValueInt(), this.b);

    Rgb.setRgb(season, this.rgb);
    this.seasonBox.setValue(String.valueOf(this.rgb));
  }
}
package club.iananderson.seasonhud.impl.season.components;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

public enum Fertility {
  FERTILE(0, "desc.seasonhud.fertility.fertile", Style.EMPTY),

  INFERTILE_BIOME(1, "desc.seasonhud.fertility.infertile_biome", Style.EMPTY),

  ALWAYS_WINTER(2, "desc.seasonhud.fertility.always_winter",
                Style.EMPTY.withColor(TextColor.fromRgb(Seasons.WINTER.getSeasonColor()))),

  UNDERGROUND(3, "desc.seasonhud.fertility.underground", Style.EMPTY.withColor(ChatFormatting.GRAY));

  private final int id;
  private final String key;
  private final Style style;
  private final int color;

  Fertility(int id, String key, Style style) {
    this.id = id;
    this.key = key;
    this.style = style;
    if (style.getColor() != null) {
      this.color = style.getColor().getValue();
    } else {
      this.color = 16777215; // White
    }
  }

  public int getId() {
    return this.id;
  }

  public String getKey() {
    return this.key;
  }

  public Style getStyle() {
    return this.style;
  }

  public int getColor() {
    return this.color;
  }
}

package club.iananderson.seasonhud.impl.season;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;

public enum Fertility {
  FERTILE(0, "desc.seasonhud.fertility.fertile", Style.EMPTY),

  INFERTILE_BIOME(1, "desc.seasonhud.fertility.infertile_biome", Style.EMPTY),

  ALWAYS_WINTER(2, "desc.seasonhud.fertility.always_winter", Style.EMPTY.withColor(Seasons.WINTER.getSeasonColor())),

  UNDERGROUND(3, "desc.seasonhud.fertility.underground", Style.EMPTY.withColor(ChatFormatting.GRAY));

  private final int id;
  private final String key;
  private final Style style;

  Fertility(int id, String key, Style style) {
    this.id = id;
    this.key = key;
    this.style = style;
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
}

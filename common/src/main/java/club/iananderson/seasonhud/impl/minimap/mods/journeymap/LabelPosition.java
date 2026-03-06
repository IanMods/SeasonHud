package club.iananderson.seasonhud.impl.minimap.mods.journeymap;

import club.iananderson.seasonhud.Common;
import journeymap.client.api.option.KeyedEnum;
import journeymap.client.render.draw.DrawUtil.HAlign;
import journeymap.client.render.draw.DrawUtil.VAlign;

public enum LabelPosition implements KeyedEnum {
  Top("jm.minimap.info_slot.top", VAlign.Above),
  Bottom("jm.minimap.info_slot.bottom", VAlign.Below);

  private final String key;
  private final VAlign vertAlign;

  LabelPosition(String key, VAlign vertAlign) {
    this.key = key;
    this.vertAlign = vertAlign;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String toString() {
    return Common.translatedText(this.key).getString();
  }

  public VAlign getVertAlign() {
    return vertAlign;
  }

  public HAlign getHoriAlign() {
    return HAlign.Center;
  }
}

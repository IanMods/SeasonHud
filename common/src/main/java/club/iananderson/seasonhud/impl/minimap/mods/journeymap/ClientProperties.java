package club.iananderson.seasonhud.impl.minimap.mods.journeymap;

import club.iananderson.seasonhud.Common;
import journeymap.api.v2.client.event.InfoSlotDisplayEvent.Position;
import journeymap.api.v2.common.option.BooleanOption;
import journeymap.api.v2.common.option.EnumOption;
import journeymap.api.v2.common.option.OptionCategory;

public class ClientProperties {
  public final BooleanOption addAdditional;
  public final EnumOption<Position> position;

  public ClientProperties() {
    OptionCategory seasonCategory = new OptionCategory(Common.MOD_ID, "desc.seasonhud.keybind.category",
                                                       "desc.seasonhud.keybind.options");
    this.addAdditional = new BooleanOption(seasonCategory, "addAdditional", "Add an additional InfoSlot?", true);
    this.position = new EnumOption<>(seasonCategory, "position", "Location of the additional InfoSlot",
                                     Position.Bottom);
  }
}
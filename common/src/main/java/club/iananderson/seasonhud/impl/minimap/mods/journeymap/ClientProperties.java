package club.iananderson.seasonhud.impl.minimap.mods.journeymap;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.platform.Services;
import journeymap.client.api.option.EnumOption;
import journeymap.client.api.option.OptionCategory;
import net.minecraft.client.gui.screens.Screen;

public class ClientProperties {
  public final EnumOption<LabelPosition> position;

  public ClientProperties() {
    OptionCategory seasonCategory = new OptionCategory(Common.MOD_ID, "desc.seasonhud.keybind.category",
                                                       "desc.seasonhud.keybind.options");
    this.position = new EnumOption<>(seasonCategory, "position", "Location of the additional InfoSlot",
                                     LabelPosition.Bottom);
  }

  public void openAddonOptionsEditor(Screen returnScreen) {
    Services.MINIMAP.openJourneyMapOptions(returnScreen);
  }
}
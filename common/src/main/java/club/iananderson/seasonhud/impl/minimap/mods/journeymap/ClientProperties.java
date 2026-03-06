package club.iananderson.seasonhud.impl.minimap.mods.journeymap;

import club.iananderson.seasonhud.Common;
import journeymap.client.api.option.EnumOption;
import journeymap.client.api.option.OptionCategory;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.component.JmUI;
import journeymap.client.ui.dialog.AddonOptionsManager;
import journeymap.common.Journeymap;
import journeymap.common.log.LogFormatter;
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
    try {
      JmUI editor = new AddonOptionsManager(returnScreen);
      UIManager.INSTANCE.open(editor);
    } catch (LinkageError e) {
      UIManager.handleLinkageError(e);
    } catch (Throwable e) {
      Journeymap.getLogger().error("Error opening Addon options manager: " + LogFormatter.toString(e));
    }

  }
}
package club.iananderson.seasonhud;

import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.gui.screens.MainConfigScreen;
import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import club.iananderson.seasonhud.impl.accessory.mods.accessories.AccessoriesCompat;

public class SeasonHudClientCommon {
  public static void initAccessoriesClient() {
    if (Common.accessoriesLoaded() && Calendar.calendar().isPresent() && !Common.curiosLoaded()) {
      AccessoriesCompat.clientInit();
    }
  }

  public static void optionsKeyInput() {
    if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
      MainConfigScreen.getInstance().open();
    }
  }
}

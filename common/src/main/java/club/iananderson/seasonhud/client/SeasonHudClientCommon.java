package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.screens.MainOptionsScreen;
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
      MainOptionsScreen.getInstance(null).open();
    }
  }

  public static void ftbChunkSetup() {
    if (Common.ftbChunksLoaded()) {
      // SeasonComponent.ftbChunkSetup();
    }
  }
}
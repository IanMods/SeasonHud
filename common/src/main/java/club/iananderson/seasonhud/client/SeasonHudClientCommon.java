package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.client.gui.screens.MainOptionsScreen;

public class SeasonHudClientCommon {
  public static void optionsKeyInput() {
    if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
      MainOptionsScreen.getInstance(null).open();
    }
  }
}
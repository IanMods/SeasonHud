package club.iananderson.seasonhud;

import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.gui.screens.MainOptionsScreen;

public class SeasonHudClientCommon {
  public static void optionsKeyInput() {
    if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
      MainOptionsScreen.getInstance().open();
    }
  }
}

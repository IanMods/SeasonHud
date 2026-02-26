package club.iananderson.seasonhud;

import club.iananderson.seasonhud.client.KeyBindings;
import club.iananderson.seasonhud.client.gui.screens.MainConfigScreen;

public class SeasonHudClientCommon {
  public static void optionsKeyInput() {
    if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
      MainConfigScreen.getInstance().open();
    }
  }
}

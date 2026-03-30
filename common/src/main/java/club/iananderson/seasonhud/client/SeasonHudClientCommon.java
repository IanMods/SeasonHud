package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.client.gui.screens.MainOptionsScreen;
import club.iananderson.seasonhud.impl.minimap.mods.ftbchunks.SeasonComponent;

public class SeasonHudClientCommon {
  public static void optionsKeyInput() {
    if (KeyBindings.seasonhudOptionsKeyMapping.consumeClick()) {
      MainOptionsScreen.getInstance().open();
    }
  }

  public static void ftbChunkSetup() {
    if (Common.ftbChunksLoaded()) {
      SeasonComponent.ftbChunkSetup();
    }
  }
}
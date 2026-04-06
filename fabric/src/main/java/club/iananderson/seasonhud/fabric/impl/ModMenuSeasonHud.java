package club.iananderson.seasonhud.fabric.impl;

import club.iananderson.seasonhud.client.gui.screens.MainOptionsScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuSeasonHud implements ModMenuApi {
  @Override
  public ConfigScreenFactory<?> getModConfigScreenFactory() {
    return MainOptionsScreen::getInstance;
  }
}
<<<<<<<< HEAD:fabric/src/main/java/club/iananderson/seasonhud/fabric/impl/minimaps/XaeroInfoDisplays.java
package club.iananderson.seasonhud.fabric.impl.minimaps;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimap;
import club.iananderson.seasonhud.impl.seasons.CurrentFertility;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
========
package club.iananderson.seasonhud.impl.minimap.mods.xaero;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.minimap.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimap.mods.MinimapMods;
import club.iananderson.seasonhud.impl.season.CurrentFertility;
import club.iananderson.seasonhud.impl.season.CurrentSeason;
>>>>>>>> origin/active/multi/1.18.2:common/src/main/java/club/iananderson/seasonhud/impl/minimap/mods/xaero/XaeroInfoDisplays.java
import net.minecraft.client.Minecraft;
import xaero.hud.minimap.info.InfoDisplay;
import xaero.hud.minimap.info.InfoDisplay.Builder;
import xaero.hud.minimap.info.widget.InfoDisplayCommonWidgetFactories;
import xaero.lib.common.config.option.value.io.serialization.BuiltInConfigValueIOCodecs;

public class XaeroInfoDisplays {
  public static final Builder<Boolean> SEASON_INFO_BUILDER;
  public static InfoDisplay<Boolean> SEASON;

  static {
    Minecraft mc = Minecraft.getInstance();

    Builder<Boolean> builder = Builder.begin();

    SEASON_INFO_BUILDER = builder.setId("season")
        .setName(Common.translatedText("xaerominimap.seasonhud.infodisplay.season"))
        .setDefaultState(true)
        .setCodec(BuiltInConfigValueIOCodecs.BOOLEAN)
        .setWidgetFactory(InfoDisplayCommonWidgetFactories.OFF_ON)
        .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
          if (displayInfo.getEffectiveState() && CurrentMinimap.xaeroLoaded() && CurrentMinimap.shouldDrawMinimapHud(
              MinimapMods.XAERO, mc) && mc.level != null) {
            compiler.addLine(CurrentSeason.getInstance(mc).getHudText());

            if (CurrentFertility.getInstance(mc).shouldDrawNewLine()) {
              compiler.addLine(CurrentFertility.getInstance(mc).getMinimapText());
            }
          }
        });
  }
}
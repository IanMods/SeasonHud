package club.iananderson.seasonhud.fabric.impl.minimaps;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap;
import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimap;
import club.iananderson.seasonhud.impl.seasons.CurrentFertility;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import net.minecraft.client.Minecraft;
import xaero.hud.minimap.info.InfoDisplay;
import xaero.hud.minimap.info.InfoDisplay.Builder;
import xaero.hud.minimap.info.codec.InfoDisplayCommonStateCodecs;
import xaero.hud.minimap.info.widget.InfoDisplayCommonWidgetFactories;

public class XaeroInfoDisplays {
  public static final Builder<Boolean> SEASON_INFO_BUILDER;
  public static InfoDisplay<Boolean> SEASON;

  static {
    Minecraft mc = Minecraft.getInstance();

    Builder<Boolean> builder = Builder.begin();

    SEASON_INFO_BUILDER = builder.setId("season")
        .setName(Common.translatedText("xaerominimap.seasonhud.infodisplay.season"))
        .setDefaultState(true)
        .setCodec(InfoDisplayCommonStateCodecs.BOOLEAN)
        .setWidgetFactory(InfoDisplayCommonWidgetFactories.OFF_ON)
        .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
          if ((Boolean) displayInfo.getState() && CurrentMinimap.xaeroLoaded() && CurrentMinimap.shouldDrawMinimapHud(
              Minimap.XAERO) && mc.level != null) {
            compiler.addLine(CurrentSeason.getInstance(mc).getHudText());

            if (Common.sereneSeasonsLoaded() && SeasonHudClient.getShowFertility()) {
              compiler.addLine(CurrentFertility.getInstance(mc).getMinimapText());
            }
          }
        });
  }
}
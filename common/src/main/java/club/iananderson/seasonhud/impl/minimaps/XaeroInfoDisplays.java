package club.iananderson.seasonhud.impl.minimaps;

import club.iananderson.seasonhud.impl.minimaps.CurrentMinimap.Minimap;
import club.iananderson.seasonhud.impl.seasons.CurrentSeason;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import xaero.hud.minimap.info.InfoDisplay;
import xaero.hud.minimap.info.InfoDisplay.Builder;
import xaero.hud.minimap.info.InfoDisplayManager;
import xaero.hud.minimap.info.codec.InfoDisplayCommonStateCodecs;
import xaero.hud.minimap.info.codec.InfoDisplayStateCodec;
import xaero.hud.minimap.info.widget.InfoDisplayCommonWidgetFactories;

public class XaeroInfoDisplays {
  private static final List<InfoDisplay<?>> ALL = new ArrayList<>();
  public static final InfoDisplay<Boolean> SEASON;

  static {
    Minecraft mc = Minecraft.getInstance();

    Builder<Boolean> BUILDER = Builder.begin();

    BUILDER.setId("season")
        .setName(Component.translatable("xaerominimap.seasonhud.infodisplay.season")).setDefaultState(true)
        .setCodec(InfoDisplayCommonStateCodecs.BOOLEAN).setWidgetFactory(InfoDisplayCommonWidgetFactories.OFF_ON)
        .setCompiler((displayInfo, compiler, session, availableWidth, playerPos) -> {
          if ((Boolean) displayInfo.getState() && CurrentMinimap.xaeroLoaded() && CurrentMinimap.shouldDrawMinimapHud(
              Minimap.XAERO)) {
            compiler.addLine(CurrentSeason.getInstance(mc).getSeasonHudText());
          }
        });

    Objects.requireNonNull(ALL);

    SEASON = BUILDER.setDestination(ALL::add)
        .build();
  }
}
package club.iananderson.seasonhud.impl.minimap.mods.journeymap;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.season.CurrentFertility;
import club.iananderson.seasonhud.impl.season.CurrentSeason;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.EnumSet;
import journeymap.client.JourneymapClient;
import journeymap.client.api.ClientPlugin;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.IClientPlugin;
import journeymap.client.api.event.ClientEvent;
import journeymap.client.api.event.ClientEvent.Type;
import journeymap.client.api.event.RegistryEvent;
import journeymap.client.api.event.RegistryEvent.OptionsRegistryEvent;
import journeymap.client.api.event.RegistryEvent.RegistryType;
import journeymap.client.io.ThemeLoader;
import journeymap.client.properties.MiniMapProperties;
import journeymap.client.render.RenderWrapper;
import journeymap.client.render.draw.DrawUtil;
import journeymap.client.ui.minimap.DisplayVars;
import journeymap.client.ui.minimap.Shape;
import journeymap.client.ui.theme.Theme;
import journeymap.client.ui.theme.Theme.LabelSpec;
import journeymap.client.ui.theme.Theme.Minimap;
import journeymap.client.ui.theme.ThemeLabelSource;
import journeymap.client.ui.theme.ThemeLabelSource.InfoSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jspecify.annotations.NonNull;

@ClientPlugin
public class JourneymapSeasonPlugin implements IClientPlugin {
  private static JourneymapSeasonPlugin INSTANCE;
  private static InfoSlot Season;
  private final String seasonKeyString = "xaerominimap.seasonhud.infodisplay.season";
  private final String fertilityKeyString = "xaerominimap.seasonhud.infodisplay.fertility";
  private final Component seasonKey = Common.translatedText(seasonKeyString);
  private final Component fertilityKey = Common.translatedText(fertilityKeyString);
  private IClientAPI api;
  private ClientProperties clientProperties;
  private Minecraft mc;

  public JourneymapSeasonPlugin() {
  }

  public static JourneymapSeasonPlugin getInstance() {
    return INSTANCE;
  }

  public ClientProperties getClientProperties() {
    return clientProperties;
  }

  @Override
  public void initialize(@NonNull IClientAPI api) {
    INSTANCE = this;
    api.subscribe(this.getModId(), EnumSet.of(Type.REGISTRY));

    this.api = api;
    this.mc = Minecraft.getInstance();

    Common.LOG.info("Initialized JourneyMapAPI");
  }

  @Override
  public String getModId() {
    return Common.MOD_ID;
  }

  @Override
  public void onEvent(ClientEvent clientEvent) {
    if (Type.REGISTRY.equals(clientEvent.type)) {
      RegistryEvent registryEvent = (RegistryEvent) clientEvent;

      // if (registryEvent.getRegistryType() == RegistryType.INFO_SLOT) {
      //   infoSlotRegistryEvent((InfoSlotRegistryEvent) registryEvent);
      // }

      if (registryEvent.getRegistryType() == RegistryType.OPTIONS) {
        optionsRegistryEvent((OptionsRegistryEvent) registryEvent);
      }
    }
  }

  // private void infoSlotRegistryEvent(InfoSlotRegistryEvent event) {
  //   event.register(Common.MOD_ID, seasonKeyString, 1000L,
  //                  () -> CurrentSeason.getInstance(mc).journeymapText().getString());
  //   event.register(Common.MOD_ID, fertilityKeyString, 1000L,
  //                  () -> CurrentFertility.getInstance(mc).getMinimapText().getString());
  // }

  private void optionsRegistryEvent(OptionsRegistryEvent event) {
    this.clientProperties = new ClientProperties();
  }

  public Theme.Minimap.MinimapSpec minimapSpec() {
    MiniMapProperties mapProperties = JourneymapClient.getInstance().getActiveMiniMapProperties();
    Minimap currentTheme = ThemeLoader.getCurrentTheme().minimap;

    if (mapProperties.shape.get() == Shape.Circle) {
      return currentTheme.circle;
    } else {
      return currentTheme.square;
    }
  }

  public void drawSeasonInfoSlot(PoseStack graphics, DisplayVars dv) {
    MiniMapProperties mapProperties = JourneymapClient.getInstance().getActiveMiniMapProperties();
    LabelSpec labelSpec;
    LabelPosition labelPosition = getClientProperties().position.get();
    int labelsHeight;
    int startY;
    int labelOffset;

    if (labelPosition == LabelPosition.Top) {
      labelSpec = minimapSpec().labelTop;
      labelsHeight = dv.getInfoLabelAreaHeight(mc.font, minimapSpec().labelTop,
                                               ThemeLabelSource.values.get(mapProperties.info1Label.get()),
                                               ThemeLabelSource.values.get(mapProperties.info2Label.get()));
      startY = dv.textureY;
      labelOffset = minimapSpec().labelTopInside
                    ? minimapSpec().margin
                    : -minimapSpec().margin - labelsHeight;
    } else {
      labelSpec = minimapSpec().labelBottom;
      labelsHeight = dv.getInfoLabelAreaHeight(mc.font, minimapSpec().labelBottom,
                                               ThemeLabelSource.values.get(mapProperties.info3Label.get()),
                                               ThemeLabelSource.values.get(mapProperties.info4Label.get()));
      startY = dv.textureY + dv.minimapHeight;
      labelOffset = minimapSpec().labelBottomInside
                    ? -minimapSpec().margin
                    : minimapSpec().margin + labelsHeight;

    }

    int labelY = startY + labelOffset;
    int labelX = (int) Math.floor((dv.textureX + (double) (dv.minimapWidth / 2)));

    DrawUtil.sizeDisplay(graphics, mc.getWindow().getWidth(), mc.getWindow().getHeight());
    RenderWrapper.enableBlend();

    MutableComponent seasonCombined = CurrentSeason.getInstance(mc).getHudText();
    DrawUtilSeason.drawLabel(graphics, seasonCombined, labelX, labelY, labelPosition.getHoriAlign(),
                             labelPosition.getVertAlign(), labelSpec.background.getColor(),
                             mapProperties.infoSlotAlpha.get(), labelSpec.foreground.getColor(),
                             labelSpec.foreground.alpha, (double) mapProperties.fontScale.get(), labelSpec.shadow,
                             0.0F);

    if (CurrentFertility.getInstance(mc).shouldDrawNewLine()) {
      int singleLabelHeight = (int) ((double) (DrawUtil.getLabelHeight(mc.font, labelSpec.shadow) + labelSpec.margin)
          * mapProperties.fontScale.get());
      labelY += singleLabelHeight;

      MutableComponent fertility = CurrentFertility.getInstance(mc).getMinimapText();
      DrawUtilSeason.drawLabel(graphics, fertility, labelX, labelY, labelPosition.getHoriAlign(),
                               labelPosition.getVertAlign(), labelSpec.background.getColor(),
                               mapProperties.infoSlotAlpha.get(), labelSpec.foreground.getColor(),
                               labelSpec.foreground.alpha, (double) mapProperties.fontScale.get(), labelSpec.shadow,
                               0.0F);
    }

    RenderWrapper.disableBlend();
    DrawUtil.sizeDisplay(graphics, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
  }
}
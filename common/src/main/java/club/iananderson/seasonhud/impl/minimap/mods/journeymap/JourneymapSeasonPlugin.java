package club.iananderson.seasonhud.impl.minimap.mods.journeymap;

import club.iananderson.seasonhud.Common;
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
import journeymap.client.ui.minimap.Shape;
import journeymap.client.ui.theme.Theme;
import journeymap.client.ui.theme.Theme.Minimap;
import journeymap.client.ui.theme.ThemeLabelSource.InfoSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
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
}
package club.iananderson.seasonhud.impl.minimap.mods.journeymap;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.season.CurrentFertility;
import club.iananderson.seasonhud.impl.season.CurrentSeason;
import journeymap.api.client.impl.ClientAPI;
import journeymap.api.v2.client.IClientAPI;
import journeymap.api.v2.client.IClientPlugin;
import journeymap.api.v2.client.JourneyMapPlugin;
import journeymap.api.v2.client.event.InfoSlotDisplayEvent;
import journeymap.api.v2.client.event.RegistryEvent.InfoSlotRegistryEvent;
import journeymap.api.v2.client.event.RegistryEvent.OptionsRegistryEvent;
import journeymap.api.v2.common.event.ClientEventRegistry;
import journeymap.api.v2.common.event.MinimapEventRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

@JourneyMapPlugin(apiVersion = "2.0.0")
public class JourneymapSeasonPlugin implements IClientPlugin {
  private static JourneymapSeasonPlugin INSTANCE;
  private final String seasonKeyString = "xaerominimap.seasonhud.infodisplay.season";
  private final String fertilityKeyString = "xaerominimap.seasonhud.infodisplay.fertility";
  private final Component seasonKey = Common.translatedText(seasonKeyString);
  private final Component fertilityKey = Common.translatedText(fertilityKeyString);
  private ClientAPI api;
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

    this.api = (ClientAPI) api;
    this.mc = Minecraft.getInstance();

    ClientEventRegistry.INFO_SLOT_REGISTRY_EVENT.subscribe(this.getModId(), this::infoSlotRegistryEvent);
    ClientEventRegistry.OPTIONS_REGISTRY_EVENT.subscribe(this.getModId(), this::optionsRegistryEvent);
    MinimapEventRegistry.INFO_SLOT_DISPLAY_EVENT.subscribe(this.getModId(), this::infoSlotDisplayEvent);

    Common.LOG.info("Initialized JourneyMapAPI");
  }

  @Override
  public String getModId() {
    return Common.MOD_ID;
  }

  private void infoSlotRegistryEvent(InfoSlotRegistryEvent event) {
    event.register(Common.MOD_ID, seasonKey, 1000L, () -> CurrentSeason.getInstance(mc).getHudText());
    event.register(Common.MOD_ID, fertilityKey, 1000L, () -> CurrentFertility.getInstance(mc).getMinimapText());
  }

  private void optionsRegistryEvent(OptionsRegistryEvent optionsRegistryEvent) {
    this.clientProperties = new ClientProperties();
  }

  private void infoSlotDisplayEvent(InfoSlotDisplayEvent event) {
    if (clientProperties.addAdditional.get()) {
      event.addLast(seasonKeyString, clientProperties.position.get());
      if (SeasonHudClient.getShowFertility()) {
        event.addLast(fertilityKeyString, clientProperties.position.get());
      }
    }
  }
}
package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.platform.services.AccessoryHelper;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class FabricAccessoryHelper implements AccessoryHelper {
  @Override
  public boolean curiosEquipped(Player player, Item item) {
    return false;
  }

  @Override
  public boolean trinketEquipped(Player player, Item item) {
    Optional<TrinketComponent> trinketInventory = TrinketsApi.getTrinketComponent(player);

    return trinketInventory.map(trinketComponent -> trinketComponent.isEquipped(item)).orElse(false);
  }
}

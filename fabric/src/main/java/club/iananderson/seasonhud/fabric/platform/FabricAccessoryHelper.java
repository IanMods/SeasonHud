package club.iananderson.seasonhud.fabric.platform;

import club.iananderson.seasonhud.platform.services.AccessoryHelper;
import dev.emi.trinkets.api.TrinketsApi;
import java.util.Collections;
import java.util.Set;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class FabricAccessoryHelper implements AccessoryHelper {
  @Override
  public boolean curiosEquipped(Player player, Item item) {
    return false;
  }

  @Override
  public boolean trinketEquipped(Player player, Item item) {
    Container trinketInventory = TrinketsApi.getTrinketComponent(player).getInventory();

    Set<Item> curioSet = Collections.singleton(item);

    return trinketInventory.hasAnyOf(curioSet);
  }
}

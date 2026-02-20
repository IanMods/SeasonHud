package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.platform.services.AccessoryHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class ForgeAccessoryHelper implements AccessoryHelper {
  @Override
  public boolean curiosEquipped(Player player, Item item) {
    // LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);
    //
    // return curiosInventory.map(inv -> inv.isEquipped(item)).orElse(false);
    return false;
  }

  @Override
  public boolean trinketEquipped(Player player, Item item) {
    return false;
  }
}

package club.iananderson.seasonhud.neoforge.platform;

import club.iananderson.seasonhud.platform.services.AccessoryHelper;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class NeoForgeAccessoryHelper implements AccessoryHelper {
  @Override
  public boolean curiosEquipped(Player player, Item item) {
    Optional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);

    return curiosInventory.map(inv -> inv.isEquipped(item)).orElse(false);
  }

  @Override
  public boolean trinketEquipped(Player player, Item item) {
    return false;
  }
}

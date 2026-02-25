package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.platform.services.AccessoryHelper;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class ForgeAccessoryHelper implements AccessoryHelper {
  @Override
  public boolean curiosEquipped(Player player, Item item) {
    List<SlotResult> curiosInventory = CuriosApi.getCuriosHelper().findCurios(player, item);

    return !curiosInventory.isEmpty();
  }

  @Override
  public boolean trinketEquipped(Player player, Item item) {
    return false;
  }
}

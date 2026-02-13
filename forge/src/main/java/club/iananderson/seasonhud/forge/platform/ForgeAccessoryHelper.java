package club.iananderson.seasonhud.forge.platform;

import club.iananderson.seasonhud.platform.services.AccessoryHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class ForgeAccessoryHelper implements AccessoryHelper {
  @Override
  public boolean curiosEquiped(Player player, Item item) {
    LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(player);

    return curiosInventory.map(inv -> inv.isEquipped(item)).orElse(false);
  }

  @Override
  public boolean trinketEquiped(Player player, Item item) {
    return false;
  }
}

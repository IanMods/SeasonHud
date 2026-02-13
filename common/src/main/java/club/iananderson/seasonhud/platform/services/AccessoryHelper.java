package club.iananderson.seasonhud.platform.services;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public interface AccessoryHelper {
  boolean curiosEquipped(Player player, Item item);

  boolean trinketEquipped(Player player, Item item);
}

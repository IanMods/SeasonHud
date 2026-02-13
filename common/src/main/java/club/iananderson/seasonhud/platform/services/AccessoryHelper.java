package club.iananderson.seasonhud.platform.services;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public interface AccessoryHelper {
  boolean curiosEquiped(Player player, Item item);

  boolean trinketEquiped(Player player, Item item);
}

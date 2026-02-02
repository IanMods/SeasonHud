package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.platform.Services;
import io.wispforest.accessories.api.AccessoriesCapability;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class Calendar {
  private Calendar() {
  }

  /**
   * Determines if the player has a calendar in an accessory mod slot.
   *
   * @param player The player whose Curios/Trinket inventory will be searched.
   * @param item   The item that is being searched for.
   * @return true if the player has a calendar in one of their accessory mod slots
   */
  public static boolean findCuriosCalendar(Player player, Item item) {
    Minecraft mc = Minecraft.getInstance();
    boolean curioEquipped = false;

    if (mc.level == null || mc.player == null || item == null) {
      return false;
    }

    if (Common.curiosLoaded() && !Common.accessoriesLoaded()) {
      curioEquipped = Services.ACCESSORY.curiosEquiped(player, item);
    }

    if (Common.trinketsLoaded() && !Common.accessoriesLoaded()) {
      curioEquipped = Services.ACCESSORY.trinketEquiped(player, item);
    } else if (Common.accessoriesLoaded()) {
      Optional<AccessoriesCapability> accessoriesInventory = AccessoriesCapability.getOptionally(player);
      if (accessoriesInventory.isPresent()) {
        curioEquipped = !accessoriesInventory.get().getEquipped(item).isEmpty();
      }
    }
    return curioEquipped;
  }

  private static boolean findCalendar(Player player, Item item) {
    boolean invCalendarFound = player.getInventory().contains(item.getDefaultInstance());
    boolean curiosCalendarFound = Calendar.findCuriosCalendar(player, item);

    return invCalendarFound | curiosCalendarFound;
  }

  private static boolean calendarFound() {
    Minecraft mc = Minecraft.getInstance();
    Item calendar = CommonSeasonHelper.commonSeasons.calendar();

    if (!Common.hasCalendarLoaded()) {
      return true;
    }

    if (mc.level == null || mc.player == null || calendar == null) {
      return false;
    }

    return findCalendar(mc.player, calendar);
  }

  public static boolean validNeedCalendar() {
    return (SeasonHudServer.getNeedCalendar() && Calendar.calendarFound()) || !SeasonHudServer.getNeedCalendar();
  }

  public static boolean validDetailedMode() {
    return (SeasonHudServer.getCalendarDetailMode() && Calendar.calendarFound())
        || !SeasonHudServer.getCalendarDetailMode();
  }
}
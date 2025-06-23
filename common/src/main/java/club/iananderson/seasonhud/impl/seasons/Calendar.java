package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.Config;
import club.iananderson.seasonhud.platform.Services;
import dev.emi.trinkets.api.TrinketsApi;
import java.util.Collections;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class Calendar {
  private Calendar() {
  }

  /**
   * @param player The player whose Curios/Trinket inventory will be searched.
   * @param item   The item that is being searched for.
   * @return The int for the Curios/Trinket inventory location
   */
  public static boolean findCuriosCalendar(Player player, Item item) {
    Minecraft mc = Minecraft.getInstance();
    boolean curioEquipped = false;

    if (mc.level == null || mc.player == null || item == null) {
      return false;
    }

    Set<Item> curioSet = Collections.singleton(item);

    if (Common.curiosLoaded() && !Common.accessoriesLoaded()) {
      curioEquipped = Services.PLATFORM.curiosFound(player, item);
    }

    if (Common.trinketsLoaded() && !Common.accessoriesLoaded()) {
      Container trinketInventory = TrinketsApi.getTrinketComponent(player).getInventory();

      if (!trinketInventory.isEmpty()) {
        curioEquipped = trinketInventory.hasAnyOf(curioSet);
      }
    }

    return curioEquipped;
  }

  private static boolean findCalendar(Player player, Item item) {
    boolean invCalenderFound = player.inventory.contains(item.getDefaultInstance());
    boolean curiosCalenderFound = Calendar.findCuriosCalendar(player, item);

    return invCalenderFound | curiosCalenderFound;
  }

  private static boolean calendarFound() {
    Minecraft mc = Minecraft.getInstance();
    Item calendar = CommonSeasonHelper.commonSeasons.CALENDAR();

    if (!Common.hasCalendarLoaded()) {
      return true;
    }

    if (mc.level == null || mc.player == null || calendar == null) {
      return false;
    }

    return findCalendar(mc.player, calendar);
  }

  public static boolean validNeedCalendar() {
    return (Config.getNeedCalendar() && Calendar.calendarFound()) || !Config.getNeedCalendar();
  }

  public static boolean validDetailedMode() {
    return (Config.getCalendarDetailMode() && Calendar.calendarFound()) || !Config.getCalendarDetailMode();
  }
}
package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudServer;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import io.wispforest.accessories.api.AccessoriesCapability;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

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

    if (Common.curiosLoaded() && !Common.accessoriesLoaded()) {
      List<SlotResult> curiosInventory = CuriosApi.getCuriosHelper().findCurios(player, item);
      curioEquipped = !curiosInventory.isEmpty();
    }

    if (Common.trinketsLoaded() && !Common.accessoriesLoaded()) {
      Optional<TrinketComponent> trinketInventory = TrinketsApi.getTrinketComponent(player);

      if (trinketInventory.isPresent()) {
        curioEquipped = trinketInventory.get().isEquipped(item);
      }
    }

    else if (Common.accessoriesLoaded()) {
      Optional<AccessoriesCapability> accessoriesInventory = AccessoriesCapability.getOptionally(player);
      if (accessoriesInventory.isPresent()) {
        curioEquipped = !accessoriesInventory.get().getEquipped(item).isEmpty();
      }
    }
    return curioEquipped;
  }

  private static boolean findCalendar(Player player, Item item) {
    boolean invCalenderFound = player.getInventory().contains(item.getDefaultInstance());
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
    return (SeasonHudServer.getNeedCalendar() && Calendar.calendarFound()) || !SeasonHudServer.getNeedCalendar();
  }

  public static boolean validDetailedMode() {
    return (SeasonHudServer.getCalendarDetailMode() && Calendar.calendarFound()) || !SeasonHudServer.getCalendarDetailMode();
  }
}
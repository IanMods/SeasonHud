package club.iananderson.seasonhud.impl.accessory.mods;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudServer;
import club.iananderson.seasonhud.impl.season.mods.CommonSeasonHelper;
import club.iananderson.seasonhud.impl.season.mods.SeasonModHelper;
import club.iananderson.seasonhud.platform.Services;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Calendar {
  private Calendar() {
  }

  public static Optional<Item> calendar() {
    SeasonModHelper helper = CommonSeasonHelper.commonSeasons.getHelper();

    if (helper == null) {
      return Optional.empty();
    }

    return helper.calendar();
  }

  public static ItemStack calendarStack() {
    if (Calendar.calendar().isEmpty()) {
      return null;
    }

    return Calendar.calendar().get().getDefaultInstance();
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
      curioEquipped = Services.ACCESSORY.curiosEquipped(player, item);
    }

    if (Common.trinketsLoaded() && !Common.accessoriesLoaded()) {
      curioEquipped = Services.ACCESSORY.trinketEquipped(player, item);
    }
    return curioEquipped;
  }

  private static boolean findCalendar(Player player) {
    if (player == null) {
      return false;
    }

    if (Calendar.calendar().isEmpty()) {
      return true;
    }

    boolean invCalendarFound = player.getInventory().contains(Objects.requireNonNull(Calendar.calendarStack()));
    boolean curiosCalendarFound = Calendar.findCuriosCalendar(player, Calendar.calendar().get());

    return invCalendarFound | curiosCalendarFound;
  }

  private static boolean calendarFound(Player player) {
    if (!Common.hasCalendarLoaded()) {
      return true;
    }

    return findCalendar(player);
  }

  public static boolean validNeedCalendar(Player player) {
    return (SeasonHudServer.getNeedCalendar() && Calendar.calendarFound(player)) || !SeasonHudServer.getNeedCalendar();
  }

  public static boolean validDetailedMode(Player player) {
    return (SeasonHudServer.getCalendarDetailMode() && Calendar.calendarFound(player))
        || !SeasonHudServer.getCalendarDetailMode();
  }
}
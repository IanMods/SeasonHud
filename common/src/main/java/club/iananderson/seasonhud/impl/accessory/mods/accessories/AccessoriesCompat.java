package club.iananderson.seasonhud.impl.accessory.mods.accessories;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.accessory.mods.accessories.item.AccessoriesCalendar;
import club.iananderson.seasonhud.platform.Services;

public class AccessoriesCompat {
  public AccessoriesCompat() {
  }

  public static void clientInit() {
    if (Common.accessoriesLoaded() && Common.hasCalendarLoaded()) {
      Common.LOG.info("Talking to Accessories Client");
      AccessoriesCalendar.clientInit();
    }
  }

  public static void init() {
    if (Common.accessoriesLoaded() && Common.hasCalendarLoaded()) {
      Common.LOG.info("Talking to Accessories {}", Services.PLATFORM.getModVersion("accessories"));
      AccessoriesCalendar.init();
    }
  }
}
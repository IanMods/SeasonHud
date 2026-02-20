package club.iananderson.seasonhud.neoforge.impl.curios;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.neoforge.impl.curios.item.CuriosCalendar;

public class CuriosCompat {
  public CuriosCompat() {
  }

  public static void init() {
    if (Common.curiosLoaded() && Common.hasCalendarLoaded()) {
      CuriosCalendar.init();
    }
  }
}

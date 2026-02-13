package club.iananderson.seasonhud.forge.impl.accessory.mods.curios.item;

import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CuriosCalendar implements ICurioItem {
  public CuriosCalendar() {
  }

  public static void init() {
    if (Calendar.calendar().isPresent()) {
      CuriosApi.registerCurio(Calendar.calendar().get(), new CuriosCalendar());
    }
  }
}
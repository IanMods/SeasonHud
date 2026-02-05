package club.iananderson.seasonhud.forge.impl.accessory.mods.curios.item;

import club.iananderson.seasonhud.impl.season.mods.CommonSeasonHelper;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CuriosCalendar implements ICurioItem {
  public CuriosCalendar() {
  }

  private static final Item calendar = CommonSeasonHelper.commonSeasons.getHelper().calendar();

  public static void init() {
    if (calendar != null) {
      CuriosApi.registerCurio(calendar, new CuriosCalendar());
    }
  }
}
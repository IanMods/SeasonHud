package club.iananderson.seasonhud.impl.accessories;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.seasons.CommonSeasonHelper;
import club.iananderson.seasonhud.platform.Services;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.slot.SlotBasedPredicate;
import io.wispforest.accessories.api.slot.SlotPredicateRegistry;
import io.wispforest.accessories.api.slot.SlotTypeReference;
import io.wispforest.accessories.api.slot.UniqueSlotHandling;
import io.wispforest.accessories.api.slot.UniqueSlotHandling.UniqueSlotBuilderFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class CalendarSlot implements UniqueSlotHandling.RegistrationCallback {
  public static final CalendarSlot INSTANCE = new CalendarSlot();
  private static SlotTypeReference calendarSlotGetter;
  private final ResourceLocation slotPredicate = Common.location("calendar_slot_equipment");

  private CalendarSlot() {
  }

  @Nullable
  public static SlotTypeReference calendarSlotRef() {
    return calendarSlotGetter;
  }

  public void init() {
    UniqueSlotHandling.EVENT.register(this);

    Item calendar = CommonSeasonHelper.commonSeasons.calendar();

    if (Services.PLATFORM.getModVersion("accessories").startsWith("1.1.0") || Services.PLATFORM.getModVersion(
        "accessories").startsWith("1.0.0")) {
      AccessoriesAPI.registerPredicate(slotPredicate, SlotBasedPredicate.ofItem(item -> item.equals(calendar)));
    }
    else {
      SlotPredicateRegistry.register(slotPredicate, SlotBasedPredicate.ofItem(item -> item.equals(calendar)));
    }
  }

  @Override
  public void registerSlots(UniqueSlotBuilderFactory factory) {
    calendarSlotGetter = factory.create(Common.location("calendarslot"), 1).slotPredicates(slotPredicate)
        .validTypes(EntityType.PLAYER)
        .build();
  }
}

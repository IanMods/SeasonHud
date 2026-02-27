package club.iananderson.seasonhud.forge.impl.accessory.mods.curios.item;

import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jspecify.annotations.NonNull;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CuriosCalendar implements ICurioItem {
  public CuriosCalendar() {
  }

  public static ICapabilityProvider initCapabilities() {
    ICurio curio = new ICurio() {
      final ItemStack stack = Calendar.calendarStack();

      @Override
      public ItemStack getStack() {
        return stack;
      }

    };
    return new ICapabilityProvider() {
      private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

      @NonNull
      @Override
      public <T> LazyOptional<T> getCapability(@NonNull Capability<T> cap, @NonNull Direction side) {

        return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
      }
    };
  }
}
package club.iananderson.seasonhud.impl.accessory.mods.accessories.item;

import club.iananderson.seasonhud.impl.season.mods.CommonSeasonHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.client.SimpleAccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AccessoriesCalendar implements Accessory {
  private static final Item calender = CommonSeasonHelper.commonSeasons.getHelper().calendar();

  public AccessoriesCalendar() {
  }

  // TODO: Double check this is still working
  public static void clientInit() {
    if (calender != null) {
      AccessoriesRendererRegistry.registerRenderer(calender, Renderer::new);
    }
  }

  public static void init() {
    if (calender != null) {
      AccessoriesAPI.registerAccessory(calender, new AccessoriesCalendar());
    }
  }

  public static class Renderer implements SimpleAccessoryRenderer {

    @Override
    public <M extends LivingEntity> void align(ItemStack stack, SlotReference reference, EntityModel<M> model,
        PoseStack matrices) {
      if (!(model instanceof HumanoidModel<? extends LivingEntity> humanoidModel)) {
        return;
      }

      matrices.scale(0.4F, 0.4F, 0.4F);
      AccessoryRenderer.transformToModelPart(matrices, humanoidModel.body, 0.75, -1, null);
      matrices.translate(-0.25F, -1.75F, -0.72F);
    }
  }
}
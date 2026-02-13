package club.iananderson.seasonhud.impl.accessory.mods.accessories.item;

import club.iananderson.seasonhud.impl.accessory.mods.Calendar;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;

public class AccessoriesCalendar implements Accessory {
  public AccessoriesCalendar() {
  }

  // TODO: Double check this is still working
  public static void clientInit() {
//    if (Calendar.calendar().isPresent()) {
//      AccessoriesRendererRegistry.registerRenderer(Calendar.calendar().get(), Renderer::new);
//    }
  }

  public static void init() {
    if (Calendar.calendar().isPresent()) {
      AccessoriesAPI.registerAccessory(Calendar.calendar().get(), new AccessoriesCalendar());
    }
  }

//  public static class Renderer implements SimpleAccessoryRenderer {
//
//    @Override
//    public <M extends LivingEntityRenderState> void align(ItemStack stack, SlotReference reference,
//        EntityModel<M> model, M renderState, PoseStack matrices) {
//      if (!(model instanceof HumanoidModel<? extends HumanoidRenderState> humanoidModel)) {
//        return;
//      }
//
//      matrices.scale(0.4F, 0.4F, 0.4F);
//      AccessoryRenderer.transformToModelPart(matrices, humanoidModel.body, 0.75, -1, null);
//      matrices.translate(-0.25F, -1.75F, -0.72F);
//    }
//  }
}
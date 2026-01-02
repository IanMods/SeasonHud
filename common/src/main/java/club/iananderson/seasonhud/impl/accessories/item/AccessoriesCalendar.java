package club.iananderson.seasonhud.impl.accessories.item;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.seasons.CommonSeasonHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.renderers.AccessoryRenderer;
import io.wispforest.accessories.api.client.renderers.SimpleAccessoryRenderer;
import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotPath;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AccessoriesCalendar implements Accessory {
  public AccessoriesCalendar() {
  }

  public static void clientInit() {
    Item calendar = CommonSeasonHelper.commonSeasons.calendar();

    AccessoriesRendererRegistry.bindItemToRenderer(calendar, Common.location("calendar_renderer"), Renderer::new);
  }

  public static void init() {
    AccessoryRegistry.register(CommonSeasonHelper.commonSeasons.calendar(), new AccessoriesCalendar());
  }

  public static class Renderer implements SimpleAccessoryRenderer {

    @Override
    public <S extends LivingEntityRenderState> void align(ItemStack stack, SlotPath path, EntityModel<S> model,
        S renderState, PoseStack matrices) {
      if (!(model instanceof HumanoidModel<? extends HumanoidRenderState> humanoidModel)) {
        return;
      }

      matrices.scale(0.4F, 0.4F, 0.4F);
      AccessoryRenderer.transformToModelPart(matrices, humanoidModel.body, 0.75, -1, null);
      matrices.translate(-0.25F, -1.75F, -0.72F);
    }
  }
}
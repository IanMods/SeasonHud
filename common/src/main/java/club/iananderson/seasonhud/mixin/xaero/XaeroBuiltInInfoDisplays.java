package club.iananderson.seasonhud.mixin.xaero;

import club.iananderson.seasonhud.impl.minimap.mods.xaero.XaeroInfoDisplays;
import java.util.List;
import java.util.Objects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.hud.minimap.info.BuiltInInfoDisplays;
import xaero.hud.minimap.info.InfoDisplay;

@Mixin(BuiltInInfoDisplays.class)
public class XaeroBuiltInInfoDisplays {
  @Shadow
  private static List<InfoDisplay<?>> ALL;

  @Inject(method = "<clinit>", at = @At("TAIL"), remap = false)

  private static void injected(CallbackInfo ci) {
    Objects.requireNonNull(ALL);
    XaeroInfoDisplays.SEASON = XaeroInfoDisplays.SEASON_INFO_BUILDER.setDestination(ALL::add)
        .build();
  }
}
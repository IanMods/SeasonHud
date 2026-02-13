package club.iananderson.seasonhud.impl.season;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.impl.season.components.Fertility;
import club.iananderson.seasonhud.impl.season.mods.CommonSeasonHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;

public class CurrentFertility {
  private final Style fertilityFormat;
  private final Fertility currentFertility;

  public CurrentFertility(Minecraft mc) {
    Player player = mc.player;
    this.currentFertility = CommonSeasonHelper.commonSeasons.getHelper().fertility(player);
    this.fertilityFormat = currentFertility.getStyle();
  }

  public static CurrentFertility getInstance(Minecraft mc) {
    return new CurrentFertility(mc);
  }

  public MutableComponent getHudText() {
    MutableComponent iconSpace = Common.literalText("   ");
    MutableComponent fertilityText = Common.translatedText(currentFertility.getKey());

    return iconSpace.append(fertilityText).withStyle(fertilityFormat);
  }

  public MutableComponent getHudTextNoFormat() {
    return Common.translatedText(currentFertility.getKey());
  }

  public MutableComponent getMinimapText() {
    return Common.translatedText(currentFertility.getKey()).withStyle(fertilityFormat);
  }

}

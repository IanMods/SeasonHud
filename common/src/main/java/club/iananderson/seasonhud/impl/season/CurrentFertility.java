package club.iananderson.seasonhud.impl.season;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.impl.season.components.Fertility;
import club.iananderson.seasonhud.impl.season.mods.CommonSeasonHelper;
import club.iananderson.seasonhud.impl.season.mods.SeasonMods;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;

public class CurrentFertility {
  private final Style fertilityFormat;
  private final Fertility currentFertility;
  private final Player player;

  public CurrentFertility(Minecraft mc) {
    this.player = mc.player;
    this.currentFertility = CommonSeasonHelper.commonSeasons.getHelper().fertility(player);
    this.fertilityFormat = currentFertility.getStyle();
  }

  public static CurrentFertility getInstance(Minecraft mc) {
    return new CurrentFertility(mc);
  }

  public MutableComponent getHudTextNoFormat() {
    MutableComponent iconSpace = Common.literalText("   ");
    MutableComponent fertilityText = Common.translatedText(currentFertility.getKey());

    if (SeasonHudClient.getFertilityReplacesSeason()) {
      iconSpace = Common.literalText("");
    }

    return iconSpace.append(fertilityText);
  }

  public MutableComponent getHudText() {
    return getHudTextNoFormat().withStyle(fertilityFormat);
  }

  public MutableComponent getMinimapText() {
    return Common.translatedText(currentFertility.getKey()).withStyle(fertilityFormat);
  }

  public boolean shouldDrawNewLine() {
    if (Common.sereneSeasonsLoaded() && SeasonHudClient.getShowFertility()) {
      boolean defaultFertility = SeasonMods.SERENE.getSeasonModHelper().fertility(player) == Fertility.FERTILE;

      return !defaultFertility && !SeasonHudClient.getFertilityReplacesSeason();
    } else {
      return false;
    }
  }

  public boolean shouldOverwriteSeason() {
    if (Common.sereneSeasonsLoaded() && SeasonHudClient.getShowFertility()) {
      boolean defaultFertility = SeasonMods.SERENE.getSeasonModHelper().fertility(player) == Fertility.FERTILE;

      return !defaultFertility && SeasonHudClient.getFertilityReplacesSeason();
    } else {
      return false;
    }
  }
}
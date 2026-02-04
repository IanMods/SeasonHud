package club.iananderson.seasonhud.impl.seasons;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.config.DefaultValues.Client;
import club.iananderson.seasonhud.config.SeasonHudClient;
import club.iananderson.seasonhud.util.Rgb;
import java.util.EnumSet;
import java.util.Map;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.network.chat.Component;

public enum SubSeasons {

  EARLY(0, "EARLY_", ".early"),

  MID(1, "MID_", ".mid"),

  LATE(2, "LATE_", ".late"),

  NONE(100, "", "");

  public static final EnumSet<SubSeasons> SUB_SEASONS_ENUM_LIST = EnumSet.allOf(SubSeasons.class);
  private final int id;
  private final String prefix;
  private final String subSeasonKey;

  SubSeasons(int id, String prefix, String subSeasonKey) {
    this.id = id;
    this.prefix = prefix;
    this.subSeasonKey = subSeasonKey;
  }

  public int getId() {
    return this.id;
  }

  public String getSubSeasonKey() {
    return this.subSeasonKey;
  }
}
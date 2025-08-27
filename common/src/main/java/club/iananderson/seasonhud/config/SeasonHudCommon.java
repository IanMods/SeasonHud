package club.iananderson.seasonhud.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class SeasonHudCommon {
  public static final ModConfigSpec GENERAL_SPEC;
  static ModConfigSpec.BooleanValue needCalendar;
  static ModConfigSpec.ConfigValue<Integer> dayLength;

  static {
    ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
    setupConfig(builder);
    GENERAL_SPEC = builder.build();
  }

  private SeasonHudCommon() {
  }

  private static void setupConfig(ModConfigSpec.Builder builder) {
    builder.push("Season");
    needCalendar = builder.comment(
        "Require the calendar item to be in the players inventory to show the HUD?\n" + "(true/false)\n"
            + "Default is false.").define("need_calendar", false);

    dayLength = builder.comment(
        "Change if you are using a Minecraft day length other than vanilla value and using Fabric Seasons.\n"
            + "Default Minecraft day is 24000.").defineInRange("day_length", 24000, 0, 2147483647);
    builder.pop();
  }

  //Season
  public static boolean getNeedCalendar() {
    return needCalendar.get();
  }

  public static void setNeedCalendar(boolean enable) {
    needCalendar.set(enable);
  }

  public static int getDayLength() {
    return dayLength.get();
  }

  public static void setDayLength(int length) {
    dayLength.set(length);
  }
}

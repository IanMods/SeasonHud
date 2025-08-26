package club.iananderson.seasonhud.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class SeasonHudServer {
  public static final ForgeConfigSpec SERVER_SPEC;
  static ForgeConfigSpec.ConfigValue<Boolean> needCalendar;
  static ForgeConfigSpec.ConfigValue<Integer> dayLength;
  static ForgeConfigSpec.ConfigValue<Boolean> calenderDetailMode;

  static {
    ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    setupConfig(builder);
    SERVER_SPEC = builder.build();
  }

  private SeasonHudServer() {
  }

  private static void setupConfig(ForgeConfigSpec.Builder builder) {
    builder.push("Season");
    needCalendar = builder.comment(
        "Require the calendar item to be in the players inventory to show the HUD?\n" + "(true/false)\n"
            + "Default is false.").define("need_calendar", false);

    calenderDetailMode = builder.comment(
            "Having the calendar item shows the detailed version of the HUD" + "Default is false.")
        .define("calendar_detail", false);

    dayLength = builder.comment(
        "Change if you are using a Minecraft day length other than vanilla value and using Fabric Seasons.\n"
            + "Default Minecraft day is 24000.").defineInRange("day_length", 24000, 0, 2147483647);
    builder.pop();
  }

  private static <T> T getOrDefault(ConfigValue<T> config) {
    if (SERVER_SPEC.isLoaded()) {
      return config.get();
    }
    else {
      return config.getDefault();
    }
  }

  //Season
  public static boolean getNeedCalendar() {
    return getOrDefault(needCalendar);
  }

  public static void setNeedCalendar(boolean enable) {
    needCalendar.set(enable);
  }

  public static boolean getCalendarDetailMode() {
    return getOrDefault(calenderDetailMode);
  }

  public static void setCalendarDetailMode(boolean enable) {
    calenderDetailMode.set(enable);
  }

  public static int getDayLength() {
    return getOrDefault(dayLength);
  }

  public static void setDayLength(int length) {
    dayLength.set(length);
  }

}

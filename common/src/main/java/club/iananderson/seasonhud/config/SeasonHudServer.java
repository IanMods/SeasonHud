package club.iananderson.seasonhud.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SeasonHudServer {
  public static final ForgeConfigSpec GENERAL_SPEC;
  static ForgeConfigSpec.BooleanValue needCalendar;
  static ForgeConfigSpec.ConfigValue<Integer> dayLength;
  static ForgeConfigSpec.BooleanValue calenderDetailMode;

  static {
    ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    setupConfig(builder);
    GENERAL_SPEC = builder.build();
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

  public static boolean getCalendarDetailMode() {
    return calenderDetailMode.get();
  }

  public static void setCalendarDetailMode(boolean enable) {
    calenderDetailMode.set(enable);
  }
}

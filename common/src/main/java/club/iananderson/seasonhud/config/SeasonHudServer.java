package club.iananderson.seasonhud.config;

import club.iananderson.seasonhud.config.DefaultValues.Server;
import club.iananderson.seasonhud.util.StringLine;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class SeasonHudServer {
  public static final ModConfigSpec SERVER_SPEC;
  static ConfigValue<Boolean> needCalendar;
  static ConfigValue<Boolean> calendarDetailMode;
  static ConfigValue<Integer> dayLength;
  static ConfigValue<Integer> subSeasonLength;

  static {
    ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
    setupConfig(builder);
    SERVER_SPEC = builder.build();
  }

  private SeasonHudServer() {
  }

  private static void setupConfig(ModConfigSpec.Builder builder) {
    builder.push("Season");
    needCalendar = builder.comment(StringLine.builder()
                                       .addLine(
                                           "Require the calendar item to be in the players inventory to show the HUD?")
                                       .addLine("(true/false)")
                                       .lastLine("Default is " + Server.DEFAULT_NEED_CALENDAR + "."))
        .define("need_calendar", Server.DEFAULT_NEED_CALENDAR);

    calendarDetailMode = builder.comment(StringLine.builder()
                                             .addLine("Having the calendar item shows the detailed version of the HUD")
                                             .lastLine("Default is " + Server.DEFAULT_CALENDAR_DETAIL_MODE + "."))
        .define("calendar_detail", Server.DEFAULT_CALENDAR_DETAIL_MODE);

    dayLength = builder.comment(StringLine.builder()
                                    .addLine("Change if you are using a Minecraft day length other than vanilla ")
                                    .addLine("value and are using Fabric Seasons.")
                                    .lastLine("Default Minecraft day is " + Server.DEFAULT_DAY_LENGTH + "."))
        .defineInRange("day_length", Server.DEFAULT_DAY_LENGTH, 0, 2147483647);
    subSeasonLength = builder.comment(StringLine.builder()
                                          .addLine("Change if you are hosting a server and changed the default")
                                          .addLine("sub-season length in the Serene Seasons config.")
                                          .lastLine("Default length is " + Server.DEFAULT_SUB_SEASON_LENGTH + " days."))
        .defineInRange("sub_season_length", Server.DEFAULT_SUB_SEASON_LENGTH, 0, 2147483647);
    builder.pop();
  }

  private static <T> T getOrDefault(ConfigValue<T> config) {
    if (SERVER_SPEC.isLoaded()) {
      return config.get();
    } else {
      return config.getDefault();
    }
  }

  // Season
  public static boolean getNeedCalendar() {
    return getOrDefault(needCalendar);
  }

  public static void setNeedCalendar(boolean enable) {
    needCalendar.set(enable);
  }

  public static boolean getCalendarDetailMode() {
    return getOrDefault(calendarDetailMode);
  }

  public static void setCalendarDetailMode(boolean enable) {
    calendarDetailMode.set(enable);
  }

  public static int getDayLength() {
    return getOrDefault(dayLength);
  }

  public static void setDayLength(int length) {
    dayLength.set(length);
  }

  public static int getSubSeasonLength() {
    return getOrDefault(subSeasonLength);
  }

  public static void setSubSeasonLength(int length) {
    subSeasonLength.set(length);
  }
}

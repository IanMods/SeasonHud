package club.iananderson.seasonhud.config;

import club.iananderson.seasonhud.config.DefaultValues.Server;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class SeasonHudServer {
  public static final ForgeConfigSpec SERVER_SPEC;
  static ForgeConfigSpec.ConfigValue<Boolean> needCalendar;
  static ForgeConfigSpec.ConfigValue<Integer> dayLength;
  static ForgeConfigSpec.ConfigValue<Boolean> calendarDetailMode;

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
        "Require the calendar item to be in the players inventory to show the HUD?\n" + "(true/false)\n" + "Default is "
            + Server.DEFAULT_NEED_CALENDAR + ".").define("need_calendar", Server.DEFAULT_NEED_CALENDAR);

    calendarDetailMode = builder.comment(
        "Having the calendar item shows the detailed version of the HUD" + "Default is "
            + Server.DEFAULT_CALENDAR_DETAIL_MODE + ".").define("calendar_detail", Server.DEFAULT_CALENDAR_DETAIL_MODE);

    dayLength = builder.comment(
            "Change if you are using a Minecraft day length other than vanilla value and using Fabric Seasons.\n"
                + "Default Minecraft day is " + Server.DEFAULT_DAY_LENGTH + ".")
        .defineInRange("day_length", Server.DEFAULT_DAY_LENGTH, 0, 2147483647);
    builder.pop();
  }

  private static Object getDefault(ConfigValue<?> config) {
    Map<List<String>, Object> configOptions = new HashMap<>();
    configOptions.put(needCalendar.getPath(), Server.DEFAULT_NEED_CALENDAR);
    configOptions.put(calendarDetailMode.getPath(), Server.DEFAULT_CALENDAR_DETAIL_MODE);
    configOptions.put(dayLength.getPath(), Server.DEFAULT_DAY_LENGTH);

    return configOptions.get(config.getPath());
  }

  private static Object getOrDefault(ConfigValue<?> config) {
    if (SERVER_SPEC.isLoaded()) {
      return config.get();
    } else {
      return getDefault(config);
    }
  }

  // Season
  public static boolean getNeedCalendar() {
    return (Boolean) getOrDefault(needCalendar);
  }

  public static void setNeedCalendar(boolean enable) {
    needCalendar.set(enable);
  }

  public static boolean getCalendarDetailMode() {
    return (Boolean) getOrDefault(calendarDetailMode);
  }

  public static void setCalendarDetailMode(boolean enable) {
    calendarDetailMode.set(enable);
  }

  public static int getDayLength() {
    return (Integer) getOrDefault(dayLength);
  }

  public static void setDayLength(int length) {
    dayLength.set(length);
  }

}

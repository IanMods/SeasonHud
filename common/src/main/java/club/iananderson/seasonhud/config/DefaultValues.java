package club.iananderson.seasonhud.config;

import club.iananderson.seasonhud.client.gui.Location;
import club.iananderson.seasonhud.client.gui.ShowDay;

public class DefaultValues {
  public static class Client {
    public static final boolean DEFAULT_ENABLE_MOD = true;
    public static final Location DEFAULT_HUD_LOCATION = Location.TOP_LEFT;
    public static final int DEFAULT_X_OFFSET = 2;
    public static final int DEFAULT_Y_OFFSET = 2;
    public static final double DEFAULT_HUD_SCALE = 1.0;
    public static final double HUD_SCALE_MIN = 0.5;
    public static final double HUD_SCALE_MAX = 10;
    public static final boolean DEFAULT_SEASON_NAME_COLOR = true;
    public static final int COLOR_MIN = 0;
    public static final int COLOR_MAX = 16777215;
    public static final int DEFAULT_SPRING_COLOR = 16753595;
    public static final int DEFAULT_SUMMER_COLOR = 16705834;
    public static final int DEFAULT_AUTUMN_COLOR = 12344871;
    public static final int DEFAULT_WINTER_COLOR = 14679292;
    public static final int DEFAULT_DRY_COLOR = 16745216;
    public static final int DEFAULT_WET_COLOR = 2068975;
    public static final boolean DEFAULT_SHOW_TROPICAL_SEASON = true;
    public static final boolean DEFAULT_SHOW_SUB_SEASON = true;
    public static final ShowDay DEFAULT_SHOW_DAY = ShowDay.SHOW_DAY;
    public static final boolean DEFAULT_SHOW_FERTILITY = false;
    public static final boolean DEFAULT_ENABLE_MINIMAP_INTEGRATION = true;
    public static final boolean DEFAULT_SHOW_DEFAULT_WHEN_MINIMAP_HIDDEN = false;
    public static final boolean DEFAULT_JOURNEYMAP_ABOVE_MAP = false;
    public static final boolean DEFAULT_JOURNEYMAP_MAC_OS = false;
  }

  public static class Server {
    public static final boolean DEFAULT_NEED_CALENDAR = false;
    public static final boolean DEFAULT_CALENDAR_DETAIL_MODE = false;
    public static final int DEFAULT_DAY_LENGTH = 24000;
  }
}

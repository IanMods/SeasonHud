package club.iananderson.seasonhud.platform;

import club.iananderson.seasonhud.Common;
import club.iananderson.seasonhud.platform.services.MinimapHelper;
import club.iananderson.seasonhud.platform.services.PlatformHelper;
import java.util.ServiceLoader;

public class Services {
  public static final PlatformHelper PLATFORM = load(PlatformHelper.class);
  public static final MinimapHelper MINIMAP = load(MinimapHelper.class);

  private Services() {
  }

  public static <T> T load(Class<T> clazz) {
    final T loadedService = ServiceLoader.load(clazz).findFirst()
        .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    Common.LOG.debug("Loaded {} for service {}", loadedService, clazz);
    return loadedService;
  }
}

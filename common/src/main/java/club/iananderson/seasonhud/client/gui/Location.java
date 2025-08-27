package club.iananderson.seasonhud.client.gui;

import club.iananderson.seasonhud.Common;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum Location {
  TOP_LEFT(0, "desc.seasonhud.location.topLeft"),

  TOP_CENTER(1, "desc.seasonhud.location.topCenter"),

  TOP_RIGHT(2, "desc.seasonhud.location.topRight"),

  BOTTOM_LEFT(3, "desc.seasonhud.location.bottomLeft"),

  BOTTOM_RIGHT(4, "desc.seasonhud.location.bottomRight"),

  CUSTOM(5, "desc.seasonhud.location.custom");

  private final String key;
  private final Component locationName;
  private final int idNum;

  Location(int id, String key) {
    this.idNum = id;
    this.key = key;
    this.locationName = Common.translatedText(key);
  }

  public int getId() {
    return this.idNum;
  }

  public String getLocation() {
    return this.key;
  }

  public Component getLocationName() {
    return this.locationName;
  }
}
package club.iananderson.seasonhud.client;

import club.iananderson.seasonhud.Common;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
  public static final Category SEASONHUD_CATEGORY = new Category(Common.location("main"));

  public static KeyMapping seasonhudOptionsKeyMapping = new KeyMapping("desc.seasonhud.keybind.options",
                                                                       InputConstants.Type.KEYSYM,
                                                                       GLFW.GLFW_KEY_H,
                                                                       SEASONHUD_CATEGORY);

  private KeyBindings() {
  }
}



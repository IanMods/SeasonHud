package club.iananderson.seasonhud.client.gui.components.buttons;

import club.iananderson.seasonhud.Common;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CheckButton extends AbstractButton {
  private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
  private static final int TEXT_COLOR = 14737632;
  private final float scale;
  private final OnPress onPress;
  private boolean selected;

  public CheckButton(int x, int y, int width, int height, Component text, float scale, OnPress onPress,
      boolean selected) {
    super(x, y, width, height, text);
    this.scale = scale;
    this.onPress = onPress;
    this.selected = selected;
  }

  public CheckButton(int x, int y, Component component, float scale, OnPress onPress, boolean selected) {
    this(x, y, (int) (20 * scale), (int) (20 * scale), component, scale, onPress, selected);
  }

  public void onPress() {
    this.selected = !this.selected;
    this.onPress.onPress(this);
  }

  public boolean selected() {
    return this.selected;
  }

  public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    narrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
    if (this.active) {
      if (this.isFocused()) {
        narrationElementOutput.add(NarratedElementType.USAGE,
                                   Common.translatedText("narration.checkbox.usage.focused"));
      }
      else {
        narrationElementOutput.add(NarratedElementType.USAGE,
                                   Common.translatedText("narration.checkbox.usage.hovered"));
      }
    }
  }

  @Override
  public void updateNarration(NarrationElementOutput narrationElementOutput) {

  }

  public interface OnPress {
    void onPress(CheckButton button);
  }
}
package com.vincentmet.customquests.screens.elements.buttons;

import com.vincentmet.customquests.lib.Ref;
import com.vincentmet.customquests.lib.Utils;
import com.vincentmet.customquests.screens.ScreenQuestingDevice;
import com.vincentmet.customquests.screens.SubScreensQuestingDevice;
import com.vincentmet.customquests.screens.elements.IQuestingGuiElement;
import com.vincentmet.customquests.screens.elements.labels.Label;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ButtonSettings implements IQuestingGuiElement {
    private Screen root;
    private static int x = 60;
    private static int y = 0;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    private Label label;

    public ButtonSettings(Screen root){
        this.root = root;
        this.label = new Label(root, "S", x + (WIDTH>>1), y + (HEIGHT>>1), 0xFFFFFF, true, true);
    }

    @Override
    public void update(PlayerEntity player, double mouseX, double mouseY, int width, int height) {
        this.label.update(player, mouseX, mouseY, 0, 0);
    }

    @Override
    public void render(PlayerEntity player, double mouseX, double mouseY) {
        if(Utils.isMouseInBounds(mouseX, mouseY, x, y, x+WIDTH, y+HEIGHT)){
            root.getMinecraft().getTextureManager().bindTexture(Ref.IMAGE_BUTTON_SQUARE_PRESSED);
        }else{
            root.getMinecraft().getTextureManager().bindTexture(Ref.IMAGE_BUTTON_SQUARE_UNPRESSED);
        }
        root.blit(x, y, 0, 0, WIDTH, HEIGHT);
        this.label.render(player, mouseX, mouseY);
    }

    @Override
    public void onClick(PlayerEntity player, double mouseX, double mouseY) {
        if(Utils.isMouseInBounds(mouseX, mouseY, x, y, x+WIDTH, y+HEIGHT)){
            ScreenQuestingDevice.setActiveScreen(SubScreensQuestingDevice.SETTINGS);
        }
        this.label.onClick(player, mouseX, mouseY);
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public boolean isVisible() {
        return true;
    }
}

package com.vincentmet.customquests.screens.elements;

import com.vincentmet.customquests.lib.Ref;
import com.vincentmet.customquests.lib.Utils;
import com.vincentmet.customquests.quests.Quest;
import com.vincentmet.customquests.quests.QuestLine;
import com.vincentmet.customquests.screens.ScreenQuestingDevice;

public class ButtonQuestline implements IQuestingGuiElement {
    public static final int WIDTH = 150;
    public static final int HEIGHT = 20;
    private int x;
    private int y;
    private QuestLine questLine;

    public ButtonQuestline(int posX, int posY, QuestLine questline){
        this.x = posX;
        this.y = posY;
        this.questLine = questline;
    }

    @Override
    public <T extends ScreenQuestingDevice> void render(T gui, double mouseX, double mouseY) {
        if(Utils.isMouseInBounds(mouseX, mouseY, x, y, x + WIDTH, y + HEIGHT)){
            gui.getMinecraft().getTextureManager().bindTexture(Ref.IMAGE_BUTTON_QUESTLINE_PRESSED);
        }else{
            gui.getMinecraft().getTextureManager().bindTexture(Ref.IMAGE_BUTTON_QUESTLINE_UNPRESSED);
        }//todo greyed out
        gui.blit(x, y, 0, 0, WIDTH, HEIGHT);


        new Label(
                x+(WIDTH/2)-Ref.FONT_RENDERER.getStringWidth(questLine.getTitle())/2,
                y+(HEIGHT/2)-Ref.FONT_RENDERER.FONT_HEIGHT/2,
                questLine.getTitle(),
                0xFFFFFF,
                false,
                false
        ).render(gui, mouseX, mouseY);
    }

    @Override
    public <T extends ScreenQuestingDevice> void onClick(T gui, double mouseX, double mouseY) {
        if(Utils.isMouseInBounds(mouseX, mouseY, x, y, x+WIDTH, y+HEIGHT)){
            ScreenQuestingDevice.activeQuestline = questLine.getId();
            ScreenQuestingDevice.activeQuest = -1;
        }
    }
}

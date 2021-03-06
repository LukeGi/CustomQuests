package com.vincentmet.customquests.screens.questingdeveicesubscreens;

import com.vincentmet.customquests.lib.Ref;
import com.vincentmet.customquests.quests.QuestLine;
import com.vincentmet.customquests.screens.elements.IQuestingGuiElement;
import com.vincentmet.customquests.screens.elements.buttons.ButtonQuestline;
import com.vincentmet.customquests.screens.elements.labels.Label;
import com.vincentmet.customquests.screens.questingdeveicesubscreens.questlines.QuestingWeb;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import java.util.ArrayList;
import java.util.List;

public class SubScreenQuestlines implements IQuestingGuiElement {
    private Screen root;
    private static final int MARGIN_QUESTLINE_BUTTONS_LEFT = 20;
    private static final int MARGIN_QUESTLINE_BUTTONS_TOP = 20;
    private static final int MARGIN_QUESTLINE_BUTTONS_RIGHT = 20;
    private int width = 0;
    private int height = 0;
    private List<ButtonQuestline> questlineButtons = new ArrayList<>();
    private Label title;
    private QuestingWeb questingWeb;

    public SubScreenQuestlines(Screen root){
        this.root = root;
        reloadQuestlines();
        this.questingWeb = new QuestingWeb(root);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public void update(PlayerEntity player, double mouseX, double mouseY, int width, int height) {
        this.width = width;
        this.height = height;
        reloadTitle();
        this.questlineButtons.forEach(buttonQuestline -> buttonQuestline.update(player, mouseX, mouseY, MARGIN_QUESTLINE_BUTTONS_LEFT + ButtonQuestline.WIDTH + MARGIN_QUESTLINE_BUTTONS_RIGHT, this.height));
        this.title.update(player, mouseX, mouseY, 0, 0);
        this.questingWeb.update(player, mouseX, mouseY, this.width - (MARGIN_QUESTLINE_BUTTONS_LEFT + ButtonQuestline.WIDTH + MARGIN_QUESTLINE_BUTTONS_RIGHT), this.height - MARGIN_QUESTLINE_BUTTONS_TOP);
    }

    @Override
    public void render(PlayerEntity player, double mouseX, double mouseY) {
        this.questlineButtons.forEach(buttonQuestline -> buttonQuestline.render(player, mouseX, mouseY));
        this.title.render(player, mouseX, mouseY);
        this.questingWeb.render(player, mouseX, mouseY);
    }

    @Override
    public void onClick(PlayerEntity player, double mouseX, double mouseY) {
        this.questlineButtons.forEach(buttonQuestline -> buttonQuestline.onClick(player, mouseX, mouseY));
        this.title.onClick(player, mouseX, mouseY);
        this.questingWeb.onClick(player, mouseX, mouseY);
    }

    public void reloadQuestlines(){
        this.questlineButtons.clear();
        int currentQuestlinesGuiHeight = 0;
        for (QuestLine questline : Ref.ALL_QUESTBOOK.getQuestlines()) {
            this.questlineButtons.add(new ButtonQuestline(root, MARGIN_QUESTLINE_BUTTONS_LEFT, MARGIN_QUESTLINE_BUTTONS_TOP + currentQuestlinesGuiHeight++ * 25, questline));
        }
    }

    public void reloadTitle(){
        this.title = new Label(
                root,
                Ref.ALL_QUESTBOOK.getQuestlines().get(QuestingWeb.getActiveQuestline()).getTitle(),
                (MARGIN_QUESTLINE_BUTTONS_LEFT + ButtonQuestline.WIDTH + MARGIN_QUESTLINE_BUTTONS_RIGHT) + (this.width - (MARGIN_QUESTLINE_BUTTONS_LEFT + ButtonQuestline.WIDTH + MARGIN_QUESTLINE_BUTTONS_RIGHT)>>1),
                MARGIN_QUESTLINE_BUTTONS_TOP>>1,
                0xFFFFFF,
                true,
                true
        );
    }
}
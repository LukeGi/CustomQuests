package com.vincentmet.customquests.screens;

import com.sun.javafx.geom.Vec2f;
import com.vincentmet.customquests.lib.LineColor;
import com.vincentmet.customquests.lib.Ref;
import com.vincentmet.customquests.lib.Utils;
import com.vincentmet.customquests.quests.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

public class ScreenQuestingDevice extends Screen {

    public static int activeQuestline = 0;
    public static int activeQuest = -1;
    public static int activeSubRequirement = 0;

    public ScreenQuestingDevice(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();

        int currentQuestlinesGuiHeight = 0;
        for(QuestLine questline : Ref.ALL_QUESTBOOK.getQuestlines()){
            Utils.addQuestlineButton(0, currentQuestlinesGuiHeight, mouseX, mouseY, questline.getTitle(), this, font);
            currentQuestlinesGuiHeight += 25;
        }

        if(activeQuest >= 0){
            int spacingIdRequirements = 0;
            int spacingIdRewards = 0;
            Utils.addLabel(
                    Ref.GUI_QUESTING_MARGIN_LEFT + (int)((this.width-Ref.GUI_QUESTING_MARGIN_LEFT)*0.5)-(font.getStringWidth(Quest.getQuestFromId(activeQuest).getTitle())/2),
                    5,
                    Quest.getQuestFromId(activeQuest).getTitle(),
                    0xFF00FF,
                    this,
                    font
            );
            //left side of the line
            Utils.drawMultilineText(
                    this, Ref.GUI_QUESTING_MARGIN_LEFT,
                    Ref.GUI_QUESTING_MARGIN_TOP,
                    (this.width - Ref.GUI_QUESTING_MARGIN_LEFT)/2,
                    font,
                    Quest.getQuestFromId(activeQuest).getDescription(),
                    0xFFFF00
            );
            //right side of the line
            for(QuestRequirement requirement : Quest.getQuestFromId(activeQuest).getRequirements()){
                Utils.addLabel(
                        Ref.GUI_QUESTING_MARGIN_LEFT + (int)((this.width - Ref.GUI_QUESTING_MARGIN_LEFT)*0.52),
                        Ref.GUI_QUESTING_MARGIN_TOP + 25 + 15 * spacingIdRequirements,
                        requirement.getType().toString(),
                        0xFF00FF,
                        this,
                        font
                );
                spacingIdRequirements++;
                for(IQuestRequirement subRequirement : requirement.getRequirement()){
                    Utils.addLabel(
                            Ref.GUI_QUESTING_MARGIN_LEFT + (int)((this.width - Ref.GUI_QUESTING_MARGIN_LEFT)*0.52),
                            Ref.GUI_QUESTING_MARGIN_TOP + 25 + 15 * spacingIdRequirements,
                            subRequirement.toString(),
                            0xFF00FF,
                            this,
                            font
                    );
                    spacingIdRequirements++;
                }
            }

            for(QuestReward reward : Quest.getQuestFromId(activeQuest).getRewards()){
                Utils.addLabel(
                        Ref.GUI_QUESTING_MARGIN_LEFT + (int)((this.width - Ref.GUI_QUESTING_MARGIN_LEFT)*0.52),
                        Ref.GUI_QUESTING_MARGIN_TOP + 25 + spacingIdRequirements + 50 + 15 * spacingIdRequirements,
                        reward.getType().toString() + " " + reward.getReward().toString(),
                        0xFF00FF,
                        this,
                        font
                );
            }
            Utils.drawLine(new Vec2f(Ref.GUI_QUESTING_MARGIN_LEFT + ((this.width - Ref.GUI_QUESTING_MARGIN_LEFT)/2), Ref.GUI_QUESTING_MARGIN_TOP), new Vec2f(Ref.GUI_QUESTING_MARGIN_LEFT + ((this.width - Ref.GUI_QUESTING_MARGIN_LEFT)/2), this.height - Ref.GUI_QUESTING_MARGIN_TOP), this, LineColor.WHITE);
        }else{
            Utils.addCenteredLabel(5, Ref.ALL_QUESTBOOK.getTitle(), 0xFFFFFF,this, font);
            for(int questId : Ref.ALL_QUESTBOOK.getQuestlines().get(activeQuestline).getQuests()){
                Quest quest = Quest.getQuestFromId(questId);
                LineColor lineColor = LineColor.BLACK;
                for(int dependencyId : quest.getDependencies()){
                    Quest qd = Quest.getQuestFromId(dependencyId);
                    if(Quest.hasUnclaimedRewardsForPlayer(Utils.getUUID("vincentmet"), quest.getId())){lineColor = LineColor.YELLOW;}
                    else if(Quest.hasQuestUncompletedDependenciesForPlayer(Utils.getUUID("vincentmet"), quest.getId())){lineColor = LineColor.RED;}
                    else if(Quest.isQuestCompletedForPlayer(Utils.getUUID("vincentmet"), quest.getId())){lineColor = LineColor.GREEN;}
                    Utils.drawConnectionLine(quest.getPosition(), qd.getPosition(), this, lineColor);
                }
            }

            for(int questId : Ref.ALL_QUESTBOOK.getQuestlines().get(activeQuestline).getQuests()){
                Quest quest = Quest.getQuestFromId(questId);
                Utils.addHexaButton(quest.getPosition().getX(), quest.getPosition().getY(), mouseX, mouseY, itemRenderer, this, new ItemStack(quest.getIcon()), quest.getId(), Utils.getUUID("vincentmet"));
            }
        }
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton){
        int currentQuestlinesGuiHeight = 0;
        for(QuestLine questline : Ref.ALL_QUESTBOOK.getQuestlines()){
            Utils.onQuestlineButtonClicked(0, currentQuestlinesGuiHeight, mouseX, mouseY, this, questline);
            currentQuestlinesGuiHeight += 25;
        }
        
        for(int questId : Ref.ALL_QUESTBOOK.getQuestlines().get(activeQuestline).getQuests()){
            Quest quest = Quest.getQuestFromId(questId);
            Utils.onHexaButtonClicked(quest.getPosition().getX(), quest.getPosition().getY(), mouseX, mouseY, this, quest, Utils.getUUID("vincentmet"));
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
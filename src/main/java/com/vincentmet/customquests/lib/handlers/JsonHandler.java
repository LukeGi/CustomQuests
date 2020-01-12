package com.vincentmet.customquests.lib.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vincentmet.customquests.lib.Ref;
import com.vincentmet.customquests.lib.Utils;
import com.vincentmet.customquests.quests.Quest;
import com.vincentmet.customquests.quests.QuestLine;
import com.vincentmet.customquests.quests.QuestUserProgress;
import com.vincentmet.customquests.quests.party.Party;
import net.minecraft.client.renderer.entity.model.RendererModel;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class JsonHandler {
    private static JsonObject jsonContainerQuests;
    private static JsonObject jsonContainerQuestbook;
    private static JsonObject jsonContainerQuestingProgress;
    private static JsonObject jsonContainerQuestingParties;

    public static void loadJson(String questsLocation, String questBookLocation, String questingProgressLocation, String questingPartiesLocation) {
        loadQuests(questsLocation);
        loadQuestbook(questBookLocation);
        loadQuestingProgress(questingProgressLocation);
        loadQuestingParties(questingPartiesLocation);
    }

    public static void loadQuests(String questsLocation){
        try{
            JsonParser parser = new JsonParser();
            jsonContainerQuests = (JsonObject)parser.parse(new FileReader(questsLocation));
        }catch(IOException e){
            try{
                FileWriter file = new FileWriter(questsLocation);
                file.write(Utils.getDefaultQuestsJson());
                file.flush();
                file.close();
                JsonParser parser = new JsonParser();
                jsonContainerQuests = (JsonObject)parser.parse(new FileReader(questsLocation));
            }catch(IOException e1){
                e1.printStackTrace();
            }
        }
    }

    public static void loadQuestbook(String questBookLocation){
        try{
            JsonParser parser = new JsonParser();
            jsonContainerQuestbook = (JsonObject)parser.parse(new FileReader(questBookLocation));
        }catch(IOException e){
            try{
                FileWriter file = new FileWriter(questBookLocation);
                file.write(Utils.getDefaultQuestBookJson());
                file.flush();
                file.close();
                JsonParser parser = new JsonParser();
                jsonContainerQuestbook = (JsonObject)parser.parse(new FileReader(questBookLocation));
            }catch(IOException e1){
                e1.printStackTrace();
            }
        }
    }

    public static void loadQuestingProgress(String questingProgressLocation){
        try{
            JsonParser parser = new JsonParser();
            jsonContainerQuestingProgress = (JsonObject)parser.parse(new FileReader(questingProgressLocation));
        }catch(IOException e){
            try{
                FileWriter file = new FileWriter(questingProgressLocation);
                file.write(Utils.getDefaultQuestingProgressJson());
                file.flush();
                file.close();
                JsonParser parser = new JsonParser();
                jsonContainerQuestingProgress = (JsonObject)parser.parse(new FileReader(questingProgressLocation));
            }catch(IOException e1){
                e1.printStackTrace();
            }
        }
    }

    public static void loadQuestingParties(String questingPartiesLocation){
        try{
            JsonParser parser = new JsonParser();
            jsonContainerQuestingParties = (JsonObject)parser.parse(new FileReader(questingPartiesLocation));
        }catch(IOException e){
            try{
                FileWriter file = new FileWriter(questingPartiesLocation);
                file.write(Utils.getDefaultQuestingPartiesJson());
                file.flush();
                file.close();
                JsonParser parser = new JsonParser();
                jsonContainerQuestingParties = (JsonObject)parser.parse(new FileReader(questingPartiesLocation));
            }catch(IOException e1){
                e1.printStackTrace();
            }
        }
    }

    public static void writeAll(String questsLocation, String questBookLocation, String questingProgressLocation, String questingPartiesLocation){
        writeQuests(questsLocation);
        writeQuestbook(questBookLocation);
        writeQuestingProgress(questingProgressLocation);
        writeQuestingParties(questingPartiesLocation);
    }

    public static void writeQuests(String questsLocation) {
        try{
            FileWriter file = new FileWriter(questsLocation);

            JsonObject json = new JsonObject();
            JsonArray questArray = new JsonArray();
            for(Quest quest : Ref.ALL_QUESTS){
                questArray.add(quest.getJson());
            }
            json.add("quests", questArray);
            file.write(json.toString());

            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void writeQuestbook(String questBookLocation) {
        try{
            FileWriter file = new FileWriter(questBookLocation);

            JsonObject json = new JsonObject();
            json.addProperty("title", Ref.ALL_QUESTBOOK.getTitle());
            json.addProperty("text", Ref.ALL_QUESTBOOK.getDescription());
            JsonArray questlineArray = new JsonArray();
            for(QuestLine questLine : Ref.ALL_QUESTBOOK.getQuestlines()){
                questlineArray.add(questLine.getJson());
            }
            json.add("questlines", questlineArray);
            file.write(json.toString());

            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void writeQuestingProgress(String questingProgressLocation){
        try{
            FileWriter file = new FileWriter(questingProgressLocation);

            JsonObject json = new JsonObject();
            JsonArray playerArray = new JsonArray();
            for(QuestUserProgress users: Ref.ALL_QUESTING_PROGRESS){
                playerArray.add(users.getJson());
            }
            json.add("players", playerArray);
            file.write(json.toString());

            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void writeQuestingParties(String questingPartiesLocation){
        try{
            FileWriter file = new FileWriter(questingPartiesLocation);

            JsonObject json = new JsonObject();
            JsonArray partyArray = new JsonArray();
            for(Party party : Ref.ALL_QUESTING_PARTIES){
                partyArray.add(party.getJson());
            }
            json.add("parties", partyArray);
            file.write(json.toString());

            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static JsonObject getQuestbookJson(){
        return jsonContainerQuestbook;
    }

    public static JsonObject getQuestsJson(){
        return jsonContainerQuests;
    }

    public static JsonObject getQuestingProgressJson(){
        return jsonContainerQuestingProgress;
    }

    public static JsonObject getQuestingPartiesJson(){
        return jsonContainerQuestingParties;
    }
}
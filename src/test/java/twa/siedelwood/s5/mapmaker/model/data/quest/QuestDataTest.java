package twa.siedelwood.s5.mapmaker.model.data.quest;

import org.junit.Before;
import org.junit.Test;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;
import twa.siedelwood.s5.mapmaker.model.data.map.MapData;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class QuestDataTest {
    private QuestCollection questCollection;

    @Before
    public void initTest() {
        questCollection = new QuestCollection();

        Quest quest = new Quest();
        quest.setName("Bockwurst");

        QuestBehavior behavior = new QuestBehavior();
        behavior.addParameter("Trigger_QuestOver");
        behavior.addParameter("Käsekuchen");
        behavior.addParameter("0");

        quest.getBehaviorList().add(behavior);
        questCollection.add(quest);
    }

    @Test
    public void readQuestsFromJsonTest() throws Exception {
        String jsonString = "[{\"Name\":\"Bockwurst\",\"Time\":0,\"Receiver\":1,\"Title\":\"\",\"Text\":\"\",\"Type\":\"MAINQUEST_OPEN\",\"Visible\":false,\"Behavior\":[[\"Trigger_QuestOver\",\"Käsekuchen\",\"0\"]],\"SubQuests\":{}}]";
        JsonParser parser = new JsonParser();
        String legacyProjectContent = new String(jsonString);

        QuestCollection questCollection = new QuestCollection();
        JsonArray source = (JsonArray) parser.parse(jsonString);
        for (Json data : source.getArray()) {
            questCollection.questList.add(Quest.parse(data.toJson()));
        }
        System.out.println(questCollection.toLua());
    }

    @Test
    public void displayQuestCollectionToJsonResult() {
        Json json = questCollection.toJson();
        String jsonString = json.toJson();
        System.out.println(jsonString);
        assertTrue(true);
    }

    @Test
    public void displayQuestCollectionToLuaResult() {
        System.out.println(questCollection.toLua());
        assertTrue(true);
    }
}
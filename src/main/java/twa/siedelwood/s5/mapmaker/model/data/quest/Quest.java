package twa.siedelwood.s5.mapmaker.model.data.quest;

import lombok.Data;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;
import twa.lib.typesavejson.models.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Quest model
 */
@Data
public class Quest implements Comparable<Quest>, Serializable {
    private Map<String, String> subQuests;

    private List<QuestBehavior> behaviorList;

    private String name;

    private int time = 0;

    private int receiver = 1;

    private String title = "";

    private String type = "MAINQUEST_OPEN";

    private String text = "";

    private boolean visible = false;

    public Quest() {
        subQuests = new LinkedHashMap<>();
        behaviorList = new ArrayList<>();
    }

    @Override
    public int compareTo(Quest o) {
        return name.compareTo(o.getName());
    }

    public void sort() {
        Collections.sort(behaviorList, Comparator.comparing(QuestBehavior::getName));
    }

    @Override
    public Quest clone() {
        try {
            return Quest.parse(toJson().toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return toJson().toJson();
    }

    public Json toJson() {
        JsonObject subQuests = new JsonObject();
        for (Map.Entry<String, String> entry : this.subQuests.entrySet()) {
            subQuests.put(entry.getKey(), entry.getValue());
        }

        JsonArray behaviors = new JsonArray();
        for (QuestBehavior behavior : behaviorList) {
            behaviors.add(behavior.toJson());
        }

        JsonObject result = new JsonObject();
        result.put("Name", name);
        result.put("Time", time);
        result.put("Receiver", receiver);
        result.put("Title", title);
        result.put("Text", text);
        result.put("Type", type);
        result.put("Visible", visible);
        result.put("Behavior", behaviors);
        result.put("SubQuests", subQuests);
        return result;
    }

    public String toLua() {
        String title = this.title.replaceAll("\"", "{qq}");
        String text  = this.text.replaceAll("\"", "{qq}");

        String lua = "{";
        lua += "Name = \"" +name+ "\", ";
        lua += "Time = " +time+ ", ";
        lua += "Receiver = " +receiver+ ", ";
        lua += "Title = \"" +title+ "\", ";
        lua += "Text = \"" +text+ "\", ";
        lua += "Type = \"" +type+ "\", ";
        lua += "Visible = " +visible+ ", ";

        String subQuests = "";
        for (Map.Entry sq : this.subQuests.entrySet()) {
            subQuests += "{" +sq.getKey()+ " = \"" +sq.getValue()+ "\"},";
        }
        lua += "SubQuests = {" +subQuests+ "}, ";

        for (QuestBehavior b : behaviorList) {
            lua += b.toLua() + ", ";
        }

        lua += "}";
        return lua;
    }

    public static Quest parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject source = (JsonObject) parser.parse(json);

        Quest quest = new Quest();
        quest.setName(source.get("Name").getStringValue());
        quest.setTime(source.get("Time").getIntValue());
        quest.setReceiver(source.get("Receiver").getIntValue());
        quest.setTitle(source.get("Title").getStringValue());
        quest.setText(source.get("Text").getStringValue());
        quest.setType(source.get("Type").getStringValue());
        quest.setVisible(source.get("Visible").getBooleanValue());

        JsonObject subQuests = (JsonObject) source.get("SubQuests");
        for (Map.Entry<String, Json> entry : subQuests.getMap().entrySet()) {
            quest.subQuests.put(entry.getKey(), entry.getValue().getStringValue());
        }

        List<Json> behaviors = source.get("Behavior").getArray();
        for (Json data : behaviors) {
            quest.behaviorList.add(QuestBehavior.parse(data.toJson()));
        }

        return quest;
    }

    public Vector<String> getBehaviorNames() {
        Vector<String> names = new Vector<>();
        for (QuestBehavior b : behaviorList) {
            names.add(b.getName());
        }
        return names;
    }
}

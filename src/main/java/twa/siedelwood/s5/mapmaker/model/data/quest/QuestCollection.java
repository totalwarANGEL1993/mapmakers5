package twa.siedelwood.s5.mapmaker.model.data.quest;

import lombok.Data;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;
import twa.siedelwood.s5.mapmaker.model.data.briefing.Briefing;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPage;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPageTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

/**
 * Collection of quests
 */
@Data
public class QuestCollection {
    List<Quest> questList;

    public QuestCollection() {
        questList = new ArrayList<>();
    }

    public QuestCollection add(Quest quest) {
        questList.add(quest);
        return this;
    }

    public void sort() {
        Collections.sort(questList, Comparator.comparing(Quest::getName));
    }

    @Override
    public String toString() {
        return toJson().toJson();
    }

    public Json toJson() {
        JsonArray questsArray = new JsonArray();
        for (Quest quest : questList) {
            questsArray.add(quest.toJson());
        }
        return questsArray;
    }

    public Vector<String> getAllCustomVariableNames() {
        Vector<String> customVariables = new Vector<>();
        for (Quest quest : questList) {
            for (QuestBehavior behavior : quest.getBehaviorList()) {
                if (behavior.getName().contains("CustomVariable")) {
                    String varName = behavior.getParameter(1);
                    if (varName != null && !varName.equals("") && !customVariables.contains(varName)) {
                        customVariables.add(varName);
                    }
                }
            }
        }
        return customVariables;
    }

    public Vector<String> getQuestsReferencingBriefing(Briefing briefing) {
        return findQuestsReferencingValueInBehaviors(briefing.getName(), new Vector<>(questList));
    }

    public Vector<String> getQuestsReferencingPage(BriefingPage page) {
        Vector<String> questNames = new Vector<>();
        if (page.getType() == BriefingPageTypes.TYPE_CHOICE) {
            questNames = findQuestsReferencingValueInBehaviors(page.getName(), new Vector<>(questList));
        }
        return questNames;
    }

    public Vector<String> getQuestsReferencingQuest(Quest quest) {
        Vector<Quest> quests = new Vector<>(questList);
        quests.remove(quest);
        return findQuestsReferencingValueInBehaviors(quest.getName(), quests);
    }

    private Vector<String> findQuestsReferencingValueInBehaviors(String value, Vector<Quest> quests) {
        Vector<String> questNames = new Vector<>();
        for (Quest q : quests) {
            for (QuestBehavior b : q.getBehaviorList()) {
                for (int i=1; i<b.getParameters().size(); i++) {
                    if (b.getParameters().get(i).equals(value) && !questNames.contains(q.getName())) {
                        questNames.add(q.getName());
                    }
                }
            }
        }
        return questNames;
    }

    public void replaceQuestNameInReferencingQuests(String name, String rep) {
        List<QuestBehavior> behaviorList = new ArrayList<>();
        for (Quest q : questList) {
            for (QuestBehavior b : q.getBehaviorList()) {
                if (b.getName().contains("Quest")) {
                    behaviorList.add(b);
                }
            }
        }
        replaceValueInReferencingQuests(name, rep, behaviorList);
    }

    public void replaceBriefingNameInReferencingQuests(String name, String rep) {
        List<QuestBehavior> behaviorList = new ArrayList<>();
        for (Quest q : questList) {
            for (QuestBehavior b : q.getBehaviorList()) {
                if (b.getName().contains("Briefing")) {
                    behaviorList.add(b);
                }
            }
        }
        replaceValueInReferencingQuests(name, rep, behaviorList);
    }

    public void replacePageNameInReferencingQuests(String name, String rep) {
        List<QuestBehavior> behaviorList = new ArrayList<>();
        for (Quest q : questList) {
            for (QuestBehavior b : q.getBehaviorList()) {
                if (b.getName().contains("MultipleChoice")) {
                    behaviorList.add(b);
                }
            }
        }
        replaceValueInReferencingQuests(name, rep, behaviorList);
    }

    private void replaceValueInReferencingQuests(String oldVal, String newVal, List<QuestBehavior> behaviorList) {
        for (QuestBehavior b : behaviorList) {
            for (int i = 1; i < b.getParameters().size(); i++) {
                if (b.getParameters().get(i).equals(oldVal)) {
                    b.getParameters().set(i, newVal);
                }
            }
        }
    }

    public Vector<String> toNamesVector() {
        Vector<String> namesVector = new Vector<>();
        for (Quest q : questList) {
            namesVector.add(q.getName());
        }
        return namesVector;
    }

    public String toLua() {
        String lua = "    Global_QuestsToGenerate = {\n";
        for (Quest q : questList) {
            lua += "        " + q.toLua() + ",\n";
        }
        lua += "    };\n";
        return lua;
    }

    public static QuestCollection parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        QuestCollection questCollection = new QuestCollection();
        JsonArray source = (JsonArray) parser.parse(json);
        for (Json data : source.getArray()) {
            questCollection.questList.add(Quest.parse(data.toJson()));
        }
        return questCollection;
    }

    public Quest remove(String name) {
        for (int i=0; i<questList.size(); i++) {
            if (questList.get(i).getName().equals(name)) {
                return remove(i);
            }
        }
        return null;
    }

    public Quest remove(int idx) {
        if (idx < 0 || idx > questList.size()-1) {
            return null;
        }
        return questList.remove(idx);
    }

    public Quest get(int idx) {
        if (idx < 0 || idx > questList.size()-1) {
            return null;
        }
        return questList.get(idx);
    }

    public Quest get(String name) {
        for (int i=0; i<questList.size(); i++) {
            if (questList.get(i).getName().equals(name)) {
                return get(i);
            }
        }
        return null;
    }
}

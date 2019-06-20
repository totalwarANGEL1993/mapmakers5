package twa.siedelwood.s5.mapmaker.service.config;

import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonObject;
import twa.siedelwood.s5.mapmaker.model.data.briefing.Briefing;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingCollection;
import twa.siedelwood.s5.mapmaker.model.data.quest.Quest;
import twa.siedelwood.s5.mapmaker.model.data.quest.QuestCollection;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

/**
 * This service provides multiple vectors for usage in the GUI
 *
 * TODO: This should use exchangeable if no swing is used!
 */
public class SelectableValueService {
    private BriefingCollection briefingCollection;
    private QuestCollection questCollection;
    private Vector<String> currentScriptNames;
    private Vector<String> playerColors;
    private Vector<String> weatherSets;
    private Vector<String> diplomacyStates;

    public SelectableValueService() {
        currentScriptNames = new Vector<>();
        briefingCollection = new BriefingCollection();
        questCollection = new QuestCollection();
    }

    public Vector<String> getPlayerColors() {
        return playerColors;
    }

    public Vector<String> getWeatherSets() {
        return weatherSets;
    }

    public void clearConfig() {
        playerColors = new Vector<>();
        weatherSets = new Vector<>();
        diplomacyStates = new Vector<>();
    }

    public void loadConfig() throws Exception {
        JsonParser parser = new JsonParser();

        String enumsContent = new String(Files.readAllBytes(Paths.get("cnf/enums.json")), Charset.forName("UTF-8"));
        JsonObject enums = (JsonObject) parser.parse(enumsContent);

        for (Object s : enums.get("PlayerColors").getArray()) {
            this.playerColors.add(((Json) s).getStringValue());
        }
        for (Object s : enums.get("WeatherSets").getArray()) {
            this.weatherSets.add(((Json) s).getStringValue());
        }
        for (Object s : enums.get("DiplomacyStates").getArray()) {
            this.diplomacyStates.add(((Json) s).getStringValue());
        }
    }

    /**
     * Adds a scriptname to the vector
     * @param scriptname Scriptname
     */
    public void addScriptName(String scriptname) {
        if (!currentScriptNames.contains(scriptname)) {
            currentScriptNames.add(scriptname);
        }
    }

    /**
     * Removes a scriptname from the vector
     * @param scriptname Scriptname
     */
    public void dropScriptName(String scriptname) {
        currentScriptNames.remove(scriptname);
    }

    /**
     * Sets the vector of script names
     * FIXME: Implement scriptnames from map and config!
     * @param scriptNames Script names
     */
    public void setScriptNames(Vector<String> scriptNames) {
        this.currentScriptNames = scriptNames;
    }

    /**
     * Returns all script names on the map
     * FIXME: Implement scriptnames from map and config!
     * @return Script names
     */
    public Vector<String> getScriptNames() {
        return currentScriptNames;
    }

    /**
     * Sets the reference to the briefing collection
     * @param briefingCollection Briefing collection
     */
    public void setBriefingCollection(BriefingCollection briefingCollection) {
        this.briefingCollection = briefingCollection;
    }

    /**
     * Sets the reference to the quest collection
     * @param questCollection Quest collection
     */
    public void setQuestCollection(QuestCollection questCollection) {
        this.questCollection = questCollection;
    }

    /**
     * Returns all briefing names
     * @return Briefing names
     */
    public Vector<String> getBriefingNames() {
        Vector<String> names = new Vector<>();
        for (Briefing b : briefingCollection.getBriefings()) {
            names.add(b.getName());
        }
        return names;
    }

    /**
     * Returns all quest names
     * @return Quest names
     */
    public Vector<String> getQuestNames() {
        Vector<String> names = new Vector<>();
        for (Quest q : questCollection.getQuestList()) {
            names.add(q.getName());
        }
        return names;
    }
}

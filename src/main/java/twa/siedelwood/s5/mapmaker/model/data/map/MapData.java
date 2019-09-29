package twa.siedelwood.s5.mapmaker.model.data.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;
import twa.lib.typesavejson.models.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Map data model
 */
@Data
@AllArgsConstructor
public class MapData {
    private String mapName = "";

    private String mapDescription = "";

    private String weatherSet = "SetupNormalWeatherGfxSet";

    private boolean randomizedWeather = false;

    private boolean checkQuests = true;

    private boolean traceQuests = false;

    private boolean useCheats = true;

    private boolean useShell = true;

    private List<Integer> resources;

    private List<Player> players;

    public MapData() {
        resources = Arrays.asList(0, 0, 0, 0, 0, 0);
        players = new ArrayList<>();
        for (int i=0; i<8; i++) {
            List<Diplomacy> diplomacyList = new ArrayList<>();
            for (int j=0; j<8; j++) {
                diplomacyList.add(new Diplomacy(j+1, 1));
            }
            players.add(new Player("", "DEFAULT_COLOR", diplomacyList));
        }
    }

    public void setResources(int gold, int wood, int clay, int stone, int iron, int sulfur) {
        resources = Arrays.asList(gold, wood, clay, stone, iron, sulfur);
    }

    public void alterPlayer(int id, String name, String color, List<Diplomacy> diplomacy) {
        if (players.size() < id) {
            return;
        }
        players.get(id-1).setName(name);
        players.get(id-1).setColor(color);
        players.get(id-1).setDiplomacy(diplomacy);
    }

    @Override
    public String toString() {
        return toJson().toJson();
    }

    public String toLua() {
        String mapName = this.mapName.replaceAll("\"", "{qq}");
        String mapDescription  = this.mapDescription.replaceAll("\"", "{qq}");

        String lua = "Global_MapConfigurationData = {\n";
        lua += "    MapName = \"" +mapName+ "\",\n";
        lua += "    MapDescription = \"" +mapDescription+ "\",\n";
        lua += "    WeatherSet = \"" +weatherSet+ "\",\n";
        lua += "    RandomizeWeather = " +randomizedWeather+ ",\n";
        lua += String.format("    Resources = {%d,%d,%d,%d,%d,%d,},\n", resources.get(0), resources.get(1), resources.get(2), resources.get(3), resources.get(4), resources.get(5));
        lua += String.format("    Debug = {%b,%b,%b,%b,},\n", checkQuests, useCheats, useShell, traceQuests);

        String players = "";
        for (int i=0; i<8; i++) {
            players += "\n        " + this.players.get(i).toLua() + ",";
            if (i == 7) players += "\n    ";
        }
        lua += "    Players = {" + players + "},\n";
        return lua + "};";
    }

    public Json toJson() {
        JsonArray resources = new JsonArray();
        for (int res : this.resources) {
            resources.add(res);
        }

        JsonArray players = new JsonArray();
        for (Player player : this.players) {
            players.add(player.toJson());
        }

        JsonObject result = new JsonObject();
        result.put("MapName", mapName);
        result.put("MapDescription", mapDescription);
        result.put("WeatherSet", weatherSet);
        result.put("RandomizeWeather", randomizedWeather);
        result.put("CheckQuests", checkQuests);
        result.put("TraceQuests", traceQuests);
        result.put("UseCheats", useCheats);
        result.put("UseShell", useShell);
        result.put("Resources", resources);
        result.put("Players", players);
        return result;
    }

    public static MapData parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject source = (JsonObject) parser.parse(json);

        MapData mapData = new MapData();
        mapData.setMapName(source.get("MapName").getStringValue());
        mapData.setMapDescription(source.get("MapDescription").getStringValue());
        mapData.setWeatherSet(source.get("WeatherSet").getStringValue());
        mapData.setRandomizedWeather(source.get("RandomizeWeather").getBooleanValue());
        mapData.setCheckQuests(source.get("CheckQuests").getBooleanValue());
        mapData.setTraceQuests(source.get("TraceQuests").getBooleanValue());
        mapData.setUseCheats(source.get("UseCheats").getBooleanValue());
        mapData.setUseShell(source.get("UseShell").getBooleanValue());

        List<Json> resources = source.get("Resources").getArray();
        mapData.resources = new ArrayList<>();
        for (Json data : resources) {
            mapData.resources.add(data.getIntValue());
        }

        List<Json> players = source.get("Players").getArray();
        mapData.players = new ArrayList<>();
        for (Json data : players) {
            mapData.players.add(Player.parse(data.toJson()));
        }
        return mapData;
    }
}

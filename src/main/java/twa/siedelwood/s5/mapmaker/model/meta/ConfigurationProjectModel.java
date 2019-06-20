package twa.siedelwood.s5.mapmaker.model.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;
import twa.lib.typesavejson.models.JsonObject;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingCollection;
import twa.siedelwood.s5.mapmaker.model.data.map.MapData;
import twa.siedelwood.s5.mapmaker.model.data.quest.QuestCollection;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class ConfigurationProjectModel {
    private String name = "";

    private String description = "";

    private String mapFile = "";

    private List<String> includes = new ArrayList<>();

    private BriefingCollection briefingCollection;

    private QuestCollection questCollection;

    private MapData mapData;

    private boolean projectAltered = false;

    @Override
    public String toString() {
        return toJson().toJson();
    }

    public Json toJson() {
        JsonArray includes = new JsonArray();
        for (String file : this.includes) {
            includes.add(file);
        }

        JsonObject result = new JsonObject();
        result.put("ProjectName", name);
        result.put("ProjectDescription", description);
        result.put("PathToMapFile", mapFile);
        result.put("FilesToInclude", includes);
        return result;
    }

    public static ConfigurationProjectModel parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject source = (JsonObject) parser.parse(json);
        ConfigurationProjectModel projectModel = new ConfigurationProjectModel();
        projectModel.setName(source.get("ProjectName").getStringValue());
        projectModel.setDescription(source.get("ProjectDescription").getStringValue());
        projectModel.setMapFile(source.get("PathToMapFile").getStringValue());
        for (Object file : source.get("FilesToInclude").getArray()) {
            projectModel.getIncludes().add(((Json) file).getStringValue());
        }
        return projectModel;
    }
}

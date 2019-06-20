package twa.siedelwood.s5.mapmaker.model.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ConfigurationBehaviorPrototypes {
    private List<ConfigurationBehaviorModel> behaviors;

    public ConfigurationBehaviorPrototypes() {
        behaviors = new ArrayList<>();
    }

    public void addBehavior(ConfigurationBehaviorModel behavior) {
        behaviors.add(behavior);
    }

    public ConfigurationBehaviorModel getBehavior(String name) {
        for (ConfigurationBehaviorModel b : behaviors) {
            if (b.getType().equals(name)) {
                return b;
            }
        }
        return null;
    }

    public static ConfigurationBehaviorPrototypes parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject source = (JsonObject) parser.parse(json);

        ConfigurationBehaviorPrototypes result = new ConfigurationBehaviorPrototypes();
        for (Map.Entry<String, Json> entry : source.getMap().entrySet()) {
            result.behaviors.add(ConfigurationBehaviorModel.parse(entry.getKey(), entry.getValue().toJson()));
        }
        return result;
    }
}

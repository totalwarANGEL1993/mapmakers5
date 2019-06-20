package twa.siedelwood.s5.mapmaker.model.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ConfigurationParamaterModel {
    private String type;
    private String description;
    private List<String> defaults;

    public ConfigurationParamaterModel(String type, String description) {
        this.type = type;
        this.description = description;
        this.defaults = new ArrayList<>();
    }

    public void addDefault(String def) {
        if (!defaults.contains(def)) {
            defaults.add(def);
        }
    }

    public static ConfigurationParamaterModel parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonArray source = (JsonArray) parser.parse(json);

        String name = source.get(0).getStringValue();
        String description = source.get(1).getStringValue();
        List<String> options = new ArrayList<>();
        for (Object param : source.get(2).getArray()) {
            options.add(((Json) param).getStringValue());
        }
        return new ConfigurationParamaterModel(name, description, options);
    }
}

package twa.siedelwood.s5.mapmaker.model.meta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonObject;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class ConfigurationBehaviorModel {
    private String type;
    private String description;
    private List<ConfigurationParamaterModel> parameters;

    public ConfigurationBehaviorModel(String type, String description) {
        this.type = type;
        this.description = description;
        this.parameters = new ArrayList<>();
    }

    public void addParameter(ConfigurationParamaterModel param) {
        parameters.add(param);
    }

    public ConfigurationParamaterModel getParameter(int idx) {
        return parameters.get(idx);
    }

    public static ConfigurationBehaviorModel parse(String name, String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject source = (JsonObject) parser.parse(json);

        String description = source.get("Description").getStringValue();
        List<ConfigurationParamaterModel> options = new ArrayList<>();
        for (Object param : source.get("Parameter").getArray()) {
            options.add(ConfigurationParamaterModel.parse(((Json) param).toJson()));
        }
        return new ConfigurationBehaviorModel(name, description, options);
    }
}

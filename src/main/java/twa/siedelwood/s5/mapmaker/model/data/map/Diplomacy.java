package twa.siedelwood.s5.mapmaker.model.data.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonObject;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Diplomacy implements Serializable {
    private int target = 1;
    private int state = 2;

    @Override
    public String toString() {
        return toJson().toJson();
    }

    public Json toJson() {
        JsonObject result = new JsonObject();
        result.put("Target", target);
        result.put("State", state);
        return result;
    }

    public String toLua() {
        return String.format("{%d, %d}",target, state);
    }

    public static Diplomacy parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject source = (JsonObject) parser.parse(json);

        return new Diplomacy(
            source.get("Target").getIntValue(),
            source.get("State").getIntValue()
        );
    }
}

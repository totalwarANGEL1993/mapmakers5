package twa.siedelwood.s5.mapmaker.model.data.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;
import twa.lib.typesavejson.models.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Player implements Serializable {
    String name;

    String color;

    List<Diplomacy> diplomacy;

    @Override
    public String toString() {
        return toJson().toJson();
    }

    public Json toJson() {
        JsonArray diplomacy = new JsonArray();
        for (Diplomacy d : this.diplomacy) {
            diplomacy.add(d.toJson());
        }

        JsonObject result = new JsonObject();
        result.put("Name", name);
        result.put("Color", color);
        result.put("Diplomacy", diplomacy);
        return result;
    }

    public String toLua() {
        String diplomacyString = "";
        for (int i=0; i<8; i++) {
            Diplomacy diplomacy = this.diplomacy.get(i);
            diplomacyString += diplomacy.toLua() + ",";
        }
        String name = this.name.replaceAll("\"", "{qq}");

        return String.format(
            "{Name = \"%s\", Color = \"%s\", Diplomacy = {%s}}",
            name, color, diplomacyString
        );
    }

    public static Player parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject source = (JsonObject) parser.parse(json);

        List<Diplomacy> diplomacyList = new ArrayList<>();
        for (Object diplomacy : source.get("Diplomacy").getArray()) {
            diplomacyList.add(Diplomacy.parse(((Json) diplomacy).toJson()));
        }

        return new Player(
            source.get("Name").getStringValue(),
            source.get("Color").getStringValue(),
            diplomacyList
        );
    }
}

package twa.siedelwood.s5.mapmaker.model.data.briefing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;

import java.io.Serializable;
import java.util.List;

/**
 * Empty seperator page
 */
@Data
@NoArgsConstructor
public class BriefingRedirectPage implements BriefingPage, Serializable {
    private String name;
    private String entity;
    private String title;
    private String text;
    private boolean dialogCamera = false;
    private String action;
    private boolean showSky = false;
    private boolean hideFoW = false;
    private boolean noEscape = true;
    private String firstSelectText;
    private String firstSelectPage;
    private String secondSelectText;
    private String secondSelectPage;
    private String answerVariable;

    public BriefingRedirectPage(String name) {
        this.name = name;
    }

    public BriefingRedirectPage(String name, String target) {
        this.name = name;
        this.entity = target;
    }

    public BriefingRedirectPage(List<Json> data) {
        name   = data.get(0) != null ? data.get(0).getStringValue() : "";
        entity = data.get(1) != null ? data.get(1).getStringValue() : "";
    }

    @Override
    public BriefingPageTypes getType() {
        return BriefingPageTypes.TYPE_JUMP;
    }

    @Override
    public Json toJson() {
        JsonArray result = new JsonArray();
        result.add(name);
        result.add(entity);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toLua() {
        return "    AP(\"" + entity + "\");";
    }

    @Override
    public BriefingPage clone() {
        try {
            return BriefingRedirectPage.parse(toJson().toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BriefingRedirectPage parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonArray source = (JsonArray) parser.parse(json);
        return new BriefingRedirectPage(
                source.getArray().get(0).getStringValue(),
                source.getArray().get(1).getStringValue()
        );
    }
}

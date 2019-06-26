package twa.siedelwood.s5.mapmaker.model.data.briefing;

import lombok.Data;
import lombok.NoArgsConstructor;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;

/**
 * Empty seperator page
 */
@Data
@NoArgsConstructor
public class BriefingSepertorPage implements BriefingPage {
    private String name;
    private String entity;
    private String title;
    private String text;
    private boolean dialogCamera = false;
    private String action;
    private boolean showSky = false;
    private boolean hideFoW = false;
    private String firstSelectText;
    private String firstSelectPage;
    private String secondSelectText;
    private String secondSelectPage;
    private String answerVariable;

    public BriefingSepertorPage(String name) {
        this.name = name;
    }

    @Override
    public BriefingPageTypes getType() {
        return BriefingPageTypes.TYPE_EMPTY;
    }

    @Override
    public Json toJson() {
        JsonArray result = new JsonArray();
        result.add(name);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String toLua() {
        return "AP();";
    }

    @Override
    public BriefingPage clone() {
        return new BriefingSepertorPage(name);
    }

    public static BriefingSepertorPage parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonArray source = (JsonArray) parser.parse(json);
        return new BriefingSepertorPage(source.getArray().get(0).getStringValue());
    }
}

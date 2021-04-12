package twa.siedelwood.s5.mapmaker.model.data.briefing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;

import java.util.List;

/**
 * Briefing select page model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BriefingChoicePage implements BriefingPage {
    private String name = "";
    private String entity = "";
    private String title = "";
    private String text = "";
    private boolean dialogCamera = false;
    private String action = "";
    private boolean showSky = false;
    private boolean hideFoW = false;
    private boolean noEscape = true;
    private String firstSelectText = "";
    private String firstSelectPage = "";
    private String secondSelectText = "";
    private String secondSelectPage = "";

    public BriefingChoicePage(String name) {
        this();
        this.name = name;
    }

    public BriefingChoicePage(List<Json> data) {
        name             = data.get(0) != null ? data.get(0).getStringValue() : "";
        entity           = data.get(1) != null ? data.get(1).getStringValue() : "";
        title            = data.get(2) != null ? data.get(2).getStringValue() : "";
        text             = data.get(3) != null ? data.get(3).getStringValue() : "";
        dialogCamera     = data.get(4) != null ? data.get(4).getBooleanValue() : false;
        action           = data.get(5) != null ? data.get(5).getStringValue() : "";
        showSky          = data.get(6) != null ? data.get(6).getBooleanValue() : false;
        hideFoW          = data.get(7) != null ? data.get(7).getBooleanValue() : false;
        firstSelectText  = data.get(8) != null ? data.get(8).getStringValue() : "";
        firstSelectPage  = data.get(9) != null ? data.get(9).getStringValue() : "";
        secondSelectText = data.get(10) != null ? data.get(10).getStringValue() : "";
        secondSelectPage = data.get(11) != null ? data.get(11).getStringValue() : "";
        noEscape         = data.get(12) != null ? data.get(12).getBooleanValue() : true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Json toJson() {
        JsonArray result = new JsonArray();
        result.add(name);
        result.add(entity);
        result.add(title);
        result.add(text);
        result.add(dialogCamera);
        result.add(action);
        result.add(showSky);
        result.add(hideFoW);
        result.add(noEscape);
        result.add(firstSelectText);
        result.add(firstSelectPage);
        result.add(secondSelectText);
        result.add(secondSelectPage);
        return result;
    }

    @Override
    public String toLua() {
        String title = this.title.replaceAll("\"", "{qq}");
        String text  = this.text.replaceAll("\"", "{qq}");
        String firstText = this.firstSelectText.replaceAll("\"", "{qq}");
        String secondText  = this.secondSelectText.replaceAll("\"", "{qq}");
        return String.format(
            "    local %s = AP{\n" +
            "        Name            = \"%s\",\n" +
            "        Target          = \"%s\",\n" +
            "        Title           = \"%s\",\n" +
            "        Text            = \"%s\",\n" +
            "        DialogCamera    = %b,\n" +
            "        Action          = %s,\n" +
            "        RenderSky       = %b,\n" +
            "        RenderFoW       = %b,\n" +
            "        DisableSkipping = %b,\n" +
            "        MC              = {\n" +
            "            {\"%s\",(_G[\"%s\"] and type(_G[\"%s\"]) == \"function\" and _G[\"%s\"]) or \"%s\", ID = 1},\n" +
            "            {\"%s\",(_G[\"%s\"] and type(_G[\"%s\"]) == \"function\" and _G[\"%s\"]) or \"%s\", ID = 2},\n" +
            "        },\n" +
            "    };",
            name, name, entity, title, text, dialogCamera, (action.equals("") ? "nil" : action),
            showSky, !hideFoW, noEscape, firstText, firstSelectPage, firstSelectPage, firstSelectPage,
            firstSelectPage, secondText, secondSelectPage, secondSelectPage, secondSelectPage, secondSelectPage
        );
    }

    @Override
    public BriefingPage clone() {
        try {
            return BriefingChoicePage.parse(toJson().toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BriefingPage parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonArray source = (JsonArray) parser.parse(json);
        return new BriefingChoicePage(
                source.get(0) != null ? source.get(0).getStringValue() : "",
                source.get(1) != null ? source.get(1).getStringValue() : "",
                source.get(2) != null ? source.get(2).getStringValue() : "",
                source.get(3) != null ? source.get(3).getStringValue() : "",
                source.get(4) != null ? source.get(4).getBooleanValue() : false,
                source.get(5) != null ? source.get(5).getStringValue() : "",
                source.get(6) != null ? source.get(6).getBooleanValue() : false,
                source.get(7) != null ? source.get(7).getBooleanValue() : false,
                source.get(8) != null ? source.get(8).getBooleanValue() : true,
                source.get(9) != null ? source.get(9).getStringValue() : "",
                source.get(10) != null ? source.get(10).getStringValue() : "",
                source.get(11) != null ? source.get(11).getStringValue() : "",
                source.get(12) != null ? source.get(12).getStringValue() : ""
        );
    }

    public BriefingPageTypes getType() {
        return BriefingPageTypes.TYPE_CHOICE;
    }
}

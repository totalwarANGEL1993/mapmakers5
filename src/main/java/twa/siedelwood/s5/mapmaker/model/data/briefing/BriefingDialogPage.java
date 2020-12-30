package twa.siedelwood.s5.mapmaker.model.data.briefing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;

import java.util.List;

/**
 * Briefing dialog page model
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BriefingDialogPage implements BriefingPage {
    private String name = "";
    private String entity = "";
    private String title = "";
    private String text = "";
    private boolean dialogCamera = false;
    private String action = "";
    private boolean showSky = false;
    private boolean hideFoW = false;
    private boolean noEscape = false;

    public BriefingDialogPage(String name) {
        this();
        this.name = name;
    }

    public BriefingDialogPage(List<Json> data) {
        name         = data.get(0) != null ? data.get(0).getStringValue() : "";
        entity       = data.get(1) != null ? data.get(1).getStringValue() : "";
        title        = data.get(2) != null ? data.get(2).getStringValue() : "";
        text         = data.get(3) != null ? data.get(3).getStringValue() : "";
        dialogCamera = data.get(4) != null ? data.get(4).getBooleanValue() : false;
        action       = data.get(5) != null ? data.get(5).getStringValue() : "";
        showSky      = data.get(6) != null ? data.get(6).getBooleanValue() : false;
        hideFoW      = data.get(7) != null ? data.get(7).getBooleanValue() : false;
        noEscape     = data.get(8) != null ? data.get(8).getBooleanValue() : false;
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
        return result;
    }

    @Override
    public String toLua() {
        String title = this.title.replaceAll("\"", "{qq}");
        String text  = this.text.replaceAll("\"", "{qq}");
        return String.format(
            "    AP{\n" +
            "        Name            = \"%s\",\n" +
            "        Target          = \"%s\",\n" +
            "        Title           = \"%s\",\n" +
            "        Text            = \"%s\",\n" +
            "        DialogCamera    = %b,\n" +
            "        Action          = %s,\n" +
            "        RenderSky       = %b,\n" +
            "        RenderFow       = %b,\n" +
            "        DisableSkipping = %b,\n" +
            "    };",
            name, entity, title, text, dialogCamera, (action.equals("") ? "nil" : action),
            showSky, !hideFoW, noEscape
        );
    }

    @Override
    public BriefingPage clone() {
        try {
            return BriefingDialogPage.parse(toJson().toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BriefingPage parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonArray source = (JsonArray) parser.parse(json);
        return new BriefingDialogPage(
            source.get(0) != null ? source.get(0).getStringValue() : "",
            source.get(1) != null ? source.get(1).getStringValue() : "",
            source.get(2) != null ? source.get(2).getStringValue() : "",
            source.get(3) != null ? source.get(3).getStringValue() : "",
            source.get(4) != null ? source.get(4).getBooleanValue() : false,
            source.get(5) != null ? source.get(5).getStringValue() : "",
            source.get(6) != null ? source.get(6).getBooleanValue() : false,
            source.get(7) != null ? source.get(7).getBooleanValue() : false,
            source.get(8) != null ? source.get(8).getBooleanValue() : false
        );
    }

    @Override
    public BriefingPageTypes getType() {
        return BriefingPageTypes.TYPE_DIALOG;
    }

    //// Unused in dialog page ////

    @Override
    public String getFirstSelectText() {return "";}

    @Override
    public String getFirstSelectPage() {return "";}

    @Override
    public String getSecondSelectText() {return "";}

    @Override
    public String getSecondSelectPage() {return "";}

    @Override
    public void setFirstSelectText(String text) {}

    @Override
    public void setFirstSelectPage(String page) {}

    @Override
    public void setSecondSelectText(String text) {}

    @Override
    public void setSecondSelectPage(String page) {}
}

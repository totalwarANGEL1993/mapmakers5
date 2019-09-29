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

    public BriefingDialogPage(String name) {
        this();
        this.name = name;
    }

    public BriefingDialogPage(List<Json> data) {
        name = data.get(0).getStringValue();
        entity = data.get(1).getStringValue();
        title = data.get(2).getStringValue();
        text = data.get(3).getStringValue();
        dialogCamera = data.get(4).getBooleanValue();
        action = data.get(5).getStringValue();
        showSky = data.get(6).getBooleanValue();
        hideFoW = data.get(7).getBooleanValue();
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
        return result;
    }

    @Override
    public String toLua() {
        String title = this.title.replaceAll("\"", "{qq}");
        String text  = this.text.replaceAll("\"", "{qq}");
        return String.format("    ASP(\"%s\",\"%s\",\"%s\",\"%s\",%b,%s,%b,%b);", name, entity, title, text, dialogCamera, (action.equals("") ? "nil" : action), showSky, hideFoW);
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
            source.get(0).getStringValue(),
            source.get(1).getStringValue(),
            source.get(2).getStringValue(),
            source.get(3).getStringValue(),
            source.get(4).getBooleanValue(),
            source.get(5).getStringValue(),
            source.get(6).getBooleanValue(),
            source.get(7).getBooleanValue()
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

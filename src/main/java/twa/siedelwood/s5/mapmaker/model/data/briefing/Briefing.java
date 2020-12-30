package twa.siedelwood.s5.mapmaker.model.data.briefing;

import lombok.Data;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;
import twa.lib.typesavejson.models.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Briefing model
 */
@Data
public class Briefing {
    private List<BriefingPage> pages;

    private String name = "";

    public Briefing(String name) {
        pages = new ArrayList<>();
        this.name = name;
    }

    public void addPage(BriefingPage page) {
        pages.add(page);
    }

    public void addPage(BriefingPage page, int pos) {
        pages.add(pos, page);
    }

    public BriefingPage getPage(String name) {
        for (BriefingPage bp : pages) {
            if (bp.getName().equals(name)) {
                return bp;
            }
        }
        return null;
    }

    public BriefingPage getPage(int idx) {
        if (idx < 0 || idx > pages.size() -1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return pages.get(idx);
    }

    public BriefingPage removePage(int idx) {
        if (idx < 0 || idx > pages.size() -1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return pages.remove(idx);
    }

    public BriefingPage removePage(String name) {
        int idx = -1;
        for (BriefingPage bp : pages) {
            idx++;
            if (bp.getName().equals(name)) {
                return pages.remove(idx);
            }
        }
        return null;
    }

    public Briefing clone() {
        try {
            return Briefing.parse(toJson().toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return toJson().toJson();
    }

    public Json toJson() {
        JsonArray pagesArray = new JsonArray();
        for (BriefingPage page : pages) {
            pagesArray.add(page.toJson());
        }

        JsonObject result = new JsonObject();
        result.put("Pages", pagesArray);
        result.put("Name", name);
        return result;
    }

    public String toLua() {
        String lua = "function " +name+ "(_Receiver)\n";
        lua += "    local briefing = {RestoreCamera = true};\n";
        lua += "    local AP = AddPages(briefing);\n\n";

        for (BriefingPage page : pages) {
            lua += page.toLua() + "\n";
        }

        lua += "\n    briefing.Finished = function()\n    end\n";
        lua += "    return StartBriefing(briefing, _Receiver);\n";
        lua += "end\n\n";
        return lua;
    }

    public static Briefing parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject source = (JsonObject) parser.parse(json);
        Briefing briefing = new Briefing(source.get("Name").getStringValue());

        List<Json> sourceList = source.get("Pages").getArray();
        for (Json data : sourceList) {
            int size = data.getArray().size();
            if (size == 1) {
                briefing.pages.add(BriefingSepertorPage.parse(data.toJson()));
            }
            if (size == 2) {
                briefing.pages.add(BriefingRedirectPage.parse(data.toJson()));
            }
            else if (size >= 12) {
                briefing.pages.add(BriefingChoicePage.parse(data.toJson()));
            }
            else {
                briefing.pages.add(BriefingDialogPage.parse(data.toJson()));
            }
        }
        return briefing;
    }

    public Vector<BriefingPage> toPageVector() {
        return new Vector<>(pages);
    }

    public Vector<String> getPageNamesOfType(BriefingPageTypes type) {
        Vector<String> namesVector = new Vector<>();
        for (BriefingPage p : pages) {
            if (p.getType() == BriefingPageTypes.TYPE_DIALOG) {
                namesVector.add(p.getName());
            }
        }
        return namesVector;
    }
}

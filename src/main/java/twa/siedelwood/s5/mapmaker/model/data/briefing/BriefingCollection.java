package twa.siedelwood.s5.mapmaker.model.data.briefing;


import lombok.Data;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * Collection of briefings
 */
@Data
public class BriefingCollection {
    List<Briefing> briefings;

    public BriefingCollection() {
        briefings = new ArrayList<>();
    }

    public BriefingCollection add(Briefing briefing) {
        briefings.add(briefing);
        return this;
    }

    public Briefing remove(int idx) {
        if (idx < 0 || idx > briefings.size() -1) {
            return null;
        }
        return briefings.remove(idx);
    }

    public Briefing remove(String name) {
        int idx = -1;
        for (Briefing b : briefings) {
            idx++;
            if (b.getName().equals(name)) {
                return remove(idx);
            }
        }
        return null;
    }

    public Briefing get(int idx) {
        if (idx < 0 || idx > briefings.size() -1) {
            return null;
        }
        return briefings.get(idx);
    }

    public Briefing get(String name) {
        for (Briefing b : briefings) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
        return null;
    }

    public Vector<String> getAllChoicePageNames() {
        Vector<String> namesVector = new Vector<>();
        for (Briefing b : briefings) {
            for (BriefingPage p : b.getPages()) {
                if (p.getType() == BriefingPageTypes.TYPE_CHOICE) {
                    namesVector.add(p.getName());
                }
            }
        }
        return namesVector;
    }

    @Override
    public String toString() {
        return toJson().toJson();
    }

    public String toLua() {
        String lua = "";
        for (Briefing b : briefings) {
            System.out.println("\n\n\n" + b.toLua());
            lua += b.toLua();
        }
        return lua;
    }

    public Json toJson() {
        JsonArray briefingsArray = new JsonArray();
        for (Briefing briefing : briefings) {
            briefingsArray.add(briefing.toJson());
        }
        return briefingsArray;
    }

    public static BriefingCollection parse(String json) throws Exception {
        JsonParser parser = new JsonParser();
        BriefingCollection briefingCollection = new BriefingCollection();
        JsonArray source = (JsonArray) parser.parse(json);
        for (Json data : source.getArray()) {
            briefingCollection.briefings.add(Briefing.parse(data.toJson()));
        }
        return briefingCollection;
    }

    public Vector<String> toNamesVector() {
        Vector<String> namesVector = new Vector<>();
        for (Briefing b : briefings) {
            namesVector.add(b.getName());
        }
        return namesVector;
    }

    public void sort() {
        Collections.sort(briefings, Comparator.comparing(Briefing::getName));
    }

    public boolean validateName(String name) {
        if (name.length() < 1 && !Pattern.matches("^[a-zA-Z0-9_]+$", name)) {
            return false;
        }
        return true;
    }

    public boolean doesBriefingNameExist(String name) {
        for (Briefing b : briefings) {
            if (b.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean doesChoicePageNameExist(String name, Briefing briefing) {
        for (BriefingPage p : briefing.getPages()) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean doesPageNameExist(String name, Briefing briefing) {
        for (BriefingPage p : briefing.getPages()) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}

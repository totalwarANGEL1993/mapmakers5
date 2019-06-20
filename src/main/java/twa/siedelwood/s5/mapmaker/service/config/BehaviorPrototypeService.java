package twa.siedelwood.s5.mapmaker.service.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationBehaviorModel;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationBehaviorPrototypes;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationParamaterModel;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationProjectModel;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

@Data
@AllArgsConstructor
public class BehaviorPrototypeService {
    private ConfigurationBehaviorPrototypes behaviorPrototypes;

    public BehaviorPrototypeService() {
        behaviorPrototypes = new ConfigurationBehaviorPrototypes();
    }

    public ConfigurationBehaviorModel getBehaviorPrototype(String name) {
        return behaviorPrototypes.getBehavior(name);
    }

    public Vector<String> getBehaviorPrototypeNames() {
        Vector<String> names = new Vector<>();
        for (ConfigurationBehaviorModel b : behaviorPrototypes.getBehaviors()) {
            names.add((b.getType()));
        }
        Collections.sort(names);
        return names;
    }

    public void load(String path) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(path)), Charset.forName("UTF-8"));
        behaviorPrototypes = ConfigurationBehaviorPrototypes.parse(content);
    }

    public String getBehaviorDescription(String name) {
        ConfigurationBehaviorModel behavior = getBehaviorPrototype(name);
        if (behavior != null) {
            return behavior.getDescription();
        }
        return null;
    }

    public List<ConfigurationParamaterModel> getBehaviorAllParameter(String name) {
        ConfigurationBehaviorModel behavior = getBehaviorPrototype(name);
        if (behavior != null) {
            return behavior.getParameters();
        }
        return null;
    }

    public ConfigurationParamaterModel getBehaviorParameter(String name, int idx) {
        ConfigurationBehaviorModel behavior = getBehaviorPrototype(name);
        if (behavior != null) {
            return behavior.getParameter(idx);
        }
        return null;
    }

    public void addBehaviorPrototype(ConfigurationBehaviorModel prototype) {
        behaviorPrototypes.addBehavior(prototype);
    }

    public int size() {
        return behaviorPrototypes.getBehaviors().size();
    }
}

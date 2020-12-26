package twa.siedelwood.s5.mapmaker.service.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationBehaviorModel;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationBehaviorPrototypes;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationParamaterModel;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Service to manage the behavior prototypes.
 *
 * The service will load the default behavior and the custom behavior.
 */
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

    /**
     * Loads the behavior into the service.
     *
     * If the service already contains behavior only new behavior will be added. If the service has no behavior all
     * behavior will be added.
     *
     * @param path Path to file
     * @throws Exception
     */
    public void load(String path) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        ConfigurationBehaviorPrototypes newBehaviorPrototypes = ConfigurationBehaviorPrototypes.parse(content);

        // If behavior already existing then just append new behavior
        if (size() > 0) {
            for (ConfigurationBehaviorModel behavior : newBehaviorPrototypes.getBehaviors()) {
                if (!getBehaviors().contains(behavior)) {
                    getBehaviors().add(behavior);
                }
            }
        }
        // If behavior list is empty just replace the object
        else {
            behaviorPrototypes = newBehaviorPrototypes;
        }
    }

    /**
     * Returns the description of the behavior.
     * @param name Behavior name
     * @return Behavior description
     */
    public String getBehaviorDescription(String name) {
        ConfigurationBehaviorModel behavior = getBehaviorPrototype(name);
        if (behavior != null) {
            return behavior.getDescription();
        }
        return null;
    }

    /**
     * Returns a list with all parameter of the behavior.
     * @param name Behavior name
     * @return Parameter
     */
    public List<ConfigurationParamaterModel> getBehaviorAllParameter(String name) {
        ConfigurationBehaviorModel behavior = getBehaviorPrototype(name);
        if (behavior != null) {
            return behavior.getParameters();
        }
        return null;
    }

    /**
     * Returns the parameter of the behavior at the index.
     * @param name Name of behavior
     * @param idx Indes of parameter
     * @return Parameter data
     */
    public ConfigurationParamaterModel getBehaviorParameter(String name, int idx) {
        ConfigurationBehaviorModel behavior = getBehaviorPrototype(name);
        if (behavior != null) {
            return behavior.getParameter(idx);
        }
        return null;
    }

    /**
     * Adds a behavior to the list of prototypes.
     * @param prototype Behavior
     */
    public void addBehaviorPrototype(ConfigurationBehaviorModel prototype) {
        behaviorPrototypes.addBehavior(prototype);
    }

    /**
     * Returns the amount of behavior.
     * @return Behavior count
     */
    public int size() {
        return behaviorPrototypes.getBehaviors().size();
    }

    /**
     * Returns the amount of behavior.
     * @return Behavior count
     */
    public List<ConfigurationBehaviorModel> getBehaviors() {
        return behaviorPrototypes.getBehaviors();
    }
}

package twa.siedelwood.s5.mapmaker.model.meta;

import lombok.Data;

@Data
public class ConfigurationProgramSettingsModel {
    private String homeDirectory;

    public ConfigurationProgramSettingsModel() {
        homeDirectory = System.getProperty("user.home") + "/MapMaker/workspace";
        System.out.println("Debug: set home directory initially to " +homeDirectory);
    }
}

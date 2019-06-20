package twa.siedelwood.s5.mapmaker.controller;

import lombok.Data;
import twa.lib.typesavejson.JsonParser;
import twa.lib.typesavejson.models.Json;
import twa.lib.typesavejson.models.JsonArray;
import twa.lib.typesavejson.models.JsonObject;
import twa.lib.typesavejson.models.JsonString;
import twa.siedelwood.s5.mapmaker.QuestSystemBehavior;
import twa.siedelwood.s5.mapmaker.model.data.briefing.Briefing;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingCollection;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingDialogPage;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPage;
import twa.siedelwood.s5.mapmaker.model.data.map.Diplomacy;
import twa.siedelwood.s5.mapmaker.model.data.map.MapData;
import twa.siedelwood.s5.mapmaker.model.data.map.Player;
import twa.siedelwood.s5.mapmaker.model.data.quest.Quest;
import twa.siedelwood.s5.mapmaker.model.data.quest.QuestBehavior;
import twa.siedelwood.s5.mapmaker.model.data.quest.QuestCollection;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationProjectModel;
import twa.siedelwood.s5.mapmaker.service.config.BehaviorPrototypeService;
import twa.siedelwood.s5.mapmaker.service.map.MapLoader;
import twa.siedelwood.s5.mapmaker.service.message.MessageService;
import twa.siedelwood.s5.mapmaker.service.config.SelectableValueService;
import twa.siedelwood.s5.mapmaker.service.script.MapScriptBuilder;
import twa.siedelwood.s5.mapmaker.view.swing.frame.WindowFrame;
import twa.siedelwood.s5.mapmaker.view.swing.frame.WorkbenchWindowFrame;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Data
public class ApplicationController {
    private static ApplicationController instance;

    private QuestSystemBehavior questSystemBehavior;
    private MessageService messageService;
    private SelectableValueService selectableValueService;
    private BehaviorPrototypeService behaviorPrototypeService;
    private MapLoader mapLoaderService;
    private MapScriptBuilder mapScriptBuilder;

    private ConfigurationProjectModel currentProject;

    private File workingDirectory;
    private File homeDirectory;
    private File mapArchive;
    private File legacyProject;
    private boolean projectUnsaved = false;
    private WindowFrame workbenchWindow;
    private WindowFrame projectWindow;

    private ApplicationController() {}

    /**
     * Returns singleton instance of the application controller.
     * @return Singleton
     */
    public static ApplicationController getInstance() {
        if (instance == null) {
            instance = new ApplicationController();
        }
        return instance;
    }

    /**
     * Marks the project as unsaved
     * @param unsaved Unsaved flag
     */
    public void setProjectUnsaved(boolean unsaved) {
        projectUnsaved = unsaved;
        updateTitle();
    }

    /**
     * Copies a file to a local path inside the project.
     * @param src Source location
     * @param path Destinated local path
     * @throws ApplicationException Copying failed
     */
    public void copyFile(String src, String path) throws ApplicationException {
        try {
            String projectName = currentProject.getName();
            String localPath = path.replaceAll("\\\\", "/");
            if (localPath.contains("/")) {
                localPath = path.substring(0, path.lastIndexOf("/"));
            }
            File project = new File(getHomeDirectory() + "/" + projectName + "/" + localPath);
            project.mkdirs();

            String destination = getHomeDirectory() + "/" + projectName + "/";
            Files.copy(Paths.get(src), Paths.get(destination + path), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the title of the window.
     */
    public void updateTitle() {
        if (workbenchWindow != null) {
            String title = workbenchWindow.getApplicationName();
            if (projectUnsaved) {
                title += " *";
                return;
            }
            workbenchWindow.setTitle(title);
        }

        if (projectWindow != null) {
            projectWindow.setTitle(projectWindow.getApplicationName());
        }
    }

    /**
     *
     * @param projectName
     * @param projectDescription
     */
    public void createProject(String projectName, String projectDescription) throws ApplicationException {
        try {
            ConfigurationProjectModel project = new ConfigurationProjectModel();
            project.setName(projectName);
            project.setDescription(projectDescription);
            if (mapArchive != null) {
                project.setMapFile(mapArchive.getAbsolutePath().replaceAll("\\\\", "/"));
            }
            currentProject = project;

            currentProject.setQuestCollection(new QuestCollection());
            currentProject.setBriefingCollection(new BriefingCollection());
            currentProject.setMapData(new MapData());
            importLegacyProjectFile();

            workingDirectory.mkdirs();
            String original = System.getProperty("user.dir") + "/lua/mapscript.lua";
            String copied = getWorkingDirectory() + "/mapscript.lua";
            System.out.println("Debug: copy " + original + " to " + copied);
            Files.copy(Paths.get(original), Paths.get(copied));

            internalSaveProject();
            internalLoadProject();
        }
        catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *
     */
    public void importLegacyProjectFile() throws ApplicationException {
        if (legacyProject != null) {
            try {
                JsonParser parser = new JsonParser();
                String legacyProjectContent = new String(Files.readAllBytes(Paths.get(legacyProject.getAbsolutePath())));
                JsonArray legacyProjectData = (JsonArray) parser.parse(legacyProjectContent);

                //// Briefings ////

                BriefingCollection briefingCollection = new BriefingCollection();
                JsonArray briefingData = (JsonArray) ((JsonObject) legacyProjectData.get(0)).get("Briefings");
                for (Json briefing : briefingData.getArray()) {
                    JsonObject briefingJson = (JsonObject) briefing;
                    Briefing briefingObject = new Briefing(briefingJson.get("Name").getStringValue());

                    int pageID = 0;
                    for (Object page : briefingJson.get("Pages").getArray()) {
                        pageID++;
                        List<Json> pageElements = ((Json) page).getArray();
                        pageElements.add(0, new JsonString("DP_" + pageID));
                        BriefingPage pageObject = new BriefingDialogPage(pageElements);
                        briefingObject.addPage(pageObject);
                    }
                    briefingCollection.add(briefingObject);
                }
                currentProject.setBriefingCollection(briefingCollection);

                //// Quests ////

                QuestCollection questCollection = new QuestCollection();
                JsonArray questData = (JsonArray) ((JsonObject) legacyProjectData.get(0)).get("Quests");
                for (Json quest : questData.getArray()) {
                    JsonObject questObject = (JsonObject) quest;
                    Quest newQuest = new Quest();
                    newQuest.setName(questObject.get("Name").getStringValue());
                    newQuest.setTime(Integer.parseInt(questObject.get("Time").getStringValue()));
                    newQuest.setReceiver(Integer.parseInt(questObject.get("Receiver").getStringValue()));
                    newQuest.setTitle(questObject.get("QuestTitle").getStringValue());
                    newQuest.setText(questObject.get("QuestText").getStringValue());
                    newQuest.setType(questObject.get("QuestType").getStringValue());
                    newQuest.setVisible(questObject.get("Visible").getBooleanValue());

                    List<QuestBehavior> behaviors = new ArrayList<>();
                    for (Object data : questObject.get("Behavior").getArray()) {
                        behaviors.add(QuestBehavior.parse(((JsonArray) data).toJson()));
                    }
                    newQuest.setBehaviorList(behaviors);
                    questCollection.add(newQuest);
                }
                currentProject.setQuestCollection(questCollection);

                //// Map settings ////

                MapData mapData = new MapData();
                JsonObject mapSettings = (JsonObject) ((JsonArray) ((JsonObject) legacyProjectData.get(0)).get("Settings")).get(0);
                mapData.setWeatherSet(mapSettings.get("Weather").getStringValue());

                JsonArray debugSettings = (JsonArray) mapSettings.get("Debug");
                mapData.setCheckQuests(debugSettings.get(0).getBooleanValue());
                mapData.setTraceQuests(debugSettings.get(3).getBooleanValue());
                mapData.setUseCheats(debugSettings.get(1).getBooleanValue());
                mapData.setUseShell(debugSettings.get(2).getBooleanValue());

                JsonArray resourceSettings = (JsonArray) mapSettings.get("Resources");
                mapData.setResources(
                    resourceSettings.get(0).getIntValue(),
                    resourceSettings.get(1).getIntValue(),
                    resourceSettings.get(2).getIntValue(),
                    resourceSettings.get(3).getIntValue(),
                    resourceSettings.get(4).getIntValue(),
                    resourceSettings.get(5).getIntValue()
                );

                List<Player> players = new ArrayList<>();
                JsonArray diplomacySettings = (JsonArray) mapSettings.get("Diplomacy");
                JsonArray colorSettings = (JsonArray) mapSettings.get("Colors");
                for (int i = 0; i < colorSettings.size(); i++) {
                    String color = ((JsonArray) colorSettings.get(i)).get(1).getStringValue();
                    String name = ((JsonArray) diplomacySettings.get(i)).get(0).getStringValue();
                    List<Diplomacy> diplomacy = Arrays.asList(
                        new Diplomacy(1, 0), new Diplomacy(2, 0), new Diplomacy(3, 0),
                        new Diplomacy(4, 0), new Diplomacy(5, 0), new Diplomacy(6, 0),
                        new Diplomacy(7, 0), new Diplomacy(8, 0)
                    );
                    players.add(new Player(name, color, diplomacy));
                }
                mapData.setPlayers(players);

                currentProject.setMapData(mapData);

            } catch (Exception e) {
                throw new ApplicationException("Failed to convert legacy project!", e);
            }
        }
    }

    /**
     *
     */
    public void saveProject() throws ApplicationException {
        internalSaveProject();
    }

    public void internalSaveProject() throws ApplicationException {
        if (currentProject != null) {
            try {
                Files.write(Paths.get(workingDirectory.getAbsolutePath() + "/project.json"), currentProject.toJson().toJson().getBytes());
                Files.write(Paths.get(workingDirectory.getAbsolutePath() + "/quests.json"), currentProject.getQuestCollection().toJson().toJson().getBytes());
                Files.write(Paths.get(workingDirectory.getAbsolutePath() + "/briefings.json"), currentProject.getBriefingCollection().toJson().toJson().getBytes());
                Files.write(Paths.get(workingDirectory.getAbsolutePath() + "/mapconfig.json"), currentProject.getMapData().toJson().toJson().getBytes());
            }
            catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
    }

    /**
     *
     */
    public void loadProject(File project) throws ApplicationException {
        try {
            String projectFile = new String(Files.readAllBytes(Paths.get(project.getAbsolutePath() + "/project.json")), Charset.forName("UTF-8"));
            currentProject = ConfigurationProjectModel.parse(projectFile);
            String queststFile = new String(Files.readAllBytes(Paths.get(project.getAbsolutePath() + "/quests.json")), Charset.forName("UTF-8"));
            currentProject.setQuestCollection(QuestCollection.parse(queststFile));
            String brefingsFile = new String(Files.readAllBytes(Paths.get(project.getAbsolutePath() + "/briefings.json")), Charset.forName("UTF-8"));
            currentProject.setBriefingCollection(BriefingCollection.parse(brefingsFile));
            String mapConfFile = new String(Files.readAllBytes(Paths.get(project.getAbsolutePath() + "/mapconfig.json")), Charset.forName("UTF-8"));
            currentProject.setMapData(MapData.parse(mapConfFile));
            internalLoadProject();
        }
        catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     *
     */
    public void internalLoadProject() {
        SwingUtilities.invokeLater(new Thread(() -> {
            if (currentProject != null) {
                try {
                    selectableValueService.setBriefingCollection(currentProject.getBriefingCollection());
                    selectableValueService.setQuestCollection(currentProject.getQuestCollection());
                    behaviorPrototypeService.load("cnf/behaviors.json");

                    try {
                        mapLoaderService.selectMap(currentProject.getMapFile());
                        mapLoaderService.unpackMap();
                        selectableValueService.setScriptNames(mapLoaderService.readScriptNames());
                        mapLoaderService.removeMapFolder();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        getMessageService().displayErrorMessage(
                            "Archivfehler",
                            "Laden der Skriptnamen aus dem Kartenarchiv fehlgeschlagen! Möglicher Weise wurde " +
                            " noch kein Archiv ausgewählt."
                        );
                    }

                    WorkbenchWindowFrame workbench = (WorkbenchWindowFrame) ApplicationController.getInstance().getWorkbenchWindow();
                    workbench.getProjectPanel().loadProjectData(currentProject);
                    workbench.getMapPanel().loadMapData(currentProject.getMapData());
                    workbench.getBriefingPanel().loadBriefingData(currentProject.getBriefingCollection());
                    workbench.getQuestPanel().loadQuestData(currentProject.getQuestCollection());
                    workbench.onViewportResized();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    /**
     *
     */
    public void exportToArchive() {
        try {

            File map = new File(currentProject.getMapFile());
            Files.copy(Paths.get(currentProject.getMapFile()), Paths.get(getWorkingDirectory() + "/" + map.getName()), StandardCopyOption.REPLACE_EXISTING);

            String localMapPath = getWorkingDirectory() + "/" + map.getName();
            mapLoaderService.selectMap(localMapPath);
            mapLoaderService.unpackMap();
            File mapFolder = new File(localMapPath + ".unpacked/maps/externalmap");

            mapScriptBuilder.setMapData(currentProject.getMapData());
            mapScriptBuilder.setQuests(currentProject.getQuestCollection());
            mapScriptBuilder.setBriefings(currentProject.getBriefingCollection());
            String content = mapScriptBuilder.replaceTokensInMapscript("lua/mainmapscript.lua");

            String mainScriptPath = localMapPath + ".unpacked/maps/externalmap/mainmapscript.lua";
            Files.delete(Paths.get(mainScriptPath));
            Files.write(Paths.get(mainScriptPath), content.getBytes(Charset.forName("UTF-8")), StandardOpenOption.CREATE);

            String mapScriptSrc = getWorkingDirectory() + "/mapscript.lua";
            String mapScriptDest = localMapPath + ".unpacked/maps/externalmap/mapscript.lua";
            Files.copy(Paths.get(mapScriptSrc), Paths.get(mapScriptDest), StandardCopyOption.REPLACE_EXISTING);

            String qsbBasePath = "lua/orthus/lua/qsb/";
            mapLoaderService.selectMap(localMapPath + ".unpacked");
            mapLoaderService.add(qsbBasePath + "information.lua", "maps/externalmap/qsb/information.lua");
            mapLoaderService.add(qsbBasePath + "information_ex2.lua", "maps/externalmap/qsb/information_ex2.lua");
            mapLoaderService.add(qsbBasePath + "information_ex3.lua", "maps/externalmap/qsb/information_ex3.lua");
            mapLoaderService.add(qsbBasePath + "interaction.lua", "maps/externalmap/qsb/interaction.lua");
            mapLoaderService.add(qsbBasePath + "oop.lua", "maps/externalmap/qsb/oop.lua");
            mapLoaderService.add(qsbBasePath + "questbehavior.lua", "maps/externalmap/qsb/questbehavior.lua");
            mapLoaderService.add(qsbBasePath + "questdebug.lua", "maps/externalmap/qsb/questdebug.lua");
            mapLoaderService.add(qsbBasePath + "questsystem.lua", "maps/externalmap/qsb/questsystem.lua");

            for (String fileName : currentProject.getIncludes()) {
                mapLoaderService.add(getWorkingDirectory() + "/inc/" +fileName, "maps/externalmap/inc/" +fileName);
            }

            mapLoaderService.packMap();
            mapLoaderService.removeMapFolder();

            getMessageService().displayInfoMessage(
                "Projekt exportiert",
                "<html>Das Projekt wurde als Kartenarchiv exportiert und im Projektverzeichnis gespeichert!" +
                "<br><br>Verteichnis:<br><code>" +getWorkingDirectory()+ "<code></html>"
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            getMessageService().displayErrorMessage(
                "Export fehlgeschlagen",
                "Das Projekt konnte nicht zu einem Kartenarchiv exportiert werden!"
            );
        }
    }

    /**
     *
     */
    public void closeProject() {}

    /**
     *
     */
    public void resetProject() {}

    /**
     *
     * @param selectedFile
     */
    public void setMapArchive(File selectedFile) {
        mapArchive = selectedFile;
    }

    /**
     *
     * @param selectedFile
     */
    public void setLegacyProjectFile(File selectedFile) {
        legacyProject = selectedFile;
    }
}

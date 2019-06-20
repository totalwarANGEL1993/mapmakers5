package twa.siedelwood.s5.mapmaker;

import twa.siedelwood.s5.mapmaker.controller.ApplicationController;
import twa.siedelwood.s5.mapmaker.service.config.BehaviorPrototypeService;
import twa.siedelwood.s5.mapmaker.service.config.SelectableValueService;
import twa.siedelwood.s5.mapmaker.service.map.BBAToolMapLoader;
import twa.siedelwood.s5.mapmaker.service.map.MapLoader;
import twa.siedelwood.s5.mapmaker.service.script.MapScriptBuilder;
import twa.siedelwood.s5.mapmaker.view.swing.SwingMessageService;
import twa.siedelwood.s5.mapmaker.view.swing.frame.ProjectWindowFrame;
import twa.siedelwood.s5.mapmaker.view.swing.frame.WorkbenchWindowFrame;

import javax.swing.*;
import java.io.File;

/**
 * Application class
 */
public class QuestSystemBehavior {
    /**
     * Main method
     * @param args Arguments
     */
    public static void main(String[] args) {
        QuestSystemBehavior program = new QuestSystemBehavior();
        program.run(program);
    }

    /**
     * Runs the application
     */
    public void run(QuestSystemBehavior program) {
        File workingDirectory = new File(System.getProperty("user.home") + "/MapMaker/workspace");
        workingDirectory.mkdirs();

        SwingMessageService swingMessageService = new SwingMessageService();
        SelectableValueService selectableValueService = new SelectableValueService();
        BehaviorPrototypeService behaviorPrototypeService = new BehaviorPrototypeService();
        MapLoader mapLoader = new BBAToolMapLoader();
        MapScriptBuilder mapScriptBuilder = new MapScriptBuilder();

        ApplicationController controller = ApplicationController.getInstance();
        controller.setQuestSystemBehavior(program);
        controller.setHomeDirectory(workingDirectory);
        controller.setMessageService(swingMessageService);
        controller.setSelectableValueService(selectableValueService);
        controller.setBehaviorPrototypeService(behaviorPrototypeService);
        controller.setMapLoaderService(mapLoader);
        controller.setMapScriptBuilder(mapScriptBuilder);

        openProjectSelectionWindow();
    }

    /**
     * Opens the project selection window.
     * (Always the first step)
     */
    public static void openProjectSelectionWindow() {
        ApplicationController controller = ApplicationController.getInstance();

        ProjectWindowFrame main = new ProjectWindowFrame();
        main.initComponents();
        main.getDirectoryField().setText(controller.getHomeDirectory().getAbsolutePath());

        controller.setProjectWindow(main);
        controller.updateTitle();
    }

    /**
     * Starts the application or restarts the project selection.
     * @param newProject
     */
    public static void onProjectHasBeenChosen(boolean newProject) {
        ApplicationController controller = ApplicationController.getInstance();
        try {
            if (controller.getWorkingDirectory() != null && controller.getWorkingDirectory().exists()) {
                openProjectWorkbenchWindow();
                if (newProject) {
                    controller.internalLoadProject();
                }
                else {
                    controller.loadProject(controller.getWorkingDirectory());
                }
                ((JFrame) controller.getProjectWindow()).dispose();
            }
        }
        catch (Exception e) {
            ((JFrame) controller.getWorkbenchWindow()).dispose();
            controller.getMessageService().displayErrorMessage("Fehler", "Das Projekt konnte nicht geöffnet werden! Möglicher Weise fehlen einige der Projektdateien.");
            openProjectSelectionWindow();
        }
    }

    /**
     * Opens the project workbench window.
     */
    public static void openProjectWorkbenchWindow() {
        ApplicationController controller = ApplicationController.getInstance();

        WorkbenchWindowFrame main = new WorkbenchWindowFrame();
        main.initComponents();

        controller.setWorkbenchWindow(main);
        controller.updateTitle();
    }
}

package twa.siedelwood.s5.mapmaker.view.swing.component.panel.project;

import twa.siedelwood.s5.mapmaker.controller.ApplicationController;
import twa.siedelwood.s5.mapmaker.controller.ApplicationException;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationProjectModel;
import twa.siedelwood.s5.mapmaker.service.message.MessageService;
import twa.siedelwood.s5.mapmaker.view.PresentationException;
import twa.siedelwood.s5.mapmaker.view.ViewPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Main panel of the project tab
 */
public class ProjectViewPanel extends JPanel implements ViewPanel {
    protected ProjectPropertiesPanel currentProjectPanel;
    protected ProjectUpdateMapFilePanel updateMapFilePanel;
    protected ProjectListImportsPanel listImportsPanel;
    protected ProjectExportProjectPanel exportProjectPanel;
    private File lastImportedFilePath;

    public ProjectViewPanel() {
        super();
    }

    /**
     * Project is loaded
     * @param project Project
     */
    public void loadProjectData(ConfigurationProjectModel project) {
        currentProjectPanel.loadProjectData(project);
        updateMapFilePanel.loadProjectData(project);
        listImportsPanel.loadProjectData(project);
    }

    /**
     * Project is reverted
     */
    protected void onProjectReverted() {

    }

    /**
     * Project is cloned
     */
    protected void onProjectCloned() {
        JFileChooser chooser = new JFileChooser();
        if (lastImportedFilePath != null) {
            chooser.setCurrentDirectory(lastImportedFilePath);
        }
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Speicherot auswählen");
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            ApplicationController controller = ApplicationController.getInstance();
            try {
                controller.cloneProject(chooser.getSelectedFile());
                SwingUtilities.invokeLater(new Thread(() -> controller.getMessageService().displayInfoMessage(
                    "Klon erstellt", "Eine Kopie des Projektes wurde erstellt."
                )));
            }
            catch (ApplicationException e) {
                e.printStackTrace();
                controller.getMessageService().displayErrorMessage("Fehler", "Das Projekt konnte nicht geklont werden!");
            }
        }
    }

    /**
     * Project is closed
     */
    protected void onProjectClosed() {}

    /**
     * Project is saved
     */
    protected void onProjectSaved() {
        ApplicationController controller = ApplicationController.getInstance();
        ConfigurationProjectModel project = controller.getCurrentProject();

        String infoText = currentProjectPanel.infoText.getText().replaceAll("\n", "{nl}");
        String infoTitle = currentProjectPanel.infoTitle.getText();
        String mapFile = updateMapFilePanel.currentMapFile.getText();

        project.setName(infoTitle);
        project.setDescription(infoText);
        project.setMapFile(mapFile);
    }

    /**
     * User removes an include from the project.
     */
    protected void onFileRemoveFromProject() {
        ApplicationController controller = ApplicationController.getInstance();
        ConfigurationProjectModel project = controller.getCurrentProject();

        int[] indices = listImportsPanel.list.getSelectedIndices();
        for (int i= indices.length-1; i>=0; i--) {
            project.getIncludes().remove(indices[i]);
        }
        listImportsPanel.loadProjectData(project);
    }

    /**
     * User reloads all import files.
     * This will remove all files that does not exist anymore.
     */
    protected void onReloadAllImportsForProject() {
        ApplicationController controller = ApplicationController.getInstance();
        ConfigurationProjectModel project = controller.getCurrentProject();
        File includes = new File(controller.getWorkingDirectory().getAbsoluteFile() + "/inc");

        // Check files still existing
        ArrayList<String> existingIncludes = new ArrayList<>();
        for (String path : project.getIncludes()) {
            File file = new File(path);
            if (file.exists()) {
                existingIncludes.add(path);
            }
        }
        project.getIncludes().clear();
        project.getIncludes().addAll(existingIncludes);
        listImportsPanel.loadProjectData(project);

        // Reload import files
        for (String path : existingIncludes) {
            Path src = Paths.get(path);
            Path dest = Paths.get(includes.getAbsolutePath() + "/" + src.toFile().getName());

            try {
                dest.toFile().mkdirs();
                Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * User want to add an include to the project.
     */
    protected void onFileAddToProject() throws PresentationException {
        try {
            JFileChooser chooser = new JFileChooser();
            if (lastImportedFilePath != null) {
                chooser.setCurrentDirectory(lastImportedFilePath);
            }
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.removeChoosableFileFilter(chooser.getChoosableFileFilters()[0]);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Lua-Skript", "lua", "luac"));
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Cutscene", "xml"));
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Grafik", "png"));
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Sound", "mp3"));
            chooser.setDialogTitle("Import hinzufügen");
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                ApplicationController controller = ApplicationController.getInstance();
                ConfigurationProjectModel project = controller.getCurrentProject();

                File includes = new File(controller.getWorkingDirectory().getAbsoluteFile() + "/inc");
                includes.mkdirs();

                Path src = Paths.get(chooser.getSelectedFile().getAbsolutePath());
                Path dest = Paths.get(includes.getAbsolutePath() + "/" + chooser.getSelectedFile().getName());
                Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                lastImportedFilePath = new File(chooser.getSelectedFile().getAbsolutePath()).getParentFile();
                String filePath = chooser.getSelectedFile().getAbsolutePath().replaceAll("\\\\", "/");
                if (!project.getIncludes().contains(filePath)) {
                    project.getIncludes().add(filePath);
                    Collections.sort(project.getIncludes());
                }
                listImportsPanel.loadProjectData(project);
            }
        }
        catch (Exception e) {
            throw new PresentationException(e);
        }
    }

    /**
     * Project is exported to a playable map.
     */
    private void onProjectExported() {
        ApplicationController.getInstance().exportToArchive();
    }

    /**
     * Returns the hight of the content
     * @return Content height
     */
    public int getContentHeight() {
        return currentProjectPanel.getHeight() + updateMapFilePanel.getHeight() + listImportsPanel.getHeight() + exportProjectPanel.getHeight() +15;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initPanel() {
        setLayout(null);

        currentProjectPanel = new ProjectPropertiesPanel() {
            @Override
            protected void onProjectReverted() {
                ProjectViewPanel.this.onProjectReverted();
            }

            @Override
            protected void onProjectClosed() {
                ProjectViewPanel.this.onProjectClosed();
            }

            @Override
            protected void onProjectSaved() {
                ProjectViewPanel.this.onProjectSaved();
            }

            @Override
            protected void onProjectCloned() {
                ProjectViewPanel.this.onProjectCloned();
            }
        };
        currentProjectPanel.initPanel();
        add(currentProjectPanel);

        updateMapFilePanel = new ProjectUpdateMapFilePanel() {
            @Override
            public void onSearchForNewMap() {
                ProjectViewPanel.this.onSearchForNewMap();
            }

            @Override
            public void onMapFileRealoaded() {
                ProjectViewPanel.this.onMapFileRealoaded();
            }
        };
        updateMapFilePanel.initPanel();
        add(updateMapFilePanel);

        exportProjectPanel = new ProjectExportProjectPanel() {
            @Override
            public void onProjectExported() {
                ProjectViewPanel.this.onProjectExported();
            }
        };

        exportProjectPanel.initPanel();
        add(exportProjectPanel);

        listImportsPanel = new ProjectListImportsPanel() {
            @Override
            protected void onFileAddToProject() {
                try {
                    ProjectViewPanel.this.onFileAddToProject();
                } catch (PresentationException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void onFileRemoveFromProject() {
                ProjectViewPanel.this.onFileRemoveFromProject();
            }

            @Override
            protected void onReloadAllImportsForProject() {
                ProjectViewPanel.this.onReloadAllImportsForProject();
            }
        };
        listImportsPanel.initPanel();
        add(listImportsPanel);
    }

    /**
     * The user searches for a map archive
     */
    public void onSearchForNewMap() {
        ApplicationController controller = ApplicationController.getInstance();
        File map = new File(controller.getCurrentProject().getMapFile());

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.removeChoosableFileFilter(chooser.getChoosableFileFilters()[0]);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Kartenarchiv","s5x"));
        chooser.setDialogTitle("Kartenarchiv wählen");
        if (map.exists()) {
            chooser.setCurrentDirectory(map.getParentFile());
        }
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath().replaceAll("\\\\", "/");
            controller.getCurrentProject().setMapFile(path);
            updateMapFilePanel.getCurrentMapFile().setText(path);
        }
    }

    /**
     * The user reloads the selected map archive
     */
    private void onMapFileRealoaded() {
        ApplicationController controller = ApplicationController.getInstance();
        if (controller.internalReloadMapFile()) {
            controller.getMessageService().displayInfoMessage(
                "Neu geladen",
                "Das Kartenarchiv wurde neu geladen und Skriptnamen sind jetzt aktualisiert!"
            );
        }
    }

    /**
     * Updates the size of all panels after the window was resized.
     * @param reference Reference component
     */
    @Override
    public void updatePanelSize(Component reference) {
        listImportsPanel.updatePanelSize(reference);
        updateMapFilePanel.updatePanelSize(reference);
        exportProjectPanel.updatePanelSize(reference);
        currentProjectPanel.updatePanelSize(reference);
        setSize(reference.getWidth() -10, getContentHeight());
    }

    //// Unused ////

    @Override
    public void setPanelHeight(int height) {

    }

    @Override
    public int getPanelHeight() {
        return 0;
    }

    @Override
    public void setVerticalOffset(int offset) {

    }

    @Override
    public int getVerticalOffset() {
        return 0;
    }

    @Override
    public void setPanelWidth(int height) {

    }

    @Override
    public int getPanelWidth() {
        return 0;
    }
}

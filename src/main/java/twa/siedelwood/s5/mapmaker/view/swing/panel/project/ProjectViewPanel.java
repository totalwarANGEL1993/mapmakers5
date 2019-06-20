package twa.siedelwood.s5.mapmaker.view.swing.panel.project;

import twa.siedelwood.s5.mapmaker.controller.ApplicationController;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationProjectModel;
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

/**
 * Main panel of the project tab
 */
public class ProjectViewPanel extends JPanel implements ViewPanel {
    protected ProjectPropertiesPanel currentProjectPanel;
    protected ProjectUpdateMapFilePanel updateMapFilePanel;
    protected ProjectListImportsPanel listImportsPanel;
    protected ProjectExportProjectPanel exportProjectPanel;

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
    protected void onProjectReverted() {}

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

        String infoText = currentProjectPanel.infoText.getText();
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
     * User want to add an include to the project.
     */
    protected void onFileAddToProject() throws PresentationException {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.removeChoosableFileFilter(chooser.getChoosableFileFilters()[0]);
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Lua-Skript", "lua", "luac"));
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Grafik", "png"));
            chooser.addChoosableFileFilter(new FileNameExtensionFilter("Konfiguration", "xml"));
            chooser.setDialogTitle("Import hinzufügen");
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                ApplicationController controller = ApplicationController.getInstance();
                ConfigurationProjectModel project = controller.getCurrentProject();

                File includes = new File(controller.getWorkingDirectory().getAbsoluteFile() + "/inc");
                includes.mkdirs();

                Path src = Paths.get(chooser.getSelectedFile().getAbsolutePath());
                Path dest = Paths.get(includes.getAbsolutePath() + "/" + chooser.getSelectedFile().getName());
                Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                String fileName = chooser.getSelectedFile().getName();
                if (!project.getIncludes().contains(fileName)) {
                    project.getIncludes().add(fileName);
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
        };
        currentProjectPanel.initPanel();
        add(currentProjectPanel);

        updateMapFilePanel = new ProjectUpdateMapFilePanel();
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
        };
        listImportsPanel.initPanel();
        add(listImportsPanel);
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

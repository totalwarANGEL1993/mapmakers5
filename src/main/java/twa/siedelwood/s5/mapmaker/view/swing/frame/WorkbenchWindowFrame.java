package twa.siedelwood.s5.mapmaker.view.swing.frame;

import twa.siedelwood.s5.mapmaker.QuestSystemBehavior;
import twa.siedelwood.s5.mapmaker.controller.ApplicationController;
import twa.siedelwood.s5.mapmaker.controller.ApplicationException;
import twa.siedelwood.s5.mapmaker.view.swing.panel.briefing.BriefingViewPanel;
import twa.siedelwood.s5.mapmaker.view.swing.panel.mapsettings.MapSettingsViewPanel;
import twa.siedelwood.s5.mapmaker.view.swing.panel.project.ProjectViewPanel;
import twa.siedelwood.s5.mapmaker.view.swing.panel.quest.QuestViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

/**
 * Window for editing the project and it's contents.
 */
public class WorkbenchWindowFrame extends BaseWindowFrame {
    private ProjectViewPanel projectPanel;
    private JScrollPane projectPanelScroll;
    private MapSettingsViewPanel mapPanel;
    private JScrollPane mapPanelScroll;
    private QuestViewPanel questPanel;
    private JScrollPane questPanelScroll;
    private BriefingViewPanel briefingPanel;
    private JScrollPane briefingPanelScroll;

    public WorkbenchWindowFrame() {
        super();
    }
    /**
     * Returns the project tabPanel
     * @return Project tabPanel
     */
    public ProjectViewPanel getProjectPanel() {
        return projectPanel;
    }

    public MapSettingsViewPanel getMapPanel() {
        return mapPanel;
    }

    public QuestViewPanel getQuestPanel() {
        return questPanel;
    }

    public BriefingViewPanel getBriefingPanel() {
        return briefingPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initComponents() {
        super.initComponents();
        setLocationRelativeTo(null);

        initProjectTab();
        initMapSettingsTab();
        initQuestsTab();
        initBriefingTab();

        addCard("Projekt", projectPanelScroll);
        addCard("Map-Assistent", mapPanelScroll);
        addCard("Quest-Assistent", questPanel);
        addCard("Briefing-Assistent", briefingPanel);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                int w = componentEvent.getComponent().getWidth();
                int h = componentEvent.getComponent().getHeight();
                if (w != currentW || h != currentH) {
                    if (tabPanel.getWidth() != currentW || tabPanel.getHeight() != currentH) {
                        onViewportResized();
                    }
                }
            }
        });
    }

    /**
     * Initalizes the briefing tab
     */
    private void initBriefingTab() {
        briefingPanel = new BriefingViewPanel();
        briefingPanel.initPanel();

        briefingPanelScroll = new JScrollPane(briefingPanel);
        briefingPanelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        briefingPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * Initalizes the quest zab
     */
    private void initQuestsTab() {
        questPanel = new QuestViewPanel();
        questPanel.initPanel();

        questPanelScroll = new JScrollPane(questPanel);
        questPanelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        questPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * Initalizes the map settings tab
     */
    private void initMapSettingsTab() {
        mapPanel = new MapSettingsViewPanel();
        mapPanel.initPanel();

        mapPanelScroll = new JScrollPane(mapPanel);
        mapPanelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mapPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * Initalizes the project tab
     */
    private void initProjectTab() {
        projectPanel = new ProjectViewPanel() {
            @Override
            protected void onProjectReverted() {
                super.onProjectReverted();

                ApplicationController controller = ApplicationController.getInstance();

                int result = controller.getMessageService().displayConformDialog(
                    "Projekt zurücksetzen", "Alle nicht gespeicherten Änderungen am Projekt werden zurückgesetzt!"
                );
                if (result == 0) {
                    try {
                        controller.loadProject(controller.getWorkingDirectory());
                    } catch (ApplicationException e) {
                        e.printStackTrace();
                    }
                    projectPanel.loadProjectData(controller.getCurrentProject());
                    mapPanel.loadMapData(controller.getCurrentProject().getMapData());
                    // TODO reset quests
                    // TODO reset briefings
                    controller.getMessageService().displayInfoMessage(
                            "Zurückgesetzt", "Das Projekt wurde neu geladen. Alle nicht gespeicherten Änderungen wurden verworfen."
                    );
                }
            }

            @Override
            protected void onProjectClosed() {
                super.onProjectClosed();
                ((JFrame) ApplicationController.getInstance().getWorkbenchWindow()).dispose();
                ApplicationController.getInstance().setCurrentProject(null);
                ApplicationController.getInstance().setWorkingDirectory(new File(System.getProperty("user.home") + "/MapMaker/workspace"));
                QuestSystemBehavior.openProjectSelectionWindow();
            }

            @Override
            protected void onProjectSaved() {
                super.onProjectSaved();
                try {
                    ApplicationController.getInstance().internalSaveProject();
                } catch (ApplicationException e) {
                    e.printStackTrace();
                }
            }
        };
        projectPanel.initPanel();

        projectPanelScroll = new JScrollPane(projectPanel);
        projectPanelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        projectPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * User has resized the view port.
     */
    @Override
    public void onViewportResized() {
        currentW = getWidth();
        currentH = getHeight();
        projectPanel.updatePanelSize(tabPane);
        projectPanel.setPreferredSize(new Dimension(currentW, projectPanel.getHeight()));
        mapPanel.updatePanelSize(tabPane);
        mapPanel.setPreferredSize(new Dimension(currentW, mapPanel.getHeight()));
        briefingPanel.updatePanelSize(tabPane);
        briefingPanel.setPreferredSize(new Dimension(currentW, briefingPanel.getHeight()));
        questPanel.updatePanelSize(tabPane);
        questPanel.setPreferredSize(new Dimension(currentW, questPanel.getHeight()));
    }
}

package twa.siedelwood.s5.mapmaker.view.swing.component.frame;

import twa.siedelwood.s5.mapmaker.QuestSystemBehavior;
import twa.siedelwood.s5.mapmaker.controller.ApplicationController;
import twa.siedelwood.s5.mapmaker.controller.ApplicationException;
import twa.siedelwood.s5.mapmaker.view.swing.component.panel.briefing.BriefingViewPanel;
import twa.siedelwood.s5.mapmaker.view.swing.component.panel.help.HelpViewPanel;
import twa.siedelwood.s5.mapmaker.view.swing.component.panel.mapsettings.MapSettingsViewPanel;
import twa.siedelwood.s5.mapmaker.view.swing.component.panel.project.ProjectViewPanel;
import twa.siedelwood.s5.mapmaker.view.swing.component.panel.quest.QuestViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
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

    private HelpViewPanel helpPanel;
    private JScrollPane helpPanelScroll;

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
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        initProjectTab();
        initMapSettingsTab();
        initQuestsTab();
        initBriefingTab();
        initHelpTab();

        //helpPanel = new JPanel(null);
        //briefingPanelScroll = new JScrollPane(helpPanel);

        addCard("Projekt", projectPanelScroll);
        addCard("Karte", mapPanelScroll);
        addCard("Quests", questPanel);
        addCard("Briefings", briefingPanel);
        addCard("Über...", helpPanel);

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
    private void initHelpTab() {
        helpPanel = new HelpViewPanel();
        helpPanel.initPanel();

        helpPanelScroll = new JScrollPane(helpPanel);
        helpPanelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        helpPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
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
                ApplicationController controller = ApplicationController.getInstance();

                int result = controller.getMessageService().displayConfirmDialog(
                    "Projekt zurücksetzen", "Alle nicht gespeicherten Änderungen am Projekt werden zurückgesetzt! Bist Du sicher?"
                );
                if (result == 0) {
                    try {
                        super.onProjectReverted();
                        controller.loadProject(controller.getWorkingDirectory());
                        projectPanel.loadProjectData(controller.getCurrentProject());
                        mapPanel.loadMapData(controller.getCurrentProject().getMapData());
                        questPanel.loadQuestData(controller.getCurrentProject().getQuestCollection());
                        briefingPanel.loadBriefingData(controller.getCurrentProject().getBriefingCollection());
                        SwingUtilities.invokeLater(new Thread(() -> controller.getMessageService().displayInfoMessage(
                            "Zurückgesetzt", "Der zuletzt gespeicherte Zustand des Projektes wurde erfolgreich wiederhergestellt."
                        )));
                    }
                    catch (ApplicationException e) {
                        SwingUtilities.invokeLater(new Thread(() -> controller.getMessageService().displayErrorMessage(
                            "Fehler", "Das Projekt konnte nicht zurückgesetzt werden! Möglicher Weise sind die Projektdateien beschädigt."
                        )));
                        e.printStackTrace();
                    }

                }
            }

            @Override
            protected void onProjectClosed() {
                ApplicationController controller = ApplicationController.getInstance();

                int result = controller.getMessageService().displayConfirmDialog(
                    "Projekt schließen", "Alle nicht gespeicherten Änderungen am Projekt werden verworfen. Danach kannst Du ein anderes Projekt auswählen. Fortfahren?"
                );
                if (result == 0) {
                    super.onProjectClosed();
                    ((JFrame) ApplicationController.getInstance().getWorkbenchWindow()).dispose();
                    ApplicationController.getInstance().setCurrentProject(null);
                    ApplicationController.getInstance().setWorkingDirectory(new File(System.getProperty("user.home") + "/MapMaker/workspace"));
                    ApplicationController.getInstance().setHomeDirectory(new File(System.getProperty("user.home") + "/MapMaker/workspace"));
                    ApplicationController.getInstance().getBehaviorPrototypeService().getBehaviorPrototypes().getBehaviors().clear();
                    QuestSystemBehavior.openProjectSelectionWindow();
                }
            }

            @Override
            protected void onProjectSaved() {
                ApplicationController controller = ApplicationController.getInstance();

                try {
                    super.onProjectSaved();
                    ApplicationController.getInstance().internalSaveProject();
                    SwingUtilities.invokeLater(new Thread(() -> controller.getMessageService().displayInfoMessage(
                        "Gespeichert", "Das Projekt wurde erfolgreich gespeichert."
                    )));
                } catch (ApplicationException e) {
                    SwingUtilities.invokeLater(new Thread(() -> controller.getMessageService().displayErrorMessage(
                        "Fehler", "Das Projekt konnte nicht gespeichert werden! Möglicher Weise sind die Projektdateien beschädigt."
                    )));
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
        helpPanel.updatePanelSize(tabPane);
        helpPanel.setPreferredSize(new Dimension(currentW, questPanel.getHeight()));
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        super.windowClosing(windowEvent);
    }
}

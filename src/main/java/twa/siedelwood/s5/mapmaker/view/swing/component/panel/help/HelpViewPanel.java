package twa.siedelwood.s5.mapmaker.view.swing.component.panel.help;

import twa.siedelwood.s5.mapmaker.controller.ApplicationController;
import twa.siedelwood.s5.mapmaker.model.data.briefing.Briefing;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingChoicePage;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingCollection;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingDialogPage;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPage;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPageTypes;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingSepertorPage;
import twa.siedelwood.s5.mapmaker.model.data.quest.QuestCollection;
import twa.siedelwood.s5.mapmaker.view.ViewPanel;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.EnterValueDialog;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.ListSelectValueDialog;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.SelectValueDialog;
import twa.siedelwood.s5.mapmaker.view.swing.component.panel.briefing.BriefingChoicePageProperties;
import twa.siedelwood.s5.mapmaker.view.swing.component.panel.briefing.BriefingDialogPageProperties;
import twa.siedelwood.s5.mapmaker.view.swing.component.panel.briefing.BriefingEditPagePanel;
import twa.siedelwood.s5.mapmaker.view.swing.component.panel.briefing.BriefingSelectBriefingPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.Vector;

/**
 * Main panel for briefings editor tab
 */
public class HelpViewPanel extends JPanel implements ViewPanel {
    private JPanel infoGroup;
    private JLabel infoLabel;
    private int baseWidth = 0;
    private int baseHeight = 0;

    /**
     * Constructor
     */
    public HelpViewPanel() {
        super();
        setLayout(null);
    }

    /**
     * Initalizes the panel
     */
    @Override
    public void initPanel() {
        setLayout(null);

        String version = "?";
        String author  = "?";
        String email   = "?";
        String skype   = "?";
        String discord = "?";
        Properties app = new Properties();
        try {
            app.load(Files.newBufferedReader(Paths.get("cnf/app.properties")));
            version = app.getProperty("app.info.version");
            author  = app.getProperty("app.info.author");
            email   = app.getProperty("app.info.email");
            skype   = app.getProperty("app.info.skype");
            discord = app.getProperty("app.info.discord");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        infoGroup = new JPanel(null);
        infoGroup.setBorder(BorderFactory.createTitledBorder("Info"));
        infoGroup.setVisible(true);
        add(infoGroup);

        infoLabel = new JLabel("<html>" +
                               "<table border=\"0\">" +
                               "<tr><td>Version:</td><td><b>" +version+ "</b></td></tr>" +
                               "<tr><td>Author:</td><td>" +author+ "</td></tr>" +
                               "<tr><td>Email:</td><td><u>" +email+ "</u></td></tr>" +
                               "<tr><td>Skype:</td><td><u>" +skype+ "</u></td></tr>" +
                               "<tr><td>Discord:</td><td><u>" +discord+ "</u></td></tr>" +
                               "</table>" +
                               "</html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setVisible(true);
        infoGroup.add(infoLabel);
    }

    //// Resizing ////

    @Override
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth();
        setSize(reference.getWidth(), getContentHeight());

        infoGroup.setBounds(0, 5, reference.getWidth() -10, 140);
        infoLabel.setBounds(10, 15, reference.getWidth() -30, 110);
    }

    @Override
    public void setPanelHeight(int height) {
        baseHeight = height;
        panelResizedByCode();
    }

    @Override
    public void setPanelWidth(int width) {
        baseWidth = width;
        panelResizedByCode();
    }

    private void panelResizedByCode() {
        setSize(baseWidth + 20, baseHeight);
        updatePanelSize(this);
    }

    @Override
    public int getPanelHeight() {
        return baseHeight;
    }

    @Override
    public int getPanelWidth() {
        return baseWidth;
    }

    /**
     * Returns the conrent height.
     * @return Height
     */
    public int getContentHeight() {
        return 20;
    }

    //// Unused ////

    @Override
    public void setVerticalOffset(int offset) {}

    @Override
    public int getVerticalOffset() {return 0;}
}

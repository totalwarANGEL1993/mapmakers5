package twa.siedelwood.s5.mapmaker.view.swing.component.panel.project;

import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationProjectModel;
import twa.siedelwood.s5.mapmaker.view.ViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectPropertiesPanel extends JPanel implements ViewPanel, ActionListener {
    protected JButton closeProject;
    protected JButton saveProject;
    protected JButton resetProject;
    protected JTextField infoTitle;
    protected JTextArea infoText;
    protected JScrollPane scrollPane;

    public ProjectPropertiesPanel() {
        super();
    }

    /**
     * Writes the data from the project to the components.
     * @param project Project
     */
    public void loadProjectData(ConfigurationProjectModel project) {
        infoTitle.setText(project.getName());
        infoText.setText(project.getDescription());
    }

    /**
     * Returns the title text field
     * @return Title component
     */
    public JTextField getInfoTitle() {
        return infoTitle;
    }

    /**
     * Returns the description text area
     * @return Text component
     */
    public JTextArea getInfoText() {
        return infoText;
    }

    /**
     * Project is reverted
     */
    protected void onProjectReverted() {

    }

    /**
     * Project is closed
     */
    protected void onProjectClosed() {

    }

    /**
     * Project is saved
     */
    protected void onProjectSaved() {

    }

    /**
     * Initializes the panel
     */
    @Override
    public void initPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Aktuelles Projekt"));

        JLabel infoDesc = new JLabel("Titel und Beschreibung des Projektes.");
        infoDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        infoDesc.setVerticalAlignment(JLabel.TOP);
        infoDesc.setVerticalTextPosition(JLabel.TOP);
        infoDesc.setBounds(10, 20, 450, 15);
        add(infoDesc);

        infoTitle = new JTextField("");
        infoTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        infoTitle.setEditable(false);
        add(infoTitle);
        scrollPane = new JScrollPane();
        infoText = new JTextArea("");
        infoText.setFont(new Font("Arial", Font.PLAIN, 12));
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(infoText);
        scrollPane.setVisible(true);
        add(scrollPane);

        JLabel description = new JLabel("Hier kannst Du Dein Projekt speichern und schließen.");
        description.setFont(new Font("Arial", Font.PLAIN, 12));
        description.setVerticalAlignment(JLabel.TOP);
        description.setVerticalTextPosition(JLabel.TOP);
        description.setBounds(10, 140, 450, 15);
        add(description);

        saveProject = new JButton("Speichern");
        saveProject.addActionListener(this);
        add(saveProject);

        closeProject = new JButton("Schließen");
        closeProject.addActionListener(this);
        add(closeProject);

        resetProject = new JButton("Reset");
        resetProject.addActionListener(this);
        resetProject.setVisible(false);
        add(resetProject);
    }

    /**
     * The user resized the window.
     * @param reference Reference component
     */
    @Override
    public void updatePanelSize(Component reference) {
        final int width = reference.getWidth() -5;
        final int height = 195;
        setSize(width, height);
        setLocation(0, 5);

        infoTitle.setBounds(10, 35, width-20, 20);
        scrollPane.setBounds(10, 60, width-20, 75);
        infoText.setSize(width-20, 75);
        saveProject.setBounds(10, 160, 120, 25);
        closeProject.setBounds(140, 160, 120, 25);
        resetProject.setBounds(270, 160, 120, 25);
    }

    //// Unused ////

    @Override
    public void setPanelHeight(int height) {}

    @Override
    public int getPanelHeight() {return 0;}

    @Override
    public void setVerticalOffset(int offset) {}

    @Override
    public int getVerticalOffset() {return 0;}

    @Override
    public void setPanelWidth(int height) {}

    @Override
    public int getPanelWidth() {return 0;}

    //// Listener stuff ////

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == saveProject) {
            onProjectSaved();
        }
        if (actionEvent.getSource() == closeProject) {
            onProjectClosed();
        }
        if (actionEvent.getSource() == resetProject) {
            onProjectReverted();
        }
    }
}

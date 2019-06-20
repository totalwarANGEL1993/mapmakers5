package twa.siedelwood.s5.mapmaker.view.swing.panel.project;

import twa.siedelwood.s5.mapmaker.controller.ApplicationController;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationProjectModel;
import twa.siedelwood.s5.mapmaker.view.ViewPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectUpdateMapFilePanel extends JPanel implements ViewPanel, ActionListener {
    protected JTextField currentMapFile;
    protected JButton currentMapButton;
    protected JLabel currentMapFileLabel;
    protected int width;
    protected int height;

    public ProjectUpdateMapFilePanel() {
        super();
    }

    /**
     * Project is loaded
     * @param project Project
     */
    public void loadProjectData(ConfigurationProjectModel project) {
        currentMapFile.setText(project.getMapFile());
    }

    /**
     * The user searches for a map archive
     */
    public void onSearchForNewMap() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.removeChoosableFileFilter(chooser.getChoosableFileFilters()[0]);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Kartenarchiv","s5x"));
        chooser.setDialogTitle("Kartenarchiv wählen");
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            ApplicationController controller = ApplicationController.getInstance();
            String path = chooser.getSelectedFile().getAbsolutePath().replaceAll("\\\\", "/");
            controller.getCurrentProject().setMapFile(path);
            currentMapFile.setText(path);
        }
    }

    /**
     * Initalizes the panel
     */
    @Override
    public void initPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Map-Datei tauschen"));

        currentMapFileLabel = new JLabel("Tausche die Mapdatei aus, die mit dem Projekt verknüpft ist.");
        currentMapFileLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        currentMapFileLabel.setVerticalAlignment(JLabel.TOP);
        currentMapFileLabel.setVerticalTextPosition(JLabel.TOP);
        add(currentMapFileLabel);

        currentMapFile = new JTextField("");
        currentMapFile.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 12));
        currentMapFile.setEditable(false);
        add(currentMapFile);

        currentMapButton = new JButton("Suchen");
        currentMapButton.addActionListener(this);
        add(currentMapButton);
    }

    /**
     * The user resized the window
     * @param reference Reference component
     */
    @Override
    public void updatePanelSize(Component reference) {
        width = reference.getWidth() -5;
        height = 95;
        setSize(width, height);
        setLocation(0, 205);

        currentMapFileLabel.setBounds(10, 20, width-20, 15);
        currentMapFile.setBounds(10, 35, width-20, 20);
        currentMapButton.setBounds(10, 60, 120, 25);
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

    //// Listener stuff ////

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == currentMapButton) {
            onSearchForNewMap();
        }
    }
}

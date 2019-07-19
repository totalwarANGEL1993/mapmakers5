package twa.siedelwood.s5.mapmaker.view.swing.component.panel.project;

import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationProjectModel;
import twa.siedelwood.s5.mapmaker.view.ViewPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Panel for the import selection
 */
public class ProjectListImportsPanel extends JPanel implements ViewPanel, ListSelectionListener, ActionListener {
    protected JList<String> list;
    protected JScrollPane scrollPane;
    protected JButton addFile;
    protected JButton removeFile;
    protected JButton reloadAll;
    protected JLabel description;
    protected int width;
    protected int height;

    /**
     * Constructor
     */
    public ProjectListImportsPanel() {
        super();
    }

    /**
     * Project is loaded
     * @param project Project
     */
    public void loadProjectData(ConfigurationProjectModel project) {
        list.clearSelection();
        list.setListData(new Vector<>(project.getIncludes()));
    }

    /**
     * Changes the contents of the List
     * @param data
     */
    public void setListData(Vector<String> data) {
        list.setListData(data);
    }

    /**
     * List selection has been changed.
     * @param idx Selected index
     */
    protected void onSelectionChanged(int idx) {}

    /**
     * Called when the user clicked the button for loading projects.
     */
    protected void onFileAddToProject() {}

    /**
     * Called when the user clicked the button for deleting projects.
     */
    protected void onFileRemoveFromProject() {}

    /**
     * Called when the user reloaded all imports.
     */
    protected void onReloadAllImportsForProject() {}

    /**
     * Displays the tabPanel
     */
    @Override
    public void initPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Importdateien"));

        scrollPane = new JScrollPane();
        list = new JList<>();
        list.addListSelectionListener(this);
        scrollPane.setViewportView(list);
        scrollPane.setVisible(true);
        // list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setListData(new Vector<>());
        list.setVisible(true);
        add(scrollPane);

        description = new JLabel("<html>Füge Dateien hinzu, die in die Map inportiert werden. <br>Du kannst Importdateien jeder Zeit wieder entfernen.</html>");
        description.setFont(new Font("Arial", Font.PLAIN, 12));
        description.setVerticalAlignment(JLabel.TOP);
        description.setVerticalTextPosition(JLabel.TOP);
        add(description);

        addFile = new JButton("Hinzufügen");
        addFile.addActionListener(this);
        add(addFile);

        removeFile = new JButton("Entfernen");
        removeFile.addActionListener(this);
        add(removeFile);

        reloadAll = new JButton("Neu laden");
        reloadAll.addActionListener(this);
        add(reloadAll);
    }

    /**
     * The user resized the window
     * @param reference Reference component
     */
    @Override
    public void updatePanelSize(Component reference) {
        width = reference.getWidth() -5;
        height = reference.getHeight() - 410;
        setSize(width, height);
        setLocation(0, 380);

        description.setBounds(10, 20, width -20, 30);
        list.setSize(width-20, height-95);
        scrollPane.setBounds(10, 50, width -20, height-95);
        addFile.setBounds(10, height-35, 120, 25);
        removeFile.setBounds(140, height -35, 120, 25);
        reloadAll.setBounds(270, height -35, 120, 25);
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
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        onSelectionChanged(list.getSelectedIndex());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == addFile) {
            onFileAddToProject();
        }
        if (actionEvent.getSource() == removeFile) {
            onFileRemoveFromProject();
        }
        if (actionEvent.getSource() == reloadAll) {
            onReloadAllImportsForProject();
        }
    }
}

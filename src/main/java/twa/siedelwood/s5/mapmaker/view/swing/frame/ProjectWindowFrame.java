package twa.siedelwood.s5.mapmaker.view.swing.frame;

import twa.siedelwood.s5.mapmaker.controller.ApplicationConstants;
import twa.siedelwood.s5.mapmaker.controller.ApplicationController;
import twa.siedelwood.s5.mapmaker.view.PresentationException;
import twa.siedelwood.s5.mapmaker.view.swing.component.FileNameFilter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.regex.Pattern;

/**
 * Window for opening and creating projects.
 */
public class ProjectWindowFrame extends JFrame implements WindowFrame, ActionListener {
    private int baseW = 500;
    private int baseH = 730;
    private JButton openProjectButton;
    private JButton selectDirectoryButton;
    private JButton createProjectButton;
    private JTextField directoryField;
    private JTextField projectNameField;
    private JTextArea projectDescField;
    private JTextField s5mField;
    private JButton selects5mButton;
    private JTextField mapField;
    private JButton selectmapButton;

    public ProjectWindowFrame() {
        super();
    }

    /**
     * Returns the directory path field.
     * @return Field
     */
    public JTextField getDirectoryField() {
        return directoryField;
    }

    /**
     * Returns the text field with the project name.
     * @return Name field
     */
    public JTextField getProjectNameField() {
        return projectNameField;
    }

    /**
     * Returns the text area with the project description
     * @return Description field
     */
    public JTextArea getProjectDescField() {
        return projectDescField;
    }

    /**
     * Creates the components of the window.
     */
    @Override
    public void initComponents() {
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(baseW, baseH);
        setLocationRelativeTo(null);

        JPanel pane = new JPanel(null);
        pane.setBounds(0, 0, baseW, baseH);
        add(pane);

        //// Open project ////

        JPanel openProjectPanel = new JPanel(null);
        openProjectPanel.setBounds(0, 10, baseW -15, (int) (baseH * 0.15));
        openProjectPanel.setBorder(BorderFactory.createTitledBorder("Projekt öffnen"));
        pane.add(openProjectPanel);

        openProjectButton = new JButton("Project öffnen");
        openProjectButton.setBounds((openProjectPanel.getWidth()/2) -90, openProjectPanel.getHeight() -45, 180, 25);
        openProjectButton.addActionListener(this);
        openProjectPanel.add(openProjectButton);

        JLabel openDesc = new JLabel("<html>Öffne ein Projekt aus einem beliebigen Verzeichnis auf Deinem Computer.</html>");
        openDesc.setBounds(10, 25, openProjectPanel.getWidth() -20, 20);
        openDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        openDesc.setVerticalAlignment(JLabel.TOP);
        openDesc.setVerticalTextPosition(JLabel.TOP);
        openDesc.setHorizontalAlignment(JLabel.CENTER);
        openProjectPanel.add(openDesc);

        //// Create project ////

        JPanel createProjectPanel = new JPanel(null);
        createProjectPanel.setBounds(0, (int) (baseH * 0.15) +20, baseW -15, ((int) (baseH * 0.85)) -50);
        createProjectPanel.setBorder(BorderFactory.createTitledBorder("Projekt anlegen"));
        pane.add(createProjectPanel);

        createProjectButton = new JButton("Project erstellen");
        createProjectButton.setBounds((createProjectPanel.getWidth()/2) -90, createProjectPanel.getHeight() -45, 180, 25);
        createProjectButton.addActionListener(this);
        createProjectPanel.add(createProjectButton);

        JLabel createDesc = new JLabel("<html>Wähle ein Verzeichnis aus und erstelle ein neues Projekt. Dein Projekt benötigt einen Namen. Im Verzeichnis darf sich kein Ordner mit dem gleichen Namen befinden.</html>");
        createDesc.setBounds(10, 25, createProjectPanel.getWidth() -20, 50);
        createDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        createDesc.setVerticalAlignment(JLabel.TOP);
        createDesc.setVerticalTextPosition(JLabel.TOP);
        createDesc.setHorizontalAlignment(JLabel.CENTER);
        createProjectPanel.add(createDesc);

        JLabel dirNameDesc = new JLabel("Verzeichnis");
        dirNameDesc.setBounds(10, 80, createProjectPanel.getWidth() -20, 15);
        dirNameDesc.setFont(new Font("Arial", Font.BOLD, 12));
        createProjectPanel.add(dirNameDesc);

        directoryField = new JTextField("");
        directoryField.setBounds(10, 95, createProjectPanel.getWidth() -20, 20);
        directoryField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        directoryField.setEditable(false);
        createProjectPanel.add(directoryField);

        selectDirectoryButton = new JButton("Durchsuchen");
        selectDirectoryButton.setBounds(10, 120, 150, 25);
        selectDirectoryButton.addActionListener(this);
        createProjectPanel.add(selectDirectoryButton);

        JLabel projectNameDesc = new JLabel("Projektname");
        projectNameDesc.setBounds(10, 170, createProjectPanel.getWidth() -20, 15);
        projectNameDesc.setFont(new Font("Arial", Font.BOLD, 12));
        createProjectPanel.add(projectNameDesc);

        projectNameField = new JTextField("");
        projectNameField.setFont(new Font("Arial", Font.PLAIN, 12));
        projectNameField.setBounds(10, 185, createProjectPanel.getWidth() -20, 20);
        createProjectPanel.add(projectNameField);

        JLabel projectDescDesc = new JLabel("Projektbeschreibung");
        projectDescDesc.setBounds(10, 215, createProjectPanel.getWidth() -20, 15);
        projectDescDesc.setFont(new Font("Arial", Font.BOLD, 12));
        createProjectPanel.add(projectDescDesc);

        JScrollPane scrollPane = new JScrollPane();
        projectDescField = new JTextArea("");
        projectDescField.setFont(new Font("Arial", Font.PLAIN, 12));
        scrollPane.setViewportView(projectDescField);
        scrollPane.setBounds(10, 235, createProjectPanel.getWidth()-20, 75);
        projectDescField.setSize(createProjectPanel.getWidth()-20, 75);
        scrollPane.setVisible(true);
        createProjectPanel.add(scrollPane);

        // Map

        JLabel mapNameDesc = new JLabel("Kartenarchiv auswählen");
        mapNameDesc.setBounds(10, 320, createProjectPanel.getWidth() -20, 15);
        mapNameDesc.setFont(new Font("Arial", Font.BOLD, 12));
        createProjectPanel.add(mapNameDesc);

        mapField = new JTextField("");
        mapField.setBounds(10, 340, createProjectPanel.getWidth() -20, 20);
        mapField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        mapField.setEditable(false);
        createProjectPanel.add(mapField);

        selectmapButton = new JButton("Durchsuchen");
        selectmapButton.setBounds(10, 365, 150, 25);
        selectmapButton.addActionListener(this);
        createProjectPanel.add(selectmapButton);

        // Legacy

        JLabel s5mNameDesc = new JLabel("Legacy-Project importieren");
        s5mNameDesc.setBounds(10, 400, createProjectPanel.getWidth() -20, 15);
        s5mNameDesc.setFont(new Font("Arial", Font.BOLD, 12));
        createProjectPanel.add(s5mNameDesc);

        s5mField = new JTextField("");
        s5mField.setBounds(10, 420, createProjectPanel.getWidth() -20, 20);
        s5mField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        s5mField.setEditable(false);
        createProjectPanel.add(s5mField);

        selects5mButton = new JButton("Durchsuchen");
        selects5mButton.setBounds(10, 445, 150, 25);
        selects5mButton.addActionListener(this);
        createProjectPanel.add(selects5mButton);

        setVisible(true);
    }

    /**
     * Sets the basic window height
     * @param w Width
     * @param h Height
     */
    @Override
    public void setBaseSize(int w, int h) {
        baseW = w;
        baseH = h;
    }

    /**
     * Retzrns the application name.
     * @return Name
     */
    @Override
    public String getApplicationName() {
        return "QuestSystemBehavior";
    }

    /**
     * User want to select a projects home directory.
     */
    private void onProjectSelectDirectoryButtonClicked() {
        ApplicationController controller = ApplicationController.getInstance();
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Projektverzeichnis wählen");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (controller.getWorkingDirectory() != null) {
            chooser.setCurrentDirectory(controller.getWorkingDirectory());
        }
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            controller.setHomeDirectory(chooser.getSelectedFile());
            directoryField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    /**
     * User want to create a new project.
     */
    private void onProjectCreateButtonClicked() throws PresentationException {
        try {
            ApplicationController controller = ApplicationController.getInstance();
            String basePath = controller.getHomeDirectory().getAbsolutePath();

            String projectName = projectNameField.getText();
            if (projectName.length() < 3 || !Pattern.matches(ApplicationConstants.DEFAULT_NAME_REGEX, projectName)) {
                controller.getMessageService().displayErrorMessage("Fehler", "Der Projektname ist zu kurz oder enthält ungültige Zeichen!", this);
                return;
            }

            File newProject = new File(basePath + "/" + projectName);
            if (newProject.exists()) {
                controller.getMessageService().displayErrorMessage("Fehler", "Es existiert bereits ein Verzeichnis mit diesem Namen!", this);
                return;
            }
            controller.setWorkingDirectory(newProject);
            controller.createProject(projectName, projectDescField.getText());
            controller.getQuestSystemBehavior().onProjectHasBeenChosen(true);
        }
        catch (Exception e) {
            throw new PresentationException(e);
        }
    }

    /**
     * User want to open a project.
     */
    private void onProjectOpenButtonClicked() {
        ApplicationController controller = ApplicationController.getInstance();
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Projektverzeichnis wählen");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.removeChoosableFileFilter(chooser.getChoosableFileFilters()[0]);
        if (controller.getWorkingDirectory() != null) {
            chooser.setCurrentDirectory(controller.getWorkingDirectory());
        }
        chooser.addChoosableFileFilter(new FileNameFilter("project.json","Projektdatei"));
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            controller.setHomeDirectory(chooser.getSelectedFile().getParentFile());
            controller.setWorkingDirectory(chooser.getSelectedFile().getParentFile());
            controller.getQuestSystemBehavior().onProjectHasBeenChosen(false);
        }
    }

    /**
     * User selects a map archive.
     */
    private void onSelectMapArchive() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.removeChoosableFileFilter(chooser.getChoosableFileFilters()[0]);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Kartenarchiv","s5x"));
        chooser.setDialogTitle("Kartenarchiv wählen");
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            ApplicationController controller = ApplicationController.getInstance();
            controller.setMapArchive(chooser.getSelectedFile());
            mapField.setText(chooser.getSelectedFile().getAbsolutePath().replaceAll("\\\\", "/"));
        }
    }

    /**
     * User selects a legacy project.
     */
    private void onSelectLegacyProjectFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.removeChoosableFileFilter(chooser.getChoosableFileFilters()[0]);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Legacy-Projekt","s5m"));
        chooser.setDialogTitle("Legacy-Projekt importieren");
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            ApplicationController controller = ApplicationController.getInstance();
            controller.setLegacyProjectFile(chooser.getSelectedFile());
            s5mField.setText(chooser.getSelectedFile().getAbsolutePath().replaceAll("\\\\", "/"));
        }
    }

    //// Listener stuff ////

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openProjectButton) {
            onProjectOpenButtonClicked();
        }
        if (e.getSource() == createProjectButton) {
            try {
                onProjectCreateButtonClicked();
            } catch (PresentationException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getSource() == selectDirectoryButton) {
            onProjectSelectDirectoryButtonClicked();
        }
        if (e.getSource() == selects5mButton) {
            onSelectLegacyProjectFile();
        }
        if (e.getSource() == selectmapButton) {
            onSelectMapArchive();
        }
    }

    //// Unused ////

    @Override
    public void addCard(String name, Component panel) {}

    @Override
    public JPanel getCard(int idx) {return null;}
}

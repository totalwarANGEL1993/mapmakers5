package twa.siedelwood.s5.mapmaker.view.swing.component.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Base window class that does generic window stuff.
 */
public abstract class BaseWindowFrame extends JFrame implements WindowFrame, WindowListener {
    protected String applicationName = "QuestSystemBehavior";
    protected JTabbedPane tabPane;
    protected int baseW = 600;
    protected int baseH = 800;
    protected int currentW = 600;
    protected int currentH = 800;
    protected JPanel tabPanel;
    protected JPanel startPanel;

    public BaseWindowFrame() {
        super();
    }

    /**
     * Returns the application name
     * @return Name
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Creates the basic window.
     */
    public void initComponents() {
        setBounds(0, 0, baseW, baseH);
        addWindowListener(this);
        setMinimumSize(new Dimension(baseW, baseH));

        tabPane = new JTabbedPane();
        tabPane.setVisible(true);

        tabPanel = new JPanel(new GridLayout(1, 1));
        tabPanel.add(tabPane);
        tabPanel.setVisible(true);
        add(tabPanel);

        startPanel = new JPanel(new GridLayout(1, 1));
        startPanel.setVisible(false);

        setVisible(true);
    }

    /**
     * Sets the basic size of the window.
     * @param w Width
     * @param h Height
     */
    public void setBaseSize(int w, int h) {
        baseW = w;
        baseH = h;
    }

    /**
     * Adds a tabPanel to the tabbed tabPane.
     * @param name Tab name
     * @param panel Content
     */
    public void addCard(String name, Component panel) {
        tabPane.addTab(name, panel);
    }

    /**
     * Returns the tab at the index
     * @param idx Index
     * @return Tab at index
     */
    public JPanel getCard(int idx) {
        return (JPanel) tabPane.getTabComponentAt(idx);
    }

    /**
     * Called when window size changes
     */
    public abstract void onViewportResized();

    //// Window listener stuff ////

    @Override
    public void windowOpened(WindowEvent windowEvent) {}

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}

package twa.siedelwood.s5.mapmaker.view.swing.component.panel.project;

import twa.siedelwood.s5.mapmaker.view.ViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectExportProjectPanel extends JPanel implements ViewPanel, ActionListener {
    protected JButton exportButton;
    protected int width;
    protected int height;
    private JLabel exportLabel;

    public ProjectExportProjectPanel() {
        super();
    }

    /**
     * The project is exportet as map archive.
     */
    public void onProjectExported() {}

    @Override
    public void initPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Projekt exportieren"));

        exportLabel = new JLabel("Exportiere Dein Projekt unter Verwendung des BBA-Tool zu einem Kartenarchiv.");
        exportLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        exportLabel.setVerticalAlignment(JLabel.TOP);
        exportLabel.setVerticalTextPosition(JLabel.TOP);
        add(exportLabel);

        exportButton = new JButton("Exportieren");
        exportButton.addActionListener(this);
        add(exportButton);
    }

    @Override
    public void updatePanelSize(Component reference) {
        width = reference.getWidth() -5;
        height = 70;
        setSize(width, height);
        setLocation(0, 305);

        exportLabel.setBounds(10, 20, width-20, 15);
        exportButton.setBounds(10, 35, 120, 25);
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
        if (e.getSource() == exportButton) {
            onProjectExported();
        }
    }
}

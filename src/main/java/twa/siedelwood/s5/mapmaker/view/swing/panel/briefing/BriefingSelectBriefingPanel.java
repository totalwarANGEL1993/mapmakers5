package twa.siedelwood.s5.mapmaker.view.swing.panel.briefing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 *
 */
public class BriefingSelectBriefingPanel extends JPanel implements ActionListener {
    private JComboBox<String> briefingSelection;
    private int height = 120;
    private JLabel briefingInfo;
    private JButton[] buttons;
    private Vector<String> briefingNamesVector;

    public BriefingSelectBriefingPanel() {
        super();
    }

    /**
     *
     */
    public void initPanel() {
        setLayout(null);
        setBorder(BorderFactory.createTitledBorder("Briefings"));

        briefingInfo = new JLabel("<html>Hier kannst Du ein Briefing erstellen, umbenennen oder bearbeiten.</html>");
        briefingInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        briefingInfo.setVerticalAlignment(JLabel.TOP);
        briefingInfo.setVerticalTextPosition(JLabel.TOP);
        add(briefingInfo);

        briefingSelection = new JComboBox<>();
        briefingSelection.addActionListener(this);
        add(briefingSelection);

        //// Buttons ////

        buttons = new JButton[4];

        buttons[0] = new JButton("Neu");
        buttons[0].addActionListener(this);
        add(buttons[0]);

        buttons[1] = new JButton("Kopieren");
        buttons[1].addActionListener(this);
        add(buttons[1]);

        buttons[2] = new JButton("Umbenennen");
        buttons[2].addActionListener(this);
        add(buttons[2]);

        buttons[3] = new JButton("Löschen");
        buttons[3].addActionListener(this);
        add(buttons[3]);
    }

    /**
     *
     */
    protected void onSelectedBriefingChanged() {}

    /**
     *
     */
    protected void onBriefingCreatedClicked() {}

    /**
     *
     */
    protected void onBriefingDeletedClicked() {}

    /**
     *
     */
    protected void onBriefingRenamedClicked() {}

    /**
     *
     */
    protected void onBriefingCopiedClicked() {}

    /**
     *
     * @param name
     */
    public void addSelectableBriefing(String name) {
        briefingSelection.addItem(name);
        briefingNamesVector.add(name);
    }

    /**
     *
     * @param name
     */
    public void replaceSelectableBriefing(String name, String oldName) {
        briefingSelection.removeActionListener(this);
        briefingNamesVector.set(briefingNamesVector.indexOf(oldName), name);
        setSelectableBriefings(briefingNamesVector);
        briefingSelection.addActionListener(this);
    }

    /**
     *
     * @param name
     */
    public void removeSelectableBriefing(String name) {
        briefingSelection.removeActionListener(this);
        briefingNamesVector.remove(name);
        setSelectableBriefings(briefingNamesVector);
        briefingSelection.addActionListener(this);
    }

    /**
     *
     * @param briefings
     */
    public void setSelectableBriefings(Vector<String> briefings) {
        briefingSelection.removeAllItems();
        for (String s : briefings) {
            briefingSelection.addItem(s);
        }
        briefingNamesVector = briefings;
    }

    /**
     *
     * @param name
     */
    public void setSelectedBriefing(String name) {
        briefingSelection.setSelectedItem(name);
    }

    /**
     *
     * @param idx
     */
    public void setSelectedBriefing(int idx) {
        if (briefingSelection.getItemCount() > 0) {
            briefingSelection.setSelectedIndex(idx);
        }
    }

    /**
     *
     */
    public void clearSelection() {
        briefingSelection.setSelectedIndex(-1);
    }

    /**
     *
     */
    public String getSelectedBriefing() {
        return (String) briefingSelection.getSelectedItem();
    }

    /**
     *
     * @return
     */
    public int getSelectedBriefingIndex() {
        if (briefingSelection.getItemCount() == 0) {
            return -1;
        }
        return briefingSelection.getSelectedIndex();
    }

    public void updatePanelSize(Component reference) {
        int width = reference.getWidth() - 10;
        setSize(width, height);
        briefingInfo.setBounds(10, 20, width -20, 15);
        int selectionWidth = (width -20 < 700) ? width -20 : 700;
        briefingSelection.setBounds(10, 40, selectionWidth, 25);
        for (int i=0; i<4; i++) {
            buttons[i].setBounds(10 + (135 * i), height - 40, 130, 25);
        }
    }

    public int getContentHeight() {
        return height;
    }

    //// Listener stuff ////

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == briefingSelection) {
            onSelectedBriefingChanged();
        }
        if (e.getSource() == buttons[0]) {
            onBriefingCreatedClicked();
        }
        if (e.getSource() == buttons[1]) {
            onBriefingCopiedClicked();
        }
        if (e.getSource() == buttons[2]) {
            onBriefingRenamedClicked();
        }
        if (e.getSource() == buttons[3]) {
            onBriefingDeletedClicked();
        }
    }
}

package twa.siedelwood.s5.mapmaker.view.swing.component.panel.mapsettings;

import twa.siedelwood.s5.mapmaker.model.data.map.MapData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Panel for configuring the debug options
 */
public class MapSettingsDebugPanel extends MapSettingsBasePanel implements ActionListener {
    private JLabel checkQuestsLabel;
    private JComboBox<String> checkQuests;
    private JLabel traceQuestsLabel;
    private JComboBox<String> traceQuests;
    private JLabel useCheatsLabel;
    private JComboBox<String> useCheats;
    private JLabel useShellLabel;
    private JComboBox<String> useShell;

    /**
     * Constructor
     */
    public MapSettingsDebugPanel() {
        super();
        height = 285;
    }

    /**
     * Takes a map data object and fills in all fields.
     * @param mapData Map data
     */
    public void loadMapData(MapData mapData) {
        checkQuests.setSelectedIndex((mapData.isCheckQuests()) ? 0 : 1);
        traceQuests.setSelectedIndex((mapData.isTraceQuests()) ? 0 : 1);
        useCheats.setSelectedIndex((mapData.isUseCheats()) ? 0 : 1);
        useShell.setSelectedIndex((mapData.isUseShell()) ? 0 : 1);
    }

    /**
     * Returns the selected index
     * @return Index
     */
    public int getCheckQuests() {
        return checkQuests.getSelectedIndex();
    }

    /**
     * Returns the selected index
     * @return Index
     */
    public int getTraceQuests() {
        return traceQuests.getSelectedIndex();
    }

    /**
     * Returns the selected index
     * @return Index
     */
    public int getUseCheats() {
        return useCheats.getSelectedIndex();
    }

    /**
     * Returns the selected index
     * @return Index
     */
    public int getUseShell() {
        return useShell.getSelectedIndex();
    }

    /**
     * Sets the selected index
     * @param idx Index
     */
    public void setCheckQuests(int idx) {
        checkQuests.setSelectedIndex(idx);
    }

    /**
     * Sets the selected index
     * @param idx Index
     */
    public void setTraceQuests(int idx) {
        traceQuests.setSelectedIndex(idx);
    }

    /**
     * Sets the selected index
     * @param idx Index
     */
    public void setUseCheats(int idx) {
        useCheats.setSelectedIndex(idx);
    }

    /**
     * Sets the selected index
     * @param idx Index
     */
    public void setUseShell(int idx) {
        useShell.setSelectedIndex(idx);
    }

    /**
     * The user submitted the changes.
     */
    @Override
    protected void onChangesSubmitted() {}

    /**
     * Initalizes the panel
     */
    @Override
    public void initPanel() {
        super.initPanel();
        setBorder(BorderFactory.createTitledBorder("Debug-Optionen"));

        checkQuestsLabel = new JLabel("<html>Aktiviere die Pr??fung von Quests. Bevor ein Quest getriggert wird, wird er auf Fehler gepr??ft und ggf. ??bersprungen.</html>");
        checkQuestsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        checkQuestsLabel.setVerticalAlignment(JLabel.TOP);
        checkQuestsLabel.setVerticalTextPosition(JLabel.TOP);
        add(checkQuestsLabel);

        checkQuests = new JComboBox<>();
        checkQuests.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        checkQuests.addItem("aktiv");
        checkQuests.addItem("abgeschaltet");
        checkQuests.addActionListener(this);
        add(checkQuests);

        traceQuestsLabel = new JLabel("<html>Aktiviere die Statusverfolfung. Es wird eine Meldung angezeigt, wenn ein Quest seinen Status wechselt.</html>");
        traceQuestsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        traceQuestsLabel.setVerticalAlignment(JLabel.TOP);
        traceQuestsLabel.setVerticalTextPosition(JLabel.TOP);
        add(traceQuestsLabel);

        traceQuests = new JComboBox<>();
        traceQuests.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        traceQuests.addItem("aktiv");
        traceQuests.addItem("abgeschaltet");
        traceQuests.addActionListener(this);
        add(traceQuests);

        useCheatsLabel = new JLabel("<html>Aktiviere die Development Cheats um Deine Map schneller testen zu k??nnen.</html>");
        useCheatsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        useCheatsLabel.setVerticalAlignment(JLabel.TOP);
        useCheatsLabel.setVerticalTextPosition(JLabel.TOP);
        add(useCheatsLabel);

        useCheats = new JComboBox<>();
        useCheats.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        useCheats.addItem("aktiv");
        useCheats.addItem("abgeschaltet");
        useCheats.addActionListener(this);
        add(useCheats);

        useShellLabel = new JLabel("<html>Aktiviere die Konsole um ??ber spezielle Befehle Abl??ufe zu manipulieren oder nachtr??glich Skripte zu laden.</html>");
        useShellLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        useShellLabel.setVerticalAlignment(JLabel.TOP);
        useShellLabel.setVerticalTextPosition(JLabel.TOP);
        add(useShellLabel);

        useShell = new JComboBox<>();
        useShell.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        useShell.addItem("aktiv");
        useShell.addItem("abgeschaltet");
        useShell.addActionListener(this);
        add(useShell);
    }

    /**
     * The user resized the window
     * @param reference Reference component
     */
    @Override
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth();

        setBounds(0, yOffset, width -30, height);
        checkQuestsLabel.setBounds(10, 20, width -50, 30);
        checkQuests.setBounds(10, 55, 150, 25);
        traceQuestsLabel.setBounds(10, 90, width -50, 30);
        traceQuests.setBounds(10, 125, 150, 25);
        useCheatsLabel.setBounds(10, 160, width -50, 30);
        useCheats.setBounds(10, 180, 150, 25);
        useShellLabel.setBounds(10, 215, width -50, 30);
        useShell.setBounds(10, 250, 150, 25);

        super.updatePanelSize(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkQuests || e.getSource() == traceQuests || e.getSource() == useCheats || e.getSource() == useShell) {
            onChangesSubmitted();
        }
    }
}

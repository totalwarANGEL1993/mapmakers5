package twa.siedelwood.s5.mapmaker.view.swing.component.panel.mapsettings;

import twa.siedelwood.s5.mapmaker.model.data.map.MapData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

/**
 * Panel for configure players (name and color)
 */
public class MapSettingsPlayerConfigPanel extends MapSettingsBasePanel implements FocusListener {
    private JLabel infoDesc;

    private JLabel[] playerNameLabel;
    private JLabel[] playerColorLabel;
    private JTextField[] playerName;
    private JComboBox<String>[] playerColor;
    private JPanel[] playerColorIcon;

    /**
     * Constructor
     */
    public MapSettingsPlayerConfigPanel() {
        super();
        height = 470;
    }

    /**
     * Takes a map data object and fills in all fields.
     * @param mapData Map data
     */
    public void loadMapData(MapData mapData) {
        for (int i=0; i<8; i++) {
            playerName[i].setText(mapData.getPlayers().get(i).getName());
            playerColor[i].setSelectedItem(mapData.getPlayers().get(i).getColor());
        }
    }

    /**
     * The user submitted the changes.
     */
    @Override
    protected void onChangesSubmitted() {}

    /**
     * Sets the vertical offset of this panel.
     * @param height Offset in pixel
     */
    @Override
    public void setPanelHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the possible colors for all players.
     * @param colors
     */
    public void setColorData(Vector<String> colors) {
        for (int i=0; i<8; i++) {
            playerColor[i].removeAllItems();
            for (String item : colors) {
                playerColor[i].addItem(item);
            }
        }
    }

    /**
     * Returns which color has been selected in the combo box.
     * @param playerID Player id
     * @return Index of selected color
     */
    public int getSelectedColorIndex(int playerID) {
        if (playerID < 1 || playerID > 8) {
            return 0;
        }
        return playerColor[playerID-1].getSelectedIndex();
    }

    /**
     * Returns which color has been selected in the combo box.
     * @param playerID Player id
     * @return Selected color
     */
    public String getSelectedColor(int playerID) {
        if (playerID < 1 || playerID > 8) {
            return "DEFAULT_COLOR";
        }
        return (String) playerColor[playerID-1].getSelectedItem();
    }

    /**
     * Returns which color has been selected in the combo box.
     * @param playerID Player id
     * @param idx Index
     */
    public void setSelectedColorIndex(int playerID, int idx) {
        if (playerID < 1 || playerID > 8) {
            return;
        }
        playerColor[playerID-1].setSelectedIndex(idx);
    }

    /**
     * Returns which player name has been entered.
     * @param playerID Player id
     * @return Player name
     */
    public String getPlayerName(int playerID) {
        if (playerID < 1 || playerID > 8) {
            return "";
        }
        return playerName[playerID-1].getText();
    }

    /**
     * Returns which player name has been entered.
     * @param playerID Player id
     * @param name Name
     */
    public void setPlayerName(int playerID, String name) {
        if (playerID < 1 || playerID > 8) {
            return;
        }
        playerName[playerID-1].setText(name);
    }

    /**
     * Initalizes the panel
     */
    @Override
    public void initPanel() {
        super.initPanel();
        setBorder(BorderFactory.createTitledBorder("Spielereinstellungen"));

        infoDesc = new JLabel("<html>Hier kannst Du Einstellungen an den Parteien vornehmen. Du kannst ihre Farbe und den Namen im Diplomatiemenü bestimmen.</html>");
        infoDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        infoDesc.setVerticalAlignment(JLabel.TOP);
        infoDesc.setVerticalTextPosition(JLabel.TOP);
        infoDesc.setBounds(10, 20, 450, 30);
        add(infoDesc);

        playerNameLabel = new JLabel[8];
        playerColorLabel = new JLabel[8];
        playerName = new JTextField[8];
        playerColor = new JComboBox[8];
        playerColorIcon = new JPanel[8];

        int y = 45;
        for (int i=0; i<8; i++) {
            JLabel info = new JLabel("Spieler " + (i+1));
            info.setBounds(10, y+5, 200, 25);
            add(info);

            playerNameLabel[i] = new JLabel("Name");
            playerNameLabel[i].setFont(new Font("Arial", Font.PLAIN, 12));
            add(playerNameLabel[i]);
            playerColorLabel[i] = new JLabel("Farbe");
            playerColorLabel[i].setFont(new Font("Arial", Font.PLAIN, 12));
            add(playerColorLabel[i]);
            playerColor[i] = new JComboBox<>();
            playerColor[i].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            playerColor[i].addActionListener(this);
            add(playerColor[i]);
            playerColorIcon[i] = new JPanel(null);
            playerColorIcon[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(playerColorIcon[i]);
            playerName[i] = new JTextField();
            add(playerName[i]);

            y = y + 50;
        }
    }

    /**
     * The user resized the window
     * @param reference Reference component
     */
    @Override
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth();

        setBounds(0, yOffset, width -30, height);
        infoDesc.setBounds(10, 20, width -50, 30);

        int y = 45;
        for (int i=0; i<8; i++) {
            int nameX = width -30 -180 - 50;
            if (nameX > 320) nameX = 320;

            playerColorLabel[i].setBounds(nameX + 15, y+20, 200, 20);
            playerColorIcon[i].setBounds(nameX + 205, y+40, 20, 20);
            playerColor[i].setBounds(nameX + 15, y+40, 180, 20);
            playerColor[i].addFocusListener(this);

            playerNameLabel[i].setBounds(10, y+20, nameX, 20);
            playerName[i].setBounds(10, y+40, nameX, 20);
            playerName[i].addFocusListener(this);

            y = y + 50;
        }

        super.updatePanelSize(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        for (int i=0; i<8; i++) {
            if (e.getSource() == playerColor[i]) {
                onComboboxValueChanged(playerColor[i], playerColorIcon[i]);
            }
        }
    }

    /**
     *
     * @param stringJComboBox
     * @param jPanel
     */
    private void onComboboxValueChanged(JComboBox<String> stringJComboBox, JPanel jPanel) {
        Color bgColor = new Color(255, 255, 255, 255);
        String value = (String) stringJComboBox.getSelectedItem();
        switch (value) {
            case "ENEMY_COLOR1":
                bgColor = new Color(230, 230, 230);
                break;
            case "ENEMY_COLOR2":
                bgColor = new Color(252, 164, 39);
                break;
            case "ENEMY_COLOR3":
                bgColor = new Color(255, 79, 200);
                break;
            case "NEPHILIM_COLOR":
                bgColor = new Color(226, 0, 0);
                break;
            case "FRIENDLY_COLOR1":
                bgColor = new Color(235, 255, 53);
                break;
            case "FRIENDLY_COLOR2":
                bgColor = new Color(0, 235, 209);
                break;
            case "FRIENDLY_COLOR3":
                bgColor = new Color(255, 150, 214);
                break;
            case "FARMER_COLOR":
                bgColor = new Color(115, 209, 65);
                break;
            case "KERBEROS_COLOR":
                bgColor = new Color(135, 135, 135);
                break;
            case "MERCENARY_COLOR":
                bgColor = new Color(178, 2, 255);
                break;
            case "EVIL_GOVERNOR_COLOR":
                bgColor = new Color(0, 140, 2);
                break;
            case "NPC_COLOR":
                bgColor = new Color(184, 182, 90);
                break;
            case "PLAYER_COLOR":
                bgColor = new Color(15, 64, 255);
                break;
            case "ROBBERS_COLOR":
                bgColor = new Color(57, 57, 57);
                break;
            case "SAINT_COLOR":
                bgColor = new Color(139, 223, 255);
                break;
            case "TRADER_COLOR":
                bgColor = new Color(184, 184, 184);
                break;
        }
        jPanel.setBackground(bgColor);
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        for (int i=0; i<8; i++) {
            if (e.getSource() == playerColor[i] || e.getSource() == playerName[i]) {
                onChangesSubmitted();
            }
        }
    }
}

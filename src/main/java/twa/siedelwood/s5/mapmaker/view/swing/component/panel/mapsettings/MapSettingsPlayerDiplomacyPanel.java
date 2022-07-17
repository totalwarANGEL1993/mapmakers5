package twa.siedelwood.s5.mapmaker.view.swing.component.panel.mapsettings;

import twa.siedelwood.s5.mapmaker.model.data.map.MapData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel for configuring the diplomatic relations between the players.
 */
public class MapSettingsPlayerDiplomacyPanel extends MapSettingsBasePanel implements ActionListener {
    private JLabel infoDesc;

    private JLabel[] playerNameTop;
    private JLabel[] playerNameSide;
    private JComboBox[][] playerDiplomacy;


    public MapSettingsPlayerDiplomacyPanel() {
        super();
        height = 340;
    }

    /**
     * Takes a map data object and fills in all fields.
     * @param mapData Map data
     */
    public void loadMapData(MapData mapData) {
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                playerDiplomacy[i][j].setSelectedIndex(mapData.getPlayers().get(i).getDiplomacy().get(j).getState() -1);
            }
        }
    }

    /**
     * The user submitted the changes.
     */
    @Override
    protected void onChangesSubmitted() {}

    /**
     * Returns the diplomatic state between two players.
     * @param p1 Player 1
     * @param p2 Player 2
     * @return State
     */
    public int getDiplomacyState(int p1, int p2) {
        if (p1 < 1 || p1 > 8 || p2 < 1 || p2 > 8) {
            return 0;
        }
        return playerDiplomacy[p1-1][p2-1].getSelectedIndex() +1;
    }

    /**
     * Sets the diplomatic state between two players.
     * @param p1 Player 1
     * @param p2 Player 2
     * @param state State
     */
    public void setDiplomacyState(int p1, int p2, int state) {
        if (p1 < 1 || p1 > 8 || p2 < 1 || p2 > 8) {
            return;
        }
        playerDiplomacy[p1-1][p2-1].setSelectedIndex(state);
    }

    /**
     * Sets the vertical offset of this panel.
     * @param height Offset in pixel
     */
    @Override
    public void setPanelHeight(int height) {
        this.height = height;
    }

    @Override
    public void initPanel() {
        super.initPanel();
        setBorder(BorderFactory.createTitledBorder("Diplomatie"));

        infoDesc = new JLabel(
            "<html>Hier kannst du die Diplomatie aller Spieler zueinander bei Spielbeginn festlagen. Die" +
            "Beziehungen werden erst nach Reihe, dann nach Spalte gelesen. <br><br>Mögliche Beziehungen:<br>" +
            "<span style=\"color: #fff893\">█</span> Neutral &nbsp;&nbsp;<span style=\"color: #db6767\">█</span>" +
            " Feindlich &nbsp;&nbsp;<span style=\"color: #a6e173\">█</span> Verbündet</html>"
        );
        infoDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        infoDesc.setVerticalAlignment(JLabel.TOP);
        infoDesc.setVerticalTextPosition(JLabel.TOP);
        add(infoDesc);

        playerDiplomacy = new JComboBox[8][8];
        playerNameSide = new JLabel[8];
        playerNameTop = new JLabel[8];

        for (int y=0; y<8; y++) {
            playerNameSide[y] = new JLabel("R. " + (y+1));
            playerNameSide[y].setFont(new Font("Arial", Font.PLAIN, 12));
            add(playerNameSide[y]);

            for (int x=0; x<8; x++) {
                playerNameTop[x] = new JLabel("S. " + (x+1));
                playerNameTop[x].setFont(new Font("Arial", Font.BOLD, 12));
                add(playerNameTop[x]);

                playerDiplomacy[y][x] = new JComboBox();
                playerDiplomacy[y][x].setFont(new Font("Arial", Font.PLAIN, 12));
                playerDiplomacy[y][x].addItem("<html><span style=\"color: #fff893\">██████</span></html>");
                playerDiplomacy[y][x].addItem("<html><span style=\"color: #a6e173\">██████</span></html>");
                playerDiplomacy[y][x].addItem("<html><span style=\"color: #db6767\">██████</span></html>");
                playerDiplomacy[y][x].addActionListener(this);
                if (y == x) {
                    playerDiplomacy[y][x].setVisible(false);
                }
                add(playerDiplomacy[y][x]);
            }
        }
    }

    @Override
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth();

        setBounds(0, yOffset, width -30, height);
        infoDesc.setBounds(10, 20, width -30, 80);

        int x = 55;
        int y = 130;

        for (int i=0; i<8; i++) {
            playerNameSide[i].setBounds(10, y, 50, 20);
            for (int j=0; j<8; j++) {
                if (i == 0) {
                    playerNameTop[j].setBounds(x +5, y-20, 53, 20);
                }
                playerDiplomacy[i][j].setBounds(x, y, 55, 20);
                x = x + 60;
            }
            x = 55;
            y = y + 25;
        }

        super.updatePanelSize(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        for (int y=0; y<8; y++) {
            for (int x=0; x<8; x++) {
                if (e.getSource() == playerDiplomacy[y][x]) {
                    playerDiplomacy[x][y].setSelectedIndex(playerDiplomacy[y][x].getSelectedIndex());
                }
            }
        }
        onChangesSubmitted();
    }
}

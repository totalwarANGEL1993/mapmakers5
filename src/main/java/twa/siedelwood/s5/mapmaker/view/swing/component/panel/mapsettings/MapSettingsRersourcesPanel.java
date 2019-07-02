package twa.siedelwood.s5.mapmaker.view.swing.component.panel.mapsettings;

import twa.siedelwood.s5.mapmaker.model.data.map.MapData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

/**
 * Panel for configuring the players start resources
 */
public class MapSettingsRersourcesPanel extends MapSettingsBasePanel implements FocusListener, PropertyChangeListener {
    private JLabel infoDesc;

    private JLabel[] labels;
    private JFormattedTextField[] fields;


    public MapSettingsRersourcesPanel() {
        super();
        height = 160;
    }

    /**
     * Takes a map data object and fills in all fields.
     * @param mapData Map data
     */
    public void loadMapData(MapData mapData) {
        fields[0].setValue(mapData.getResources().get(0));
        fields[1].setValue(mapData.getResources().get(1));
        fields[2].setValue(mapData.getResources().get(2));
        fields[3].setValue(mapData.getResources().get(3));
        fields[4].setValue(mapData.getResources().get(4));
        fields[5].setValue(mapData.getResources().get(5));
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
     * Get amount of gold.
     * @return Amount
     */
    public int getGold() {
        return Integer.parseInt(fields[0].getText());
    }

    /**
     * Get amount of clay.
     * @return Amount
     */
    public int getClay() {
        return Integer.parseInt(fields[1].getText());
    }

    /**
     * Get amount of wood.
     * @return Amount
     */
    public int getWood() {
        return Integer.parseInt(fields[2].getText());
    }

    /**
     * Get amount of stone.
     * @return Amount
     */
    public int getStone() {
        return Integer.parseInt(fields[3].getText());
    }

    /**
     * Get amount of iron.
     * @return Amount
     */
    public int getIron() {
        return Integer.parseInt(fields[4].getText());
    }

    /**
     * Get amount of sulfur.
     * @return Amount
     */
    public int getSulfur() {
        return Integer.parseInt(fields[5].getText());
    }

    /**
     * Set the amount of gold.
     * @param amount Amount
     */
    public void setGold(int amount) {
        fields[0].setText("" +amount);
    }

    /**
     * Set the amount of clay.
     * @param amount Amount
     */
    public void setClay(int amount) {
        fields[0].setText("" +amount);
    }

    /**
     * Set the amount of wood.
     * @param amount Amount
     */
    public void setWood(int amount) {
        fields[0].setText("" +amount);
    }

    /**
     * Set the amount of stone.
     * @param amount Amount
     */
    public void setStone(int amount) {
        fields[0].setText("" +amount);
    }

    /**
     * Set the amount of iron.
     * @param amount Amount
     */
    public void setIron(int amount) {
        fields[0].setText("" +amount);
    }

    /**
     * Set the amount of sulfur.
     * @param amount Amount
     */
    public void setSulfur(int amount) {
        fields[0].setText("" +amount);
    }

    @Override
    public void initPanel() {
        super.initPanel();
        setBorder(BorderFactory.createTitledBorder("Rohstoffe"));

        infoDesc = new JLabel("<html>Hier werden die Rohstoffe zu Spielbeginn gesetzt.</html>");
        infoDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        infoDesc.setVerticalAlignment(JLabel.TOP);
        infoDesc.setVerticalTextPosition(JLabel.TOP);
        infoDesc.setBounds(10, 20, 450, 30);
        add(infoDesc);

        labels = new JLabel[6];
        fields = new JFormattedTextField[6];

        labels[0] = new JLabel("Taler");
        add(labels[0]);
        labels[1] = new JLabel("Lehm");
        add(labels[1]);
        labels[2] = new JLabel("Holz");
        add(labels[2]);
        labels[3] = new JLabel("Stein");
        add(labels[3]);
        labels[4] = new JLabel("Eisen");
        add(labels[4]);
        labels[5] = new JLabel("Schwefel");
        add(labels[5]);

        for (int i=0; i<6; i++) {
            NumberFormat integerFieldFormatter = NumberFormat.getIntegerInstance();
            integerFieldFormatter.setGroupingUsed(false);
            fields[i] = new JFormattedTextField();
            fields[i].setValue(0);
            fields[i].addFocusListener(this);
            fields[i].addPropertyChangeListener(this);
            add(fields[i]);
        }
    }

    @Override
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth();

        setBounds(0, yOffset, width -30, height);
        infoDesc.setBounds(10, 20, width -50, 30);

        int resourceWidth = (width / 3) -40;
        if (resourceWidth > 250) resourceWidth = 250;
        if (resourceWidth < 170) resourceWidth = 170;

        labels[0].setBounds((resourceWidth * 0) +10, 40, resourceWidth, 15);
        fields[0].setBounds((resourceWidth * 0) +10, 55, resourceWidth, 20);
        labels[1].setBounds((resourceWidth * 1) +20, 40, resourceWidth, 15);
        fields[1].setBounds((resourceWidth * 1) +20, 55, resourceWidth, 20);
        labels[2].setBounds((resourceWidth * 2) +30, 40, resourceWidth, 15);
        fields[2].setBounds((resourceWidth * 2) +30, 55, resourceWidth, 20);

        labels[3].setBounds((resourceWidth * 0) +10, 80, resourceWidth, 15);
        fields[3].setBounds((resourceWidth * 0) +10, 95, resourceWidth, 20);
        labels[4].setBounds((resourceWidth * 1) +20, 80, resourceWidth, 15);
        fields[4].setBounds((resourceWidth * 1) +20, 95, resourceWidth, 20);
        labels[5].setBounds((resourceWidth * 2) +30, 80, resourceWidth, 15);
        fields[5].setBounds((resourceWidth * 2) +30, 95, resourceWidth, 20);

        super.updatePanelSize(this);
    }

    //// Listener stuff ////

    @Override
    public void focusGained(FocusEvent focusEvent) {
        try {
            JFormattedTextField src = (JFormattedTextField) focusEvent.getSource();
            SwingUtilities.invokeLater(src::selectAll);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        try {
            JFormattedTextField src = (JFormattedTextField) propertyChangeEvent.getSource();
            if (src.getText().startsWith("0") && src.getText().length() > 1) {
                src.setText(src.getText().substring(1));
            }
        }
        catch (Exception e) {}
    }
}

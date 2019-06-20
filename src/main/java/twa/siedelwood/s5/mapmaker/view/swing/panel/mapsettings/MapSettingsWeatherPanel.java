package twa.siedelwood.s5.mapmaker.view.swing.panel.mapsettings;

import twa.siedelwood.s5.mapmaker.model.data.map.MapData;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Panel for configure the weather set.
 */
public class MapSettingsWeatherPanel extends MapSettingsBasePanel {
    private JLabel infoDesc;

    private JComboBox<String> weatherSets;

    /**
     * Constructor
     */
    public MapSettingsWeatherPanel() {
        super();
        height = 110;
    }

    /**
     * The user submitted the changes.
     */
    @Override
    protected void onChangesSubmitted() {}

    /**
     * Takes a map data object and fills in all fields.
     * @param mapData Map data
     */
    public void loadMapData(MapData mapData) {
        weatherSets.setSelectedItem(mapData.getWeatherSet());
    }

    /**
     * Adds the selectable items to the weather set combobox.
     * @param sets Vector of sets
     */
    public void setWeatherSets(Vector<String> sets) {
        weatherSets.removeAllItems();
        for (String s : sets) {
            weatherSets.addItem(s);
        }
    }

    /**
     * Sets the selected weather set.
     * @param idx Index
     */
    public void setSelectedSetIndex(int idx) {
        weatherSets.setSelectedIndex(idx);
    }

    /**
     * Returns the selected weather set.
     * @return Index
     */
    public int getSelectedSetIndex() {
        return weatherSets.getSelectedIndex();
    }

    /**
     * Returns the selected weather set.
     * @return Weather set
     */
    public String getSelectedSet() {
        return (String) weatherSets.getSelectedItem();
    }

    /**
     * Sets the vertical offset of this panel.
     * @param height Offset in pixel
     */
    @Override
    public void setPanelHeight(int height) {
        this.height = height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initPanel() {
        super.initPanel();
        setBorder(BorderFactory.createTitledBorder("Wetterset"));

        infoDesc = new JLabel("<html>Das Wetterset bestimmt die Belichtungsverhältnisse im Spiel.</html>");
        infoDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        infoDesc.setVerticalAlignment(JLabel.TOP);
        infoDesc.setVerticalTextPosition(JLabel.TOP);
        infoDesc.setBounds(10, 20, 450, 30);
        add(infoDesc);

        weatherSets = new JComboBox<>();
        weatherSets.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        add(weatherSets);
    }

    /**
     * {@inheritDoc}
     * @param reference Reference component
     */
    @Override
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth();

        setBounds(0, yOffset, width -30, height);
        infoDesc.setBounds(10, 20, width -50, 15);
        weatherSets.setBounds(10, 40, 300, 25);

        super.updatePanelSize(this);
    }
}

package twa.siedelwood.s5.mapmaker.view.swing.component.panel.mapsettings;

import twa.siedelwood.s5.mapmaker.model.data.map.MapData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

/**
 * Panel for configure the weather set.
 */
public class MapSettingsWeatherPanel extends MapSettingsBasePanel implements ActionListener {
    private JLabel infoSet;
    private JLabel infoRandomWeather;

    private JComboBox<String> weatherSets;
    private JComboBox<String> randomizeWeather;

    /**
     * Constructor
     */
    public MapSettingsWeatherPanel() {
        super();
        height = 150;
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
        randomizeWeather.setSelectedIndex(mapData.isRandomizedWeather() ? 0 : 1);
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
     * Returns if randomized weather is used.
     * @return Random weather active
     */
    public boolean getUseRandomizedWeather() {
        return randomizeWeather.getSelectedIndex() == 0;
    }

    /**
     * Activates or deactivates randomized weather.
     * @param active Random weather active
     */
    public void setUseRandomizedWeather(boolean active) {
        randomizeWeather.setSelectedIndex(active ? 0 : 1);
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

        infoSet = new JLabel("<html>Das Wetterset bestimmt die Belichtungsverh??ltnisse im Spiel.</html>");
        infoSet.setFont(new Font("Arial", Font.PLAIN, 12));
        infoSet.setVerticalAlignment(JLabel.TOP);
        infoSet.setVerticalTextPosition(JLabel.TOP);
        add(infoSet);

        weatherSets = new JComboBox<>();
        weatherSets.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        weatherSets.addActionListener(this);
        add(weatherSets);

        infoRandomWeather = new JLabel(
            "<html>" +
            "Aktiviere diese Option, wenn automatische Wetterwechsel f??r das ausgew??hlte Set generiert werden sollen.<br>" +
            "<b>Hinweis</b>: Wetterwechsel sind zuf??llig und k??nnen zu unvorteilhaften Konstellationen f??hren!" +
            "</html>");
        infoRandomWeather.setFont(new Font("Arial", Font.PLAIN, 12));
        infoRandomWeather.setVerticalAlignment(JLabel.TOP);
        infoRandomWeather.setVerticalTextPosition(JLabel.TOP);
        add(infoRandomWeather);

        randomizeWeather = new JComboBox<>();
        randomizeWeather.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        randomizeWeather.addItem("aktiv");
        randomizeWeather.addItem("abgeschaltet");
        randomizeWeather.addActionListener(this);
        add(randomizeWeather);
    }

    /**
     * {@inheritDoc}
     * @param reference Reference component
     */
    @Override
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth();

        setBounds(0, yOffset, width -30, height);
        infoSet.setBounds(10, 20, width -50, 15);
        weatherSets.setBounds(10, 40, 300, 25);
        infoRandomWeather.setBounds(10, 70, width -50, 45);
        randomizeWeather.setBounds(10, 115, 300, 25);

        super.updatePanelSize(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == weatherSets || e.getSource() == randomizeWeather) {
            onChangesSubmitted();
        }
    }
}

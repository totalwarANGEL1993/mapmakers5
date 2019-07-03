package twa.siedelwood.s5.mapmaker.view.swing.component.panel.mapsettings;

import twa.siedelwood.s5.mapmaker.model.data.map.MapData;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for changing the map description (ingame)
 */
public class MapSettingsMapDescriptionPanel extends MapSettingsBasePanel {
    protected JTextField infoTitle;
    protected JTextArea infoText;
    protected JScrollPane scrollPane;

    /**
     * Constructor
     */
    public MapSettingsMapDescriptionPanel() {
        super();
        height = 180;
    }

    /**
     * Takes a map data object and fills in all fields.
     * @param mapData Map data
     */
    public void loadMapData(MapData mapData) {
        setMapTitle(mapData.getMapName());
        setMapDescription(mapData.getMapDescription());
    }

    /**
     * Returns the map title
     * @return Title
     */
    public String getMapTitle() {
        return infoTitle.getText();
    }

    /**
     * Sets the map title
     * @param text Title
     */
    public void setMapTitle(String text) {
        infoTitle.setText(text);
    }

    /**
     * Returns the map description
     * @return Description
     */
    public String getMapDescription() {
        return infoText.getText();
    }

    /**
     * Sets the map description
     * @param text Description
     */
    public void setMapDescription(String text) {
        infoText.setText(text);
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
     * Initalizes the panel
     */
    @Override
    public void initPanel() {
        super.initPanel();
        setBorder(BorderFactory.createTitledBorder("Kartenbeschreibung"));

        infoTitle = new JTextField("");
        infoTitle.setFont(new Font("Arial", Font.PLAIN, 12));
        add(infoTitle);
        scrollPane = new JScrollPane();
        infoText = new JTextArea("");
        infoText.setFont(new Font("Arial", Font.PLAIN, 12));
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(infoText);
        scrollPane.setVisible(true);
        add(scrollPane);

        JLabel infoDesc = new JLabel("Titel und Beschreibung der Map.");
        infoDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        infoDesc.setVerticalAlignment(JLabel.TOP);
        infoDesc.setVerticalTextPosition(JLabel.TOP);
        infoDesc.setBounds(10, 20, 450, 15);
        add(infoDesc);
    }

    /**
     * The user resized the window
     * @param reference Reference component
     */
    @Override
    public void updatePanelSize(Component reference) {
        int width = reference.getWidth();

        setBounds(0, yOffset, width -30, height);
        infoTitle.setBounds(10, 35, width-50, 20);
        scrollPane.setBounds(10, 60, width-50, 75);
        infoText.setSize(width-50, 75);

        super.updatePanelSize(this);
    }
}

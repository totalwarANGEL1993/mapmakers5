package twa.siedelwood.s5.mapmaker.view.swing.component.panel.mapsettings;

import twa.siedelwood.s5.mapmaker.controller.ApplicationController;
import twa.siedelwood.s5.mapmaker.model.data.map.Diplomacy;
import twa.siedelwood.s5.mapmaker.model.data.map.MapData;
import twa.siedelwood.s5.mapmaker.model.data.map.Player;
import twa.siedelwood.s5.mapmaker.service.config.SelectableValueService;
import twa.siedelwood.s5.mapmaker.view.ViewPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Main panel for the map settings tab
 */
public class MapSettingsViewPanel extends JPanel implements ViewPanel {
    private MapSettingsMapDescriptionPanel mapDescriptionPanel;
    private MapSettingsPlayerConfigPanel mapPlayerPanel;
    private MapSettingsRersourcesPanel mapResourcesPanel;
    private MapSettingsWeatherPanel mapWeatherPanel;
    private MapSettingsDebugPanel mapDebugPanel;
    private MapSettingsPlayerDiplomacyPanel mapDiplomacyPanel;

    public MapSettingsViewPanel() {
        super();
    }

    /**
     * Returns the panel for changing the map description.
     * @return Map description panel
     */
    public MapSettingsMapDescriptionPanel getMapDescriptionPanel() {
        return mapDescriptionPanel;
    }

    /**
     * Returns the panel for setup players.
     * @return Player settings panel
     */
    public MapSettingsPlayerConfigPanel getPlayerPanel() {
        return mapPlayerPanel;
    }

    /**
     * Returns the panel for setup diplomacy.
     * @return Diplomacy settings panel
     */
    public MapSettingsPlayerDiplomacyPanel getDiplomacyPanel() {
        return mapDiplomacyPanel;
    }

    /**
     * Returns the panel for setup resources.
     * @return Resources settings panel
     */
    public MapSettingsRersourcesPanel getResourcePanel() {
        return mapResourcesPanel;
    }

    /**
     * Returns the panel for setup weather.
     * @return Weather settings panel
     */
    public MapSettingsWeatherPanel getWeatherPanel() {
        return mapWeatherPanel;
    }

    /**
     * Returns the panel for setup debug.
     * @return Debug settings panel
     */
    public MapSettingsDebugPanel getDebugPanel() {
        return mapDebugPanel;
    }

    /**
     * Takes a map data object and fills in all fields.
     * @param mapData Map data
     */
    public void loadMapData(MapData mapData) {
        mapDescriptionPanel.loadMapData(mapData);
        mapPlayerPanel.loadMapData(mapData);
        mapDiplomacyPanel.loadMapData(mapData);
        mapResourcesPanel.loadMapData(mapData);
        mapWeatherPanel.loadMapData(mapData);
        mapDebugPanel.loadMapData(mapData);
    }

    /**
     * Initalizes the main panel.
     */
    @Override
    public void initPanel() {
        setLayout(null);

        SelectableValueService service = ApplicationController.getInstance().getSelectableValueService();
        service.clearConfig();
        try {
            service.loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createDescriptionPanel();
        createPlayerPanel(service);
        createDiplomacyPanel();
        createResourcesPanel();
        createWeatherPanel(service);
        createMapDebugPanel();
    }

    /**
     * Creates the map description panel.
     */
    private void createDescriptionPanel() {
        mapDescriptionPanel = new MapSettingsMapDescriptionPanel() {
            @Override
            protected void onChangesSubmitted() {
                ApplicationController controller = ApplicationController.getInstance();

                String title = mapDescriptionPanel.getMapTitle();
                title = title.replaceAll("[\"'\\n\\r\\t]", "");
                mapDescriptionPanel.setMapTitle(title);
                controller.getCurrentProject().getMapData().setMapName(title);

                String text = mapDescriptionPanel.getMapDescription();
                text = text.replaceAll("[\"'\\r\\t]", "");
                text = text.replaceAll("\\n", "{cr}");
                mapDescriptionPanel.setMapDescription(text);
                controller.getCurrentProject().getMapData().setMapDescription(text);
            }
        };
        mapDescriptionPanel.initPanel();
        mapDescriptionPanel.setVerticalOffset(5);
        add(mapDescriptionPanel);
    }

    /**
     * Creates the player panel.
     * @param service Combobox content service
     */
    private void createPlayerPanel(SelectableValueService service) {
        mapPlayerPanel = new MapSettingsPlayerConfigPanel() {
            @Override
            protected void onChangesSubmitted() {
                ApplicationController controller = ApplicationController.getInstance();
                List<Player> players = controller.getCurrentProject().getMapData().getPlayers();
                for (int i=0; i<8; i++) {
                    String name = mapPlayerPanel.getPlayerName(i+1);
                    name = name.replaceAll("[\"'\\n\\r\\t]", "");
                    players.get(i).setName(name);

                    String color = mapPlayerPanel.getSelectedColor(i+1);
                    players.get(i).setColor(color);
                }
                controller.getCurrentProject().getMapData().setPlayers(players);
            }
        };
        mapPlayerPanel.initPanel();
        mapPlayerPanel.setVerticalOffset(mapDescriptionPanel.getVerticalOffset() + mapDescriptionPanel.getPanelHeight() + 10);
        mapPlayerPanel.setColorData(service.getPlayerColors());
        add(mapPlayerPanel);
    }

    /**
     * Creates the diplomacy panel.
     */
    private void createDiplomacyPanel() {
        mapDiplomacyPanel = new MapSettingsPlayerDiplomacyPanel() {
            @Override
            protected void onChangesSubmitted() {
                ApplicationController controller = ApplicationController.getInstance();
                List<Player> players = controller.getCurrentProject().getMapData().getPlayers();
                for (int i=0; i<8; i++) {
                    List<Diplomacy> diplomacy = players.get(i).getDiplomacy();
                    for (int j=0; j<8; j++) {
                        int state = mapDiplomacyPanel.getDiplomacyState(i+1, j+1);
                        diplomacy.get(j).setState(state);
                    }
                    players.get(i).setDiplomacy(diplomacy);
                }
                controller.getCurrentProject().getMapData().setPlayers(players);
            }
        };
        mapDiplomacyPanel.initPanel();
        mapDiplomacyPanel.setVerticalOffset(mapPlayerPanel.getVerticalOffset() + mapPlayerPanel.getPanelHeight() + 10);
        add(mapDiplomacyPanel);
    }

    /**
     * Creates the resources panel.
     */
    private void createResourcesPanel() {
        mapResourcesPanel = new MapSettingsRersourcesPanel() {
            @Override
            protected void onChangesSubmitted() {
                ApplicationController controller = ApplicationController.getInstance();
                int[] res = new int[6];
                res[0] = mapResourcesPanel.getGold();
                res[1] = mapResourcesPanel.getClay();
                res[2] = mapResourcesPanel.getWood();
                res[3] = mapResourcesPanel.getStone();
                res[4] = mapResourcesPanel.getIron();
                res[5] = mapResourcesPanel.getSulfur();
                controller.getCurrentProject().getMapData().setResources(res[0], res[1], res[2], res[3], res[4], res[5]);
            }
        };
        mapResourcesPanel.initPanel();
        mapResourcesPanel.setVerticalOffset(mapDiplomacyPanel.getVerticalOffset() + mapDiplomacyPanel.getPanelHeight() + 10);
        add(mapResourcesPanel);
    }

    /**
     * Creates the weather panel.
     * @param service Combobox content service
     */
    private void createWeatherPanel(SelectableValueService service) {
        mapWeatherPanel = new MapSettingsWeatherPanel() {
            @Override
            protected void onChangesSubmitted() {
                ApplicationController controller = ApplicationController.getInstance();
                String set = mapWeatherPanel.getSelectedSet();
                controller.getCurrentProject().getMapData().setWeatherSet(set);
                boolean active = mapWeatherPanel.getUseRandomizedWeather();
                controller.getCurrentProject().getMapData().setRandomizedWeather(active);
            }
        };
        mapWeatherPanel.initPanel();
        mapWeatherPanel.setVerticalOffset(mapResourcesPanel.getVerticalOffset() + mapResourcesPanel.getPanelHeight() + 10);
        mapWeatherPanel.setWeatherSets(service.getWeatherSets());
        add(mapWeatherPanel);
    }

    /**
     * Creates the map debug panel.
     */
    private void createMapDebugPanel() {
        mapDebugPanel = new MapSettingsDebugPanel() {
            @Override
            protected void onChangesSubmitted() {
                ApplicationController controller = ApplicationController.getInstance();
                boolean[] debug = new boolean[4];
                debug[0] = mapDebugPanel.getCheckQuests() == 0;
                debug[1] = mapDebugPanel.getTraceQuests() == 0;
                debug[2] = mapDebugPanel.getUseCheats() == 0;
                debug[3] = mapDebugPanel.getUseShell() == 0;
                controller.getCurrentProject().getMapData().setCheckQuests(debug[0]);
                controller.getCurrentProject().getMapData().setTraceQuests(debug[1]);
                controller.getCurrentProject().getMapData().setUseCheats(debug[2]);
                controller.getCurrentProject().getMapData().setUseShell(debug[3]);
            }
        };
        mapDebugPanel.initPanel();
        mapDebugPanel.setVerticalOffset(mapWeatherPanel.getVerticalOffset() + mapWeatherPanel.getPanelHeight() + 10);
        add(mapDebugPanel);
    }

    /**
     * Updates the panel size.
     * @param reference Reference component
     */
    @Override
    public void updatePanelSize(Component reference) {
        mapDescriptionPanel.updatePanelSize(reference);
        mapPlayerPanel.updatePanelSize(reference);
        mapResourcesPanel.updatePanelSize(reference);
        mapWeatherPanel.updatePanelSize(reference);
        mapDebugPanel.updatePanelSize(reference);
        mapDiplomacyPanel.updatePanelSize(reference);
        setSize(reference.getWidth() -10, getContentHeight());
    }

    /**
     * Returns the conrent height.
     * @return Height
     */
    public int getContentHeight() {
        return mapDescriptionPanel.height + mapPlayerPanel.height + mapResourcesPanel.height +
                mapWeatherPanel.height + mapDebugPanel.height + mapDiplomacyPanel.height +60;
    }

    //// unused ////

    @Override
    public void setPanelHeight(int height) {}

    @Override
    public int getPanelHeight() {
        return 0;
    }

    @Override
    public void setVerticalOffset(int offset) {}

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
}

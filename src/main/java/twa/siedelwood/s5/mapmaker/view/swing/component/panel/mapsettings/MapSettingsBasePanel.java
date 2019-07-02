package twa.siedelwood.s5.mapmaker.view.swing.component.panel.mapsettings;

import twa.siedelwood.s5.mapmaker.view.ViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The basic map settings panel
 */
public abstract class MapSettingsBasePanel extends JPanel implements ViewPanel, ActionListener {
    protected JButton updateButton;
    protected int height = 0;
    protected int yOffset = 0;

    /**
     * Constructor
     */
    public MapSettingsBasePanel() {
        super();
    }

    /**
     * Changes are saved in the project object.
     */
    protected abstract void onChangesSubmitted();

    /**
     * Sets the panel width.
     * @param height Width
     */
    @Override
    public void setPanelWidth(int height) {

    }

    /**
     * Returns the panel width.
     * @return Width
     */
    @Override
    public int getPanelWidth() {
        return 0;
    }

    /**
     * Sets the height of this panel.
     * @param height Height in pixel
     */
    @Override
    public void setPanelHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the panel height.
     * @return Height
     */
    @Override
    public int getPanelHeight() {
        return this.height;
    }

    /**
     * Retunrs the vertical offset of the panel.
     * @return Offset
     */
    @Override
    public int getVerticalOffset() {
        return this.yOffset;
    }

    /**
     * Sets the vertical offset of the panel.
     * @param offset Offset in pixel
     */
    @Override
    public void setVerticalOffset(int offset) {
        this.yOffset = offset;
    }

    /**
     * Initalizes the panel
     */
    @Override
    public void initPanel() {
        setLayout(null);

        updateButton = new JButton("Aktualisieren");
        updateButton.addActionListener(this);
        add(updateButton);
    }

    /**
     * The User resized the window
     * @param reference Reference component
     */
    @Override
    public void updatePanelSize(Component reference) {
        updateButton.setBounds(reference.getWidth() -160, reference.getHeight() -35, 150, 25);
    }

    //// Listener stuff ////

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == updateButton) {
            onChangesSubmitted();
        }
    }
}

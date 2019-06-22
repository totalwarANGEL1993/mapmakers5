package twa.siedelwood.s5.mapmaker.view.swing.component.renderer;

import javax.swing.*;
import java.awt.*;

/**
 * Cell renderer for behavior types.
 */
public class BehaviorCellRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
        Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
        String row = (String) value;
        if (row.startsWith("Goal")) {
            c.setBackground(new Color(180, 255, 200));
        }
        if (row.startsWith("Reprisal")) {
            c.setBackground(new Color(174, 253, 255));
        }
        if (row.startsWith("Reward")) {
            c.setBackground(new Color(255, 198, 183));
        }
        if (row.startsWith("Trigger")) {
            c.setBackground(new Color(212, 179, 255));
        }
        return c;
    }
}

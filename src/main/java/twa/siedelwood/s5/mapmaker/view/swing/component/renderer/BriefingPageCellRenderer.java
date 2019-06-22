package twa.siedelwood.s5.mapmaker.view.swing.component.renderer;

import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPage;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPageTypes;

import javax.swing.*;
import java.awt.*;

/**
 * Cell renderer for briefing page types.
 */
public class BriefingPageCellRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
        Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
        BriefingPage row = (BriefingPage) value;
        if (row.getType() == BriefingPageTypes.TYPE_CHOICE) {
            c.setBackground(new Color(247, 255, 208));
        }
        if (row.getType() == BriefingPageTypes.TYPE_DIALOG) {
            c.setBackground(new Color(200, 216, 255));
        }
        if (row.getType() == BriefingPageTypes.TYPE_EMPTY) {
            c.setBackground(new Color(255, 205, 250));
        }
        if (row.getType() == BriefingPageTypes.TYPE_JUMP) {
            c.setBackground(new Color(212, 179, 255));
        }
        return c;
    }
}

package twa.siedelwood.s5.mapmaker.view.swing.component.renderer;

import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPage;
import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPageTypes;

import javax.swing.*;
import java.awt.*;

/**
 * Cell renderer for briefing page types.
 */
public class BriefingPageCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus );
        BriefingPage row = (BriefingPage) value;
        BriefingPage sel = (BriefingPage) list.getSelectedValue();
        boolean changeColor = sel == null || !sel.equals(row);
        if (row.getType() == BriefingPageTypes.TYPE_CHOICE) {
            if (changeColor) {
                c.setBackground(new Color(247, 255, 208));
            }
        }
        if (row.getType() == BriefingPageTypes.TYPE_DIALOG) {
            if (changeColor) {
                c.setBackground(new Color(209, 255, 205));
            }
        }
        if (row.getType() == BriefingPageTypes.TYPE_EMPTY) {
            if (changeColor) {
                c.setBackground(new Color(255, 205, 250));
            }
        }
        if (row.getType() == BriefingPageTypes.TYPE_JUMP) {
            if (changeColor) {
                c.setBackground(new Color(212, 179, 255));
            }
        }
        return c;
    }
}

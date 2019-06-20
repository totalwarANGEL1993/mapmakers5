package twa.siedelwood.s5.mapmaker.view;

import java.awt.*;

public interface ViewPanel {
    void initPanel();
    void setLayout(LayoutManager layout);
    void updatePanelSize(Component reference);
    void setPanelHeight(int height);
    int getPanelHeight();
    void setVerticalOffset(int offset);
    int getVerticalOffset();
    void setPanelWidth(int height);
    int getPanelWidth();
}

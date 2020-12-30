package twa.siedelwood.s5.mapmaker.view.swing.component.panel.briefing;

import javax.swing.*;

public interface BriefingPageProperties {
    void onTargetOption1Clicked();
    void onTargetOption2Clicked();
    void onConfirm();
    void showDialog();
    void initDialog();
    JTextField getEntity();
}

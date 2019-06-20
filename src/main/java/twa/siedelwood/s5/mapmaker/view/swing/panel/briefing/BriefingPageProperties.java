package twa.siedelwood.s5.mapmaker.view.swing.panel.briefing;

import javax.swing.*;

public interface BriefingPageProperties {
    void onTargetOption2Clicked();
    void onTargetOption1Clicked();
    void onConfirm();
    void showDialog();
    void initDialog();
    JTextField getEntity();
}

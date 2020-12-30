package twa.siedelwood.s5.mapmaker.view.swing.component.panel.briefing;

import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPage;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.BasicConfirmDialog;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.ConfirmDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class BriefingRedirectPageProperties implements ConfirmDialog, ActionListener, BriefingPageProperties {
    private final JFrame frame;
    private final BriefingPage page;
    private int width = 500;
    private int height = 130;
    private BasicConfirmDialog dialog;
    private Boolean accepted;
    protected JTextField targetPage;
    private JButton selectTargetPage;

    /**
     * Constructor
     * @param page Page
     * @param frame Parent
     */
    BriefingRedirectPageProperties(BriefingPage page, JFrame frame) {
        this.page = page;
        this.frame = frame;
    }

    @Override
    public JTextField getEntity() {
        return targetPage;
    }

    /**
     * Page is loaded into the dialog
     */
    private void onPageLoaded() {
        if (dialog != null) {
            targetPage.setText(page.getEntity());
        }
    }

    /**
     * Select target for redirect
     */
    protected void onTargetSelectClicked() {}

    /**
     * Create the dialog window
     */
    @Override
    public void initDialog() {
        dialog = new BasicConfirmDialog(this.frame, "Weiterleitungsseite bearbeiten") {
            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onConfirm() {
                super.onConfirm();
                BriefingRedirectPageProperties.this.onConfirm();
            }
        };
        setSize(width, height);
        dialog.initDialog();

        JLabel targetPageLabel = new JLabel("Ziel der Weiterleitung");
        targetPageLabel.setBounds(10, 10, 140, 15);
        dialog.add(targetPageLabel);
        targetPage = new JTextField();
        targetPage.setBounds(160, 10, 200, 25);
        targetPage.setFont(new Font("Arial", Font.PLAIN, 12));
        dialog.add(targetPage);
        selectTargetPage = new JButton("...");
        selectTargetPage.setBounds(360, 10, 30, 24);
        selectTargetPage.addActionListener(this);
        dialog.add(selectTargetPage);

        onPageLoaded();
    }

    /**
     * Display the dialog
     */
    @Override
    public void showDialog() {
        if (dialog != null) {
            dialog.showDialog();
        }
    }

    /**
     * User cancled the dialog
     */
    @Override
    public void onCancel() {}

    /**
     * User confirmed the dialog
     */
    @Override
    public void onConfirm() {
        page.setEntity(targetPage.getText());
    }

    @Override
    public void setSize(int w, int h) {
        width = w;
        height = h;
        if (dialog != null) {
            dialog.setSize(w, h);
        }
    }

    @Override
    public boolean isAccepted() {
        return accepted;
    }

    @Override
    public boolean isUndecided() {
        return accepted == null;
    }

    @Override
    public void setUndecided() {
        accepted = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectTargetPage) {
            onTargetSelectClicked();
        }
    }

    @Override
    public void onTargetOption1Clicked() {}

    @Override
    public void onTargetOption2Clicked() {}
}

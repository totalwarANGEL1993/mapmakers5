package twa.siedelwood.s5.mapmaker.view.swing.component.panel.briefing;

import twa.siedelwood.s5.mapmaker.model.data.briefing.BriefingPage;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.BasicConfirmDialog;
import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.ConfirmDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BriefingDialogPageProperties implements ConfirmDialog, ActionListener, BriefingPageProperties {
    private final JFrame frame;
    private final BriefingPage page;
    private int width = 500;
    private int height = 455;
    private BasicConfirmDialog dialog;
    private Boolean accepted;
    private JTextField title;
    JTextField entity;
    private JTextField action;
    private JComboBox<String> dialogCam;
    private JComboBox<String> showSky;
    private JComboBox<String> hideFoW;
    private JTextArea text;
    private JButton selEntity;

    public BriefingDialogPageProperties(BriefingPage page, JFrame frame) {
        this.page = page;
        this.frame = frame;
    }

    /**
     *
     * @param pos
     */
    public void bypassPagePosition(String pos) {
        entity.setText(pos);
    }

    /**
     *
     */
    protected void onPageLoaded() {
        if (dialog != null) {
            entity.setText(page.getEntity());
            title.setText(page.getTitle());
            text.setText(page.getText());
            action.setText(page.getAction());

            dialogCam.setSelectedIndex(page.isDialogCamera() ? 0 : 1);
            showSky.setSelectedIndex(page.isShowSky() ? 0 : 1);
            hideFoW.setSelectedIndex(page.isHideFoW() ? 0 : 1);
        }
    }

    /**
     *
     */
    @Override
    public void initDialog() {
        dialog = new BasicConfirmDialog(this.frame, "Dialogseite bearbeiten") {
            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onConfirm() {
                super.onConfirm();
                BriefingDialogPageProperties.this.onConfirm();
            }
        };
        setSize(width, height);
        //dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.initDialog();

        JLabel titleLabel = new JLabel("Titel der Seite");
        titleLabel.setBounds(10, 10, 140, 15);
        dialog.add(titleLabel);
        title = new JTextField();
        title.setFont(new Font("Arial", Font.PLAIN, 12));
        title.setBounds(160, 10, width-185, 25);
        dialog.add(title);

        JLabel textLabel = new JLabel("Inhalt der Seite");
        textLabel.setBounds(10, 45, 140, 15);
        dialog.add(textLabel);
        JScrollPane scrollPane = new JScrollPane();
        text = new JTextArea("");
        text.setFont(new Font("Arial", Font.PLAIN, 12));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(text);
        scrollPane.setBounds(160, 40, width-185, 120);
        scrollPane.setVisible(true);
        dialog.add(scrollPane);

        JLabel entityLabel = new JLabel("Position");
        entityLabel.setBounds(10, 185, 140, 15);
        dialog.add(entityLabel);
        entity = new JTextField();
        entity.setBounds(160, 185, 200, 25);
        entity.setFont(new Font("Arial", Font.PLAIN, 12));
        dialog.add(entity);
        selEntity = new JButton("...");
        selEntity.setBounds(360, 185, 30, 24);
        selEntity.addActionListener(this);
        dialog.add(selEntity);


        JLabel dialogCamLabel = new JLabel("Dialogkamera");
        dialogCamLabel.setBounds(10, 220, 140, 15);
        dialog.add(dialogCamLabel);
        dialogCam = new JComboBox<String>();
        dialogCam.addItem("aktiv");
        dialogCam.addItem("abgeschaltet");
        dialogCam.setBounds(160, 220, 150, 25);
        dialog.add(dialogCam);

        JLabel actionLabel = new JLabel("Lua-Funktion");
        actionLabel.setBounds(10, 255, 140, 15);
        dialog.add(actionLabel);
        action = new JTextField();
        action.setFont(new Font("Arial", Font.PLAIN, 12));
        action.setBounds(160, 255, width-185, 25);
        dialog.add(action);

        JLabel skyLabel = new JLabel("Himmel anzeigen");
        skyLabel.setBounds(10, 290, 140, 15);
        dialog.add(skyLabel);;
        showSky = new JComboBox<String>();
        showSky.addItem("aktiv");
        showSky.addItem("abgeschaltet");
        showSky.setBounds(160, 290, 150, 25);
        dialog.add(showSky);

        JLabel fowLabel = new JLabel("FoW abschalten");
        fowLabel.setBounds(10, 325, 140, 15);
        dialog.add(fowLabel);;
        hideFoW = new JComboBox<String>();
        hideFoW.addItem("aktiv");
        hideFoW.addItem("abgeschaltet");
        hideFoW.setBounds(160, 325, 150, 25);
        dialog.add(hideFoW);

        onPageLoaded();
    }

    @Override
    public JTextField getEntity() {
        return entity;
    }

    /**
     *
     */
    @Override
    public void showDialog() {
        if (dialog != null) {
            dialog.showDialog();
        }
    }

    /**
     *
     */
    @Override
    public void onCancel() {}

    /**
     *
     */
    @Override
    public void onConfirm() {
        String text = this.text.getText().replaceAll("\\n", "{cr}");
        page.setText(text);

        page.setTitle(title.getText());
        page.setEntity(entity.getText());
        page.setAction(action.getText());

        page.setDialogCamera(dialogCam.getSelectedIndex() == 0);
        page.setShowSky(showSky.getSelectedIndex() == 0);
        page.setHideFoW(hideFoW.getSelectedIndex() == 0);
    }

    /**
     *
     */
    protected void onEntitySelectClicked() {

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
        if (e.getSource() == selEntity) {
            onEntitySelectClicked();
        }
    }

    @Override
    public void onTargetOption2Clicked() {}

    @Override
    public void onTargetOption1Clicked() {}
}

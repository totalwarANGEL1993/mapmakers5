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
public class BriefingChoicePageProperties implements ConfirmDialog, ActionListener, BriefingPageProperties {
    private final JFrame frame;
    private final BriefingPage page;
    private int width = 500;
    private int height = 575;
    private BasicConfirmDialog dialog;
    private Boolean accepted;
    private JTextField title;
    protected JTextField entity;
    private JTextField action;
    private JComboBox<String> dialogCam;
    private JComboBox<String> showSky;
    private JComboBox<String> hideFoW;
    private JTextArea text;
    private JButton selEntity;
    JTextField op1Target;
    private JButton selOp1;
    JTextField op2Target;
    private JButton selOp2;
    private JTextField op2Text;
    private JTextField op1Text;

    /**
     * Constructor
     * @param page Page
     * @param frame Parent
     */
    BriefingChoicePageProperties(BriefingPage page, JFrame frame) {
        this.page = page;
        this.frame = frame;
    }

    @Override
    public JTextField getEntity() {
        return entity;
    }

    /**
     * Page is loaded into the dialog
     */
    private void onPageLoaded() {
        if (dialog != null) {
            entity.setText(page.getEntity());
            title.setText(page.getTitle());
            text.setText(page.getText());
            action.setText(page.getAction());

            dialogCam.setSelectedIndex(page.isDialogCamera() ? 0 : 1);
            showSky.setSelectedIndex(page.isShowSky() ? 0 : 1);
            hideFoW.setSelectedIndex(page.isHideFoW() ? 0 : 1);

            op1Target.setText(page.getFirstSelectPage());
            op2Target.setText(page.getSecondSelectPage());
            op1Text.setText(page.getFirstSelectText());
            op2Text.setText(page.getSecondSelectText());
        }
    }

    /**
     * Select target for option 2
     */
    public void onTargetOption2Clicked() {

    }

    /**
     * Select target for option 1
     */
    public void onTargetOption1Clicked() {

    }

    /**
     * Create the dialog window
     */
    @Override
    public void initDialog() {
        dialog = new BasicConfirmDialog(this.frame, "Entscheidungsseite bearbeiten") {
            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onConfirm() {
                super.onConfirm();
                BriefingChoicePageProperties.this.onConfirm();
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

        JLabel op1TLabel = new JLabel("Option 1 Text");
        op1TLabel.setBounds(10, 360, 140, 15);
        dialog.add(op1TLabel);
        op1Text = new JTextField();
        op1Text.setFont(new Font("Arial", Font.PLAIN, 12));
        op1Text.setBounds(160, 360, width-185, 25);
        dialog.add(op1Text);

        JLabel op1ZLabel = new JLabel("Option 1 Ziel");
        op1ZLabel.setBounds(10, 395, 140, 15);
        dialog.add(op1ZLabel);
        op1Target = new JTextField();
        op1Target.setBounds(160, 395, 200, 25);
        op1Target.setFont(new Font("Arial", Font.PLAIN, 12));
        dialog.add(op1Target);
        selOp1 = new JButton("...");
        selOp1.setBounds(360, 395, 30, 24);
        selOp1.addActionListener(this);
        dialog.add(selOp1);

        JLabel op2TLabel = new JLabel("Option 2 Text");
        op2TLabel.setBounds(10, 430, 140, 15);
        dialog.add(op2TLabel);
        op2Text = new JTextField();
        op2Text.setFont(new Font("Arial", Font.PLAIN, 12));
        op2Text.setBounds(160, 430, width-185, 25);
        dialog.add(op2Text);

        JLabel op2ZLabel = new JLabel("Option 2 Ziel");
        op2ZLabel.setBounds(10, 465, 140, 15);
        dialog.add(op2ZLabel);
        op2Target = new JTextField();
        op2Target.setBounds(160, 465, 200, 25);
        op2Target.setFont(new Font("Arial", Font.PLAIN, 12));
        dialog.add(op2Target);
        selOp2 = new JButton("...");
        selOp2.setBounds(360, 465, 30, 24);
        selOp2.addActionListener(this);
        dialog.add(selOp2);

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
        String text = this.text.getText().replaceAll("\\n", "{cr}");
        page.setText(text);

        page.setTitle(title.getText());
        page.setEntity(entity.getText());
        page.setAction(action.getText());

        page.setDialogCamera(dialogCam.getSelectedIndex() == 0);
        page.setShowSky(showSky.getSelectedIndex() == 0);
        page.setHideFoW(hideFoW.getSelectedIndex() == 0);
        page.setNoEscape(true);

        page.setFirstSelectText(op1Text.getText());
        page.setSecondSelectText(op2Text.getText());
        page.setFirstSelectPage(op1Target.getText());
        page.setSecondSelectPage(op2Target.getText());
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
        if (e.getSource() == selOp1) {
            onTargetOption1Clicked();
        }
        if (e.getSource() == selOp2) {
            onTargetOption2Clicked();
        }
    }
}

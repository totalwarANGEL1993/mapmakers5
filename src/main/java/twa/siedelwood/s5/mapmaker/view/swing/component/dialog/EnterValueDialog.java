package twa.siedelwood.s5.mapmaker.view.swing.component.dialog;

import javax.swing.*;

/**
 * Dialog for enterning a value.
 */
public class EnterValueDialog implements ConfirmDialog {
    private BasicConfirmDialog dialog;
    private JTextField name;
    private JFrame frame;
    private int width = 400;
    private int height = 120;
    private String chosenName;

    /**
     * Constructor
     *
     * @param frame Parent frame
     */
    public EnterValueDialog(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Returns the text of the name field
     * @return Chosen name
     */
    public String getValue() {
        return chosenName;
    }

    /**
     * Sets the text of the name field
     * @param name New name
     */
    public void setValue(String name) {
        if (this.name != null) {
            this.name.setText(name);
        }
        chosenName = name;
    }

    /**
     * Returns if the chosen name is valid
     * @return Name is valid
     */
    protected boolean isValid() {
        if (name == null) {
            return false;
        }
        // return Pattern.matches("^[a-zA-Z0-9_]$", name.getText());
        return !name.getText().equals("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDialog() {
        if (dialog != null) {
            dialog.showDialog();
        }
    }

    /**
     * {@inheritDoc}
     * Can be called after disposal of the confirm dialog to redisplay the dialog.
     */
    @Override
    public void initDialog() {
        dialog = new BasicConfirmDialog(this.frame, "Namen eingeben") {
            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onConfirm() {
                super.onConfirm();
                EnterValueDialog.this.onConfirm();
            }
        };
        setSize(width, height);
        //dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.initDialog();

        name = new JTextField();
        name.setBounds(10, 10, width -35, 20);
        dialog.add(name);
    }

    /**
     * User aborted the action
     */
    @Override
    public void onConfirm() {
        if (dialog != null) {
            chosenName = name.getText();
            dialog.foreAccepted();
            dialog.dispose();
        }
    }

    /**
     * User confirms the action
     */
    @Override
    public void onCancel() {}

    /**
     * Changes the size of the dialog.
     * @param w Width
     * @param h Height
     */
    @Override
    public void setSize(int w, int h) {
        width = w;
        height = h;
        if (dialog != null) {
            dialog.setSize(w, h);
        }
    }

    /**
     * {@inheritDoc}
     * @return Accepted
     */
    @Override
    public boolean isAccepted() {
        return chosenName != null && !chosenName.equals("");
    }

    /**
     * {@inheritDoc}
     * @return Undecided
     */
    @Override
    public boolean isUndecided() {
        return chosenName == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUndecided() {
        if (dialog != null) {
            dialog.setUndecided();
        }
    }
}

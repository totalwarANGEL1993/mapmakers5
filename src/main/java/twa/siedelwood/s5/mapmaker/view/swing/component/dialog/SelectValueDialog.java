package twa.siedelwood.s5.mapmaker.view.swing.component.dialog;

import javax.swing.*;
import java.util.Vector;

/**
 * Dialog for selecting a value.
 */
public class SelectValueDialog implements ConfirmDialog {
    private String title = "Wert auswählen";
    private BasicConfirmDialog dialog;
    private JComboBox<String> valueSelect;
    private Vector<String> values;
    private JFrame frame;
    private int width = 400;
    private int height = 120;
    private String chosenValue;

    /**
     * Constructor
     *
     * @param frame Parent frame
     */
    public SelectValueDialog(JFrame frame) {
        this.frame = frame;
        values = new Vector<>();
    }

    /**
     * Constructor
     *
     * @param frame Parent frame
     * @param title Title
     */
    public SelectValueDialog(JFrame frame, String title) {
        this.frame = frame;
        this.title = title;
        values = new Vector<>();
    }

    /**
     * Returns the text of the name field
     * @return Chosen value
     */
    public String getValue() {
        return chosenValue;
    }

    /**
     * Returns the text of the name field
     * @return Chosen value
     */
    public int getValueIndex() {
        return valueSelect.getSelectedIndex();
    }

    /**
     * Sets the text of the name field
     * @param value New value
     */
    public void setValue(String value) {
        chosenValue = value;
    }

    /**
     *
     * @param values
     */
    public void setValues(Vector<String> values) {
        this.values = values;
        if (valueSelect != null) {
            valueSelect.removeAllItems();
            for (String value : values) {
                valueSelect.addItem(value);
            }
            if (values.size() > 0) {
                valueSelect.setSelectedIndex(0);
            }
            else {
                dialog.okButton.setEnabled(false);
            }
        }
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
        dialog = new BasicConfirmDialog(this.frame, "Wert auswählen") {
            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onConfirm() {
                SelectValueDialog.this.onConfirm();
            }
        };
        setSize(width, height);
        //dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.initDialog();

        valueSelect = new JComboBox<>();
        valueSelect.setBounds(10, 10, width -35, 20);
        valueSelect.removeAllItems();
        for (String value : values) {
            valueSelect.addItem(value);
        }
        dialog.add(valueSelect);
    }

    /**
     * User aborted the action
     */
    @Override
    public void onConfirm() {
        if (dialog != null && valueSelect.getSelectedIndex() >= 0) {
            chosenValue = dialog.getTitle();
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
        return chosenValue != null && !chosenValue.equals("");
    }

    /**
     * {@inheritDoc}
     * @return Undecided
     */
    @Override
    public boolean isUndecided() {
        return chosenValue == null;
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

package twa.siedelwood.s5.mapmaker.view.swing.component.dialog;

import javax.swing.*;
import java.util.Vector;

/**
 * Dialog for selecting a value.
 */
public class ListSelectValueDialog implements ConfirmDialog {
    protected String title = "Wert auswählen";
    protected BasicConfirmDialog dialog;
    protected JList<String> valueSelect;
    protected Vector<String> values;
    protected JFrame frame;
    protected int width = 400;
    protected int height = 300;
    protected String chosenValue;

    /**
     * Constructor
     *
     * @param frame Parent frame
     */
    public ListSelectValueDialog(JFrame frame) {
        this.frame = frame;
        values = new Vector<>();
    }

    /**
     * Constructor
     *
     * @param frame Parent frame
     * @param title Title
     */
    public ListSelectValueDialog(JFrame frame, String title) {
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
     * Changes the value list and sets the selected index to the first entry.
     * @param values Values vector
     */
    public void setValues(Vector<String> values) {
        this.values = values;
        if (valueSelect != null) {
            valueSelect.setListData(values);
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
            dialog.okButton.requestFocus();
        }
    }

    /**
     * {@inheritDoc}
     * Can be called after disposal of the confirm dialog to redisplay the dialog.
     */
    @Override
    public void initDialog() {
        dialog = new BasicConfirmDialog(this.frame, this.title) {
            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onConfirm() {
                ListSelectValueDialog.this.onConfirm();
            }
        };
        setSize(width, height);
        //dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.initDialog();

        JScrollPane scrollPane = new JScrollPane();
        valueSelect = new JList<>();
        valueSelect.setBounds(10, 10, width -35, 190);
        valueSelect.setListData(values);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(valueSelect);
        scrollPane.setBounds(10, 10, width -35, 190);
        scrollPane.setVisible(true);
        dialog.add(scrollPane);
    }

    /**
     * User aborted the action
     */
    @Override
    public void onConfirm() {
        if (dialog != null && valueSelect.getSelectedIndex() >= 0) {
            chosenValue = valueSelect.getSelectedValue();
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

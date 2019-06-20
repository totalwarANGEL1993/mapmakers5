package twa.siedelwood.s5.mapmaker.view.swing.component.dialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Basic confirm dialog
 */
public abstract class BasicConfirmDialog extends JDialog implements ConfirmDialog, WindowListener, ActionListener {
    protected final String title;
    protected JFrame frame;
    protected int width = 400;
    protected int height = 120;
    protected JButton okButton;
    protected JButton cancelButton;
    protected Boolean result = null;

    /**
     * Constructor
     * @param frame Parent frame
     * @param title Window title
     */
    public BasicConfirmDialog(JFrame frame, String title) {
        super(frame);
        this.frame = frame;
        this.title = title;
    }

    /**
     * Changes the size of the dialog.
     * @param w Width
     * @param h Height
     */
    public void setSize(int w, int h) {
        width = w;
        height = h;
        super.setSize(w, h);
    }

    /**
     * Forced the dialog to be accepted.
     */
    public void foreAccepted() {
        result = true;
    }

    /**
     * Returns the result
     * @return Result
     */
    public boolean isAccepted() {
        return result;
    }

    /**
     * Returns if the dialog result is unset.
     * @return Undecided
     */
    public boolean isUndecided() {
        return result == null;
    }

    /**
     * Resets the dialog to being undecided.
     */
    public void setUndecided() {
        result = null;
    }

    /**
     * Displays the dialog.
     */
    public void showDialog() {
        setVisible(true);
    }

    /**
     * Initalizes the dialog window. Should be overwritten!
     */
    public void initDialog() {
        setModal(true);
        setLayout(null);
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(frame);
        setTitle(title);

        okButton = new JButton("Okay");
        okButton.addActionListener(this);
        okButton.setBounds((width / 2) -120, height -75, 120, 25);
        add(okButton);

        cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(this);
        cancelButton.setBounds((width / 2) +10, height -75, 120, 25);
        add(cancelButton);

        result = null;
    }

    /**
     * User clicked cancel
     */
    public void onCancel() {
        result = false;
        dispose();
    }

    /**
     * User clicked confirm
     */
    public void onConfirm() {
        result = true;
        dispose();
    }

    //// Listener stuff ////

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            onConfirm();
        }
        if (e.getSource() == cancelButton) {
            onCancel();
        }
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        onCancel();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}

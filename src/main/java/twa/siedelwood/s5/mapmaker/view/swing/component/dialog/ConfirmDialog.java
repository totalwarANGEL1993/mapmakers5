package twa.siedelwood.s5.mapmaker.view.swing.component.dialog;

/**
 *
 */
public interface ConfirmDialog {
    /**
     * Changes the size of the dialog.
     * @param w Width
     * @param h Height
     */
    void setSize(int w, int h);

    /**
     * Returns the result
     * @return Result
     */
    boolean isAccepted();

    /**
     * Returns if the dialog result is unset.
     * @return Undecided
     */
    boolean isUndecided();

    /**
     * Displays the dialog.
     */
    void showDialog();

    /**
     * Initalizes the dialog window. Should be overwritten!
     */
    void initDialog();

    /**
     * User clicked cancel
     */
    void onCancel();

    /**
     * User clicked confirm
     */
    void onConfirm();

    /**
     * Resets the dialog to being undecided.
     */
    void setUndecided();
}

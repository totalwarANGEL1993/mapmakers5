package twa.siedelwood.s5.mapmaker.service.message;

/**
 * Interface for creating error messages.
 * @param <E> Type of parent component
 */
public interface MessageService<E> {
    /**
     * Shows an error message with a simple confirm button.
     * @param title Title of dialog
     * @param text Text of dialog
     * @param parent Parent
     */
    @SuppressWarnings("unchecked")
    void displayErrorMessage(String title, String text, E parent);

    /**
     * Shows an info message with a simple confirm button.
     * @param title Title of dialog
     * @param text Text of dialog
     * @param parent Parent
     */
    @SuppressWarnings("unchecked")
    void displayInfoMessage(String title, String text, E parent);

    /**
     * Shows an warning message with a simple confirm button.
     * @param title Title of dialog
     * @param text Text of dialog
     * @param parent Parent
     */
    @SuppressWarnings("unchecked")
    void displayWarningMessage(String title, String text, E parent);

    /**
     * Shows an error message with a simple confirm button.
     * @param title Title of dialog
     * @param text Text of dialog
     */
    @SuppressWarnings("unchecked")
    void displayErrorMessage(String title, String text);

    /**
     * Shows an info message with a simple confirm button.
     * @param title Title of dialog
     * @param text Text of dialog
     */
    @SuppressWarnings("unchecked")
    void displayInfoMessage(String title, String text);

    /**
     * Shows an warning message with a simple confirm button.
     * @param title Title of dialog
     * @param text Text of dialog
     */
    @SuppressWarnings("unchecked")
    void displayWarningMessage(String title, String text);

    /**
     * Shows a confirmation screen with warning sign and returns the selected option.
     * @param title Title of dialog
     * @param text Text of dialog
     * @param parent Parent
     * @return Selected option
     */
    @SuppressWarnings("unchecked")
    int displayConformDialog(String title, String text, E parent);

    /**
     * Shows a confirmation screen with warning sign and returns the selected option.
     * @param title Title of dialog
     * @param text Text of dialog
     * @return Selected option
     */
    @SuppressWarnings("unchecked")
    int displayConformDialog(String title, String text);
}

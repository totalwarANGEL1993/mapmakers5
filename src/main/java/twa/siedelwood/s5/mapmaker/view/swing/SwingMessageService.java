package twa.siedelwood.s5.mapmaker.view.swing;

import twa.siedelwood.s5.mapmaker.service.message.MessageService;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog message implementation for Swing.
 */
public class SwingMessageService<E extends Component> implements MessageService<E> {
    /**
     * {@inheritDoc}
     * @param title Title of dialog
     * @param text Text of dialog
     * @param parent Parent
     */
    @Override
    public void displayErrorMessage(String title, String text, E parent) {
        JOptionPane.showMessageDialog(parent, text, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * {@inheritDoc}
     * @param title Title of dialog
     * @param text Text of dialog
     * @param parent Parent
     */
    @Override
    public void displayInfoMessage(String title, String text, E parent) {
        JOptionPane.showMessageDialog(parent, text, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * {@inheritDoc}
     * @param title Title of dialog
     * @param text Text of dialog
     * @param parent Parent
     */
    @Override
    public void displayWarningMessage(String title, String text, E parent) {
        JOptionPane.showMessageDialog(parent, text, title, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * {@inheritDoc}
     * @param title Title of dialog
     * @param text Text of dialog
     */
    @Override
    public void displayErrorMessage(String title, String text) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * {@inheritDoc}
     * @param title Title of dialog
     * @param text Text of dialog
     */
    @Override
    public void displayInfoMessage(String title, String text) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * {@inheritDoc}
     * @param title Title of dialog
     * @param text Text of dialog
     */
    @Override
    public void displayWarningMessage(String title, String text) {
        JOptionPane.showMessageDialog(null, text, title, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * {@inheritDoc}
     * @param title Title of dialog
     * @param text Text of dialog
     * @param parent Parent
     * @return Result
     */
    @Override
    public int displayConfirmDialog(String title, String text, E parent) {
        return JOptionPane.showConfirmDialog(parent, text, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * {@inheritDoc}
     * @param title Title of dialog
     * @param text Text of dialog
     * @return Result
     */
    @Override
    public int displayConfirmDialog(String title, String text) {
        return JOptionPane.showConfirmDialog(null, text, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
    }
}

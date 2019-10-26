package twa.siedelwood.s5.mapmaker.view.swing.component.swing;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;

/**
 * Implements a textarea with a simple change listener.
 */
public class JOnChangeTextFieldPanel extends JPanel {
    private final JLabel label;
    private JTextField textField;
    private boolean active = true;

    /**
     * Builds the component
     * @param text Label text
     */
    public JOnChangeTextFieldPanel(String text) {
        setLayout(null);

        label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        add(label);

        textField = new JTextField("");
        textField.setFont(new Font("Arial", Font.PLAIN, 12));
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (active) {
                    onTextChanged(textField.getText());
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (active) {
                    onTextChanged(textField.getText());
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
        add(textField);
    }

    /**
     * Builds the component
     */
    public JOnChangeTextFieldPanel() {
        this("");
    }

    /**
     * Changes the size of the panel.
     * @param x X position
     * @param y Y position
     * @param w Width of panel
     * @param h Height of panel
     */
    public void resizeComponent(int x, int y, int w, int h) {
        h = (h < 40) ? 40 : h;
        w = (w < 120) ? 120 : w;
        setBounds(x, y, w, h);
        label.setBounds(0, 3, w, 15);
        textField.setBounds(0, 20, w, h -20);
    }

    /**
     * Removes the text from the textarea. The change callback is not triggered.
     */
    public void clear() {
        active = false;
        textField.setText("");
        active = true;
    }

    /**
     * Sets the content of the textfield. The change callback is not triggered.
     * @param text Content
     */
    public void setText(String text) {
        active = false;
        textField.setText(text);
        active = true;
    }

    /**
     * Changes the label of the panel
     * @param text Text
     */
    public void setLabel(String text) {
        label.setText(text);
    }

    /**
     * Returns the content of the textfield.
     * @return Content
     */
    public String getText() {
        return textField.getText();
    }

    /**
     * The document of the textfield.
     * @return Document
     */
    public Document getDocument() {
        return textField.getDocument();
    }

    /**
     * This function is called when the text changes
     * @param text Current text
     */
    protected void onTextChanged(String text) {}
}

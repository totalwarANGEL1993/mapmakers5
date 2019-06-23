package twa.siedelwood.s5.mapmaker.view.swing.component.input;

import twa.siedelwood.s5.mapmaker.view.swing.component.swing.JNumberTextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Input component implementation for number input requests.
 * @author totalwarANGEL
 */
@SuppressWarnings("serial")
public class InputComponentIntegerField extends JPanel implements InputComponent, DocumentListener
{	
    protected JLabel description;

    protected JNumberTextField value;

    protected int w;

    protected int h;

    /**
     * Constructor
     * @param w Width
     * @param h Height
     */
    public InputComponentIntegerField(final int w, final int h) {
        this.w = w;
        this.h = h;
        build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCaption(final String text) {
        description.setText(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCaption() {
        return description.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        String text = value.getText();
        if (text.contains(".")) {
            text = text.replaceAll("\\.", "");
        }
        return text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getPane() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        value.setText("0");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertUpdate(final DocumentEvent e)
    {
        if (e.getDocument().equals(value.getDocument())) {
            value.setText(value.getText().replaceAll("\\D", ""));
            if (value.getText().startsWith("0") && value.getText().length() > 1) {
                value.setText(value.getText().substring(1));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUpdate(final DocumentEvent e)
    {
        if (e.getDocument().equals(value.getDocument())) {
            value.setText(value.getText().replaceAll("\\D", ""));
            if (value.getText().startsWith("0") && value.getText().length() > 1) {
                value.setText(value.getText().substring(1));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String string)
    {
        if (string.equals("")) {
            string = "0";
        }
        value.setText(string);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changedUpdate(final DocumentEvent e)
    {
        if (e.getDocument().equals(value.getDocument())) {
            value.setText(value.getText().replaceAll("\\D", ""));
            if (value.getText().startsWith("0") && value.getText().length() > 1) {
                value.setText(value.getText().substring(1));
            }
        }
    }

    /**
     * Initializes the parts of the input component.
     */
    private void build() {
        setLayout(null);
        setBounds(0, 0, w, h);

        description = new JLabel("");
        description.setBounds(2, 0, w - 20, 15);
        add(description);

        value = new JNumberTextField();
        value.setFont(new Font("Arial", Font.PLAIN, 12));
        value.setBounds(2, 17, w - 20, 20);
        value.build();
        add(value);

        setVisible(true);
    }

    /**
     * Main method for testing.
     * @param args Program arguments
     */
    public static void main(final String[] args)
    {
        final int width = 502;
        final int height = 100;

        final JFrame f = new JFrame();
        f.setSize(width, height);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        final InputComponentIntegerField test = new InputComponentIntegerField(width, 40);
        test.setCaption("Some Value");
        f.add(test);

        System.out.println(test.getHeight());

        f.setVisible(true);
    }
}


package twa.siedelwood.s5.mapmaker.view.swing.component.input;

import twa.siedelwood.s5.mapmaker.view.swing.component.swing.JScriptSaveTextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Input component implementation for string input requests.
 * 
 * @author totalwarANGEL
 */
@SuppressWarnings("serial")
public class InputComponentStringField extends JPanel implements InputComponent, DocumentListener
{
    protected JLabel description;

    protected JScriptSaveTextField value;

    protected int w;

    protected int h;

    /**
     * Constructor
     * 
     * @param w Width
     * @param h Height
     */
    public InputComponentStringField(final int w, final int h)
    {
        this.w = w;
        this.h = h;
        build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCaption(final String text)
    {
        description.setText(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCaption()
    {
        return description.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue()
    {
        return value.getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getPane()
    {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(final String string)
    {
        value.setText(string);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset()
    {
        value.setText("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertUpdate(final DocumentEvent e)
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeUpdate(final DocumentEvent e)
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changedUpdate(final DocumentEvent e)
    {

    }

    /**
     * Initializes the parts of the input component.
     */
    private void build()
    {
        setLayout(null);
        setBounds(0, 0, w, h);

        description = new JLabel("");
        description.setBounds(2, 0, w - 20, 15);
        add(description);

        value = new JScriptSaveTextField("");
        value.setFont(new Font("Arial", Font.PLAIN, 12));
        value.setBounds(2, 17, w - 20, 20);
        add(value);

        setVisible(true);
    }

    /**
     * Main method for testing.
     * 
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

        final InputComponentStringField test = new InputComponentStringField(width, 40);
        test.setCaption("Some Value");
        f.add(test);

        f.setVisible(true);
    }
}


package twa.siedelwood.s5.mapmaker.view.swing.component.input;

import javax.swing.*;
import java.awt.*;

/**
 * Input component implementation for boolean requests.
 * 
 * @author totalwarANGEL
 */
@SuppressWarnings("serial")
public class InputComponentBooleanBox extends JPanel implements InputComponent
{
    protected JLabel description;

    protected JComboBox<Boolean> value;

    protected int w;

    protected int h;

    /**
     * Constructor
     * 
     * @param w Width
     * @param h Height
     */
    public InputComponentBooleanBox(final int w, final int h)
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
        return Boolean.toString(value.getItemAt(value.getSelectedIndex()));
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
        value.setSelectedIndex(string.equals("true") ? 0 : 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset()
    {
        value.setSelectedIndex(0);
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

        value = new JComboBox<>();
        value.addItem(true);
        value.addItem(false);
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

        final InputComponentBooleanBox test = new InputComponentBooleanBox(width, 22);
        test.setCaption("Some Value");
        f.add(test);

        f.setVisible(true);
    }
}

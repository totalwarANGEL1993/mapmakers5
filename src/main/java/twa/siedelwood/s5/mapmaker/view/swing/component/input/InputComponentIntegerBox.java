
package twa.siedelwood.s5.mapmaker.view.swing.component.input;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Input component implementation for number selection requests.
 * 
 * @author totalwarANGEL
 */
@SuppressWarnings("serial")
public class InputComponentIntegerBox extends JPanel implements InputComponent
{
    protected JLabel description;

    protected JComboBox<String> value;

    protected int w;

    protected int h;

    /**
     * Constructor
     * 
     * @param w Width
     * @param h Height
     */
    public InputComponentIntegerBox(final int w, final int h)
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
        return value.getItemAt(value.getSelectedIndex());
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
        value.setSelectedItem(string);
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
     * Sets the selectable items of the combo box.
     * 
     * @param options Options to select
     */
    public void setOptions(final List<String> options)
    {
        for (int i = 0; i < value.getItemCount(); i++)
        {
            value.removeItemAt(0);
        }
        for (int i = 0; i < options.size(); i++)
        {
            value.addItem(options.get(i));
        }
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
        value.setFont(new Font("Arial", Font.PLAIN, 12));
        value.setBounds(2, 17, w - 20, 20);
        add(value);

        setVisible(true);
    }

    /**
     * Creates demonstration data.
     */
    public void demo()
    {
        setOptions(Arrays.asList("1001", "-568", "88"));
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

        final InputComponentIntegerBox test = new InputComponentIntegerBox(width, 40);
        test.setCaption("Some Value");
        test.demo();
        f.add(test);

        f.setVisible(true);
    }
}


package twa.siedelwood.s5.mapmaker.view.swing.component.input;

import twa.siedelwood.s5.mapmaker.view.swing.component.swing.JRegexTextArea;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Input component implementation for string input requests.
 * 
 * @author totalwarANGEL
 */
@SuppressWarnings("serial")
public class InputComponentMessageField extends JPanel implements InputComponent, DocumentListener
{
    protected JLabel description;

    protected JRegexTextArea value;

    protected int w;

    protected int h;

    /**
     * Constructor
     * 
     * @param w Width
     * @param h Height
     */
    public InputComponentMessageField(final int w, final int h)
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
        String text = value.getText();
        text = text.replaceAll("\n", " @cr ");
        return text;
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

        final JScrollPane scrollPaneDesc = new JScrollPane();
        value = new JRegexTextArea("[^ \n0-9a-zäöüß@A-ZÄÖÜ.:\\-_!?'§\\$%/\\(\\)]");
        value.setLineWrap(true);
        value.setWrapStyleWord(true);
        scrollPaneDesc.setViewportView(value);
        scrollPaneDesc.setBounds(2, 17, w - 20, 40);
        scrollPaneDesc.setVisible(true);
        scrollPaneDesc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneDesc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        value.build();
        add(scrollPaneDesc);

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
        final int height = 60;

        final JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        final InputComponentMessageField test = new InputComponentMessageField(width, height);
        test.setCaption("Some Value");

        f.setSize(width, height + test.getHeight());
        f.add(test);

        f.setVisible(true);
    }
}

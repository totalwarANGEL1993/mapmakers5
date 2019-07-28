
package twa.siedelwood.s5.mapmaker.view.swing.component.input;

import twa.siedelwood.s5.mapmaker.view.swing.component.dialog.ListSelectValueDialog;
import twa.siedelwood.s5.mapmaker.view.swing.component.swing.JScriptSaveTextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Input component implementation for string input requests and additional selection of values.
 * 
 * @author totalwarANGEL
 */
public class InputComponentStringFieldChoice extends JPanel implements InputComponent, DocumentListener, ActionListener {
    protected final String title;
    protected JLabel description;
    protected JScriptSaveTextField value;
    protected JButton button;
    protected Vector<String> choices;
    protected int w;
    protected int h;

    /**
     * Constructor
     *
     * @param w Width
     * @param h Height
     */
    public InputComponentStringFieldChoice(final int w, final int h, String title)
    {
        this.w = w;
        this.h = h;
        this.title = title;
        choices = new Vector<>();
        build();
    }

    /**
     * Constructor
     *
     * @param w Width
     * @param h Height
     * @param choices Choices
     */
    public InputComponentStringFieldChoice(final int w, final int h, String title, Vector<String> choices)
    {
        this.w = w;
        this.h = h;
        this.title = title;
        this.choices = choices;
        build();
    }

    /**
     * Changes the choices of this field
     * @param choices Choices
     */
    public void updateChoices(Vector<String> choices) {
        this.choices = choices;
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
     *
     */
    protected void onValueChanged() {}

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
        onValueChanged();
    }

    /**
     * Initializes the parts of the input component.
     */
    private void build()
    {
        setLayout(null);
        setBounds(0, 0, w, h);

        description = new JLabel(title);
        description.setBounds(2, 0, w - 20, 15);
        add(description);

        value = new JScriptSaveTextField("");
        value.setFont(new Font("Arial", Font.PLAIN, 12));
        value.setBounds(2, 17, w - 50, 20);
        add(value);

        button = new JButton("...");
        button.setBounds(w - 50, 17, 30, 19);
        button.addActionListener(this);
        add(button);

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

        Vector<String> values = new Vector<>();
        values.add("value 1");
        values.add("value 2");
        values.add("value 3");

        final InputComponentStringFieldChoice test = new InputComponentStringFieldChoice(width, 40, "Select value", values);
        test.setCaption("Some Value");
        f.add(test);

        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            onSelectButtonClicked();
        }
    }

    protected void onSelectButtonClicked() {
        ListSelectValueDialog dialog = new ListSelectValueDialog(null, this.title) {
            @Override
            public void onConfirm() {
                super.onConfirm();
                value.setText(getValue());
                onValueChanged();
            }
        };
        dialog.initDialog();
        dialog.setValues(this.choices);
        dialog.showDialog();
    }
}

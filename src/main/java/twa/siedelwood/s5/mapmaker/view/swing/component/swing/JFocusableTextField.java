
package twa.siedelwood.s5.mapmaker.view.swing.component.swing;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Implements a text field that selects all of its contents when it is selected.
 * @author totalwarANGEL
 *
 */
@SuppressWarnings("serial")
public class JFocusableTextField extends JTextField
{

	/**
	 * {@inheritDoc}
	 */
	public JFocusableTextField()
	{
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public JFocusableTextField(final String text)
	{
		super(text);
	}

	/**
	 * Builds the component.
	 */
	public void build()
	{
		addFocusListener(getFocusListener());
	}

	/**
	 * Creates a focus listener and returns it.
	 * 
	 * @return Focus listener
	 */
	protected FocusListener getFocusListener()
	{
		return new FocusListener()
		{
			@Override
			public void focusLost(final FocusEvent pE)
			{
				final JFocusableTextField source = (JFocusableTextField) pE.getSource();
				source.select(0, 0);
			}

			@Override
			public void focusGained(final FocusEvent pE)
			{
				final JFocusableTextField source = (JFocusableTextField) pE.getSource();
				final int length = source.getText().length();
				source.select(0, length);
			}
		};
	}
}

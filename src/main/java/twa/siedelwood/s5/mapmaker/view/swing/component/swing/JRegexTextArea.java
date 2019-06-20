
package twa.siedelwood.s5.mapmaker.view.swing.component.swing;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * A text area extension that takes a regular expression. All not matching
 * characters are removed from the document.
 * @author totalwarANGEL
 *
 */
@SuppressWarnings("serial")
public class JRegexTextArea extends JFocusableTextArea
{
	protected String regex;
	
	protected String defaultText = "";

	/**
	 * Constructor
	 * @param regex Regular expression
	 */
	public JRegexTextArea(final String regex)
	{
		super();
		this.regex = regex;
	}

	/**
	 * Constructor
	 * @param text Text
	 * @param regex Regular expression
	 */
	public JRegexTextArea(final String text, final String regex)
	{
		super(text);
		this.regex = regex;
	}

	/**
	 * Constructor
	 * @param text Text
	 * @param regex Regular expression
	 * @param defaultText Default if empty
	 */
	public JRegexTextArea(final String text, final String regex, final String defaultText)
	{
		super(text);
		this.regex = regex;
		this.defaultText = defaultText;
	}

	/**
	 * Builds the component.
	 */
	@Override
	public void build()
	{
		addFocusListener(getFocusListener());
	}

	/**
	 * Creates a focus listener and returns it.
	 * 
	 * @return Focus listener
	 */
	@Override
	protected FocusListener getFocusListener()
	{
		return new FocusListener()
		{
			@Override
			public void focusLost(final FocusEvent pE)
			{
				final JFocusableTextArea source = (JFocusableTextArea) pE.getSource();
    			String text = source.getText();
				text = text.replaceAll(regex, "");
				if ((text.length() == 0) && !defaultText.equals("")) {
					text = defaultText;
				}
				source.setText(text);
				source.select(0, 0);
			}

			@Override
			public void focusGained(final FocusEvent pE)
			{
				final JFocusableTextArea source = (JFocusableTextArea) pE.getSource();
				final int length = source.getText().length();
				source.select(0, length);
			}
		};
	}
}

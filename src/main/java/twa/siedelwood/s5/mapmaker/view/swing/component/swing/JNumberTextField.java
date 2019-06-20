
package twa.siedelwood.s5.mapmaker.view.swing.component.swing;

/**
 * A text field extension that only accepts numbers.
 * 
 * @author totalwarANGEL
 */
@SuppressWarnings("serial")
public class JNumberTextField extends JRegexTextField
{
	public JNumberTextField()
	{
		super("", "\\D", "0");
	}

	public JNumberTextField(final String text)
	{
		super(text, "\\D", "0");
	}

	public void setText(Integer value) {
		setText(value.toString());
	}
}

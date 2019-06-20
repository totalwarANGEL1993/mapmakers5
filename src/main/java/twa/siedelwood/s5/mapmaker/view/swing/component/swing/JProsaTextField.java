
package twa.siedelwood.s5.mapmaker.view.swing.component.swing;

/**
 * A text field extension that only accepts german letters and some special
 * characters.
 * 
 * @author totalwarANGEL
 *
 */
@SuppressWarnings("serial")
public class JProsaTextField extends JRegexTextField
{
	public JProsaTextField()
	{
		super("", "[^ 0-9a-zäöüßA-ZÄÖÜ.:\\-_!?'§\\$%/\\(\\)]", "");
	}

	public JProsaTextField(final String text)
	{
		super(text, "[^ 0-9a-zäöüßA-ZÄÖÜ.:\\-_!?'§\\$%/\\(\\)]", "");
	}

}


package twa.siedelwood.s5.mapmaker.view.swing.component.swing;

/**
 * A text field extension that only accepts german letters and some special
 * characters.
 * 
 * @author totalwarANGEL
 *
 */
@SuppressWarnings("serial")
public class JProsaTextArea extends JRegexTextArea
{
	public JProsaTextArea()
	{
		super("", "[^ 0-9a-zäöüßA-ZÄÖÜ.:\\-_!?'§\\$%/\\(\\)]", "");
	}

	public JProsaTextArea(final String text)
	{
		super(text, "[^ 0-9a-zäöüßA-ZÄÖÜ.:\\-_!?'§\\$%/\\(\\)]", "");
	}

}

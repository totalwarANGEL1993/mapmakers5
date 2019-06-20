
package twa.siedelwood.s5.mapmaker.view.swing.component.swing;

/**
 * A text field extension that only accepts characters that are valid as part of
 * identifier in lua.
 * 
 * @author totalwarANGEL
 *
 */
@SuppressWarnings("serial")
public class JScriptSaveTextField extends JRegexTextField
{
	public JScriptSaveTextField()
	{
		super("", "[^0-9a-zA-Z\\-_]", "");
	}

	public JScriptSaveTextField(final String text)
	{
		super(text, "[^0-9a-zA-Z\\-_]", "");
	}

}

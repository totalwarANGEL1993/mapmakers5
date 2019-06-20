
package twa.siedelwood.s5.mapmaker.view.swing.component.input;

import javax.swing.*;

/**
 * Interface for any input component in requester dialogs.
 * 
 * @author totalwarANGEL
 */
public interface InputComponent
{
	/**
	 * Sets the caption label of the input component.
	 * 
	 * @param text Label text
	 */
	public void setCaption(final String text);

	/**
	 * Returns the caption label of the input component.
	 * 
	 * @return Label text
	 */
	public String getCaption();

	/**
	 * Returns the entered value as string.
	 * 
	 * @return Value
	 */
	public String getValue();

	/**
	 * Returns the panel of the component.
	 * 
	 * @return Component panel
	 */
	public JPanel getPane();

	/**
	 * Resets the component.
	 */
	public void reset();

	/**
	 * Sets the value of the component.
	 * 
	 * @param string Value
	 */
	public void setValue(String string);
}

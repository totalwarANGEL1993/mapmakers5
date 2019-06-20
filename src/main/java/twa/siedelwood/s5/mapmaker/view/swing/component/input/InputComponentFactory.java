
package twa.siedelwood.s5.mapmaker.view.swing.component.input;

import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationParamaterModel;

import java.util.List;
import java.util.Vector;

/**
 * Factory for input components.
 * 
 * @author totalwarANGEL
 *
 */
public interface InputComponentFactory
{
	/**
	 * Creates a boolean selection component for a dialog window.
	 * 
	 * @param w Width of component
	 * @param h Height of component
	 * @param caption Label text
	 * @return Boolean selection component
	 */
	InputComponentBooleanBox getBooleanBox(final int w, final int h, final String caption);

	/**
	 * Creates a integer declaration component for a dialog window.
	 * 
	 * @param w Width of component
	 * @param h Height of component
	 * @param caption Label text
	 * @return Integer component
	 */
	InputComponentIntegerField getIntegerField(final int w, final int h, final String caption);

	/**
	 * Creates a string declaration component for a dialog window.
	 * 
	 * @param w Width of component
	 * @param h Height of component
	 * @param caption Label text
	 * @return String component
	 */
	InputComponentStringField getStringField(final int w, final int h, final String caption);

	/**
	 * Creates a string declaration component for a dialog window with optional choice option.
	 * @param w Width of component
	 * @param h Height of component
	 * @param caption Label text
	 * @return String choice component
	 */
	InputComponentStringFieldChoice getStringChoiceField(int w, int h, String caption);

	/**
	 * Creates a string declaration component for a dialog window with optional choice option.
	 * @param w Width of component
	 * @param h Height of component
	 * @param caption Label text
	 * @param choices Choices
	 * @return String choice component
	 */
	InputComponentStringFieldChoice getStringChoiceField(int w, int h, String caption, Vector<String> choices);

	/**
	 * Creates a integer selection component for a dialog window.
	 * 
	 * @param w Width of component
	 * @param h Height of component
	 * @param caption Label text
	 * @param options Option list
	 * @return Integer selection component
	 */
	InputComponentIntegerBox getIntegerBox(
			final int w, final int h, final String caption, final List<String> options
	);

	/**
	 * Creates a string selection component for a dialog window.
	 *
	 * @param w Width of component
	 * @param h Height of component
	 * @param caption Label text
	 * @param options Option list
	 * @return String selection component
	 */
	InputComponentStringBox getStringBox(
			final int w, final int h, final String caption, final List<String> options
	);

	/**
	 * Creates a string declaration component for a dialog window.
	 * 
	 * @param w Width of component
	 * @param h Height of component
	 * @param caption Label text
	 * @return String component
	 */
	InputComponentMessageField getMessageField(final int w, final int h, final String caption);

	/**
	 * Creates an input component from parameter data.
	 * 
	 * @param w Width of component
	 * @param h Height of component
	 * @param parameter Parameter data
	 * @return Input component
	 */
	InputComponent getFromParameterData(final int w, final int h, ConfigurationParamaterModel parameter);

	/**
	 * Creates an input component from parameter data and bypasses different choices than the defaults.
	 *
	 * @param w Width of component
	 * @param h Height of component
	 * @param parameter Parameter data
	 * @return Input component
	 */
	InputComponent getFromParameterData(int w, int h, ConfigurationParamaterModel parameter, Vector<String> choices);
}

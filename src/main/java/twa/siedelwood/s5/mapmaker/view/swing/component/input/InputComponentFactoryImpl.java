
package twa.siedelwood.s5.mapmaker.view.swing.component.input;

import lombok.NoArgsConstructor;
import twa.siedelwood.s5.mapmaker.model.meta.ConfigurationParamaterModel;

import java.util.List;
import java.util.Vector;

/**
 * Implementation for the input component factory.
 * 
 * @author totalwarANGEL
 *
 */
@NoArgsConstructor
public class InputComponentFactoryImpl implements InputComponentFactory
{
    /**
     * {@inheritDoc}
     */
    @Override
    public InputComponentBooleanBox getBooleanBox(final int w, final int h, final String caption)
    {
        final InputComponentBooleanBox component = new InputComponentBooleanBox(w, h);
        component.setCaption(caption);
        return component;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputComponentIntegerField getIntegerField(final int w, final int h, final String caption)
    {
        final InputComponentIntegerField component = new InputComponentIntegerField(w, h);
        component.setCaption(caption);
        return component;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputComponentStringField getStringField(final int w, final int h, final String caption)
    {
        final InputComponentStringField component = new InputComponentStringField(w, h);
        component.setCaption(caption);
        return component;
    }

    /**
     * {@inheritDoc}
     */
    public InputComponentStringFieldChoice getStringChoiceField(int w, int h, String caption) {
        return new InputComponentStringFieldChoice(w, h, caption);
    }

    /**
     * {@inheritDoc}
     */
    public InputComponentStringFieldChoice getStringChoiceField(int w, int h, String caption, Vector<String> chouces) {
        return new InputComponentStringFieldChoice(w, h, caption, chouces);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputComponentMessageField getMessageField(final int w, final int h, final String caption)
    {
        final InputComponentMessageField component = new InputComponentMessageField(w, h);
        component.setCaption(caption);
        return component;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputComponentIntegerBox getIntegerBox(
        final int w, final int h, final String caption, final List<String> options
    )
    {
        final InputComponentIntegerBox component = new InputComponentIntegerBox(w, h);
        component.setCaption(caption);
        component.setOptions(options);
        return component;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputComponentStringBox getStringBox(
        final int w, final int h, final String caption, final List<String> options
    )
    {
        final InputComponentStringBox component = new InputComponentStringBox(w, h);
        component.setCaption(caption);
        component.setOptions(options);
        return component;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputComponent getFromParameterData(final int w, final int h, final ConfigurationParamaterModel parameter)
    {
        if (parameter.getType().equals("NumberInput"))
        {
            return getIntegerField(w, h, parameter.getDescription());
        }
        if (parameter.getType().equals("NumberSelect"))
        {
            final InputComponentIntegerBox box = getIntegerBox(w, h, parameter.getDescription(), parameter.getDefaults());
            return box;
        }
        if (parameter.getType().equals("StringInput"))
        {
            return getStringField(w, h, parameter.getDescription());
        }
        if (parameter.getType().equals("StringSelect"))
        {
            return getStringBox(w, h, parameter.getDescription(), parameter.getDefaults());
        }
        if (parameter.getType().equals("MessageInput"))
        {
            return getMessageField(w, h, parameter.getDescription());
        }
        if (parameter.getType().equals("BooleanSelect"))
        {
            return getBooleanBox(w, h, parameter.getDescription());
        }
        if (parameter.getType().contains("Name"))
        {
            return getStringChoiceField(w, h, parameter.getDescription());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputComponent getFromParameterData(final int w, final int h, final ConfigurationParamaterModel parameter, Vector<String> choices)
    {
        if (parameter.getType().equals("NumberInput"))
        {
            return getIntegerField(w, h, parameter.getDescription());
        }
        if (parameter.getType().equals("NumberSelect"))
        {
            return getIntegerBox(w, h, parameter.getDescription(), choices);
        }
        if (parameter.getType().equals("StringInput"))
        {
            return getStringField(w, h, parameter.getDescription());
        }
        if (parameter.getType().equals("StringSelect"))
        {
            return getStringBox(w, h, parameter.getDescription(), choices);
        }
        if (parameter.getType().equals("MessageInput"))
        {
            return getMessageField(w, h, parameter.getDescription());
        }
        if (parameter.getType().equals("BooleanSelect"))
        {
            return getBooleanBox(w, h, parameter.getDescription());
        }
        if (parameter.getType().contains("Name"))
        {
            return getStringChoiceField(w, h, parameter.getDescription(), choices);
        }
        return null;
    }
}

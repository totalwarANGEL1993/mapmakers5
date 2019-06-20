package twa.siedelwood.s5.mapmaker.service.map;

/**
 * Exception for failed map loading.
 * @author totalwarANGEL
 *
 */
@SuppressWarnings("serial")
public class MapLoaderException extends Exception
{
	public MapLoaderException()
	{
	}

	public MapLoaderException(final String message)
	{
		super(message);
	}

	public MapLoaderException(final Throwable cause)
	{
		super(cause);
	}

	public MapLoaderException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public MapLoaderException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

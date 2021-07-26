
package twa.siedelwood.s5.mapmaker.service.map;

import java.io.InputStream;
import java.util.Vector;

/**
 * Interface for map loaders.
 * 
 * @author totalwarANGEL
 *
 */
public interface MapLoader
{
	/**
	 * Selects a map file that will be processed by the map loader.
	 * 
	 * @param filename Map to select
	 * @throws MapLoaderException
	 */
	void selectMap(String filename) throws MapLoaderException;

	/**
	 * Packs the map.
	 * 
	 * @throws MapLoaderException
	 */
	void packMap() throws MapLoaderException;

	/**
	 * Unpacks the map.
	 * 
	 * @throws MapLoaderException
	 */
	void unpackMap() throws MapLoaderException;

	/**
	 * Copies the community lib into the map.
	 *
	 * @throws MapLoaderException
	 */
	void addCommunityLib() throws MapLoaderException;

	/**
	 * Copies a file to new location inside the map.
	 * 
	 * @param source Source file to copy
	 * @param dest Destinated location
	 * @throws MapLoaderException
	 */
	void add(String source, String dest) throws MapLoaderException;

	/**
	 * Writes an input stream as file to a location inside the map.
	 * 
	 * @param source Source stream
	 * @param dest Destinated location
	 * @throws MapLoaderException
	 */
	void add(InputStream source, String dest) throws MapLoaderException;

	/**
	 * Removes a file from the map.
	 * 
	 * @param filename
	 * @throws MapLoaderException
	 */
	void remove(String filename) throws MapLoaderException;

	/**
	 * Removes the map folder.
	 * @throws MapLoaderException
	 */
	void removeMapFolder() throws MapLoaderException;

	/**
	 * Returns all script names inside the map. Map must be unpacked first!
	 * @return Vector of script names
	 * @throws MapLoaderException
	 */
	Vector<String> readScriptNames() throws MapLoaderException;
}


package twa.siedelwood.s5.mapmaker.service.map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import twa.siedelwood.s5.mapmaker.model.data.quest.Quest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation for a map loader that uses the external BBA tol by yoq.
 * 
 * @author totalwarANGEL
 *
 */
public class BBAToolMapLoader implements MapLoader
{

    protected String mapPath;

    /**
     * Constructor
     */
    public BBAToolMapLoader()
    {
        mapPath = "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectMap(final String filename) throws MapLoaderException
    {
        mapPath = filename;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void packMap() throws MapLoaderException
    {
        final File result = new File(mapPath.substring(0, mapPath.length() - 9));
        try
        {
            System.out.println(result.getPath());
            if (result.exists())
            {
                System.out.println("MapLoader: Deleting old version...");
                Files.delete(Paths.get(result.getAbsolutePath()));
                System.out.println("MapLoader: Done!");
            }
        }
        catch (final Exception e)
        {
            throw new MapLoaderException(e);
        }

        final File f = new File(mapPath);
        if (!f.exists() || !f.isDirectory())
        {
            throw new MapLoaderException("Could not pack map: " + mapPath);
        }
        execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unpackMap() throws MapLoaderException
    {
        final File result = new File(mapPath + ".unpacked");
        try
        {
            System.out.println("MapLoader: Deleting unpacked...");
            if (result.exists())
            {
                Files.walk(Paths.get(result.getAbsolutePath())).sorted(Comparator.reverseOrder()).map(Path::toFile)
                .peek(System.out::println).forEach(File::delete);
            }
            System.out.println("MapLoader: Done!");
        }
        catch (final Exception e)
        {
            throw new MapLoaderException(e);
        }

        final File f = new File(mapPath);
        if (!f.exists() || f.isDirectory())
        {
            throw new MapLoaderException("Could not unpack map: " + mapPath);
        }

        execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCommunityLib() throws MapLoaderException
    {
        try {
            String s5cBasePath = "lua/orthus/lua/s5c/";
            File s5cSrc = new File(s5cBasePath);
            File s5cDst = new File(mapPath + "/maps/externalmap/s5c");
            // s5cDst.mkdirs();
            FileUtils.copyDirectory(s5cSrc, s5cDst);
            File s5cDstGit = new File(mapPath + "/maps/externalmap/s5c/s5communitylib/.git");
            if (s5cDstGit.exists())
                FileUtils.deleteDirectory(s5cDstGit);
        }
        catch (Exception e) {
            throw new MapLoaderException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(final String source, String dest) throws MapLoaderException
    {
        dest = dest == null ? "" : dest;
        final File destFile = new File(mapPath + "/" + dest);
        destFile.mkdirs();
        try
        {
            final Path sourcePath = Paths.get(source);
            final Path destPath = Paths.get(destFile.getAbsolutePath());
            destPath.toFile().delete();
            System.out.println("MapLoader: copy " + sourcePath + " -> " + destPath);
            Files.copy(sourcePath, destPath);
        }
        catch (final Exception e)
        {
            throw new MapLoaderException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(final InputStream source, String dest) throws MapLoaderException
    {
        dest = dest == null ? "" : dest;
        final File destFile = new File(mapPath + "/" + dest);

        destFile.mkdirs();
        try
        {
            final Path destPath = Paths.get(destFile.getAbsolutePath());
            destPath.toFile().delete();
            System.out.println("MapLoader: copy stream -> " + destPath);
            Files.copy(source, destPath);
        }
        catch (final Exception e)
        {
            throw new MapLoaderException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(final String filename) throws MapLoaderException
    {
        File file = new File(mapPath + "/" + filename);
        if (file.isDirectory()) {
            deleteDirectory(file);
        }
        else {
            file.delete();
        }
    }

    /**
     *
     * @throws MapLoaderException
     */
    @Override
    public void removeMapFolder() throws MapLoaderException {
        String path = mapPath;
        if (!path.endsWith(".unpacked")) {
            path = path + ".unpacked";
        }
        deleteDirectory(new File(path));
    }

    /**
     * Returns all script names inside the map. Map must be unpacked first!
     * @return Vector of script names
     * @throws MapLoaderException
     */
    @Override
    public Vector<String> readScriptNames() throws MapLoaderException {
        Vector<String> scriptNames = new Vector<>();
        try {
            String mapData = new String(Files.readAllBytes(Paths.get(mapPath + ".unpacked/maps/externalmap/mapdata.xml")), Charset.forName("UTF-8"));
            Matcher m = Pattern.compile("<Name>.*</Name>").matcher(mapData);
            while (m.find()) {
                String scriptName = m.group();
                scriptName = scriptName.substring(6, scriptName.length()-7);
                if (scriptName.length() > 0) {
                    scriptNames.add(scriptName);
                }
            }
        } catch (Exception e) {
            throw new MapLoaderException(e);
        }
        scriptNames.sort(Comparator.comparing(String::toLowerCase));
        return scriptNames;
    }

    /**
     * Returns if the operating system is windows.
     * 
     * @return Is windows
     */
    protected boolean isWindows()
    {
        return SystemUtils.IS_OS_WINDOWS;
    }

    /**
     * Deletes a directory with contents.
     * @param directory Directory to delete
     * @return
     */
    protected boolean deleteDirectory(final File directory) {
        if (directory.exists()) {
            final File[] files = directory.listFiles();
            if (null != files) {
                for (final File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    }
                    else {
                        file.delete();
                    }
                }
            }
        }
        return directory.delete();
    }

    /**
     * Builds the command string for the bba tool.
     * 
     * @param path Map path
     * @return Command string
     */
    protected String buildExecutionString(final String path)
    {
        String exec = System.getProperty("user.dir") + "\\bin\\bba5.exe \"" + path + "\"";
        if (!isWindows())
        {
            exec = "wine " + System.getProperty("user.dir") + "/bin/bba5.exe \"" + path + "\"";
        }
        System.out.println(exec);
        return exec;
    }

    /**
     * Executes the bba command.
     * 
     * @throws MapLoaderException
     */
    protected void execute() throws MapLoaderException
    {
        try
        {
            System.out.println("MapLoader: Processing map...");

            final Process process = Runtime.getRuntime().exec(buildExecutionString(mapPath));

            /*
            final InputStream is = process.getInputStream();
            int size = 0;
            final byte[] buffer = new byte[1024];
            while ((size = is.read(buffer)) != -1)
            {
                System.out.write(buffer, 0, size);
            }
            process.waitFor();
            */

            if (process.waitFor() != 0)
            {
                throw new MapLoaderException("MapLoader: Error while packing/unpacking a map!");
            }
            else
            {
                System.out.println("Maploader: Ready!");
            }
        }
        catch (final Exception e)
        {
            throw new MapLoaderException(e);
        }
    }
}

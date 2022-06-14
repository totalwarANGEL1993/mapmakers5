
package twa.siedelwood.s5.mapmaker.service.map;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
public class McbToolMapLoader implements MapLoader
{
    protected String mapPath;

    /**
     * Constructor
     */
    public McbToolMapLoader()
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
                Files.walk(Paths.get(result.getAbsolutePath()))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .peek(System.out::println)
                    .forEach(File::delete);
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
            copyDirectory(s5cSrc, s5cDst);
            File s5cDstGit = new File(mapPath + "/maps/externalmap/s5c/s5communitylib/.git");
            if (s5cDstGit.exists())
                deleteDirectory(s5cDstGit);
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

    @Override
    public void convertToLowercase() throws MapLoaderException {
        throw new MapLoaderException("Not implemented!");
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
        final Vector<String> scriptNames = new Vector<>();
        try {
            final String mapData = new String(
                Files.readAllBytes(Paths.get(mapPath + ".unpacked/maps/externalmap/mapdata.xml")),
                StandardCharsets.UTF_8
            );
            final Matcher m = Pattern.compile("<Name>.*</Name>").matcher(mapData);
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

    // Test

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
     * Copies a directory with contents.
     * @param src Directory to copy
     * @param dst Destination
     */
    public static void copyDirectory(File src, File dst) throws IOException
    {
        Files.walk(Paths.get(src.getAbsolutePath()))
            .forEach(source -> {
                Path destination = Paths.get(
                    dst.getAbsolutePath(),
                    source.toString().substring(src.getAbsolutePath().length())
                );
                try {
                    if (destination.toFile().exists()) {
                        destination.toFile().delete();
                    }
                    Files.copy(source, destination);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

    /**
     * Deletes a directory with contents.
     * @param directory Directory to delete
     * @return Success
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
     * @param mapPath Path to map
     * @return Command string
     */
    protected String[] buildExecutionString(final String mapPath) throws MapLoaderException {
        String mapSourcePath;
        String mapDestPath;
        if (mapPath.endsWith(".unpacked")) {
            mapSourcePath = new String(mapPath);
            mapDestPath = mapPath.substring(0, mapPath.length() - 9);
        }
        else if (mapPath.endsWith(".s5x")) {
            mapSourcePath = mapPath;
            mapDestPath = mapPath + ".unpacked";
        }
        else {
            throw new MapLoaderException("File " + mapPath + " is of unsupported extension!");
        }

        final List<String> parameterList = new ArrayList<>();
        parameterList.add(System.getProperty("user.dir") + "\\bin\\bba\\bbaToolS5.exe");
        parameterList.add("-err");
        parameterList.add("\"" + mapSourcePath.replaceAll(" ", "\\\\ ") + "\"");
        parameterList.add("\"" + mapDestPath.replaceAll(" ", "\\\\ ") + "\"");
        if (!isWindows())
        {
            parameterList.add(0, "wine");
        }
        final String exec = String.join(" ", parameterList);
        System.out.println(exec);

        final String[] parameterArray = new String[parameterList.size()];
        for (int i=0; i<parameterList.size(); i++) {
            parameterArray[i] = parameterList.get(i);
        }
        return parameterArray;
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
            final String[] parameter = buildExecutionString(mapPath);
            final Process process = new ProcessBuilder(parameter).start();

            int processResult = process.waitFor();
            if (processResult != 0)
            {
                throw new MapLoaderException("MapLoader: Error while packing/unpacking a map! Process result: " + processResult);
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

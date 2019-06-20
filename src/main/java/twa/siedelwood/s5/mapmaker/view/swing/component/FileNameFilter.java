package twa.siedelwood.s5.mapmaker.view.swing.component;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * File filter that only accepts a single file with a specific name.
 */
public class FileNameFilter extends FileFilter {
    private final String filename;
    private String description;

    /**
     * Constructor
     * @param filename Desired name
     * @param description Description
     */
    public FileNameFilter(String filename, String description) {
        this.filename = filename;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     * @param f File
     * @return File is accepted
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        return f.getName().equals(filename);
    }
}

package cz.zcu.kiv.oop.uxf.util;

import java.io.File;
import java.io.FilenameFilter;

import org.springframework.util.StringUtils;

/**
 * Filename filter of UXF files.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfFilenameFilter implements FilenameFilter {

  /** Extension of UXF files. */
  public static final String UXF_EXTENSION = "uxf";

  /** Singleton instance of this filename filter. */
  protected static UxfFilenameFilter instance;

  /**
   * Private constructor which makes from this class the singleton.
   */
  private UxfFilenameFilter() {}

  /**
   * Tests if a specified file is UXF file and should be included in a file list.
   *
   * @param dir the directory in which the file was found.
   * @param name the name of the file.
   * @return <code>true</code> if and only if the name is UXF file should be included in the file list; <code>false</code>
   *         otherwise.
   */
  @Override
  public boolean accept(File dir, String name) {
    String extension = StringUtils.getFilenameExtension(name);

    return extension != null && extension.equalsIgnoreCase(UXF_EXTENSION);
  }

  /**
   * Returns singleton instance of this filename filter.
   *
   * @return Singleton instance of this filename filter.
   */
  public static UxfFilenameFilter getInstance() {
    if (instance == null) {
      instance = new UxfFilenameFilter();
    }

    return instance;
  }

}

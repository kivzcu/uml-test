package cz.zcu.kiv.oop.uxf.util;

import java.io.File;
import java.io.FileFilter;

import org.springframework.util.StringUtils;

/**
 * File filter of UXF files.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfFileFilter implements FileFilter {

  /** Singleton instance of this file filter. */
  protected static UxfFileFilter instance;

  /**
   * Private constructor which makes from this class the singleton.
   */
  private UxfFileFilter() {}

  @Override
  public boolean accept(File pathname) {
    if (pathname.isDirectory()) {
      return false;
    }

    String extension = StringUtils.getFilenameExtension(pathname.getName());

    return extension != null && extension.equalsIgnoreCase(UxfFilenameFilter.UXF_EXTENSION);
  }

  /**
   * Returns singleton instance of this file filter.
   *
   * @return Singleton instance of this file filter.
   */
  public static UxfFileFilter getInstance() {
    if (instance == null) {
      instance = new UxfFileFilter();
    }

    return instance;
  }

}

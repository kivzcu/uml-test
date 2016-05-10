package cz.zcu.kiv.oop.uxf.version;

import cz.zcu.kiv.oop.uxf.exception.UxfVersionReaderException;

/**
 * Reader which serves for reading version from UXF files. This reader is used by {@linkplain cz.zcu.kiv.oop.uxf.UxfReaderFactory}
 * for determination of implementation of UXF reader.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface UxfVersionReader {

  /**
   * Returns read version of UXF file.
   *
   * @param uxfFile path to uxf file from which will be read the version.
   * @return Read version of UXF file.
   * @throws UxfVersionReaderException If some error occurs during reading of UXF version.
   */
  public UxfVersion readVersion(String uxfFile) throws UxfVersionReaderException;

}

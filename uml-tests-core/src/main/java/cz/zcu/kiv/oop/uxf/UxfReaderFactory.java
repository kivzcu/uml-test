package cz.zcu.kiv.oop.uxf;

import cz.zcu.kiv.oop.uxf.exception.UxfReaderFactoryException;
import cz.zcu.kiv.oop.uxf.reader.UxfReader;

/**
 * Factory which creates UXF readers depends on version of UXF file.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public interface UxfReaderFactory {

  /**
   * Returns UXF reader depends on version of UXF file with specified path.
   *
   * @param uxfFile path to UXF file for which will be returned reader.
   * @return UXF reader which can reads UXF files of specified version.
   * @throws UxfReaderFactoryException If some error occurs during reader creation (for example if cannot be found implementation of
   *           UXF reader for some version).
   */
  public UxfReader getReader(String uxfFile) throws UxfReaderFactoryException;

}

package cz.zcu.kiv.oop.uxf;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uxf.exception.UxfReaderFactoryException;
import cz.zcu.kiv.oop.uxf.exception.UxfVersionReaderException;
import cz.zcu.kiv.oop.uxf.reader.UxfReader;
import cz.zcu.kiv.oop.uxf.version.UxfVersion;
import cz.zcu.kiv.oop.uxf.version.UxfVersionReader;
import cz.zcu.kiv.oop.uxf.version.UxfVersionReaderImpl;

/**
 * Singleton implementation of factory which creates UXF readers depends on version of UXF file.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfReaderFactoryImpl implements UxfReaderFactory {

  /** Singleton instance of this factory. */
  protected static UxfReaderFactory instance;

  /** Cache for created readers. This cache maps UXF version to created reader. */
  protected final Map<Integer, UxfReader> readersCache = new HashMap<Integer, UxfReader>();
  /** Reader of version of UXF file. */
  protected final UxfVersionReader versionReader = UxfVersionReaderImpl.getInstance();

  /**
   * Private constructor which makes from this class the singleton.
   */
  private UxfReaderFactoryImpl() {}

  /**
   * Returns UXF reader depends on version of UXF file with specified path.
   *
   * @param uxfFile path to UXF file for which will be returned reader.
   * @return UXF reader which can reads UXF files of specified version.
   * @throws UxfReaderFactoryException If some error occurs during reader creation (for example if cannot be found implementation of
   *           UXF reader for some version).
   */
  @Override
  public UxfReader getReader(String uxfFile) throws UxfReaderFactoryException {
    if (!StringUtils.hasText(uxfFile)) {
      throw new UxfReaderFactoryException(Strings.get("exc.uxf.empty-filename"));
    }

    UxfVersion uxfVersion = getUxfVersion(uxfFile);
    UxfReader reader = readersCache.get(uxfVersion.getMajor());
    if (reader == null) {
      reader = createReader(uxfVersion);
      readersCache.put(uxfVersion.getMajor(), reader);
    }

    return reader;
  }

  /**
   * Returns version of UXF file with specified path.
   *
   * @param uxfFile path to UXF file from which will be read version.
   * @return Version of UXF file.
   * @throws UxfReaderFactoryException If version of UXF cannot be obtained (read from UXF file).
   */
  protected UxfVersion getUxfVersion(String uxfFile) throws UxfReaderFactoryException {
    try {
      UxfVersion version = versionReader.readVersion(uxfFile);
      if (version == null) {
        throw new UxfReaderFactoryException(Strings.get("exc.uxf.cannot-read-version"));
      }

      return version;
    }
    catch (UxfVersionReaderException exc) {
      throw new UxfReaderFactoryException(Strings.get("exc.uxf.version-reader-exception"), exc);
    }
  }

  /**
   * Creates reader for reading UXF files with specified version. Readers are cached and for same versions are returned same
   * instances of readers (readers are like singletons).
   *
   * @param uxfVersion version of UXF file for which will be created reader.
   * @return Created UXF reader which can read UXF files with specified version.
   * @throws UxfReaderFactoryException If some error occurs during UXF reader creation.
   */
  protected UxfReader createReader(UxfVersion uxfVersion) throws UxfReaderFactoryException {
    String uxfReaderImplementationName = getReaderClassName(uxfVersion);

    Object uxfReaderObject = null;
    try {
      Class<?> uxfReaderImplementationClass = Class.forName(uxfReaderImplementationName);
      uxfReaderObject = uxfReaderImplementationClass.newInstance();
    }
    catch (ClassNotFoundException exc) {
      throw new UxfReaderFactoryException(
              Strings.getFormatted("exc.uxf.reader-not-found", uxfReaderImplementationName), exc);
    }
    catch (Exception exc) {
      throw new UxfReaderFactoryException(Strings.get("exc.uxf.reader-init"), exc);
    }

    if (uxfReaderObject instanceof UxfReader) {
      return (UxfReader)uxfReaderObject;
    }

    throw new UxfReaderFactoryException(
            Strings.getFormatted("exc.uxf.this-class-cannot-read", uxfReaderImplementationName));
  }

  /**
   * Returns implementation class name for reader which serves for reading of UXF files with specified version. Name of reader
   * implementation is made by this rule:
   *
   * <pre>
   *  {UxfReader.package}.v{UxfVersion.majorVersion}.UxfReaderImpl
   * </pre>
   *
   * @param uxfVersion version of UXF file for which will be returned class name of implementation.
   * @return Class name of UXF reader implementation for specified version of UXF file.
   */
  protected String getReaderClassName(UxfVersion uxfVersion) {
    Package uxfReaderPackage = UxfReader.class.getPackage();

    return uxfReaderPackage.getName() + ".v" + uxfVersion.getMajor() + "." + UxfReader.class.getSimpleName() + "Impl";
  }

  /**
   * Returns singleton instance of this factory.
   *
   * @return Singleton instance of this factory.
   */
  public static UxfReaderFactory getInstance() {
    if (instance == null) {
      instance = new UxfReaderFactoryImpl();
    }

    return instance;
  }
}

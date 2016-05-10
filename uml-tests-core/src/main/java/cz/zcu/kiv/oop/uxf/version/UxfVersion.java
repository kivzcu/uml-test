package cz.zcu.kiv.oop.uxf.version;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uxf.exception.UxfVersionFormatException;

/**
 * Stores version of UXF file.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfVersion implements Cloneable, Serializable {

  /** Serial version UID. */
  private static final long serialVersionUID = 3304906834031955667L;

  /** Major part of version of UXF file. */
  protected final int major;
  /** Minor part of version of UXF file. */
  protected final int minor;
  /** Patch part of version of UXF file. */
  protected final int patch;

  /**
   * Constructs UXF version.
   *
   * @param major number of major part.
   */
  public UxfVersion(int major) {
    this(major, 0, 0);
  }

  /**
   * Constructs UXF version.
   *
   * @param major number of major part.
   * @param minor number of minor part.
   */
  public UxfVersion(int major, int minor) {
    this(major, minor, 0);
  }

  /**
   * Constructs UXF version.
   *
   * @param major number of major part.
   * @param minor number of minor part.
   * @param patch number of patch part.
   */
  public UxfVersion(int major, int minor, int patch) {
    this.major = major;
    this.minor = minor;
    this.patch = patch;
  }

  /**
   * Parses entered string into version of UXF. Entered string have to be in format <code>MAJOR</code>, <code>MAJOR.MINOR</code> of
   * <code>MAJOR.MINOR.PATCH</code>.
   *
   * @param version UXF version in string which will be parsed.
   * @return Parsed UXF version.
   * @throws UxfVersionFormatException If entered string has illegal format.
   */
  public static UxfVersion parseVersion(String version) throws UxfVersionFormatException {
    if (!StringUtils.hasText(version)) {
      throw new UxfVersionFormatException(Strings.get("exc.uxf.version.empty"));
    }

    if (!version.matches("^([0-9]+\\.)*[0-9]+$")) {
      throw new UxfVersionFormatException(Strings.get("exc.uxf.version.wrong-format"));
    }

    int[] versionParts = {0, 0, 0};
    String[] parsedVersionParts = version.split("\\.");
    if (parsedVersionParts.length > versionParts.length) {
      throw new UxfVersionFormatException(Strings.get("exc.uxf.version.too-long"));
    }

    try {
      for (int i = 0; i < parsedVersionParts.length; i++) {
        versionParts[i] = Integer.parseInt(parsedVersionParts[i]);
      }
    }
    catch (NumberFormatException exc) {
      throw new UxfVersionFormatException(Strings.get("exc.uxf.version.wrong-format"), exc);
    }

    return new UxfVersion(versionParts[0], versionParts[1], versionParts[2]);
  }

  /**
   * Return number of major part of UXF version.
   *
   * @return Major part of UXF version.
   */
  public int getMajor() {
    return major;
  }

  /**
   * Return number of minor part of UXF version.
   *
   * @return Minor part of UXF version.
   */
  public int getMinor() {
    return minor;
  }

  /**
   * Return number of patch part of UXF version.
   *
   * @return Patch part of UXF version.
   */
  public int getPatch() {
    return patch;
  }

  /**
   * Clones version of UXF file.
   *
   * @return Clone of UXF file version.
   */
  @Override
  public UxfVersion clone() {
    return new UxfVersion(major, minor, patch);
  }

  /**
   * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by
   * {@link java.util.HashMap}.
   *
   * @return A hash code value for this object.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;

    result = prime * result + major;
    result = prime * result + minor;
    result = prime * result + patch;

    return result;
  }

  /**
   * Indicates whether some object is "equal to" this one.
   *
   * @param obj the reference object with which to compare.
   * @return <code>true</code> if this object is the same as the obj argument; <code>false</code> otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof UxfVersion)) {
      return false;
    }

    UxfVersion other = (UxfVersion)obj;
    if (major != other.major) {
      return false;
    }

    if (minor != other.minor) {
      return false;
    }

    if (patch != other.patch) {
      return false;
    }

    return true;
  }

  /**
   * Returns string value of UXF version.
   *
   * @return String value of UXF version.
   */
  @Override
  public String toString() {
    return getClass().getName() + " [major=" + major + ", minor=" + minor + ", patch=" + patch + "]";
  }

}

package cz.zcu.kiv.oop.uxf.exception;

/**
 * Exception which is thrown by readers of UXF versions from UXF files.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfVersionReaderException extends UxfException {

  /** Serial version UID. */
  private static final long serialVersionUID = -8262401920994268381L;

  /**
   * Constructs exception.
   */
  public UxfVersionReaderException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public UxfVersionReaderException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public UxfVersionReaderException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public UxfVersionReaderException(String message, Throwable cause) {
    super(message, cause);
  }

}

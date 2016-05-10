package cz.zcu.kiv.oop.uxf.exception;

/**
 * Exception which is thrown by readers of UXF files.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfReaderException extends UxfException {

  /** Serial version UID. */
  private static final long serialVersionUID = 2228187507185773054L;

  /**
   * Constructs exception.
   */
  public UxfReaderException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public UxfReaderException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public UxfReaderException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public UxfReaderException(String message, Throwable cause) {
    super(message, cause);
  }

}

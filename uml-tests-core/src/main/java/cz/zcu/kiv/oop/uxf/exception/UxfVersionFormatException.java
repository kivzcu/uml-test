package cz.zcu.kiv.oop.uxf.exception;

/**
 * Runtime exception which is thrown in case of parsing UXF version in wrong format.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfVersionFormatException extends IllegalArgumentException {

  /** Serial version UID. */
  private static final long serialVersionUID = -25938381159863812L;

  /**
   * Constructs exception.
   */
  public UxfVersionFormatException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public UxfVersionFormatException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public UxfVersionFormatException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public UxfVersionFormatException(String message, Throwable cause) {
    super(message, cause);
  }

}

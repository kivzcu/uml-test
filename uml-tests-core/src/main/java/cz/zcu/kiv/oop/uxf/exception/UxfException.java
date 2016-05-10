package cz.zcu.kiv.oop.uxf.exception;

/**
 * General exception for manipulation with UXF files.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfException extends Exception {

  /** Serial version UID. */
  private static final long serialVersionUID = 8391292278783390128L;

  /**
   * Constructs exception.
   */
  public UxfException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public UxfException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public UxfException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public UxfException(String message, Throwable cause) {
    super(message, cause);
  }

}

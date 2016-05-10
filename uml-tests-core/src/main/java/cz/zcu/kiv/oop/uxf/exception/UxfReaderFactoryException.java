package cz.zcu.kiv.oop.uxf.exception;

/**
 * Exception which is thrown by factory which creates readers of UXF files depending on version of UXF file.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UxfReaderFactoryException extends UxfException {

  /** Serial version UID. */
  private static final long serialVersionUID = 6980119453534765323L;

  /**
   * Constructs exception.
   */
  public UxfReaderFactoryException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public UxfReaderFactoryException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public UxfReaderFactoryException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public UxfReaderFactoryException(String message, Throwable cause) {
    super(message, cause);
  }

}

package cz.zcu.kiv.oop.uml.exception;

/**
 * This exception can be thrown during manipulation with UML elements.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlElementException extends UmlException {

  /** Serial version UID. */
  private static final long serialVersionUID = -3261935739102777956L;

  /**
   * Constructs exception.
   */
  public UmlElementException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public UmlElementException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public UmlElementException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public UmlElementException(String message, Throwable cause) {
    super(message, cause);
  }

}

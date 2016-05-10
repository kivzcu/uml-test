package cz.zcu.kiv.oop.uml.exception;

/**
 * General exception for manipulation with UML diagrams.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlException extends Exception {

  /** Serial version UID. */
  private static final long serialVersionUID = -5364438711213656151L;

  /**
   * Constructs exception.
   */
  public UmlException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public UmlException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public UmlException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public UmlException(String message, Throwable cause) {
    super(message, cause);
  }

}

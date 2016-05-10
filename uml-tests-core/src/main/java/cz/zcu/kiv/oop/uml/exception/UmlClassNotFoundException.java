package cz.zcu.kiv.oop.uml.exception;

/**
 * Runtime exception which can be thrown by {@link cz.zcu.kiv.oop.uml.util.UmlUtils} in case that the class with some name doesn't
 * exists in UML diagram.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlClassNotFoundException extends RuntimeException {

  /** Serial version UID. */
  private static final long serialVersionUID = 6798720090517845152L;

  /**
   * Constructs exception.
   */
  public UmlClassNotFoundException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public UmlClassNotFoundException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public UmlClassNotFoundException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public UmlClassNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

}

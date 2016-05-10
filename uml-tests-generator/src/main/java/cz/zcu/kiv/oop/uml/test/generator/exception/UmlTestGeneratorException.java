package cz.zcu.kiv.oop.uml.test.generator.exception;

/**
 * This exception can be thrown by generator of tests of UML diagram.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlTestGeneratorException extends Exception {

  /** Serial version UID. */
  private static final long serialVersionUID = -7507638992472371820L;

  /**
   * Constructs exception.
   */
  public UmlTestGeneratorException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public UmlTestGeneratorException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public UmlTestGeneratorException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public UmlTestGeneratorException(String message, Throwable cause) {
    super(message, cause);
  }

}

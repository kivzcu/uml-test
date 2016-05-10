package cz.zcu.kiv.oop.uml.junit.runners.model;

/**
 * Test runner exception which is thrown in case that one of dependencies tests was not run.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class NotRunDependentTestException extends Exception {

  /** Serial version UID. */
  private static final long serialVersionUID = -4658905423275661669L;

  /**
   * Constructs exception.
   */
  public NotRunDependentTestException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public NotRunDependentTestException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public NotRunDependentTestException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public NotRunDependentTestException(String message, Throwable cause) {
    super(message, cause);
  }

}

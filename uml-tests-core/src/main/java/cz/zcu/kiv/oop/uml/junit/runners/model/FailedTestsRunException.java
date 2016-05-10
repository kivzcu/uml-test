package cz.zcu.kiv.oop.uml.junit.runners.model;

/**
 * Test runner exception which is thrown in case that tests run failed.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class FailedTestsRunException extends RuntimeException {

  /** Serial version UID. */
  private static final long serialVersionUID = -4701661754590909212L;

  /**
   * Constructs exception.
   */
  public FailedTestsRunException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public FailedTestsRunException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public FailedTestsRunException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public FailedTestsRunException(String message, Throwable cause) {
    super(message, cause);
  }

}

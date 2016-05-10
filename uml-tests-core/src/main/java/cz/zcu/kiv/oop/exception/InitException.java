package cz.zcu.kiv.oop.exception;

/**
 * This runtime exception can be thrown by in case of initialization problem.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class InitException extends RuntimeException {

  /** Serial version UID. */
  private static final long serialVersionUID = -6001422764186794053L;

  /**
   * Constructs exception.
   */
  public InitException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public InitException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public InitException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public InitException(String message, Throwable cause) {
    super(message, cause);
  }

}

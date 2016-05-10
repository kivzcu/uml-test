package cz.zcu.kiv.oop.exception;

/**
 * Thrown when a clone cannot be created. In contrast to {@link CloneNotSupportedException} but this is a {@link RuntimeException}.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class CloneFailedException extends RuntimeException {

  /** Serial version UID. */
  private static final long serialVersionUID = -3683279367931977086L;

  /**
   * Constructs exception.
   */
  public CloneFailedException() {}

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   */
  public CloneFailedException(String message) {
    super(message);
  }

  /**
   * Constructs exception.
   *
   * @param cause cause of this exception.
   */
  public CloneFailedException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructs exception.
   *
   * @param message message with details for exception.
   * @param cause cause of this exception.
   */
  public CloneFailedException(String message, Throwable cause) {
    super(message, cause);
  }

}

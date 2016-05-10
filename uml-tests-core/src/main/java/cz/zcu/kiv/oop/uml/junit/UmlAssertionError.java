package cz.zcu.kiv.oop.uml.junit;

/**
 * Assertion error which is thrown by {@link UmlAssert} in case of some error during assertion.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlAssertionError extends AssertionError {

  /** Serial version UID. */
  private static final long serialVersionUID = 6244327689545872639L;

  /**
   * Constructs assertion error.
   *
   * @param message message of error.
   */
  public UmlAssertionError(String message) {
    super("UML: " + message);
  }

}

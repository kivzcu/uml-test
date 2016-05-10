package cz.zcu.kiv.oop.uml.junit.runners.model;

/**
 * Result of the test method run.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public enum TestResult {

  /** Result for test method which was not run. */
  NOT_RUN,
  /** Result for test method which was ignored. */
  IGNORED,
  /** Result for test method which was skipped because of unsatisfied test dependencies. */
  SKIPPED,
  /** Result for test method which was failed. */
  FAILURE,
  /** Result for test method in which the assumption error was occurred. */
  ASSUMPTION_FAILURE,
  /** Result for test method which was ran successfully. */
  SUCCESS,
  ;

}

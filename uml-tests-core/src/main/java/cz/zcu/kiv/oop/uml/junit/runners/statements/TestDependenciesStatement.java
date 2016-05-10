package cz.zcu.kiv.oop.uml.junit.runners.statements;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uml.junit.runners.model.NotRunDependentTestException;
import cz.zcu.kiv.oop.uml.junit.runners.model.TestResult;
import cz.zcu.kiv.oop.uml.junit.runners.model.TestsResults;

/**
 * Statement which checks before evaluating of next statement (invoking the test method) are satisfied all dependencies. That means
 * that before test invocation will be tested if all dependency test methods was run successfully.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class TestDependenciesStatement extends Statement {

  /** Next statement (test invocation) which will be evaluated after this one. */
  protected Statement next;
  /** Method for which will be tested dependencies. */
  protected FrameworkMethod method;
  /** Array of test method dependencies. */
  protected String[] dependencies;
  /** Results of ran tests. */
  protected TestsResults testsResults;

  /**
   * Constructs statement.
   *
   * @param next next statement (test invocation) which will be evaluated after this one.
   * @param method method for which will be tested dependencies.
   * @param dependencies array of test method dependencies.
   * @param testsResults results of ran tests.
   */
  public TestDependenciesStatement(Statement next, FrameworkMethod method, String[] dependencies, TestsResults testsResults) {
    this.next = next;
    this.method = method;
    this.dependencies = dependencies;
    this.testsResults = testsResults;
  }

  /**
   * Checks if all dependency test methods was run successfully.
   *
   * @return If all dependency test methods was run successfully.
   * @throws Throwable If some dependent test method was not run
   */
  protected boolean checkDependencies() throws Throwable {
    for (String dependency : dependencies) {
      if (testsResults.getTestResult(dependency) == TestResult.NOT_RUN) {
        throw new NotRunDependentTestException(Strings.getFormatted("exc.test.dependent-test-was-not-run", dependency));
      }

      if (testsResults.getTestResult(dependency) != TestResult.SUCCESS) {
        return false;
      }
    }

    return true;
  }

  /**
   * Evaluate statement - checks if all dependency test methods was run successfully and then evaluate next statement.
   *
   * @throws Throwable If some dependency test method was not run or another error occurs.
   */
  @Override
  public void evaluate() throws Throwable {
    if (!checkDependencies()) {
      testsResults.addTestResult(method.getName(), TestResult.SKIPPED);
      return;
    }

    next.evaluate();
  }

}

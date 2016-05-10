package cz.zcu.kiv.oop.uml.junit.runners;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uml.junit.runners.model.FailedTestsRunException;

/**
 * JUnit 4 runner which runs tests until the first failure occurs. After that the whole testing ends.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class BlockJUnit4ClassRunnerWithSingleFail extends ExtendedBlockJUnit4ClassRunner {

  /**
   * Constructs runner to run the test class {@code clazz}.
   *
   * @param clazz the test class which will be run with this runner.
   * @throws InitializationError if the test class is malformed.
   */
  public BlockJUnit4ClassRunnerWithSingleFail(Class<?> clazz) throws InitializationError {
    super(clazz);
  }

  /**
   * Runs a {@link Statement} that represents a leaf (aka atomic) test.
   *
   * @param statement statement of test method block.
   * @param description description of test method.
   * @param notifier notifier of running tests.
   */
  @Override
  protected final void runLeaf2(Statement statement, Description description, RunNotifier notifier) {
    EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
    eachNotifier.fireTestStarted();

    try {
      statement.evaluate();
    }
    catch (AssumptionViolatedException e) {
      eachNotifier.addFailedAssumption(e);
    }
    catch (Throwable e) {
      eachNotifier.addFailure(e);

      throw new FailedTestsRunException(
          Strings.getFormatted("exc.test.test-has-failed", description.getMethodName()));
    }
    finally {
      eachNotifier.fireTestFinished();
    }
  }

}

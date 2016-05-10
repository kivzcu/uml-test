package cz.zcu.kiv.oop.uml.junit.runners;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uml.junit.ImportantTest;
import cz.zcu.kiv.oop.uml.junit.TestDependencies;
import cz.zcu.kiv.oop.uml.junit.runners.model.FailedTestsRunException;
import cz.zcu.kiv.oop.uml.junit.runners.model.TestResult;
import cz.zcu.kiv.oop.uml.junit.runners.model.TestsResults;
import cz.zcu.kiv.oop.uml.junit.runners.statements.TestDependenciesStatement;

/**
 * JUnit 4 runner which supports tests which have dependencies between tests methods. Each test method can have dependencies which
 * have to be satisfied, that means the all test finished successfully. If some test was not run, the whole test running fails. If
 * one or more dependencies of test are not satisfied, the test method is skipped (ignored).
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class BlockJUnit4ClassRunnerWithDependencies extends ExtendedBlockJUnit4ClassRunner {

  /** Results of ran tests. */
  protected final TestsResults runnedTests = new TestsResults();

  /**
   * Constructs runner to run the test class {@code clazz}.
   *
   * @param clazz the test class which will be run with this runner.
   * @throws InitializationError if the test class is malformed.
   */
  public BlockJUnit4ClassRunnerWithDependencies(Class<?> clazz) throws InitializationError {
    super(clazz);
  }

  /**
   * Builds statement for test method block. Here is an outline of the default implementation:
   * <ul>
   * <li>Checks if all dependency test methods was run successfully and then evaluate next statement.
   * <li>Invoke {@code method} on the result of {@code createTest()}, and throw any exceptions thrown by either operation.
   * <li>HOWEVER, if {@code method}'s {@code @Test} annotation has the {@code expecting} attribute, return normally only if the
   * previous step threw an exception of the correct type, and throw an exception otherwise.
   * <li>HOWEVER, if {@code method}'s {@code @Test} annotation has the {@code timeout} attribute, throw an exception if the previous
   * step takes more than the specified number of milliseconds.
   * <li>ALWAYS run all non-overridden {@code @Before} methods on this class and superclasses before any of the previous steps; if
   * any throws an Exception, stop execution and pass the exception on.
   * <li>ALWAYS run all non-overridden {@code @After} methods on this class and superclasses after any of the previous steps; all
   * After methods are always executed: exceptions thrown by previous steps are combined, if necessary, with exceptions from After
   * methods into a {@link MultipleFailureException}.
   * <li>ALWAYS allow {@code @Rule} fields to modify the execution of the above steps. A {@code Rule} may prevent all execution of
   * the above steps, or add additional behavior before and after, or modify thrown exceptions. For more information, see
   * {@link TestRule}
   * </ul>
   *
   * @param method test method.
   * @param test instance of test class for calling test method.
   * @return Statement for test method block.
   */
  @Override
  @SuppressWarnings("deprecation")
  protected Statement buildMethodBlockStatement(FrameworkMethod method, Object test) {
    Statement statement = methodInvoker(method, test);
    statement = withDependencies(method, test, statement);
    statement = possiblyExpectingExceptions(method, test, statement);
    statement = withPotentialTimeout(method, test, statement);
    statement = withBefores(method, test, statement);
    statement = withAfters(method, test, statement);
    statement = withRules(method, test, statement);

    return statement;
  }

  /**
   * If test method has annotation {@link TestDependencies}, then returns statement for checking whether all dependency test methods
   * was run successfully.
   *
   * @param method the test method.
   * @param test instance of test class for calling test method.
   * @param next the next statement.
   * @return Statement which checks satisfying of all dependencies test methods.
   */
  protected Statement withDependencies(FrameworkMethod method, Object test, Statement next) {
    TestDependencies annotation = method.getAnnotation(TestDependencies.class);
    if (annotation == null || annotation.value() == null || annotation.value().length == 0) {
      return next;
    }

    return new TestDependenciesStatement(next, method, annotation.value(), runnedTests);
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

    String testName = description.getMethodName();
    try {
      statement.evaluate();

      TestResult result = runnedTests.getTestResult(testName);
      if (result == null || result == TestResult.NOT_RUN) {
        runnedTests.addTestResult(testName, TestResult.SUCCESS);
      }
      else if (result == TestResult.SKIPPED) {
        notifier.fireTestIgnored(description);
      }
    }
    catch (AssumptionViolatedException e) {
      runnedTests.addTestResult(testName, TestResult.ASSUMPTION_FAILURE);
      eachNotifier.addFailedAssumption(e);
    }
    catch (Throwable e) {
      runnedTests.addTestResult(testName, TestResult.FAILURE);
      eachNotifier.addFailure(e);

      if(description.getAnnotation(ImportantTest.class) != null) {
        throw new FailedTestsRunException(
            Strings.getFormatted("exc.test.important-test-has-failed", description.getMethodName()));
      }
    }
    finally {
      eachNotifier.fireTestFinished();
    }
  }

}

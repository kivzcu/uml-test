package cz.zcu.kiv.oop.uml.junit.runners;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.rules.MethodRule;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.RunnerScheduler;
import org.junit.runners.model.Statement;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uml.junit.runners.model.FailedTestsRunException;

/**
 * Extended and also extensible JUnit 4 runner. This class replaces most of private ancestor methods and variables with protected
 * equivalent or with method which reflectively invokes private method or variable.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class ExtendedBlockJUnit4ClassRunner extends BlockJUnit4ClassRunner {

  /**
   * Constructs runner to run the test class {@code clazz}.
   *
   * @param clazz the test class which will be run with this runner.
   * @throws InitializationError if the test class is malformed.
   */
  public ExtendedBlockJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
    super(clazz);
  }

  /**
   * Returns a Statement that, when executed, either returns normally if {@code method} passes, or throws an exception if
   * {@code method} fails. The statement is builded by method {@link #buildMethodBlockStatement(FrameworkMethod, Object)}.
   *
   * @param method test method for which will be created statement.
   * @return Statement for entered test method.
   */
  @Override
  protected Statement methodBlock(FrameworkMethod method) {
    Object test;
    try {
      test = new ReflectiveCallable() {
        @Override
        protected Object runReflectiveCall() throws Throwable {
          return createTest();
        }
      }.run();
    }
    catch (Throwable e) {
      return new Fail(e);
    }

    return buildMethodBlockStatement(method, test);
  }

  /**
   * Builds statement for test method block. Here is an outline of the default implementation:
   * <ul>
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
   * @param method the test method.
   * @param test instance of test class for calling test method.
   * @return Statement for test method block.
   */
  @SuppressWarnings("deprecation")
  protected Statement buildMethodBlockStatement(FrameworkMethod method, Object test) {
    Statement statement = methodInvoker(method, test);
    statement = possiblyExpectingExceptions(method, test, statement);
    statement = withPotentialTimeout(method, test, statement);
    statement = withBefores(method, test, statement);
    statement = withAfters(method, test, statement);
    statement = withRules(method, test, statement);

    return statement;
  }

  /**
   * Returns statement applying rules for entered statement.
   *
   * @param method the test method.
   * @param target instance of test class for calling test method.
   * @param statement the statement for which will be applied rules.
   * @return Statement applying rules for entered statement.
   */
  protected Statement withRules(FrameworkMethod method, Object target, Statement statement) {
    List<TestRule> testRules = getTestRules(target);
    Statement result = statement;
    result = withMethodRules(method, testRules, target, result);
    result = withTestRules(method, testRules, result);

    return result;
  }

  /**
   * Returns statement applying method rules for entered statement.
   *
   * @param method the test method.
   * @param testRules list of test rules from which will be used method rules.
   * @param target instance of test class for calling test method.
   * @param result statement for which will be applied rules.
   * @return Statement applying method rules for entered statement.
   */
  protected Statement withMethodRules(FrameworkMethod method, List<TestRule> testRules, Object target, Statement result) {
    for (MethodRule each : getMethodRules(target)) {
      if (!testRules.contains(each)) {
        result = each.apply(result, method, target);
      }
    }
    return result;
  }

  /**
   * Returns statement applying test rules for entered statement.
   *
   * @param method the test method.
   * @param testRules list of test rules.
   * @param statement statement for which will be applied rules.
   * @return Statement applying test rules for entered statement.
   */
  protected Statement withTestRules(FrameworkMethod method, List<TestRule> testRules, Statement statement) {
    return testRules.isEmpty() ? statement : new RunRules(statement, testRules, describeChild(method));
  }

  /**
   * Returns list of method rules of entered target.
   *
   * @param target the test case instance
   * @return a list of MethodRules that should be applied when executing this test
   */
  protected List<MethodRule> getMethodRules(Object target) {
    return rules(target);
  }

  /**
   * Returns a {@link Statement}: Call {@link #runChild(Object, RunNotifier)} on each object returned by {@link #getChildren()}
   * (subject to any imposed filter and sort). Also in case of
   */
  @Override
  protected Statement childrenInvoker(final RunNotifier notifier) {
    return new Statement() {
      @Override
      public void evaluate() throws Exception {
        try {
          runChildren(notifier);
        }
        catch (RuntimeException exc) {
          processChildError(exc);
        }
      }
    };
  }

  /**
   * Processes the error of child test run. If the entered exception is instance of {@link FailedTestsRunException}, the exception
   * is ignored. In another case the exception is re-thrown.
   *
   * @param exc runtime exception which can occurs during child test run.
   * @throws Exception If the entered exception is re-thrown.
   */
  protected void processChildError(RuntimeException exc) throws Exception {
    if (!(exc instanceof FailedTestsRunException)) {
      throw exc;
    }
  }

  /**
   * Runs all children tests.
   *
   * @param notifier notifier of running tests.
   */
  protected void runChildren(final RunNotifier notifier) {
    final RunnerScheduler currentScheduler = getScheduler();
    try {
      for (final FrameworkMethod each : getFilteredChildren2()) {
        currentScheduler.schedule(new Runnable() {
          @Override
          public void run() {
            runChild(each, notifier);
          }
        });
      }
    }
    finally {
      currentScheduler.finished();
    }
  }

  /**
   * Runs child test.
   *
   * @param method method of child test.
   * @param notifier notifier of running tests.
   */
  @Override
  protected void runChild(FrameworkMethod method, RunNotifier notifier) {
    Description description = describeChild(method);
    if (isIgnored(method)) {
      notifier.fireTestIgnored(description);
    }
    else {
      runLeaf2(methodBlock(method), description, notifier);
    }
  }

  /**
   * Runs a {@link Statement} that represents a leaf (aka atomic) test. This method can be extended.
   *
   * @param statement statement of test method block.
   * @param description description of test method.
   * @param notifier notifier of running tests.
   */
  protected void runLeaf2(Statement statement, Description description, RunNotifier notifier) {
    runLeaf(statement, description, notifier);
  }

  /**
   * Reflectively gets value of field <code>scheduler</code>.
   *
   * @return The runner scheduler stored in variable.
   */
  protected RunnerScheduler getScheduler() {
    try {
      Class<?> parent = ExtendedBlockJUnit4ClassRunner.class.getSuperclass().getSuperclass();
      Field field = parent.getDeclaredField("scheduler");
      field.setAccessible(true);

      return (RunnerScheduler)field.get(this);
    }
    catch (Exception exc) {
      throw new RuntimeException(Strings.get("exc.test.cannot-obtain-runner-scheduler"), exc);
    }
  }

  /**
   * Reflectively calls parent method <code>getFilteredChildren()</code>.
   *
   * @return Collection of framework method which are returned by call.
   */
  @SuppressWarnings("unchecked")
  protected Collection<FrameworkMethod> getFilteredChildren2() {
    try {
      Class<?> parent = ExtendedBlockJUnit4ClassRunner.class.getSuperclass().getSuperclass();
      Method method = parent.getDeclaredMethod("getFilteredChildren");
      method.setAccessible(true);

      return (Collection<FrameworkMethod>)method.invoke(this);
    }
    catch (Exception exc) {
      throw new RuntimeException(Strings.get("exc.test.cannot-obtain-filtered-children"), exc);
    }
  }

}

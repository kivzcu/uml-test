package cz.zcu.kiv.oop.uml.junit.rules;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import cz.zcu.kiv.oop.uml.junit.runners.model.TestResult;
import cz.zcu.kiv.oop.uml.junit.runners.model.TestsResults;

/**
 * Watcher of results of tests methods.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class TestResultWatcher extends TestWatcher {

  /** Map of tests results for test classes. */
  protected static final Map<String, TestsResults> testsResults = new LinkedHashMap<String, TestsResults>();

  /**
   * Applies watcher rule for specific statement.
   *
   * @param base The {@link Statement} to be modified.
   * @param description description of test class.
   */
  @Override
  public Statement apply(Statement base, Description description) {
    if (testsResults.get(description.getClassName()) == null) {
      testsResults.put(description.getClassName(), new TestsResults());
    }

    return super.apply(base, description);
  }

  /**
   * Invoked in case of succeeded test.
   *
   * @param description description of test method.
   */
  @Override
  protected void succeeded(Description description) {
    TestsResults results = testsResults.get(description.getClassName());
    results.addTestResult(description.getMethodName(), TestResult.SUCCESS);
  }

  /**
   * Invoked in case of failed test.
   *
   * @param throwable error which caused test fail.
   * @param description description of test method.
   */
  @Override
  protected void failed(Throwable throwable, Description description) {
    TestsResults results = testsResults.get(description.getClassName());
    results.addTestResult(description.getMethodName(), TestResult.FAILURE);
  }

  /**
   * Invoked in case of skipped test (in test occurs assumption error).
   *
   * @param exception error which caused test assumption error.
   * @param description description of test method.
   */
  @Override
  protected void skipped(AssumptionViolatedException exception, Description description) {
    TestsResults results = testsResults.get(description.getClassName());
    results.addTestResult(description.getMethodName(), TestResult.ASSUMPTION_FAILURE);

    super.skipped(exception, description);
  }

  /**
   * Returns array of ran tests for specific class.
   *
   * @param clazz class for which will be returned array of ran tests.
   * @return Array of ran tests.
   */
  public String[] getRanTests(Class<?> clazz) {
    return getRanTests(clazz.getName());
  }

  /**
   * Returns array of ran tests for class with specific name.
   *
   * @param className name of class for which will be returned array of ran tests.
   * @return Array of ran tests.
   */
  public String[] getRanTests(String className) {
    TestsResults results = testsResults.get(className);

    return (testsResults == null) ? new String[0] : results.getRanTests();
  }

  /**
   * Returns collection of ran tests for specific class.
   *
   * @param clazz class for which will be returned collection of ran tests.
   * @return Collection of ran tests.
   */
  public Collection<String> getRanTestsCollection(Class<?> clazz) {
    return getRanTestsCollection(clazz.getName());
  }

  /**
   * Returns collection of ran tests for class with specific name.
   *
   * @param className name of class for which will be returned collection of ran tests.
   * @return Collection of ran tests.
   */
  public Collection<String> getRanTestsCollection(String className) {
    TestsResults results = testsResults.get(className);

    return (testsResults == null) ? new HashSet<String>() : results.getRanTestsCollection();
  }

  /**
   * Returns result of ran test method. If test method wasn't run the method returns <code>null</code>.
   *
   * @param clazz class which contains test method.
   * @param testName name of test method.
   * @return Result of ran test or <code>null</code>.
   */
  public TestResult getRanTestResult(Class<?> clazz, String testName) {
    return getRanTestResult(clazz.getName(), testName);
  }

  /**
   * Returns result of ran test method. If test method wasn't run the method returns <code>null</code>.
   *
   * @param className name of class which contains test method.
   * @param testName name of test method.
   * @return Result of ran test or <code>null</code>.
   */
  public TestResult getRanTestResult(String className, String testName) {
    TestsResults results = testsResults.get(className);

    return (testsResults == null) ? null : results.getTestResult(testName);
  }

}

package cz.zcu.kiv.oop.uml.junit.runners.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Contains results of ran test methods. For each test class should be used new instance of this class. Also each test method can
 * have stored only one state which cannot be rewritten. If the result of test method is not stored, the {@link TestResult#NOT_RUN}
 * is returned.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class TestsResults {

  /** Map of ran tests an their results. */
  protected final Map<String, TestResult> testResults = new LinkedHashMap<String, TestResult>();

  /**
   * Adds result of ran test.
   *
   * @param testName name of ran test.
   * @param testResult result of ran test.
   */
  public void addTestResult(String testName, TestResult testResult) {
    if (testResults.get(testName) != null) {
      throw new IllegalStateException("Cannot change test result");
    }

    testResults.put(testName, testResult);
  }

  /**
   * Returns array of ran tests.
   *
   * @return Array of ran tests.
   */
  public String[] getRanTests() {
    Set<String> testNames = testResults.keySet();

    return testNames.toArray(new String[testNames.size()]);
  }

  /**
   * Returns collection of ran tests.
   *
   * @return Collection of ran tests.
   */
  public Collection<String> getRanTestsCollection() {
    return testResults.keySet();
  }

  /**
   * Returns result of ran test method. If test method wasn't run the method returns <code>{@link TestResult#NOT_RUN}</code>.
   *
   * @param testName name of test method.
   * @return Result of ran test or <code>{@link TestResult#NOT_RUN}</code>.
   */
  public TestResult getTestResult(String testName) {
    TestResult result = testResults.get(testName);

    return (result == null) ? TestResult.NOT_RUN : result;
  }

}

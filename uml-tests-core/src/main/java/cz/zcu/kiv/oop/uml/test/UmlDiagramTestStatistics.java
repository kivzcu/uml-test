package cz.zcu.kiv.oop.uml.test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.util.Counter;
import cz.zcu.kiv.oop.util.ImmutablePair;
import cz.zcu.kiv.oop.util.Pair;

/**
 * Statistics of ran tests in {@link UmlDiagramTestBatchRunner}.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlDiagramTestStatistics {

  /** Regular expression for test method name. */
  protected static final String TEST_METHOD_REGEXP = "^t[0-9]{7}_.+$";

  /** Test class for which are made statistics. */
  protected Class<?> testClass;

  /** Counter of ran tests. */
  protected final Counter testsCounter;
  /** Counter of failed ran tests. */
  protected final Counter failedTestsCounter;
  /** Counter of successful ran tests. */
  protected final Counter successfulTestsCounter;

  /** Map for counting of failed methods of tests. */
  protected final Map<String, Counter> failedMethods;

  /** Counter for spent time by tests. */
  protected long spentTime = 0;

  /**
   * Constructs statistics.
   */
  public UmlDiagramTestStatistics() {
    testsCounter = new Counter();
    failedTestsCounter = new Counter();
    successfulTestsCounter = new Counter();

    failedMethods = new HashMap<String, Counter>();
  }

  /**
   * Clears statistics.
   */
  public void clear() {
    testClass = null;

    testsCounter.reset();
    failedTestsCounter.reset();
    successfulTestsCounter.reset();

    failedMethods.clear();

    spentTime = 0;
  }

  /**
   * Returns test class for which are made statistics.
   *
   * @return Test class for which are made statistics.
   */
  public Class<?> getTestClass() {
    return testClass;
  }

  /**
   * Sets test class for which are made statistics.
   *
   * @param testClass test class to set.
   */
  public void setTestClass(Class<?> testClass) {
    this.testClass = testClass;
  }

  /**
   * Increases number (counter) of ran tests.
   */
  public void increaseTestsCounter() {
    testsCounter.increase();
  }

  /**
   * Returns number (count) of ran tests.
   *
   * @return Count of ran tests.
   */
  public long getTestsCounterValue() {
    return testsCounter.getValue();
  }

  /**
   * Increases number (counter) of failed ran tests.
   */
  public void increaseFailedTestsCounter() {
    failedTestsCounter.increase();
  }

  /**
   * Returns number (count) of failed ran tests.
   *
   * @return Count of ran tests.
   */
  public long getFailedTestsCounterValue() {
    return failedTestsCounter.getValue();
  }

  /**
   * Increases number (counter) of successful ran tests.
   */
  public void increaseSuccessfulTestsCounter() {
    successfulTestsCounter.increase();
  }

  /**
   * Returns number (count) of successful ran tests.
   *
   * @return Count of ran tests.
   */
  public long getSuccessfulTestsCounterValue() {
    return successfulTestsCounter.getValue();
  }

  /**
   * Adds spent time by tests.
   *
   * @param time spent time by test to add.
   */
  public void addSpentTime(long time) {
    spentTime += time;
  }

  /**
   * Returns spent time by tests.
   *
   * @return Spent time by tests.
   */
  public long getSpentTime() {
    return spentTime;
  }

  /**
   * Adds failed method (increases counter for specified method).
   *
   * @param methodName name of failed method.
   */
  public void addFailedMethod(String methodName) {
    Counter counter = failedMethods.get(methodName);
    if (counter == null) {
      counter = new Counter();
      failedMethods.put(methodName, counter);
    }

    counter.increase();
  }

  /**
   * Returns list with pairs where each pair contains name of failed method and count of the method fails.
   *
   * @return List with pairs of failed method with counts of method fails.
   */
  protected List<Pair<String, Long>> getFailedMethodsCounts() {
    List<Pair<String, Long>> listOfFailedMethods = new ArrayList<Pair<String, Long>>();
    for (Entry<String, Counter> method : failedMethods.entrySet()) {
      listOfFailedMethods.add(new ImmutablePair<String, Long>(method.getKey(), method.getValue().getValue()));
    }

    return listOfFailedMethods;
  }

  /**
   * Returns how many times failed method with entered name.
   *
   * @param methodName name of method for which will be returned count of fails.
   * @return Count of method fails.
   */
  public long getFailedMethodCount(String methodName) {
    Counter counter = failedMethods.get(methodName);
    if (counter == null) {
      return 0;
    }

    return counter.getValue();
  }

  /**
   * Prints statistics to standard output stream.
   */
  public void print() {
    try {
      print(System.out);
    }
    catch (IOException exc) {
      // This should not happens and if yes... whatever...
    }
  }

  /**
   * Prints statistics into entered output stream.
   *
   * @param os output stream into which will be printed the statistics.
   * @throws IOException if some I/O error occurs during printing.
   */
  public void print(OutputStream os) throws IOException {
    OutputStreamWriter osw = null;
    try {
      osw = new OutputStreamWriter(os);

      long testsCount = getTestsCounterValue();

      println(osw, Strings.get("log.info.stats.1"));
      println(osw, Strings.getFormatted("log.info.stats.2", testsCount, getSuccessfulTestsCounterValue(), getFailedTestsCounterValue()) + '\n');
      println(osw, Strings.get("log.info.stats.3"));

      Locale locale = new Locale("cs", "CZ");
      final Collator collator = Collator.getInstance(locale);
      List<Pair<String, Long>> failedMethodsCounts = getFailedMethodsCounts();
      Collections.sort(failedMethodsCounts, new Comparator<Pair<String, Long>>() {
        @Override
        public int compare(Pair<String, Long> pair1, Pair<String, Long> pair2) {
          return collator.compare(pair1.getKey(), pair2.getKey());
        }
      });

      String prefix = null;
      for (Pair<String, Long> failedMethodCount : failedMethodsCounts) {
        long failsCount = failedMethodCount.getValue();
        double ratio = failsCount / (double)testsCount;

        String methodPrefix = failedMethodCount.getKey().substring(0, 3);
        if (!methodPrefix.equals(prefix)) {
          printLine(osw);
          prefix = methodPrefix;
        }

        println(osw, "%s: %d (%d%%)", failedMethodCount.getKey(), failsCount, Math.round(ratio * 100));
      }
      printLine(osw);

      osw.write('\n' + Strings.get("log.info.tests-without-failures") + '\n');
      Method[] methods = testClass.getMethods();
      Arrays.sort(methods, new Comparator<Method>() {
        @Override
        public int compare(Method method1, Method method2) {
          String name1 = method1.getName();
          String name2 = method2.getName();

          return collator.compare(name1, name2);
        }
      });

      prefix = null;
      for (Method method : methods) {
        if (method.getName().matches(TEST_METHOD_REGEXP)) {
          if (getFailedMethodCount(method.getName()) == 0) {
            String methodPrefix = method.getName().substring(0, 3);
            if (!methodPrefix.equals(prefix)) {
              printLine(osw);
              prefix = methodPrefix;
            }

            osw.write(method.getName() + ": 0\n");
          }
        }
      }
      printLine(osw);

      println(osw, '\n' + Strings.getFormatted("log.info.total-time", spentTime));
    }
    catch (IOException exc) {
      throw new IOException(Strings.get("exc.stats.io-exception"), exc);
    }
    finally {
      if (osw != null) {
        try {
          osw.flush(); // try to flush...
        }
        catch (IOException exc) {
          // whatever...
        }

        try {
          osw.close(); // try to close...
        }
        catch (IOException exc) {
          // whatever...
        }
      }
    }
  }

  /**
   * Prints formated text into specified writer.
   *
   * @param writer writer into which will be printed the formated text.
   * @param format the formated text.
   * @param args parameters for formated text.
   * @throws IOException If some I/O error occurs.
   */
  protected void print(Writer writer, String format, Object... args) throws IOException {
    writer.write(String.format(format, args));
  }

  /**
   * Prints formated text and line breaker into specified writer.
   *
   * @param writer writer into which will be printed the formated text.
   * @param format the formated text.
   * @param args parameters for formated text.
   * @throws IOException If some I/O error occurs.
   */
  protected void println(Writer writer, String format, Object... args) throws IOException {
    writer.write(String.format(format, args));
    writer.write('\n');
  }

  /**
   * Prints line separator (70x character '-') and line breaker into specified writer.
   *
   * @param writer writer into which will be printed the line separator.
   * @throws IOException If some I/O error occurs.
   */
  protected void printLine(Writer writer) throws IOException {
    writer.write("----------------------------------------------------------------------\n");
  }

}

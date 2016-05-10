package cz.zcu.kiv.oop.uml.test;

import java.io.File;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.springframework.util.StringUtils;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uxf.util.UxfFilenameFilter;

/**
 * Batch runner of UML diagram tests which can runs one or more tests in specified directory.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlDiagramTestBatchRunner extends UmlDiagramTestRunner {

  /** Name of property for directory with UXF files whose will be tested. */
  public static final String PROPERTY_DIRECTORY_NAME = "directoryName";
  /** Name of property for boolean property whatever will be shown names of failed tests methods. */
  public static final String PROPERTY_SHOW_FAILED_TESTS_NAMES = "methodNames";

  /** Statistics of tests. */
  protected final UmlDiagramTestStatistics statistics;
  /** Information whether will be shown method names. */
  protected final boolean showFailedTestsNames;

  /**
   * Constructs runner.
   */
  public UmlDiagramTestBatchRunner() {
    statistics = new UmlDiagramTestStatistics();
    showFailedTestsNames = Boolean.parseBoolean(System.getProperty(PROPERTY_SHOW_FAILED_TESTS_NAMES));
  }

  /**
   * Writes a formated error message.
   *
   * @param errorMessage formated error message.
   * @param args arguments for error message.
   */
  @Override
  protected void error(String errorMessage, Object... args) {
    System.out.printf(errorMessage, args);
  }

  /**
   * Prints help.
   */
  @Override
  public void printHelp() {
    String jarFile = getJarName();

    info(Strings.getFormatted("log.info.batch-runner-help.1", PROPERTY_SHOW_FAILED_TESTS_NAMES, jarFile), + '\n' + '\n');
    info(Strings.get("log.info.batch-runner-help.2")  + '\n');
    info(Strings.getFormatted("log.info.batch-runner-help.3", PROPERTY_SHOW_FAILED_TESTS_NAMES), + '\n');
    info(Strings.get("log.info.batch-runner-help.4")  + '\n');
    info(Strings.get("log.info.batch-runner-help.5")  + '\n');
  }

  /**
   * Runs tests from test class.
   */
  @Override
  public void run() {
    clear(); // clears runner

    String directoryName = System.getProperty(PROPERTY_DIRECTORY_NAME);
    if (PRINT_HELP_FILE_NAME.equalsIgnoreCase(directoryName)) {
      printHelp();
      return;
    }

    if (!checkDirectoryName()) {
      return;
    }

    File[] uxfFiles = getUxfFiles();
    if (uxfFiles == null || uxfFiles.length == 0) {
      warn(Strings.get("log.warning.no-ufx-files") + '\n');

      return;
    }

    Class<?> testClass = getTestClass();
    if (testClass == null) {
      return;
    }

    statistics.setTestClass(testClass);

    sortUxfFiles(uxfFiles);
    for (File uxfFile : uxfFiles) {
      System.setProperty(PROPERTY_UXF_FILE_NAME, uxfFile.getAbsolutePath()); // sets next testing UXF file

      info(Strings.getFormatted("log.info.test-file", uxfFile.getName()) + '\n');
      if (checkFileName()) {
        Result result = runTests(testClass);
        if (result != null) {
          printResult(result);
        }

        info("\n");
      }
    }

    if (statistics.getTestsCounterValue() == 0) {
      warn('\n' + Strings.get("log.warning.no-tests") + '\n');

      return;
    }

    info("======================================================================\n\n");
    statistics.print();
  }

  /**
   * Clears previous runs of runner.
   */
  protected void clear() {
    statistics.clear();
  }

  /**
   * Checks if directory with UXF files was set and if exists and is directory.
   *
   * @return <code>true</code> if file name in property {@value #PROPERTY_DIRECTORY_NAME} was set and if exists and is directory;
   *         <code>false</code> otherwise.
   */
  protected boolean checkDirectoryName() {
    String directoryName = System.getProperty(PROPERTY_DIRECTORY_NAME);
    if (!StringUtils.hasText(directoryName)) {
      error(Strings.get("log.error.no-folder") + '\n');

      return false;
    }

    File directory = new File(directoryName);
    if (!directory.exists()) {
      error(Strings.getFormatted("log.error.folder-not-found", directoryName) + '\n');

      return false;
    }

    if (!directory.isDirectory()) {
      error(Strings.getFormatted("log.error.path-is-not-a-folder", directoryName) + '\n');

      return false;
    }

    return true;
  }

  /**
   * Returns array of UXF files in directory with UXF files.
   *
   * @return Array of UXF files.
   */
  protected File[] getUxfFiles() {
    File directory = new File(System.getProperty(PROPERTY_DIRECTORY_NAME));

    return directory.listFiles(UxfFilenameFilter.getInstance());
  }

  /**
   * Sorts array of UXF files.
   *
   * @param uxfFiles array of UXF files which will be sorted.
   */
  protected void sortUxfFiles(File[] uxfFiles) {
    Locale locale = new Locale("cs", "CZ");
    final Collator collator = Collator.getInstance(locale);
    Arrays.sort(uxfFiles, new Comparator<File>() {
      @Override
      public int compare(File file1, File file2) {
        if (file1.getName() == null) {
          return -1;
        }

        return collator.compare(file1.getName(), file2.getName());
      }
    });
  }

  /**
   * Prints result of ran tests.
   *
   * @param testResult result of ran tests.
   */
  @Override
  protected void printResult(Result testResult) {
    if (testResult == null) {
      return;
    }

    statistics.increaseTestsCounter();

    int testsCount = testResult.getRunCount();
    if (testResult.wasSuccessful()) {
      info(Strings.getFormatted("log.info.number-of-tests", testsCount) + '\n');
    } else {
      info(Strings.getFormatted("log.info.number-of-tests.with-failures", testsCount) + '\n');
    }

    if (!testResult.wasSuccessful()) {
      statistics.increaseFailedTestsCounter();

      info(Strings.getFormatted("log.info.number-of-failures", testResult.getFailureCount()), + '\n');
      info(Strings.get("log.info.list-of-failures") + '\n');
      for (Failure failure : testResult.getFailures()) {
        Description testDescription = failure.getDescription();
        String className = testDescription.getClassName();
        String methodName = testDescription.getMethodName();
        if (methodName == null && className != null) {
          StackTraceElement[] stackTrace = failure.getException().getStackTrace();
          for (int i = 0; i < stackTrace.length; i++) {
            StackTraceElement element = stackTrace[i];
            if (className.equals(element.getClassName())) {
              methodName = element.getMethodName();
              break;
            }
          }
        }

        statistics.addFailedMethod(methodName);

        if (showFailedTestsNames) {
          info("%s: ", methodName);
        }

        if (failure.getException() instanceof AssertionError) {
          info("%s\n", failure.getMessage());
        }

        else {
          Throwable throwable = failure.getException();
          info(Strings.getFormatted("log.info.failure", throwable.getClass().getName(), throwable.getMessage()), + '\n');
          if (throwable.getCause() != null) {
            for (Throwable cause = throwable.getCause(); cause != null; cause = cause.getCause()) {
              info(Strings.getFormatted("log.info.caused-by", cause.getClass().getName(), cause.getMessage()), + '\n');
            }
          }
          info("\n");
        }
      }
    }
    else {
      statistics.increaseSuccessfulTestsCounter();
      info(Strings.get("log.info.no-failures") + '\n');
    }

//    info("\nTest trval: %dms\n", testResult.getRunTime());
    statistics.addSpentTime(testResult.getRunTime());
  }
}

package cz.zcu.kiv.oop.uml.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.springframework.util.StringUtils;

import cz.zcu.kiv.oop.Strings;
import cz.zcu.kiv.oop.uxf.util.UxfFilenameFilter;

/**
 * Runner of UML diagram tests.
 *
 * @author Mr.FrAnTA (Michal Dékány)
 */
public class UmlDiagramTestRunner implements Runnable {

  /** Name of property for boolean property whatever will be shown names of failed tests methods. */
  public static final String PROPERTY_SHOW_FAILED_TESTS_NAMES = "methodNames";

  /** Simple name of test class. */
  public static final String TEST_CLASS_NAME = "UmlDiagramTest";

  /** Name of manifest property for test number. */
  public static final String TEST_NUMBER_MANIFEST_PROPERTY = "Test-Number";

  /** Name of property for file name of UXF which will be tested. */
  public static final String PROPERTY_UXF_FILE_NAME = "fileName";

  /** Value of property for UXF file which will prints help. */
  public static final String PRINT_HELP_FILE_NAME = "-help";

  /** Safety fuse for reading test number from manifest. */
  protected static final String DEFAULT_TEST_NUMBER = "DD";

  /** Read test number from manifest. */
  protected String testNumber;

  /** Information whether will be shown method names. */
  protected final boolean showFailedTestsNames;
  
  /** navratova hodnota celeho programu - bude odvozena od vysledku JUnit testu */
  protected static int mainResult = 0;

  /** hodnota mainResult v pripade chyby ve vstupnim souboru */
  public static final int CHYBA_VE_VSTUPNIM_SOUBORU = 1;

  
  /**
   * Constructs runner.
   */
  public UmlDiagramTestRunner() {
    testNumber = getTestNumber();
    showFailedTestsNames = Boolean.parseBoolean(System.getProperty(PROPERTY_SHOW_FAILED_TESTS_NAMES));
  }

  /**
   * Writes a formated error message.
   *
   * @param errorMessage formated error message.
   * @param args arguments for error message.
   */
  protected void error(String errorMessage, Object... args) {
    System.err.printf(errorMessage, args);
  }

  /**
   * Writes a formated warning message.
   *
   * @param warningMessage formated warning message.
   * @param args arguments for warning message.
   */
  protected void warn(String warningMessage, Object... args) {
    System.out.printf(warningMessage, args);
  }

  /**
   * Writes a formated info message.
   *
   * @param infoMessage formated info message.
   * @param args arguments for info message.
   */
  protected void info(String infoMessage, Object... args) {
    System.out.printf(infoMessage, args);
  }

  /**
   * Returns name of JAR file which contains runner.
   *
   * @return Name of JAR file.
   */
  protected String getJarName() {
    try {
      String path = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
      if ("jar".equalsIgnoreCase(StringUtils.getFilenameExtension(path))) {
        return new File(path).getName();
      }
    }
    catch (Exception exc) {
      // nevermind, use safety fuse :D some name of jar
    }

    return "<nazev_baliku>.jar";
  }

  /**
   * Returns number of test from manifest of jar.
   *
   * @return Test number.
   */
  protected String getTestNumber() {
    try {
      Enumeration<URL> resources = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
      while (resources.hasMoreElements()) {
        Manifest manifest = new Manifest(resources.nextElement().openStream());
        Attributes attributes = manifest.getMainAttributes();
        Attributes.Name name = new Attributes.Name(TEST_NUMBER_MANIFEST_PROPERTY);
        if (attributes.containsKey(name)) {
          return attributes.getValue(TEST_NUMBER_MANIFEST_PROPERTY);
        }
      }
    }
    catch (IOException e) {
      // nevermind, use safety fuse :D some test number
    }

    return DEFAULT_TEST_NUMBER;
  }

  /**
   * Prints help.
   */
  public void printHelp() {
    String jarFile = getJarName();
    info(Strings.getFormatted("log.info.runner-help.1", jarFile, testNumber, UxfFilenameFilter.UXF_EXTENSION) + '\n');
    info(Strings.get("log.info.runner-help.2") + '\n');
    info(Strings.getFormatted("log.info.runner-help.3", PROPERTY_SHOW_FAILED_TESTS_NAMES) + '\n');
  }

  /**
   * Runs tests from test class.
   */
  @Override
  public void run() {
    String fileName = System.getProperty(PROPERTY_UXF_FILE_NAME);
    if (PRINT_HELP_FILE_NAME.equalsIgnoreCase(fileName)) {
      printHelp();
      mainResult = CHYBA_VE_VSTUPNIM_SOUBORU;
      return;
    }

    if (checkFileName() == true) {
      Class<?> testClass = getTestClass();
      if (testClass == null) {
        mainResult = CHYBA_VE_VSTUPNIM_SOUBORU;
        return;
      }

      Result result = runTests(testClass);
      printResult(result);
      mainResult = result.getFailureCount();
    }
    else {
      mainResult = CHYBA_VE_VSTUPNIM_SOUBORU;
    }
  }

  /**
   * Checks if testing file was set and if the name of file has correct format. Also tests if the file exists.
   *
   * @return <code>true</code> if file name in property {@value #PROPERTY_UXF_FILE_NAME} was set and has correct format and exists;
   *         <code>false</code> otherwise.
   */
  protected boolean checkFileName() {
    String fileName = System.getProperty(PROPERTY_UXF_FILE_NAME);
    if (!StringUtils.hasText(fileName)) {
      error(Strings.get("log.error.no-input-file") + '\n');
      return false;
    }

    File file = new File(fileName);
    if (!file.exists()) {
      error(Strings.getFormatted("log.error.uxf-file-not-found", fileName) + '\n');
      return false;
    }

    fileName = file.getName();
    String extension = UxfFilenameFilter.UXF_EXTENSION;
    if (!extension.equalsIgnoreCase(StringUtils.getFilenameExtension(fileName))) {
      error(Strings.getFormatted("log.error.wrong-file-extension", extension) + '\n');
      return false;
    }

    if (!fileName.matches("^[0-9]{2}_[AESPKFRZD][0-9]{2}[BN][0-9]{4}[PK]\\..*$")) {
      error(Strings.getFormatted("log.error.wrong-file-name", fileName) + '\n');
      return false;
    }

    if (!DEFAULT_TEST_NUMBER.equals(testNumber) && !fileName.startsWith(testNumber)) {
      error(Strings.get("log.error.wrong-test-number-in-file-name") + '\n');
      return false;
    }

    return true;
  }

  /**
   * Returns full name of test class.
   *
   * @return Full name of test class.
   */
  protected String getTestClassName() {
    Package pckge = getClass().getPackage();

    return pckge.getName() + "." + TEST_CLASS_NAME;
  }

  /**
   * Returns class with tests.
   *
   * @return Class with tests; <code>null</code> if the class was not found.
   */
  protected Class<?> getTestClass() {
    String testClassName = getTestClassName();
    try {
      return Class.forName(testClassName);
    }
    catch (ClassNotFoundException exc) {
      error(Strings.getFormatted("log.error.test-class-not-found", testClassName) + '\n');
    }

    return null;
  }

  /**
   * Runs tests from test class.
   *
   * @return Result of ran tests.
   */
  protected Result runTests(Class<?> testClass) {
    return JUnitCore.runClasses(testClass);
  }

  /**
   * Prints result of ran tests.
   *
   * @param testResult result of ran tests.
   */
  protected void printResult(Result testResult) {
    if (testResult == null) {
      return;
    }

    if (!testResult.wasSuccessful()) {
      int testsCount = testResult.getRunCount();
      error(Strings.getFormatted("log.info.number-of-tests.with-failures", testsCount) + '\n');

      error(Strings.getFormatted("log.info.number-of-failures", testResult.getFailureCount()), + '\n');
      error(Strings.get("log.info.list-of-failures") + '\n');
      for (Failure failure : testResult.getFailures()) {
        Description testDescription = failure.getDescription();
        String methodName = testDescription.getMethodName();
        if (showFailedTestsNames) {
          error("%s: ", methodName);
        }

        if (failure.getException() instanceof AssertionError) {
          error("%s\n", failure.getMessage());
        }
        else {
          Throwable throwable = failure.getException();
          error(Strings.getFormatted("log.info.failure", throwable.getClass().getName(), throwable.getMessage()), + '\n');
          
          if (throwable.getCause() != null) {
            for (Throwable cause = throwable.getCause(); cause != null; cause = cause.getCause()) {
              error(Strings.getFormatted("log.info.caused-by", cause.getClass().getName(), cause.getMessage()), + '\n');
            }
          }
        }
      }
    }
    else {
      info(Strings.getFormatted("log.info.number-of-tests", testResult.getRunCount()) + '\n');
      info(Strings.get("log.info.no-failures") + '\n');
    }
  }

}

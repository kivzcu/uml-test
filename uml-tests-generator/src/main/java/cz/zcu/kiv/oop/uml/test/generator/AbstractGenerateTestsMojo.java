package cz.zcu.kiv.oop.uml.test.generator;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import cz.zcu.kiv.oop.properties.PropertiesProvider;
import cz.zcu.kiv.oop.uml.UmlDiagram;
import cz.zcu.kiv.oop.uml.test.UmlDiagramTestRunner;
import cz.zcu.kiv.oop.uml.test.generator.configuration.UmlTestGeneratorPropertiesProvider;
import cz.zcu.kiv.oop.uml.test.generator.exception.UmlTestGeneratorException;
import cz.zcu.kiv.oop.uml.test.generator.jenesis4java.Jenesis4JavaUmlTestGenerator;
import cz.zcu.kiv.oop.uxf.UxfReaderFactory;
import cz.zcu.kiv.oop.uxf.UxfReaderFactoryImpl;
import cz.zcu.kiv.oop.uxf.exception.UxfReaderException;
import cz.zcu.kiv.oop.uxf.exception.UxfReaderFactoryException;
import cz.zcu.kiv.oop.uxf.reader.UxfReader;

public abstract class AbstractGenerateTestsMojo extends AbstractMojo {

  public static final String LOCK_FILE = ".lock";

  protected static final int TIMESTAMP_READING_ERROR = -1;
  protected static final int TIMESTAMP_DIAGRAM_NOT_CHANGED = -2;

  protected final Log log;

  @Parameter(name = "mavenProject", defaultValue = "${project}", required = true, readonly = true)
  protected MavenProject mavenProject;
  @Parameter(name = "sourceDirectory", defaultValue = "${project.basedir}/src/main/uxf/", required = true)
  protected String sourceDirectory;
  @Parameter(name = "sourceDiagram", required = true)
  protected String sourceDiagram;
  @Parameter(name = "lockFile", defaultValue = "true", required = true)
  protected Boolean lockFile;
  @Parameter(name = "configurationFile", required = false)
  protected File configurationFile;

  public AbstractGenerateTestsMojo() {
    log = getLog();
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    String diagramPath = getSourceDiagramPath();

    long timeStamp = TIMESTAMP_READING_ERROR;
    if (lockFile) {
      // reads time stamp from lock file
      timeStamp = readLockFile(diagramPath);
    }

    if (timeStamp == TIMESTAMP_DIAGRAM_NOT_CHANGED) {
      return;
    }

    log.info("Generating UML tests from digram: " + diagramPath);

    UmlDiagram diagram = null;
    try {
      diagram = readDiagram(diagramPath);
    }
    catch (UxfReaderFactoryException exc) {
      throw new MojoExecutionException("Reader creation failed", exc);
    }
    catch (UxfReaderException exc) {
      throw new MojoExecutionException("Reading UXF file failed", exc);
    }

    try {
      generateTestClass(diagram);
    }
    catch (UmlTestGeneratorException exc) {
      throw new MojoExecutionException("Generating UML tests failed", exc);
    }

    if (lockFile && timeStamp >= 0) {
      createLockFile(timeStamp);
    }

    log.info("Adding generated testing class into build path...");
    addOutputDirectoryToCompileSourceRoot();

    log.info("Generation of UML tests finished successfully...");
  }

  protected String getUnixPath(String path) {
    char pathSeparatorChar = File.separatorChar;
    if (pathSeparatorChar != '/') {
      char[] pathChars = path.toCharArray();
      for (int i = 0; i < pathChars.length; i++) {
        if (pathChars[i] == pathSeparatorChar) {
          pathChars[i] = '/';
        }
      }

      path = new String(pathChars);
    }

    return path;
  }

  protected String getSourceDiagramPath() {
    String sourceDirectory = getUnixPath(this.sourceDirectory);
    String sourceDiagram = getUnixPath(this.sourceDiagram);
    if (!sourceDirectory.endsWith("/")) {
      sourceDirectory += "/";
    }

    return sourceDirectory + sourceDiagram;
  }

  protected abstract File getOutputDirectory();

  protected String getOutputDirectoryAbsolutePath() {
    return getOutputDirectory().getAbsolutePath();
  }

  protected String getLockFilePath() {
    return getOutputDirectoryAbsolutePath() + "/" + LOCK_FILE;
  }

  protected long readLockFile(String diagramPath) {
    File diagramFile = new File(diagramPath);
    if (!diagramFile.exists()) {
      return TIMESTAMP_READING_ERROR;
    }

    long timeStamp = 0;

    log.info("Checking the lock file");
    File lockFile = new File(getLockFilePath());
    if (!lockFile.exists()) {
      log.info("Lock file was not found");

      return diagramFile.lastModified();
    }
    InputStream is = null;
    InputStreamReader isr = null;
    BufferedReader br = null;
    try {
      is = new FileInputStream(lockFile);
      isr = new InputStreamReader(is, Charset.forName("utf-8"));
      br = new BufferedReader(isr);

      timeStamp = Long.parseLong(br.readLine());

      log.info("Read time stamp " + timeStamp + " from lock file");
    }
    catch (Exception exc) {
      log.warn("Cannot read time stamp from the lock file", exc);
    }
    finally {
      closeResource(br);
      closeResource(isr);
      closeResource(is);
    }

    if (timeStamp == diagramFile.lastModified()) {
      log.info("The diagram file was not changed");
      return TIMESTAMP_DIAGRAM_NOT_CHANGED;
    }

    log.info("The diagram file was changed");

    return diagramFile.lastModified();
  }

  protected void createLockFile(long timeStamp) {
    File lockFile = new File(getLockFilePath());
    OutputStream os = null;
    OutputStreamWriter osw = null;
    try {
      os = new FileOutputStream(lockFile);
      osw = new OutputStreamWriter(os);
      log.info("Creating the lock file with time stamp: " + timeStamp);
      osw.write("" + timeStamp);
      osw.flush();
      osw.close();
    }
    catch (IOException exc) {
      log.warn("Cannot create the lock file", exc);
    }
    finally {
      closeResource(osw);
      closeResource(os);
    }
  }

  /**
   * Helper method for closing close-able resources.
   *
   * @param closeable close-able resource which will be closed.
   */
  protected void closeResource(Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      }
      catch (IOException exc) {
        // closing source is not important for re-throwing of exception.
      }
    }
  }

  protected UmlDiagram readDiagram(String uxfFile) throws UxfReaderFactoryException, UxfReaderException {
    log.info("Preparing factory for reader creation...");
    UxfReaderFactory factory = UxfReaderFactoryImpl.getInstance();
    log.info("Creating reader for UXF file: " + uxfFile);
    UxfReader reader = factory.getReader(uxfFile);
    log.info("Reading diagram with reader: " + reader.getClass().getName());

    return reader.readDiagram(uxfFile);
  }

  protected UmlTestGenerator createUmlTestGenerator() {
    UmlTestGenerator generator = new Jenesis4JavaUmlTestGenerator();
    if (configurationFile == null) {
      log.info("No configuration file set. Generator will use default configuration");
    }
    else if (!configurationFile.exists()) {
      log.warn("Configuration file doesn't exist. Generator will use default configuration");
    }
    else {
      try {
        String configurationPath = configurationFile.getAbsolutePath();
        PropertiesProvider propertiesProvider = new UmlTestGeneratorPropertiesProvider(configurationPath);
        generator.setPropertiesProvider(propertiesProvider);
        log.info("Generator will use configuration from file: " + getUnixPath(configurationPath));
      }
      catch (Exception exc) {
        log.error("The initialization of properties provider failed", exc);
      }
    }

    return generator;
  }

  protected void generateTestClass(UmlDiagram diagram) throws UmlTestGeneratorException {
    Package pckge = UmlDiagramTestRunner.class.getPackage();
    String className = UmlDiagramTestRunner.TEST_CLASS_NAME;
    UmlTestClassDescriptor descriptor = new UmlTestClassDescriptor(pckge.getName(), className, getTestsRunnerType());

    log.info("Generating testing class...");
    UmlTestGenerator generator = createUmlTestGenerator();
    generator.generateTest(diagram, descriptor, getOutputDirectoryAbsolutePath());
  }

  protected abstract TestsRunnerType getTestsRunnerType();

  protected abstract void addOutputDirectoryToCompileSourceRoot();

}
